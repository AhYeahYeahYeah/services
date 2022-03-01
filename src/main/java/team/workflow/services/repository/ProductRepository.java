package team.workflow.services.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import team.workflow.services.entity.Product;

public interface ProductRepository extends ReactiveCrudRepository<Product,String> {

    @Query("UPDATE product SET oid=:oid where pid=:pid")
    Mono<Integer> setOid(String oid,String pid);

    @Query("UPDATE product SET oid=NULL WHERE pid=:pid")
    Mono<Integer> deleteOid(String pid);

}
