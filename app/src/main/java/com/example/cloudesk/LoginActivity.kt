package com.example.cloudesk

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : BaseActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_GOOGLE_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()
        SeedData.seedIfEmpty(db)

        // --- Single Sign-On (Google) ---
        // NOTE: This needs a real OAuth client registered in Google Cloud/Firebase
        // console (package name + SHA-1 fingerprint) to actually authenticate — see
        // R.string.default_web_client_id. Until that's configured, tapping the button
        // will show Google's own "sign-in failed" error, which is expected.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        findViewById<Button>(R.id.googleSignInButton).setOnClickListener {
            // Sign out of the cached Google account first so the account picker
            // is shown every time and the user can choose which Google account to use.
            googleSignInClient.signOut().addOnCompleteListener {
                startActivityForResult(googleSignInClient.signInIntent, RC_GOOGLE_SIGN_IN)
            }
        }

        val goToRegister = findViewById<TextView>(R.id.goToRegister)
        goToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.forgotPassword).setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Fingerprint / screen-lock sign-in for the account that enabled it in Settings.
        val biometricButton = findViewById<Button>(R.id.biometricLoginButton)
        val biometricUserId = BiometricHelper.getEnabledUserId(this)
        if (biometricUserId != -1 && BiometricHelper.isAvailable(this)) {
            biometricButton.visibility = android.view.View.VISIBLE
            biometricButton.setOnClickListener {
                BiometricHelper.authenticate(
                    this,
                    getString(R.string.biometric_prompt_title),
                    getString(R.string.biometric_prompt_subtitle),
                    onSuccess = {
                        val user = userDao.getUserById(biometricUserId)
                        if (user == null || !user.active) {
                            Toast.makeText(this, getString(R.string.toast_account_deactivated), Toast.LENGTH_SHORT).show()
                        } else {
                            completeLogin(user)
                        }
                    },
                    onFailure = { message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
                )
            }
        }

        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                val user = userDao.getUserByEmail(email)
                if (user != null && PasswordHasher.verify(password, user.password)) {
                    if (!user.active) {
                        Toast.makeText(this@LoginActivity, getString(R.string.toast_account_deactivated), Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    // Quietly upgrade old plain-text passwords to hashed ones.
                    val verifiedUser = if (PasswordHasher.needsUpgrade(user.password)) {
                        val upgraded = user.copy(password = PasswordHasher.hash(password))
                        userDao.updateUser(upgraded)
                        upgraded
                    } else user
                    completeLogin(verifiedUser)
                } else {
                    Toast.makeText(this@LoginActivity, getString(R.string.toast_invalid_credentials), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this@LoginActivity, getString(R.string.toast_enter_credentials), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun completeLogin(user: UserEntity) {
        Toast.makeText(this, getString(R.string.toast_login_successful), Toast.LENGTH_SHORT).show()
        SessionManager.saveSession(this, user.id, "${user.firstName} ${user.lastName}", user.role, user.email)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("USER_ID", user.id)
        intent.putExtra("USER_NAME", "${user.firstName} ${user.lastName}")
        intent.putExtra("USER_ROLE", user.role)
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                handleGoogleSignIn(account)
            } catch (e: ApiException) {
                Toast.makeText(
                    this,
                    "Google Sign-In isn't configured for this app yet (code ${e.statusCode}). " +
                            "Add an OAuth client in Google Cloud/Firebase console to enable it.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun handleGoogleSignIn(account: GoogleSignInAccount?) {
        val email = account?.email
        if (email.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.toast_google_no_email), Toast.LENGTH_SHORT).show()
            return
        }

        val db = AppDatabase.getDatabase(this)
        val userDao = db.userDao()
        var user = userDao.getUserByEmail(email)

        if (user == null) {
            // First time signing in with this Google account — auto-provision a
            // standard user account (an admin can change the role later if needed).
            val newUser = UserEntity(
                firstName = account.givenName ?: "New",
                lastName = account.familyName ?: "User",
                email = email,
                password = PasswordHasher.hash(java.util.UUID.randomUUID().toString()), // SSO users don't use a local password
                department = "General",
                role = "user"
            )
            val newId = userDao.insertUser(newUser)
            user = newUser.copy(id = newId.toInt())
        }

        if (!user.active) {
            Toast.makeText(this, getString(R.string.toast_account_deactivated), Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(this, getString(R.string.toast_signed_in_as, user.email), Toast.LENGTH_SHORT).show()
        SessionManager.saveSession(this, user.id, "${user.firstName} ${user.lastName}", user.role, user.email)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("USER_ID", user.id)
        intent.putExtra("USER_NAME", "${user.firstName} ${user.lastName}")
        intent.putExtra("USER_ROLE", user.role)
        startActivity(intent)
        finish()
    }
}