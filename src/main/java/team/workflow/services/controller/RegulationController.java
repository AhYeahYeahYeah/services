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
    @PostMapping("/whitelist")
    public Mono<ResponseEntity> Whitelist(@RequestBody String jsonStr){
        return regulationService.Whitelist(jsonStr);
    }
}
