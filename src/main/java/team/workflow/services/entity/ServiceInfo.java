package team.workflow.services.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ServiceInfo {
  @Id
  private String sid;
  private String sname;
  private String description;


  public String getSid() {
    return sid;
  }

  public void setSid(String sid) {
    this.sid = sid;
  }


  public String getSname() {
    return sname;
  }

  public void setSname(String sname) {
    this.sname = sname;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
