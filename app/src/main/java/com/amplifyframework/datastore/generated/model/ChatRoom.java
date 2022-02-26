package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the ChatRoom type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "ChatRooms", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class ChatRoom implements Model {
  public static final QueryField ID = field("ChatRoom", "id");
  public static final QueryField OTHER_USER_ID = field("ChatRoom", "otherUserId");
  public static final QueryField OTHER_USER_NAME = field("ChatRoom", "otherUserName");
  public static final QueryField USER_ID = field("ChatRoom", "userID");
  public static final QueryField CHAT_ROOM_ID = field("ChatRoom", "chatRoomId");
  public static final QueryField USER_CHAT_ROOMS_ID = field("ChatRoom", "userChatRoomsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String otherUserId;
  private final @ModelField(targetType="String") String otherUserName;
  private final @ModelField(targetType="ID") String userID;
  private final @ModelField(targetType="String") String chatRoomId;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String userChatRoomsId;
  public String getId() {
      return id;
  }
  
  public String getOtherUserId() {
      return otherUserId;
  }
  
  public String getOtherUserName() {
      return otherUserName;
  }
  
  public String getUserId() {
      return userID;
  }
  
  public String getChatRoomId() {
      return chatRoomId;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getUserChatRoomsId() {
      return userChatRoomsId;
  }
  
  private ChatRoom(String id, String otherUserId, String otherUserName, String userID, String chatRoomId, String userChatRoomsId) {
    this.id = id;
    this.otherUserId = otherUserId;
    this.otherUserName = otherUserName;
    this.userID = userID;
    this.chatRoomId = chatRoomId;
    this.userChatRoomsId = userChatRoomsId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      ChatRoom chatRoom = (ChatRoom) obj;
      return ObjectsCompat.equals(getId(), chatRoom.getId()) &&
              ObjectsCompat.equals(getOtherUserId(), chatRoom.getOtherUserId()) &&
              ObjectsCompat.equals(getOtherUserName(), chatRoom.getOtherUserName()) &&
              ObjectsCompat.equals(getUserId(), chatRoom.getUserId()) &&
              ObjectsCompat.equals(getChatRoomId(), chatRoom.getChatRoomId()) &&
              ObjectsCompat.equals(getCreatedAt(), chatRoom.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), chatRoom.getUpdatedAt()) &&
              ObjectsCompat.equals(getUserChatRoomsId(), chatRoom.getUserChatRoomsId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getOtherUserId())
      .append(getOtherUserName())
      .append(getUserId())
      .append(getChatRoomId())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getUserChatRoomsId())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("ChatRoom {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("otherUserId=" + String.valueOf(getOtherUserId()) + ", ")
      .append("otherUserName=" + String.valueOf(getOtherUserName()) + ", ")
      .append("userID=" + String.valueOf(getUserId()) + ", ")
      .append("chatRoomId=" + String.valueOf(getChatRoomId()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("userChatRoomsId=" + String.valueOf(getUserChatRoomsId()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static ChatRoom justId(String id) {
    return new ChatRoom(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      otherUserId,
      otherUserName,
      userID,
      chatRoomId,
      userChatRoomsId);
  }
  public interface BuildStep {
    ChatRoom build();
    BuildStep id(String id);
    BuildStep otherUserId(String otherUserId);
    BuildStep otherUserName(String otherUserName);
    BuildStep userId(String userId);
    BuildStep chatRoomId(String chatRoomId);
    BuildStep userChatRoomsId(String userChatRoomsId);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String otherUserId;
    private String otherUserName;
    private String userID;
    private String chatRoomId;
    private String userChatRoomsId;
    @Override
     public ChatRoom build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new ChatRoom(
          id,
          otherUserId,
          otherUserName,
          userID,
          chatRoomId,
          userChatRoomsId);
    }
    
    @Override
     public BuildStep otherUserId(String otherUserId) {
        this.otherUserId = otherUserId;
        return this;
    }
    
    @Override
     public BuildStep otherUserName(String otherUserName) {
        this.otherUserName = otherUserName;
        return this;
    }
    
    @Override
     public BuildStep userId(String userId) {
        this.userID = userId;
        return this;
    }
    
    @Override
     public BuildStep chatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
        return this;
    }
    
    @Override
     public BuildStep userChatRoomsId(String userChatRoomsId) {
        this.userChatRoomsId = userChatRoomsId;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String otherUserId, String otherUserName, String userId, String chatRoomId, String userChatRoomsId) {
      super.id(id);
      super.otherUserId(otherUserId)
        .otherUserName(otherUserName)
        .userId(userId)
        .chatRoomId(chatRoomId)
        .userChatRoomsId(userChatRoomsId);
    }
    
    @Override
     public CopyOfBuilder otherUserId(String otherUserId) {
      return (CopyOfBuilder) super.otherUserId(otherUserId);
    }
    
    @Override
     public CopyOfBuilder otherUserName(String otherUserName) {
      return (CopyOfBuilder) super.otherUserName(otherUserName);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder chatRoomId(String chatRoomId) {
      return (CopyOfBuilder) super.chatRoomId(chatRoomId);
    }
    
    @Override
     public CopyOfBuilder userChatRoomsId(String userChatRoomsId) {
      return (CopyOfBuilder) super.userChatRoomsId(userChatRoomsId);
    }
  }
  
}
