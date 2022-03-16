package team.workflow.services.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.workflow.services.dto.CustomerDto;
import team.workflow.services.entity.Log;
import team.workflow.services.entity.Orders;
import team.workflow.services.repository.LogRepository;
import team.workflow.services.repository.OrderRepository;
import team.workflow.services.service.SystemService;

import javax.annotation.Resource;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private OrderRepository orderRepository;
    @Resource
    private LogRepository logRepository;

    @Override
    public Mono<ResponseEntity> setLog(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String oid = (String) object.get("oid");
        String description = (String) object.get("description");
        Mono<Orders> ordersMono = orderRepository.findById(oid);
        return ordersMono
                .flatMap(ordersMono1 -> Mono.just(Optional.of(ordersMono1)))
                .defaultIfEmpty(Optional.empty())
                .flatMap(ordersMono1 -> {
                    if (ordersMono1.isEmpty()) {
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                    Orders order = ordersMono1.map(Collections::singletonList).orElse(Collections.emptyList()).get(0);
                    String status;
                    if (order.getStatus() == 0) {
                        status = "正在运行";
                    } else if (order.getStatus() == 1) {
                        status = "运行成功";
                    } else {
                        status = "运行失败";
                    }
                    Date orderDate = new Date(Long.parseLong(order.getOrderDate()));
                    Date expireDate = new Date(Long.parseLong(order.getExpireDate()));
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    Log log = new Log();
                    log.setLid(UUID.randomUUID().toString());
                    log.setOid(order.getOid());
                    log.setDescription("产品ID:" + order.getPid() + "\n"
                            + "用户ID:" + order.getCid() + "        "
                            + "付款金额:" + order.getPayment() + "\n"
                            + "起息日/订单时间:" + simpleDateFormat.format(orderDate) + "        "
                            + "到期日:" + simpleDateFormat.format(expireDate) + "\n"
                            + "工作流结果ID:" + order.getWorkflowId() + "        "
                            + "订单状态:" + status + "\n"
                            + "备注:" + description);
                    Mono<Integer> res = logRepository.insert(log.getLid(), log.getOid(), log.getDescription());
                    return Mono.just(new ResponseEntity(res, HttpStatus.OK));
                });
    }
}
