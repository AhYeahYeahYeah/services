package team.workflow.services.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface RegulationService {
    Mono<ResponseEntity> Whitelist(String jsonStr);
}
