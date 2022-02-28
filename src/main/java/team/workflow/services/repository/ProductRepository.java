package team.workflow.services.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import team.workflow.services.entity.Product;

public interface ProductRepository extends ReactiveCrudRepository<Product,String> {

}
