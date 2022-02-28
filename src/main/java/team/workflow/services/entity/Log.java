package team.workflow.services.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Log {
  @Id
  private String lid;
  private String description;


  public String getLid() {
    return lid;
  }

  public void setLid(String lid) {
    this.lid = lid;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
