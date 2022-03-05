package team.workflow.services.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import team.workflow.services.service.RegulationService;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/v1/regulation")
public class RegulationController {
    @Resource
    RegulationService regulationService;

    //白名单购买控制
    @PostMapping("/whitelist")
    public Mono<ResponseEntity> Whitelist(@RequestBody String jsonStr) {
        return regulationService.Whitelist(jsonStr);
    }

    //黑名单购买控制
    @PostMapping("/blacklist")
    public Mono<ResponseEntity> Blacklist(@RequestBody String jsonStr) {
        return regulationService.Blacklist(jsonStr);
    }

    //地域购买控制
    @PostMapping("/region")
    public Mono<ResponseEntity> Region(@RequestBody String jsonStr) {
        return regulationService.Region(jsonStr);
    }

    //用户标签控制
    @PostMapping("/tag")
    public Mono<ResponseEntity> Tag(@RequestBody String jsonStr) {
        return regulationService.Tag(jsonStr);
    }
}
