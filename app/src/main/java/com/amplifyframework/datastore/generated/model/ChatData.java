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

/** This is an auto generated class representing the ChatData type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "ChatData", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class ChatData implements Model {
  public static final QueryField ID = field("ChatData", "id");
  public static final QueryField MESSAGE = field("ChatData", "message");
  public static final QueryField CREATED_AT = field("ChatData", "createdAt");
  public static final QueryField CHAT_ROOM_ID = field("ChatData", "chatRoomId");
  public static final QueryField SENDER_ID = field("ChatData", "senderId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String message;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime createdAt;
  private final @ModelField(targetType="String") String chatRoomId;
  private final @ModelField(targetType="String") String senderId;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getMessage() {
      return message;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public String getChatRoomId() {
      return chatRoomId;
  }
  
  public String getSenderId() {
      return senderId;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private ChatData(String id, String message, Temporal.DateTime createdAt, String chatRoomId, String senderId) {
    this.id = id;
    this.message = message;
    this.createdAt = createdAt;
    this.chatRoomId = chatRoomId;
    this.senderId = senderId;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      ChatData chatData = (ChatData) obj;
      return ObjectsCompat.equals(getId(), chatData.getId()) &&
              ObjectsCompat.equals(getMessage(), chatData.getMessage()) &&
              ObjectsCompat.equals(getCreatedAt(), chatData.getCreatedAt()) &&
              ObjectsCompat.equals(getChatRoomId(), chatData.getChatRoomId()) &&
              ObjectsCompat.equals(getSenderId(), chatData.getSenderId()) &&
              ObjectsCompat.equals(getUpdatedAt(), chatData.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getMessage())
      .append(getCreatedAt())
      .append(getChatRoomId())
      .append(getSenderId())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("ChatData {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("message=" + String.valueOf(getMessage()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("chatRoomId=" + String.valueOf(getChatRoomId()) + ", ")
      .append("senderId=" + String.valueOf(getSenderId()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
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
  public static ChatData justId(String id) {
    return new ChatData(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      message,
      createdAt,
      chatRoomId,
      senderId);
  }
  public interface BuildStep {
    ChatData build();
    BuildStep id(String id);
    BuildStep message(String message);
    BuildStep createdAt(Temporal.DateTime createdAt);
    BuildStep chatRoomId(String chatRoomId);
    BuildStep senderId(String senderId);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String message;
    private Temporal.DateTime createdAt;
    private String chatRoomId;
    private String senderId;
    @Override
     public ChatData build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new ChatData(
          id,
          message,
          createdAt,
          chatRoomId,
          senderId);
    }
    
    @Override
     public BuildStep message(String message) {
        this.message = message;
        return this;
    }
    
    @Override
     public BuildStep createdAt(Temporal.DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    
    @Override
     public BuildStep chatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
        return this;
    }
    
    @Override
     public BuildStep senderId(String senderId) {
        this.senderId = senderId;
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
    private CopyOfBuilder(String id, String message, Temporal.DateTime createdAt, String chatRoomId, String senderId) {
      super.id(id);
      super.message(message)
        .createdAt(createdAt)
        .chatRoomId(chatRoomId)
        .senderId(senderId);
    }
    
    @Override
     public CopyOfBuilder message(String message) {
      return (CopyOfBuilder) super.message(message);
    }
    
    @Override
     public CopyOfBuilder createdAt(Temporal.DateTime createdAt) {
      return (CopyOfBuilder) super.createdAt(createdAt);
    }
    
    @Override
     public CopyOfBuilder chatRoomId(String chatRoomId) {
      return (CopyOfBuilder) super.chatRoomId(chatRoomId);
    }
    
    @Override
     public CopyOfBuilder senderId(String senderId) {
      return (CopyOfBuilder) super.senderId(senderId);
    }
  }
  
}
