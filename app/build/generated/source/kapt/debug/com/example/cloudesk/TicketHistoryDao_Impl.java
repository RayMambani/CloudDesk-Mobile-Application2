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
public final class TicketHistoryDao_Impl implements TicketHistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TicketHistoryEntity> __insertionAdapterOfTicketHistoryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public TicketHistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTicketHistoryEntity = new EntityInsertionAdapter<TicketHistoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `ticket_history` (`id`,`ticketId`,`actorName`,`field`,`oldValue`,`newValue`,`createdAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TicketHistoryEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getTicketId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTicketId());
        }
        if (entity.getActorName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getActorName());
        }
        if (entity.getField() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getField());
        }
        if (entity.getOldValue() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getOldValue());
        }
        if (entity.getNewValue() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNewValue());
        }
        if (entity.getCreatedAt() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCreatedAt());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM ticket_history";
        return _query;
      }
    };
  }

  @Override
  public void insert(final TicketHistoryEntity entry) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTicketHistoryEntity.insert(entry);
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
  public List<TicketHistoryEntity> getForTicket(final String ticketId) {
    final String _sql = "SELECT * FROM ticket_history WHERE ticketId = ? ORDER BY id DESC";
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
      final int _cursorIndexOfActorName = CursorUtil.getColumnIndexOrThrow(_cursor, "actorName");
      final int _cursorIndexOfField = CursorUtil.getColumnIndexOrThrow(_cursor, "field");
      final int _cursorIndexOfOldValue = CursorUtil.getColumnIndexOrThrow(_cursor, "oldValue");
      final int _cursorIndexOfNewValue = CursorUtil.getColumnIndexOrThrow(_cursor, "newValue");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final List<TicketHistoryEntity> _result = new ArrayList<TicketHistoryEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TicketHistoryEntity _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpTicketId;
        if (_cursor.isNull(_cursorIndexOfTicketId)) {
          _tmpTicketId = null;
        } else {
          _tmpTicketId = _cursor.getString(_cursorIndexOfTicketId);
        }
        final String _tmpActorName;
        if (_cursor.isNull(_cursorIndexOfActorName)) {
          _tmpActorName = null;
        } else {
          _tmpActorName = _cursor.getString(_cursorIndexOfActorName);
        }
        final String _tmpField;
        if (_cursor.isNull(_cursorIndexOfField)) {
          _tmpField = null;
        } else {
          _tmpField = _cursor.getString(_cursorIndexOfField);
        }
        final String _tmpOldValue;
        if (_cursor.isNull(_cursorIndexOfOldValue)) {
          _tmpOldValue = null;
        } else {
          _tmpOldValue = _cursor.getString(_cursorIndexOfOldValue);
        }
        final String _tmpNewValue;
        if (_cursor.isNull(_cursorIndexOfNewValue)) {
          _tmpNewValue = null;
        } else {
          _tmpNewValue = _cursor.getString(_cursorIndexOfNewValue);
        }
        final String _tmpCreatedAt;
        if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
          _tmpCreatedAt = null;
        } else {
          _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
        }
        _item = new TicketHistoryEntity(_tmpId,_tmpTicketId,_tmpActorName,_tmpField,_tmpOldValue,_tmpNewValue,_tmpCreatedAt);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int count() {
    final String _sql = "SELECT COUNT(*) FROM ticket_history";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if (_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
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
