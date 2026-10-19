package com.example.cloudesk;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
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
public final class CommentDao_Impl implements CommentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CommentEntity> __insertionAdapterOfCommentEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CommentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCommentEntity = new EntityInsertionAdapter<CommentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `comments` (`id`,`ticketId`,`userId`,`userName`,`content`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CommentEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTicketId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTicketId());
        }
        statement.bindLong(3, entity.getUserId());
        if (entity.getUserName() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getUserName());
        }
        if (entity.getContent() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getContent());
        }
        if (entity.getCreated_at() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCreated_at());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM comments";
        return _query;
      }
    };
  }

  @Override
  public void insertComment(final CommentEntity comment) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCommentEntity.insert(comment);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAll() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
    try {
      __db.beginTransaction();
      try {
        _stmt.executeUpdateDelete();
        __db.setTransactionSuccessful();
      } finally {
        __db.endTransaction();
      }
    } finally {
      __preparedStmtOfDeleteAll.release(_stmt);
    }
  }

  @Override
  public List<CommentEntity> getCommentsForTicket(final String ticketId) {
    final String _sql = "SELECT * FROM comments WHERE ticketId = ? ORDER BY created_at ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ticketId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ticketId);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTicketId = CursorUtil.getColumnIndexOrThrow(_cursor, "ticketId");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfUserName = CursorUtil.getColumnIndexOrThrow(_cursor, "userName");
      final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final List<CommentEntity> _result = new ArrayList<CommentEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final CommentEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpTicketId;
        if (_cursor.isNull(_cursorIndexOfTicketId)) {
          _tmpTicketId = null;
        } else {
          _tmpTicketId = _cursor.getString(_cursorIndexOfTicketId);
        }
        final int _tmpUserId;
        _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        final String _tmpUserName;
        if (_cursor.isNull(_cursorIndexOfUserName)) {
          _tmpUserName = null;
        } else {
          _tmpUserName = _cursor.getString(_cursorIndexOfUserName);
        }
        final String _tmpContent;
        if (_cursor.isNull(_cursorIndexOfContent)) {
          _tmpContent = null;
        } else {
          _tmpContent = _cursor.getString(_cursorIndexOfContent);
        }
        final String _tmpCreated_at;
        if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
          _tmpCreated_at = null;
        } else {
          _tmpCreated_at = _cursor.getString(_cursorIndexOfCreatedAt);
        }
        _item = new CommentEntity(_tmpId,_tmpTicketId,_tmpUserId,_tmpUserName,_tmpContent,_tmpCreated_at);
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
