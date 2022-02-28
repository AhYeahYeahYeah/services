package team.workflow.services.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public void StorageLock(String pid,String oid){
        storageService.StorageLock(pid, oid);
    }
}
