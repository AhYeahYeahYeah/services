package team.workflow.services.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import team.workflow.services.dto.Interest;
import team.workflow.services.entity.Orders;


public interface OrderRepository extends ReactiveCrudRepository<Orders, String> {
    @Query("SELECT payment,annual_rate FROM product,orders WHERE orders.pid=product.pid AND orders.oid=:oid")
    Mono<Interest> findToInterest(String oid);
}
