package team.workflow.services.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.workflow.services.entity.Blacklist;
import team.workflow.services.entity.CustomerProfile;
import team.workflow.services.entity.UserGroup;
import team.workflow.services.entity.Whitelist;
import team.workflow.services.repository.BlacklistRepository;
import team.workflow.services.repository.CustomerProfileRepository;
import team.workflow.services.repository.UserGroupRepository;
import team.workflow.services.repository.WhitelistRepository;
import team.workflow.services.service.RegulationService;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Function;

@Service
public class RegulationServiceImpl implements RegulationService {
    private static final Whitelist WHITELIST_NULL = new Whitelist();
    private static final Blacklist BLACKLIST_NULL = new Blacklist();
    private static final CustomerProfile CUSTOMER_PROFILE_NULL = new CustomerProfile();
    private static final UserGroup USER_GROUP_NULL = new UserGroup();
    @Resource
    private WhitelistRepository whitelistRepository;
    @Resource
    private BlacklistRepository blacklistRepository;
    @Resource
    private CustomerProfileRepository customerProfileRepository;
    @Resource
    private UserGroupRepository userGroupRepository;

    @Override
    public Mono<ResponseEntity> Whitelist(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String cid = (String) object.get("cid");
        String wid = (String) object.get("wid");
        Mono<Whitelist> whitelistMono = whitelistRepository.findById(wid);
        return whitelistMono.defaultIfEmpty(WHITELIST_NULL)
                .flatMap(new Function<Whitelist, Mono<ResponseEntity>>() {
                    @Override
                    public Mono<ResponseEntity> apply(Whitelist whitelist) {
                        if (whitelist == WHITELIST_NULL) {
                            System.out.println("find no whitelist");
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                        String cidlist = whitelist.getUsers();
                        cidlist = cidlist.replace("[", "")
                                .replace("]", "");
//                                .replace("\"", "");
                        String[] cidlists = cidlist.split(",");
                        for (String i : cidlists
                        ) {
                            System.out.println(i);
                            if (i.equals(cid)) {
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
        String cid = (String) object.get("cid");
        String bid = (String) object.get("bid");
        Mono<Blacklist> blacklistMono = blacklistRepository.findById(bid);
        return blacklistMono.defaultIfEmpty(BLACKLIST_NULL)
                .flatMap(new Function<Blacklist, Mono<ResponseEntity>>() {
                    @Override
                    public Mono<ResponseEntity> apply(Blacklist blacklist) {
                        if (blacklist == BLACKLIST_NULL) {
                            System.out.println("find no blacklist");
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                        String cidlist = blacklist.getUsers();
                        cidlist = cidlist.replace("[", "")
                                .replace("]", "");
//                                .replace("\"", "");
                        String[] cidlists = cidlist.split(",");
                        for (String i : cidlists
                        ) {
                            System.out.println(i);
                            if (i.equals(cid)) {
                                System.out.println("blacklist failed");
                                return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                            }
                        }
                        System.out.println("blacklist success");
                        return Mono.just(new ResponseEntity(HttpStatus.OK));
                    }
                });
    }

    @Override
    public Mono<ResponseEntity> Region(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String cid = (String) object.get("cid");
        List<String> region = (List<String>) object.get("region");
        Mono<CustomerProfile> customerProfileMono = customerProfileRepository.findById(cid);
        return customerProfileMono.defaultIfEmpty(CUSTOMER_PROFILE_NULL)
                .flatMap(new Function<CustomerProfile, Mono<ResponseEntity>>() {
                    @Override
                    public Mono<ResponseEntity> apply(CustomerProfile customerProfile) {
                        if (customerProfile == CUSTOMER_PROFILE_NULL) {
                            System.out.println("find no customerprofile");
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                        String address = customerProfile.getAddress();
                        String addr = address.substring(0, 3);
                        if (address.startsWith("黑龙江省")) {
                            addr = address.substring(0, 4);
                        }
                        if (address.startsWith("香港") || address.startsWith("澳门") || address.startsWith("台湾")) {
                            addr = address.substring(0, 2);
                        }

                        for (String i : region
                        ) {
                            System.out.println(i);
                            if (i.equals(addr)) {
                                System.out.println("success");
                                return Mono.just(new ResponseEntity(HttpStatus.OK));
                            }
                        }
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                });


    }

    @Override
    public Mono<ResponseEntity> Tag(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String cid = (String) object.get("cid");
        String gid = (String) object.get("gid");
        Mono<UserGroup> userGroupMono = userGroupRepository.findById(gid);
        return userGroupMono.defaultIfEmpty(USER_GROUP_NULL)
                .flatMap(new Function<UserGroup, Mono<ResponseEntity>>() {
                    @Override
                    public Mono<ResponseEntity> apply(UserGroup userGroup) {
                        if (userGroup == USER_GROUP_NULL) {
                            System.out.println("find no usergroup");
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                        String cidlist = userGroup.getUsers();
                        cidlist = cidlist.replace("[", "")
                                .replace("]", "");
//                                .replace("\"", "");
                        String[] cidlists = cidlist.split(",");
                        for (String i : cidlists
                        ) {
                            System.out.println(i);
                            if (i.equals(cid)) {
                                System.out.println("success");
                                return Mono.just(new ResponseEntity(HttpStatus.OK));
                            }
                        }
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                });
    }
}
