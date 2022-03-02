package team.workflow.services.dto;

import lombok.Data;
import team.workflow.services.entity.Customer;

@Data
public class CustomerDto extends Customer {

    private String phoneNum;
}
