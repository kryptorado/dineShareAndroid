package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the CallLog type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "CallLogs", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class CallLog implements Model {
  public static final QueryField ID = field("CallLog", "id");
  public static final QueryField DURATION = field("CallLog", "duration");
  public static final QueryField CALLEE_NAME = field("CallLog", "calleeName");
  public static final QueryField USERS = field("CallLog", "userCallLogsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String duration;
  private final @ModelField(targetType="String") String calleeName;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "userCallLogsId", type = User.class) User users;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getDuration() {
      return duration;
  }
  
  public String getCalleeName() {
      return calleeName;
  }
  
  public User getUsers() {
      return users;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private CallLog(String id, String duration, String calleeName, User users) {
    this.id = id;
    this.duration = duration;
    this.calleeName = calleeName;
    this.users = users;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      CallLog callLog = (CallLog) obj;
      return ObjectsCompat.equals(getId(), callLog.getId()) &&
              ObjectsCompat.equals(getDuration(), callLog.getDuration()) &&
              ObjectsCompat.equals(getCalleeName(), callLog.getCalleeName()) &&
              ObjectsCompat.equals(getUsers(), callLog.getUsers()) &&
              ObjectsCompat.equals(getCreatedAt(), callLog.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), callLog.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDuration())
      .append(getCalleeName())
      .append(getUsers())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("CallLog {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("duration=" + String.valueOf(getDuration()) + ", ")
      .append("calleeName=" + String.valueOf(getCalleeName()) + ", ")
      .append("users=" + String.valueOf(getUsers()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
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
  public static CallLog justId(String id) {
    return new CallLog(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      duration,
      calleeName,
      users);
  }
  public interface BuildStep {
    CallLog build();
    BuildStep id(String id);
    BuildStep duration(String duration);
    BuildStep calleeName(String calleeName);
    BuildStep users(User users);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String duration;
    private String calleeName;
    private User users;
    @Override
     public CallLog build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new CallLog(
          id,
          duration,
          calleeName,
          users);
    }
    
    @Override
     public BuildStep duration(String duration) {
        this.duration = duration;
        return this;
    }
    
    @Override
     public BuildStep calleeName(String calleeName) {
        this.calleeName = calleeName;
        return this;
    }
    
    @Override
     public BuildStep users(User users) {
        this.users = users;
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
    private CopyOfBuilder(String id, String duration, String calleeName, User users) {
      super.id(id);
      super.duration(duration)
        .calleeName(calleeName)
        .users(users);
    }
    
    @Override
     public CopyOfBuilder duration(String duration) {
      return (CopyOfBuilder) super.duration(duration);
    }
    
    @Override
     public CopyOfBuilder calleeName(String calleeName) {
      return (CopyOfBuilder) super.calleeName(calleeName);
    }
    
    @Override
     public CopyOfBuilder users(User users) {
      return (CopyOfBuilder) super.users(users);
    }
  }
  
}
