package team.workflow.services.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface ExamineService {
    Mono<ResponseEntity> Credential(String jsonStr);

    Mono<ResponseEntity> Profile(String jsonStr);
}
