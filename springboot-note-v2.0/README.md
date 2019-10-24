# springboot-note-v2.0

> 学习资料：[尚硅谷SpringBoot全集](https://www.bilibili.com/video/av23478787/?p=4) 

## 杂记

### @Transaction手动回滚事务

+ 回滚当前事务

  ```java
  TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
  ```

+ 示例

  ```java
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void update(HySysUserMatchQuery hySysUserMatchQuery) {
    // 下面两个方法是其他带事务的方法
    deleteByMatchCode(hySysUserMatchQuery.getMatchCode());
    save(hySysUserMatchQuery,hySysUserMatchQuery.getMatchCode());
    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
  }
  ```

### 关闭自动配置

```properties
spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
```

