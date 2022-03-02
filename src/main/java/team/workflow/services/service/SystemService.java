package team.workflow.services.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface SystemService {
    Mono<ResponseEntity> setLog(String jsonStr);
}
