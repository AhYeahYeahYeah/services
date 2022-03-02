package team.workflow.services.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface DataService {
    public Mono<ResponseEntity> Interestrate(String jsonStr);
}
