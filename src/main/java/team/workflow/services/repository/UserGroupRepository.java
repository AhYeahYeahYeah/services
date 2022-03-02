package team.workflow.services.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import team.workflow.services.entity.UserGroup;

public interface UserGroupRepository extends ReactiveCrudRepository<UserGroup, String> {
}
