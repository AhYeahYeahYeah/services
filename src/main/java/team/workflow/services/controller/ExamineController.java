package team.workflow.services.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import team.workflow.services.service.ExamineService;
import team.workflow.services.service.StorageService;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/v1/examine")
public class ExamineController {
    @Resource
    private ExamineService examineService;
    //证件审查
    @PostMapping("/credential")
    public Mono<ResponseEntity> Credential(@RequestBody String jsonStr){
        return examineService.Credential(jsonStr);
    }
    //用户信息验证
    @PostMapping("/profile")
    public Mono<ResponseEntity> Profile(@RequestBody String jsonStr){
        return examineService.Profile(jsonStr);
    }
}
