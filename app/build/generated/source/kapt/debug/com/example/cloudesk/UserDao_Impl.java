package com.example.cloudesk;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<UserEntity> __insertionAdapterOfUserEntity;

  private final EntityDeletionOrUpdateAdapter<UserEntity> __updateAdapterOfUserEntity;

  public UserDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserEntity = new EntityInsertionAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `users` (`id`,`firstName`,`lastName`,`email`,`password`,`department`,`role`,`phone`,`active`,`securityQuestion`,`securityAnswerHash`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getFirstName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFirstName());
        }
        if (entity.getLastName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLastName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getPassword() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPassword());
        }
        if (entity.getDepartment() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDepartment());
        }
        if (entity.getRole() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRole());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPhone());
        }
        final int _tmp = entity.getActive() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getSecurityQuestion() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getSecurityQuestion());
        }
        if (entity.getSecurityAnswerHash() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getSecurityAnswerHash());
        }
      }
    };
    this.__updateAdapterOfUserEntity = new EntityDeletionOrUpdateAdapter<UserEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `users` SET `id` = ?,`firstName` = ?,`lastName` = ?,`email` = ?,`password` = ?,`department` = ?,`role` = ?,`phone` = ?,`active` = ?,`securityQuestion` = ?,`securityAnswerHash` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final UserEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getFirstName() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFirstName());
        }
        if (entity.getLastName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getLastName());
        }
        if (entity.getEmail() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getEmail());
        }
        if (entity.getPassword() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPassword());
        }
        if (entity.getDepartment() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getDepartment());
        }
        if (entity.getRole() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRole());
        }
        if (entity.getPhone() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getPhone());
        }
        final int _tmp = entity.getActive() ? 1 : 0;
        statement.bindLong(9, _tmp);
        if (entity.getSecurityQuestion() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getSecurityQuestion());
        }
        if (entity.getSecurityAnswerHash() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getSecurityAnswerHash());
        }
        statement.bindLong(12, entity.getId());
      }
    };
  }

  @Override
  public long insertUser(final UserEntity user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfUserEntity.insertAndReturnId(user);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateUser(final UserEntity user) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfUserEntity.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public UserEntity getUserByEmail(final String email) {
    final String _sql = "SELECT * FROM users WHERE email = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (email == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, email);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
      final int _cursorIndexOfSecurityQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "securityQuestion");
      final int _cursorIndexOfSecurityAnswerHash = CursorUtil.getColumnIndexOrThrow(_cursor, "securityAnswerHash");
      final UserEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpFirstName;
        if (_cursor.isNull(_cursorIndexOfFirstName)) {
          _tmpFirstName = null;
        } else {
          _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        }
        final String _tmpLastName;
        if (_cursor.isNull(_cursorIndexOfLastName)) {
          _tmpLastName = null;
        } else {
          _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        }
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        final boolean _tmpActive;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfActive);
        _tmpActive = _tmp != 0;
        final String _tmpSecurityQuestion;
        if (_cursor.isNull(_cursorIndexOfSecurityQuestion)) {
          _tmpSecurityQuestion = null;
        } else {
          _tmpSecurityQuestion = _cursor.getString(_cursorIndexOfSecurityQuestion);
        }
        final String _tmpSecurityAnswerHash;
        if (_cursor.isNull(_cursorIndexOfSecurityAnswerHash)) {
          _tmpSecurityAnswerHash = null;
        } else {
          _tmpSecurityAnswerHash = _cursor.getString(_cursorIndexOfSecurityAnswerHash);
        }
        _result = new UserEntity(_tmpId,_tmpFirstName,_tmpLastName,_tmpEmail,_tmpPassword,_tmpDepartment,_tmpRole,_tmpPhone,_tmpActive,_tmpSecurityQuestion,_tmpSecurityAnswerHash);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public UserEntity getUserById(final int id) {
    final String _sql = "SELECT * FROM users WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
      final int _cursorIndexOfSecurityQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "securityQuestion");
      final int _cursorIndexOfSecurityAnswerHash = CursorUtil.getColumnIndexOrThrow(_cursor, "securityAnswerHash");
      final UserEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpFirstName;
        if (_cursor.isNull(_cursorIndexOfFirstName)) {
          _tmpFirstName = null;
        } else {
          _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        }
        final String _tmpLastName;
        if (_cursor.isNull(_cursorIndexOfLastName)) {
          _tmpLastName = null;
        } else {
          _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        }
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        final boolean _tmpActive;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfActive);
        _tmpActive = _tmp != 0;
        final String _tmpSecurityQuestion;
        if (_cursor.isNull(_cursorIndexOfSecurityQuestion)) {
          _tmpSecurityQuestion = null;
        } else {
          _tmpSecurityQuestion = _cursor.getString(_cursorIndexOfSecurityQuestion);
        }
        final String _tmpSecurityAnswerHash;
        if (_cursor.isNull(_cursorIndexOfSecurityAnswerHash)) {
          _tmpSecurityAnswerHash = null;
        } else {
          _tmpSecurityAnswerHash = _cursor.getString(_cursorIndexOfSecurityAnswerHash);
        }
        _result = new UserEntity(_tmpId,_tmpFirstName,_tmpLastName,_tmpEmail,_tmpPassword,_tmpDepartment,_tmpRole,_tmpPhone,_tmpActive,_tmpSecurityQuestion,_tmpSecurityAnswerHash);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<UserEntity> getUsersByRole(final String role) {
    final String _sql = "SELECT * FROM users WHERE role = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (role == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, role);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
      final int _cursorIndexOfSecurityQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "securityQuestion");
      final int _cursorIndexOfSecurityAnswerHash = CursorUtil.getColumnIndexOrThrow(_cursor, "securityAnswerHash");
      final List<UserEntity> _result = new ArrayList<UserEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final UserEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpFirstName;
        if (_cursor.isNull(_cursorIndexOfFirstName)) {
          _tmpFirstName = null;
        } else {
          _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        }
        final String _tmpLastName;
        if (_cursor.isNull(_cursorIndexOfLastName)) {
          _tmpLastName = null;
        } else {
          _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        }
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        final boolean _tmpActive;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfActive);
        _tmpActive = _tmp != 0;
        final String _tmpSecurityQuestion;
        if (_cursor.isNull(_cursorIndexOfSecurityQuestion)) {
          _tmpSecurityQuestion = null;
        } else {
          _tmpSecurityQuestion = _cursor.getString(_cursorIndexOfSecurityQuestion);
        }
        final String _tmpSecurityAnswerHash;
        if (_cursor.isNull(_cursorIndexOfSecurityAnswerHash)) {
          _tmpSecurityAnswerHash = null;
        } else {
          _tmpSecurityAnswerHash = _cursor.getString(_cursorIndexOfSecurityAnswerHash);
        }
        _item = new UserEntity(_tmpId,_tmpFirstName,_tmpLastName,_tmpEmail,_tmpPassword,_tmpDepartment,_tmpRole,_tmpPhone,_tmpActive,_tmpSecurityQuestion,_tmpSecurityAnswerHash);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<UserEntity> getAllUsers() {
    final String _sql = "SELECT * FROM users ORDER BY role, firstName";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFirstName = CursorUtil.getColumnIndexOrThrow(_cursor, "firstName");
      final int _cursorIndexOfLastName = CursorUtil.getColumnIndexOrThrow(_cursor, "lastName");
      final int _cursorIndexOfEmail = CursorUtil.getColumnIndexOrThrow(_cursor, "email");
      final int _cursorIndexOfPassword = CursorUtil.getColumnIndexOrThrow(_cursor, "password");
      final int _cursorIndexOfDepartment = CursorUtil.getColumnIndexOrThrow(_cursor, "department");
      final int _cursorIndexOfRole = CursorUtil.getColumnIndexOrThrow(_cursor, "role");
      final int _cursorIndexOfPhone = CursorUtil.getColumnIndexOrThrow(_cursor, "phone");
      final int _cursorIndexOfActive = CursorUtil.getColumnIndexOrThrow(_cursor, "active");
      final int _cursorIndexOfSecurityQuestion = CursorUtil.getColumnIndexOrThrow(_cursor, "securityQuestion");
      final int _cursorIndexOfSecurityAnswerHash = CursorUtil.getColumnIndexOrThrow(_cursor, "securityAnswerHash");
      final List<UserEntity> _result = new ArrayList<UserEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final UserEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpFirstName;
        if (_cursor.isNull(_cursorIndexOfFirstName)) {
          _tmpFirstName = null;
        } else {
          _tmpFirstName = _cursor.getString(_cursorIndexOfFirstName);
        }
        final String _tmpLastName;
        if (_cursor.isNull(_cursorIndexOfLastName)) {
          _tmpLastName = null;
        } else {
          _tmpLastName = _cursor.getString(_cursorIndexOfLastName);
        }
        final String _tmpEmail;
        if (_cursor.isNull(_cursorIndexOfEmail)) {
          _tmpEmail = null;
        } else {
          _tmpEmail = _cursor.getString(_cursorIndexOfEmail);
        }
        final String _tmpPassword;
        if (_cursor.isNull(_cursorIndexOfPassword)) {
          _tmpPassword = null;
        } else {
          _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        }
        final String _tmpDepartment;
        if (_cursor.isNull(_cursorIndexOfDepartment)) {
          _tmpDepartment = null;
        } else {
          _tmpDepartment = _cursor.getString(_cursorIndexOfDepartment);
        }
        final String _tmpRole;
        if (_cursor.isNull(_cursorIndexOfRole)) {
          _tmpRole = null;
        } else {
          _tmpRole = _cursor.getString(_cursorIndexOfRole);
        }
        final String _tmpPhone;
        if (_cursor.isNull(_cursorIndexOfPhone)) {
          _tmpPhone = null;
        } else {
          _tmpPhone = _cursor.getString(_cursorIndexOfPhone);
        }
        final boolean _tmpActive;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfActive);
        _tmpActive = _tmp != 0;
        final String _tmpSecurityQuestion;
        if (_cursor.isNull(_cursorIndexOfSecurityQuestion)) {
          _tmpSecurityQuestion = null;
        } else {
          _tmpSecurityQuestion = _cursor.getString(_cursorIndexOfSecurityQuestion);
        }
        final String _tmpSecurityAnswerHash;
        if (_cursor.isNull(_cursorIndexOfSecurityAnswerHash)) {
          _tmpSecurityAnswerHash = null;
        } else {
          _tmpSecurityAnswerHash = _cursor.getString(_cursorIndexOfSecurityAnswerHash);
        }
        _item = new UserEntity(_tmpId,_tmpFirstName,_tmpLastName,_tmpEmail,_tmpPassword,_tmpDepartment,_tmpRole,_tmpPhone,_tmpActive,_tmpSecurityQuestion,_tmpSecurityAnswerHash);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
