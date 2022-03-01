package team.workflow.services.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
    public Mono<ResponseEntity> StorageLock(@RequestBody String jsonStr){
          return storageService.StorageLock(jsonStr);
    }


}
