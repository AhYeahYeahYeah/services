package team.workflow.services.service;

public interface StorageService {
    // 库存锁定
    void StorageLock(String pid,String oid);

}
