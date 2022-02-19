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

/** This is an auto generated class representing the Interest type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Interests", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Interest implements Model {
  public static final QueryField ID = field("Interest", "id");
  public static final QueryField INTEREST_ID = field("Interest", "interestId");
  public static final QueryField NAME = field("Interest", "name");
  public static final QueryField STRENGTH = field("Interest", "strength");
  public static final QueryField USERS = field("Interest", "userInterestsId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="Int") Integer interestId;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="Int", isRequired = true) Integer strength;
  private final @ModelField(targetType="User") @BelongsTo(targetName = "userInterestsId", type = User.class) User users;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public Integer getInterestId() {
      return interestId;
  }
  
  public String getName() {
      return name;
  }
  
  public Integer getStrength() {
      return strength;
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
  
  private Interest(String id, Integer interestId, String name, Integer strength, User users) {
    this.id = id;
    this.interestId = interestId;
    this.name = name;
    this.strength = strength;
    this.users = users;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Interest interest = (Interest) obj;
      return ObjectsCompat.equals(getId(), interest.getId()) &&
              ObjectsCompat.equals(getInterestId(), interest.getInterestId()) &&
              ObjectsCompat.equals(getName(), interest.getName()) &&
              ObjectsCompat.equals(getStrength(), interest.getStrength()) &&
              ObjectsCompat.equals(getUsers(), interest.getUsers()) &&
              ObjectsCompat.equals(getCreatedAt(), interest.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), interest.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getInterestId())
      .append(getName())
      .append(getStrength())
      .append(getUsers())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Interest {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("interestId=" + String.valueOf(getInterestId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("strength=" + String.valueOf(getStrength()) + ", ")
      .append("users=" + String.valueOf(getUsers()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Interest justId(String id) {
    return new Interest(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      interestId,
      name,
      strength,
      users);
  }
  public interface NameStep {
    StrengthStep name(String name);
  }
  

  public interface StrengthStep {
    BuildStep strength(Integer strength);
  }
  

  public interface BuildStep {
    Interest build();
    BuildStep id(String id);
    BuildStep interestId(Integer interestId);
    BuildStep users(User users);
  }
  

  public static class Builder implements NameStep, StrengthStep, BuildStep {
    private String id;
    private String name;
    private Integer strength;
    private Integer interestId;
    private User users;
    @Override
     public Interest build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Interest(
          id,
          interestId,
          name,
          strength,
          users);
    }
    
    @Override
     public StrengthStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep strength(Integer strength) {
        Objects.requireNonNull(strength);
        this.strength = strength;
        return this;
    }
    
    @Override
     public BuildStep interestId(Integer interestId) {
        this.interestId = interestId;
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
    private CopyOfBuilder(String id, Integer interestId, String name, Integer strength, User users) {
      super.id(id);
      super.name(name)
        .strength(strength)
        .interestId(interestId)
        .users(users);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder strength(Integer strength) {
      return (CopyOfBuilder) super.strength(strength);
    }
    
    @Override
     public CopyOfBuilder interestId(Integer interestId) {
      return (CopyOfBuilder) super.interestId(interestId);
    }
    
    @Override
     public CopyOfBuilder users(User users) {
      return (CopyOfBuilder) super.users(users);
    }
  }
  
}
