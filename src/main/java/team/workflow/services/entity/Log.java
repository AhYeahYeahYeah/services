package team.workflow.services.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Log {
    @Id
    private String lid;
    private String oid;
    private String description;


    public String getLid() {
        return lid;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
