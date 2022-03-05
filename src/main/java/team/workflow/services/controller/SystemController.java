package team.workflow.services.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import team.workflow.services.service.SystemService;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/v1/system")
public class SystemController {
    @Resource
    private SystemService systemService;

    //日志录入
    @PostMapping("/log")
    public Mono<ResponseEntity> setLog(@RequestBody String jsonStr) {
        return systemService.setLog(jsonStr);
    }

}
