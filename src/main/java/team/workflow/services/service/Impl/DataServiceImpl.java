package team.workflow.services.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.data.relational.core.sql.In;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.workflow.services.dto.Interest;
import team.workflow.services.entity.Whitelist;
import team.workflow.services.repository.OrderRepository;
import team.workflow.services.service.DataService;

import javax.annotation.Resource;
import java.util.function.Function;

@Service
public class DataServiceImpl implements DataService {
    private Interest INTEREST_NULL = new Interest();
    @Resource
    private OrderRepository orderRepository;

    @Override
    public Mono<ResponseEntity> Interestrate(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String oid = (String) object.get("oid");
        Mono<Interest> interestMono = orderRepository.findToInterest(oid);
        return interestMono.defaultIfEmpty(INTEREST_NULL)
                .flatMap(new Function<Interest, Mono<ResponseEntity>>() {
                    @Override
                    public Mono<ResponseEntity> apply(Interest interest) {
                        if (interest == INTEREST_NULL) {
                            System.out.println("find no interest");
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                        float res = 0;
                        float payment = interest.getPayment();
                        float annualRate = interest.getAnnualRate();
                        res = payment * annualRate / 365;
                        JSONObject jo = new JSONObject();
                        jo.put("interest", res);
                        return Mono.just(new ResponseEntity(jo, HttpStatus.OK));
                    }
                });

    }
}
