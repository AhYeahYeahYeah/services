package team.workflow.services.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.workflow.services.dto.OrdersDto;
import team.workflow.services.entity.Customer;
import team.workflow.services.entity.Log;
import team.workflow.services.entity.Orders;
import team.workflow.services.entity.Product;
import team.workflow.services.repository.CustomerRepository;
import team.workflow.services.repository.LogRepository;
import team.workflow.services.repository.OrderRepository;
import team.workflow.services.repository.ProductRepository;
import team.workflow.services.service.SystemService;

import javax.annotation.Resource;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
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
        Mono<OrdersDto> ordersDtoMono = orderRepository.findToLog(oid);
        return ordersDtoMono
                .flatMap(ordersDtoMono1 -> Mono.just(Optional.of(ordersDtoMono1)))
                .defaultIfEmpty(Optional.empty())
                .flatMap(ordersDtoMono1 -> {
                    if (ordersDtoMono1.isEmpty()) {
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                    OrdersDto ordersDto = ordersDtoMono1.map(Collections::singletonList).orElse(Collections.emptyList()).get(0);
//                    String status;
//                    if (orders.getStatus() == 0) {
//                        status = "正在运行";
//                    } else if (order.getStatus() == 1) {
//                        status = "运行成功";
//                    } else {
//                        status = "运行失败";
//                    }
                    Log log = new Log();
                    log.setLid(UUID.randomUUID().toString());
                    log.setOid(ordersDto.getOid());
                    Date orderDate = new Date(Long.parseLong(ordersDto.getOrderDate()));
                    Date expireDate = new Date(Long.parseLong(ordersDto.getExpireDate()));
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    log.setDescription("产品编号:" + ordersDto.getProductNum() + "      "
                            +"产品名称:" + ordersDto.getProductName() + "      "
                            + "用户账户:" + ordersDto.getAccount() + "      "
                            + "付款金额:" + ordersDto.getPayment() + "      "
                            + "起息日/订单时间:" + simpleDateFormat.format(orderDate) + "      "
                            + "到期日:" + simpleDateFormat.format(expireDate) + "      "
                            + "备注:" + description);
//                    System.out.println(log.getDescription());
                    Mono<Integer> res = logRepository.insert(log.getLid(), log.getOid(), log.getDescription());
                    return Mono.just(new ResponseEntity(res, HttpStatus.OK));
                });
    }
}
