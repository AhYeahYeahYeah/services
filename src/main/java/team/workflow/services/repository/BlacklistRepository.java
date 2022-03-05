package team.workflow.services.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import team.workflow.services.entity.Blacklist;

public interface BlacklistRepository extends ReactiveCrudRepository<Blacklist, String> {
}
