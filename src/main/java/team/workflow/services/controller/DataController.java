package team.workflow.services.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import team.workflow.services.service.DataService;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/v1/data")
public class DataController {
    @Resource
    DataService dataService;

    @PostMapping("/interestrate")
    public Mono<ResponseEntity> Interestrate(@RequestBody String jsonStr) {
        return dataService.Interestrate(jsonStr);
    }
}
