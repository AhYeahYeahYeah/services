package team.workflow.services.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import team.workflow.services.entity.Whitelist;

public interface WhitelistRepository extends ReactiveCrudRepository<Whitelist,String> {
}
