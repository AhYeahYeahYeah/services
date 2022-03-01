package team.workflow.services.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import team.workflow.services.entity.CustomerProfile;

public interface CustomerProfileRepository extends ReactiveCrudRepository<CustomerProfile, String> {
}
