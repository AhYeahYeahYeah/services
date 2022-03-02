package team.workflow.services.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.workflow.services.dto.CustomerDto;
import team.workflow.services.entity.CustomerProfile;
import team.workflow.services.entity.Product;
import team.workflow.services.repository.CustomerProfileRepository;
import team.workflow.services.repository.CustomerRepository;
import team.workflow.services.service.ExamineService;
import team.workflow.services.utils.IDCardValidate;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ExamineServiceImpl implements ExamineService {
    @Resource
    private CustomerProfileRepository customerProfileRepository;
    @Resource
    private CustomerRepository customerRepository;
    @Override
    public Mono<ResponseEntity> Credential(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String cid= (String) object.get("cid");
        Mono<CustomerProfile> customerProfileMono=customerProfileRepository.findById(cid);
        return customerProfileMono
                .flatMap(customerProfile1 -> Mono.just(Optional.of(customerProfile1)))
                .defaultIfEmpty(Optional.empty())
                .flatMap(customerProfile1 -> {
                    if(customerProfile1.isEmpty()){
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                    List<CustomerProfile> customerProfilelist=customerProfile1.map(Collections::singletonList).orElse(Collections.emptyList());
                    String sid=customerProfilelist.get(0).getSid();
                    if(sid==null){
                        System.out.println("身份证为null");
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }else {
                        IDCardValidate idCardValidate=new IDCardValidate();

                        if(idCardValidate.chekIdCard(sid).equals("SUCCESS")){
                            System.out.println("验证成功");
                            return Mono.just(new ResponseEntity(HttpStatus.OK));
                        }else {
                            System.out.println("验证失败");
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }

                    }

                });
    }

    @Override
    public Mono<ResponseEntity> Profile(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String cid= (String) object.get("cid");
        String password= (String) object.get("password");
        String phoneNum= (String) object.get("phoneNum");
        Mono<CustomerDto> customerDtoMono=customerRepository.selectCustomerProfile(cid);
        return customerDtoMono
                .flatMap(customerDtoMono1 -> Mono.just(Optional.of(customerDtoMono1)))
                .defaultIfEmpty(Optional.empty())
                .flatMap(customerDtoMono1 -> {
                            if(customerDtoMono1.isEmpty()){
                                return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                            }else {
                                List<CustomerDto> customerDtolist=customerDtoMono1.map(Collections::singletonList).orElse(Collections.emptyList());
                                if(customerDtolist.get(0).getPassword().equals(password)&&customerDtolist.get(0).getPhoneNum().equals(phoneNum)){
                                    return Mono.just(new ResponseEntity(HttpStatus.OK));
                                }else{
                                    return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                                }
                            }
                        }

                );
    }
}
