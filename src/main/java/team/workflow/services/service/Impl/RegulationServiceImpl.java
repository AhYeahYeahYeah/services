package team.workflow.services.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.workflow.services.entity.Blacklist;
import team.workflow.services.entity.Whitelist;
import team.workflow.services.repository.BlacklistRepository;
import team.workflow.services.repository.WhitelistRepository;
import team.workflow.services.service.RegulationService;

import javax.annotation.Resource;
import java.util.function.Function;

@Service
public class RegulationServiceImpl implements RegulationService {
    private static final Whitelist WHITELIST_NULL = new Whitelist();
    private static final Blacklist BLACKLIST_NULL = new Blacklist();
    @Resource
    private WhitelistRepository whitelistRepository;
    @Resource
    private BlacklistRepository blacklistRepository;
    @Override
    public Mono<ResponseEntity> Whitelist(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String cid= (String) object.get("cid");
        String wid= (String) object.get("wid");
        Mono<Whitelist> whitelistMono=whitelistRepository.findById(wid);
        return whitelistMono.defaultIfEmpty(WHITELIST_NULL)
                .flatMap(new Function<Whitelist, Mono<ResponseEntity>>() {
                    @Override
                    public Mono<ResponseEntity> apply(Whitelist whitelist) {
                        if(whitelist==WHITELIST_NULL){
                            System.out.println("find no whitelist");
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                        String cidlist = whitelist.getUsers();
                        cidlist=cidlist.replace("[","")
                                        .replace("]","").replace("\"","");
                        String[] cidlists=cidlist.split(",");
                        for (String i:cidlists
                             ) {
                            System.out.println(i);
                            if(i.equals(cid)){
                                System.out.println("success");
                                return Mono.just(new ResponseEntity(HttpStatus.OK));
                            }
                        }
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                });
    }

    @Override
    public Mono<ResponseEntity> Blacklist(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String cid= (String) object.get("cid");
        String bid= (String) object.get("bid");
        Mono<Blacklist> blacklistMono=blacklistRepository.findById(bid);
        return blacklistMono.defaultIfEmpty(BLACKLIST_NULL)
                .flatMap(new Function<Blacklist, Mono<ResponseEntity>>() {
                    @Override
                    public Mono<ResponseEntity> apply(Blacklist blacklist) {
                        if(blacklist==BLACKLIST_NULL){
                            System.out.println("find no blacklist");
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                        String cidlist = blacklist.getUsers();
                        cidlist=cidlist.replace("[","")
                                .replace("]","").replace("\"","");
                        String[] cidlists=cidlist.split(",");
                        for (String i:cidlists
                        ) {
                            System.out.println(i);
                            if(i.equals(cid)){
                                System.out.println("success");
                                return Mono.just(new ResponseEntity(HttpStatus.OK));
                            }
                        }
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                });
    }
}
