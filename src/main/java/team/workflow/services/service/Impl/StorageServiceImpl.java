package team.workflow.services.service.Impl;

import org.springframework.stereotype.Service;
import team.workflow.services.repository.ProductRepository;
import team.workflow.services.service.StorageService;

import javax.annotation.Resource;

@Service
public class StorageServiceImpl implements StorageService {
    @Resource
    private ProductRepository productRepository;
    @Override
    public void StorageLock(String pid, String oid) {

    }
}
