package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
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

/** This is an auto generated class representing the User type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Users", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class User implements Model {
  public static final QueryField ID = field("User", "id");
  public static final QueryField FIRST_NAME = field("User", "firstName");
  public static final QueryField LAST_NAME = field("User", "lastName");
  public static final QueryField RTM_TOKEN = field("User", "rtmToken");
  public static final QueryField EMAIL = field("User", "email");
  public static final QueryField REPORTED_TIMES = field("User", "reportedTimes");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", authRules = {
    @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
    @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.READ })
  }) String firstName;
  private final @ModelField(targetType="String", authRules = {
    @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
    @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.READ })
  }) String lastName;
  private final @ModelField(targetType="String") String rtmToken;
  private final @ModelField(targetType="String", authRules = {
    @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
    @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.READ })
  }) String email;
  private final @ModelField(targetType="Int", authRules = {
    @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
    @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
  }) Integer reportedTimes;
  private final @ModelField(targetType="Interest") @HasMany(associatedWith = "users", type = Interest.class) List<Interest> interests = null;
  private final @ModelField(targetType="ChatRoom") @HasMany(associatedWith = "userChatRoomsId", type = ChatRoom.class) List<ChatRoom> chatRooms = null;
  private final @ModelField(targetType="CallLog") @HasMany(associatedWith = "users", type = CallLog.class) List<CallLog> callLogs = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getFirstName() {
      return firstName;
  }
  
  public String getLastName() {
      return lastName;
  }
  
  public String getRtmToken() {
      return rtmToken;
  }
  
  public String getEmail() {
      return email;
  }
  
  public Integer getReportedTimes() {
      return reportedTimes;
  }
  
  public List<Interest> getInterests() {
      return interests;
  }
  
  public List<ChatRoom> getChatRooms() {
      return chatRooms;
  }
  
  public List<CallLog> getCallLogs() {
      return callLogs;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private User(String id, String firstName, String lastName, String rtmToken, String email, Integer reportedTimes) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.rtmToken = rtmToken;
    this.email = email;
    this.reportedTimes = reportedTimes;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      User user = (User) obj;
      return ObjectsCompat.equals(getId(), user.getId()) &&
              ObjectsCompat.equals(getFirstName(), user.getFirstName()) &&
              ObjectsCompat.equals(getLastName(), user.getLastName()) &&
              ObjectsCompat.equals(getRtmToken(), user.getRtmToken()) &&
              ObjectsCompat.equals(getEmail(), user.getEmail()) &&
              ObjectsCompat.equals(getReportedTimes(), user.getReportedTimes()) &&
              ObjectsCompat.equals(getCreatedAt(), user.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), user.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getFirstName())
      .append(getLastName())
      .append(getRtmToken())
      .append(getEmail())
      .append(getReportedTimes())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("User {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("firstName=" + String.valueOf(getFirstName()) + ", ")
      .append("lastName=" + String.valueOf(getLastName()) + ", ")
      .append("rtmToken=" + String.valueOf(getRtmToken()) + ", ")
      .append("email=" + String.valueOf(getEmail()) + ", ")
      .append("reportedTimes=" + String.valueOf(getReportedTimes()) + ", ")
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
  public static User justId(String id) {
    return new User(
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
      firstName,
      lastName,
      rtmToken,
      email,
      reportedTimes);
  }
  public interface BuildStep {
    User build();
    BuildStep id(String id);
    BuildStep firstName(String firstName);
    BuildStep lastName(String lastName);
    BuildStep rtmToken(String rtmToken);
    BuildStep email(String email);
    BuildStep reportedTimes(Integer reportedTimes);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String firstName;
    private String lastName;
    private String rtmToken;
    private String email;
    private Integer reportedTimes;
    @Override
     public User build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new User(
          id,
          firstName,
          lastName,
          rtmToken,
          email,
          reportedTimes);
    }
    
    @Override
     public BuildStep firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    
    @Override
     public BuildStep lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    
    @Override
     public BuildStep rtmToken(String rtmToken) {
        this.rtmToken = rtmToken;
        return this;
    }
    
    @Override
     public BuildStep email(String email) {
        this.email = email;
        return this;
    }
    
    @Override
     public BuildStep reportedTimes(Integer reportedTimes) {
        this.reportedTimes = reportedTimes;
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
    private CopyOfBuilder(String id, String firstName, String lastName, String rtmToken, String email, Integer reportedTimes) {
      super.id(id);
      super.firstName(firstName)
        .lastName(lastName)
        .rtmToken(rtmToken)
        .email(email)
        .reportedTimes(reportedTimes);
    }
    
    @Override
     public CopyOfBuilder firstName(String firstName) {
      return (CopyOfBuilder) super.firstName(firstName);
    }
    
    @Override
     public CopyOfBuilder lastName(String lastName) {
      return (CopyOfBuilder) super.lastName(lastName);
    }
    
    @Override
     public CopyOfBuilder rtmToken(String rtmToken) {
      return (CopyOfBuilder) super.rtmToken(rtmToken);
    }
    
    @Override
     public CopyOfBuilder email(String email) {
      return (CopyOfBuilder) super.email(email);
    }
    
    @Override
     public CopyOfBuilder reportedTimes(Integer reportedTimes) {
      return (CopyOfBuilder) super.reportedTimes(reportedTimes);
    }
  }
  
}
