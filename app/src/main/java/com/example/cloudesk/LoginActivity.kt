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
import org.json.JSONObject

/**
 * Sign-in screen. Passwords are checked by the ONLINE backend (api.php) — the phone never
 * holds a password hash. A successful sign-in returns a login token plus the user's data,
 * which is cached locally (see ServerSync).
 */
class LoginActivity : BaseActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_GOOGLE_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val usernameEditText = findViewById<EditText>(R.id.username)
        val passwordEditText = findViewById<EditText>(R.id.password)
        val loginButton = findViewById<Button>(R.id.loginButton)

        // --- Single Sign-On (Google) ---
        // The app asks Google for an ID token for OUR web client; the server verifies that
        // token with Google before signing the person in. Needs ApiConfig.GOOGLE_WEB_CLIENT_ID.
        val gsoBuilder = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
        if (ApiConfig.isGoogleConfigured()) {
            gsoBuilder.requestIdToken(ApiConfig.GOOGLE_WEB_CLIENT_ID)
        }
        googleSignInClient = GoogleSignIn.getClient(this, gsoBuilder.build())

        findViewById<Button>(R.id.googleSignInButton).setOnClickListener {
            if (!ApiConfig.isGoogleConfigured()) {
                Toast.makeText(this, getString(R.string.toast_google_failed), Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            // Sign out of the cached Google account first so the account picker
            // is shown every time and the user can choose which Google account to use.
            googleSignInClient.signOut().addOnCompleteListener {
                startActivityForResult(googleSignInClient.signInIntent, RC_GOOGLE_SIGN_IN)
            }
        }

        findViewById<TextView>(R.id.goToRegister).setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        findViewById<TextView>(R.id.forgotPassword).setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Fingerprint / screen-lock sign-in for the account that enabled it in Settings.
        val biometricButton = findViewById<Button>(R.id.biometricLoginButton)
        val deviceToken = BiometricHelper.getDeviceToken(this)
        if (BiometricHelper.getEnabledUserId(this) != -1 && deviceToken != null && BiometricHelper.isAvailable(this)) {
            biometricButton.visibility = android.view.View.VISIBLE
            biometricButton.setOnClickListener {
                BiometricHelper.authenticate(
                    this,
                    getString(R.string.biometric_prompt_title),
                    getString(R.string.biometric_prompt_subtitle),
                    onSuccess = {
                        val body = JSONObject().put("deviceToken", deviceToken)
                        ApiClient.callAsync(this, "biometric_login", body, authenticated = false) { r ->
                            if (r.ok) {
                                finishSignIn(r)
                            } else {
                                if (r.error == "unauthorized") BiometricHelper.disable(this) // token was revoked
                                Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    onFailure = { message -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
                )
            }
        }

        loginButton.setOnClickListener {
            val email = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, getString(R.string.toast_enter_credentials), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            loginButton.isEnabled = false
            val body = JSONObject().put("email", email).put("password", password)
            ApiClient.callAsync(this, "login", body, authenticated = false) { r ->
                loginButton.isEnabled = true
                if (r.ok) finishSignIn(r)
                else Toast.makeText(this@LoginActivity, ApiClient.errorText(this@LoginActivity, r), Toast.LENGTH_SHORT).show()
            }
        }
    }

    /** Saves the login token, caches the user's data, and opens the dashboard. */
    private fun finishSignIn(r: ApiClient.Result) {
        val json = r.json ?: return
        SessionManager.saveToken(this, json.getString("token"))
        ServerSync.apply(this, json)
        val user = ServerSync.parseUser(json.getJSONObject("me"))

        Toast.makeText(this, getString(R.string.toast_signed_in_as, user.email), Toast.LENGTH_SHORT).show()
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
                handleGoogleSignIn(task.getResult(ApiException::class.java))
            } catch (e: ApiException) {
                Toast.makeText(
                    this,
                    "Google Sign-In failed (code ${e.statusCode}). Check the OAuth client in Google Cloud Console.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun handleGoogleSignIn(account: GoogleSignInAccount?) {
        val idToken = account?.idToken
        if (idToken.isNullOrBlank()) {
            Toast.makeText(this, getString(R.string.toast_google_failed), Toast.LENGTH_LONG).show()
            return
        }
        // The server verifies the token with Google; a new Google account becomes a normal user.
        ApiClient.callAsync(this, "google_login", JSONObject().put("idToken", idToken), authenticated = false) { r ->
            if (r.ok) finishSignIn(r)
            else Toast.makeText(this, ApiClient.errorText(this, r), Toast.LENGTH_LONG).show()
        }
    }
}
