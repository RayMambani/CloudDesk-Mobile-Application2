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
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TicketDao_Impl implements TicketDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<TicketEntity> __insertionAdapterOfTicketEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public TicketDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTicketEntity = new EntityInsertionAdapter<TicketEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tickets` (`id`,`title`,`description`,`status`,`priority`,`category`,`created_at`,`userId`,`assignedTo`,`attachmentUri`,`attachmentName`,`resolvedAt`,`rating`,`ratingComment`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final TicketEntity entity) {
        if (entity.getId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getId());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getTitle());
        }
        if (entity.getDescription() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getDescription());
        }
        if (entity.getStatus() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getStatus());
        }
        if (entity.getPriority() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getPriority());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getCategory());
        }
        if (entity.getCreated_at() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getCreated_at());
        }
        statement.bindLong(8, entity.getUserId());
        if (entity.getAssignedTo() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getAssignedTo());
        }
        if (entity.getAttachmentUri() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getAttachmentUri());
        }
        if (entity.getAttachmentName() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getAttachmentName());
        }
        if (entity.getResolvedAt() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getResolvedAt());
        }
        if (entity.getRating() == null) {
          statement.bindNull(13);
        } else {
          statement.bindLong(13, entity.getRating());
        }
        if (entity.getRatingComment() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getRatingComment());
        }
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM tickets";
        return _query;
      }
    };
  }

  @Override
  public void insertTickets(final List<TicketEntity> tickets) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTicketEntity.insert(tickets);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertTicket(final TicketEntity ticket) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTicketEntity.insert(ticket);
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
  public List<TicketEntity> getAllTickets() {
    final String _sql = "SELECT * FROM tickets ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assignedTo");
      final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
      final int _cursorIndexOfAttachmentName = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentName");
      final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfRatingComment = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingComment");
      final List<TicketEntity> _result = new ArrayList<TicketEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final TicketEntity _item;
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final String _tmpPriority;
        if (_cursor.isNull(_cursorIndexOfPriority)) {
          _tmpPriority = null;
        } else {
          _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final String _tmpCreated_at;
        if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
          _tmpCreated_at = null;
        } else {
          _tmpCreated_at = _cursor.getString(_cursorIndexOfCreatedAt);
        }
        final int _tmpUserId;
        _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        final String _tmpAssignedTo;
        if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
          _tmpAssignedTo = null;
        } else {
          _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
        }
        final String _tmpAttachmentUri;
        if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
          _tmpAttachmentUri = null;
        } else {
          _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
        }
        final String _tmpAttachmentName;
        if (_cursor.isNull(_cursorIndexOfAttachmentName)) {
          _tmpAttachmentName = null;
        } else {
          _tmpAttachmentName = _cursor.getString(_cursorIndexOfAttachmentName);
        }
        final String _tmpResolvedAt;
        if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
          _tmpResolvedAt = null;
        } else {
          _tmpResolvedAt = _cursor.getString(_cursorIndexOfResolvedAt);
        }
        final Integer _tmpRating;
        if (_cursor.isNull(_cursorIndexOfRating)) {
          _tmpRating = null;
        } else {
          _tmpRating = _cursor.getInt(_cursorIndexOfRating);
        }
        final String _tmpRatingComment;
        if (_cursor.isNull(_cursorIndexOfRatingComment)) {
          _tmpRatingComment = null;
        } else {
          _tmpRatingComment = _cursor.getString(_cursorIndexOfRatingComment);
        }
        _item = new TicketEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpStatus,_tmpPriority,_tmpCategory,_tmpCreated_at,_tmpUserId,_tmpAssignedTo,_tmpAttachmentUri,_tmpAttachmentName,_tmpResolvedAt,_tmpRating,_tmpRatingComment);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public TicketEntity getTicketById(final String id) {
    final String _sql = "SELECT * FROM tickets WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (id == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, id);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
      final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
      final int _cursorIndexOfAssignedTo = CursorUtil.getColumnIndexOrThrow(_cursor, "assignedTo");
      final int _cursorIndexOfAttachmentUri = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentUri");
      final int _cursorIndexOfAttachmentName = CursorUtil.getColumnIndexOrThrow(_cursor, "attachmentName");
      final int _cursorIndexOfResolvedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "resolvedAt");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfRatingComment = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingComment");
      final TicketEntity _result;
      if (_cursor.moveToFirst()) {
        final String _tmpId;
        if (_cursor.isNull(_cursorIndexOfId)) {
          _tmpId = null;
        } else {
          _tmpId = _cursor.getString(_cursorIndexOfId);
        }
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpDescription;
        if (_cursor.isNull(_cursorIndexOfDescription)) {
          _tmpDescription = null;
        } else {
          _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        }
        final String _tmpStatus;
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _tmpStatus = null;
        } else {
          _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        }
        final String _tmpPriority;
        if (_cursor.isNull(_cursorIndexOfPriority)) {
          _tmpPriority = null;
        } else {
          _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
        }
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final String _tmpCreated_at;
        if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
          _tmpCreated_at = null;
        } else {
          _tmpCreated_at = _cursor.getString(_cursorIndexOfCreatedAt);
        }
        final int _tmpUserId;
        _tmpUserId = _cursor.getInt(_cursorIndexOfUserId);
        final String _tmpAssignedTo;
        if (_cursor.isNull(_cursorIndexOfAssignedTo)) {
          _tmpAssignedTo = null;
        } else {
          _tmpAssignedTo = _cursor.getString(_cursorIndexOfAssignedTo);
        }
        final String _tmpAttachmentUri;
        if (_cursor.isNull(_cursorIndexOfAttachmentUri)) {
          _tmpAttachmentUri = null;
        } else {
          _tmpAttachmentUri = _cursor.getString(_cursorIndexOfAttachmentUri);
        }
        final String _tmpAttachmentName;
        if (_cursor.isNull(_cursorIndexOfAttachmentName)) {
          _tmpAttachmentName = null;
        } else {
          _tmpAttachmentName = _cursor.getString(_cursorIndexOfAttachmentName);
        }
        final String _tmpResolvedAt;
        if (_cursor.isNull(_cursorIndexOfResolvedAt)) {
          _tmpResolvedAt = null;
        } else {
          _tmpResolvedAt = _cursor.getString(_cursorIndexOfResolvedAt);
        }
        final Integer _tmpRating;
        if (_cursor.isNull(_cursorIndexOfRating)) {
          _tmpRating = null;
        } else {
          _tmpRating = _cursor.getInt(_cursorIndexOfRating);
        }
        final String _tmpRatingComment;
        if (_cursor.isNull(_cursorIndexOfRatingComment)) {
          _tmpRatingComment = null;
        } else {
          _tmpRatingComment = _cursor.getString(_cursorIndexOfRatingComment);
        }
        _result = new TicketEntity(_tmpId,_tmpTitle,_tmpDescription,_tmpStatus,_tmpPriority,_tmpCategory,_tmpCreated_at,_tmpUserId,_tmpAssignedTo,_tmpAttachmentUri,_tmpAttachmentName,_tmpResolvedAt,_tmpRating,_tmpRatingComment);
      } else {
        _result = null;
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
