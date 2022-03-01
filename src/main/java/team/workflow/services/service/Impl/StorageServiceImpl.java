package team.workflow.services.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;
import team.workflow.services.entity.Product;
import team.workflow.services.repository.ProductRepository;
import team.workflow.services.service.StorageService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StorageServiceImpl implements StorageService {
    @Resource
    private ProductRepository productRepository;
    // 库存锁定
    @Override
    public Mono<ResponseEntity> StorageLock(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String pid= (String) object.get("pid");
        String oid= (String) object.get("oid");
        Mono<Product> product=productRepository.findById(pid);
        //product.subscribe(System.out::println);
        return product.flatMap(product1 -> Mono.just(Optional.of(product1)))
                .defaultIfEmpty(Optional.empty())
                .flatMap(product1 -> {
                            if(product1.isEmpty()){
                                System.out.println("isempty");
                                return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                            }
                            //optional转list
                            List<Product> productlist=product1.map(Collections::singletonList).orElse(Collections.emptyList());
                            if(productlist.get(0).getStorage()<=0){
                                System.out.println("error");
                                return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                            }
                            if(productlist.get(0).getOid()!=null){
                                try {
                                    Thread.sleep(6000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                for(int f=10;f>0;f--){
                                    System.out.println("2222222S");
                                    Mono<Product> p=productRepository.findById(pid);
                                    p.subscribe(a->System.out.println(a));
                                    Mono<Integer> rep=p.flatMap(p1->{
                                        if(p1.getOid()==null){
                                            System.out.println("okkkk");
                                            Mono<Integer>res=productRepository.setOid(oid,pid);
                                            return Mono.just(1);
                                        }else {
                                            System.out.println("11111");
                                            try {
                                                Thread.sleep(6000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            System.out.println("in");
                                            return Mono.just(0);
                                        }
                                    });

                                }
                                return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                            }else {
                                Mono<Integer> res=productRepository.setOid(oid,pid);
                                return Mono.just(new ResponseEntity(res,HttpStatus.OK));
                            }
                        }

                );
        //return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
    }

    //库存解锁
    @Override
    public Mono<ResponseEntity> StorageUnlock(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String pid= (String) object.get("pid");
        Mono<Integer> res=productRepository.deleteOid(pid);
        return Mono.just(new ResponseEntity(res,HttpStatus.OK));
    }
}
