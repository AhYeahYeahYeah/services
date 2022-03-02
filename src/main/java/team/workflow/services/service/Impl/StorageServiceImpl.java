package team.workflow.services.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import team.workflow.services.entity.Product;
import team.workflow.services.repository.ProductRepository;
import team.workflow.services.service.StorageService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class StorageServiceImpl implements StorageService {
    @Resource
    private ProductRepository productRepository;
    // 库存锁定
    //等待锁定待修改
    @Override
    synchronized public Mono<ResponseEntity> StorageLock(String jsonStr) {
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
                                int f;
                                AtomicInteger flag= new AtomicInteger();
                                for(f=10;f>0;f--){
                                    Mono<Product> p=productRepository.findById(pid);
                                    p.subscribe(p1->{
                                        if(p1.getOid()==null){
                                            System.out.println("okkkk");
                                            flag.set(1);
                                        }else {
                                            try {
                                                Thread.sleep(6000);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            System.out.println("in");
                                        }
                                    });
                                    if(flag.get()==1){
                                        break;
                                    }
                                }
                                if(f==0){
                                    return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                                }else {
                                    Mono<Integer> res=productRepository.setOid(oid, pid);
                                    return Mono.just(new ResponseEntity(res,HttpStatus.OK));
                                }
                            }else {
                                Mono<Integer> res=productRepository.setOid(oid,pid);
                                return Mono.just(new ResponseEntity(res,HttpStatus.OK));
                            }
                        }

                );
    }

    //库存解锁
    //判定库存锁定的oid为自己的oid
    @Override
    public Mono<ResponseEntity> StorageUnlock(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String pid= (String) object.get("pid");
        String oid= (String) object.get("oid");
        Mono<Product> product=productRepository.findById(pid);
        //检测是否解锁成功
        return product
                .flatMap(product1 -> Mono.just(Optional.of(product1)))
                .defaultIfEmpty(Optional.empty())
                .flatMap(product1 -> {
                       if(product1.isEmpty()){
                           return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                        List<Product> productList=product1.map(Collections::singletonList).orElse(Collections.emptyList());
                       //检测是否为自己的oid
                        if(productList.get(0).getOid().equals(oid)){
                            //解锁，将oid设为null
                            Mono<Integer> res=productRepository.deleteOid(pid);
                            return  Mono.just(new ResponseEntity(res,HttpStatus.OK));

                        }else {
                            return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                        }
                });
    }
    //库存更新
    @Override
    public Mono<ResponseEntity> StorageUpdate(String jsonStr) {
        JSONObject object = JSON.parseObject(jsonStr);
        String pid= (String) object.get("pid");
        String oid= (String) object.get("oid");
        Mono<Product> product=productRepository.findById(pid);
        //检测是否解锁成功
        return product
                .flatMap(product1 -> Mono.just(Optional.of(product1)))
                .defaultIfEmpty(Optional.empty())
                .flatMap(product1 -> {
                    if(product1.isEmpty()){
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                    List<Product> productList=product1.map(Collections::singletonList).orElse(Collections.emptyList());
                    //检测是否为自己的oid
                    if(productList.get(0).getOid().equals(oid)){
                        //库存-1
                        Mono<Integer> res=productRepository.updateStorage(pid);
                        return  Mono.just(new ResponseEntity(res,HttpStatus.OK));

                    }else {
                        return Mono.just(new ResponseEntity(HttpStatus.NOT_ACCEPTABLE));
                    }
                });
    }
}
