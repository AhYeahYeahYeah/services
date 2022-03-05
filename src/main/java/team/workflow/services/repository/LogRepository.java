package team.workflow.services.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import team.workflow.services.entity.Log;

public interface LogRepository extends ReactiveCrudRepository<Log, String> {

    @Query("insert into log (lid,oid,description) values (:lid,:oid,:description)")
    Mono<Integer> insert(String lid, String oid, String description);

}
