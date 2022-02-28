package team.workflow.services.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import team.workflow.services.service.StorageService;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/v1/storage")
public class StorageController {
    @Resource
    private StorageService storageService;
    // 库存锁定
    @PostMapping("/lock")
    public Mono<ResponseEntity> StorageLock(@RequestBody String pid, String oid){
//        storageService.StorageLock(pid, oid);
        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));

    }
//    @PostMapping("/lock1")
//    public Mono<Integer> StorageLock1(@RequestBody String pid, String oid){
////        storageService.StorageLock(pid, oid);
//        return Mono.just(1);
//
//    }
}
