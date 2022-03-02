package team.workflow.services.service;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface StorageService {
    // 库存锁定
    Mono<ResponseEntity> StorageLock(String jsonStr);

    Mono<ResponseEntity> StorageUnlock(String jsonStr);

    Mono<ResponseEntity> StorageUpdate(String jsonStr);
}
