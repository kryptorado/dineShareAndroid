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

/** This is an auto generated class representing the Reported type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Reporteds", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Reported implements Model {
  public static final QueryField ID = field("Reported", "id");
  public static final QueryField USER_ID = field("Reported", "userId");
  public static final QueryField REPORTED_TIMES = field("Reported", "reportedTimes");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String userId;
  private final @ModelField(targetType="Int") Integer reportedTimes;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getUserId() {
      return userId;
  }
  
  public Integer getReportedTimes() {
      return reportedTimes;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Reported(String id, String userId, Integer reportedTimes) {
    this.id = id;
    this.userId = userId;
    this.reportedTimes = reportedTimes;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Reported reported = (Reported) obj;
      return ObjectsCompat.equals(getId(), reported.getId()) &&
              ObjectsCompat.equals(getUserId(), reported.getUserId()) &&
              ObjectsCompat.equals(getReportedTimes(), reported.getReportedTimes()) &&
              ObjectsCompat.equals(getCreatedAt(), reported.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), reported.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUserId())
      .append(getReportedTimes())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Reported {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("userId=" + String.valueOf(getUserId()) + ", ")
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
  public static Reported justId(String id) {
    return new Reported(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      userId,
      reportedTimes);
  }
  public interface BuildStep {
    Reported build();
    BuildStep id(String id);
    BuildStep userId(String userId);
    BuildStep reportedTimes(Integer reportedTimes);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String userId;
    private Integer reportedTimes;
    @Override
     public Reported build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Reported(
          id,
          userId,
          reportedTimes);
    }
    
    @Override
     public BuildStep userId(String userId) {
        this.userId = userId;
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
    private CopyOfBuilder(String id, String userId, Integer reportedTimes) {
      super.id(id);
      super.userId(userId)
        .reportedTimes(reportedTimes);
    }
    
    @Override
     public CopyOfBuilder userId(String userId) {
      return (CopyOfBuilder) super.userId(userId);
    }
    
    @Override
     public CopyOfBuilder reportedTimes(Integer reportedTimes) {
      return (CopyOfBuilder) super.reportedTimes(reportedTimes);
    }
  }
  
}
