package team.workflow.services.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import team.workflow.services.dto.CustomerDto;
import team.workflow.services.entity.Blacklist;
import team.workflow.services.entity.Customer;

public interface CustomerRepository extends ReactiveCrudRepository<Customer,String> {
    @Query("SELECT * from customer,customer_profile WHERE customer.cid=customer_profile.cid AND customer.cid=:cid")
    Mono<CustomerDto> selectCustomerProfile(String cid);

}
