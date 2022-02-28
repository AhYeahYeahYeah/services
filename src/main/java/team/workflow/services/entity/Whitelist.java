package team.workflow.services.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Whitelist {
  @Id
  private String wid;
  private String name;
  private String users;
  private String description;


  public String getWid() {
    return wid;
  }

  public void setWid(String wid) {
    this.wid = wid;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getUsers() {
    return users;
  }

  public void setUsers(String users) {
    this.users = users;
  }


  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
