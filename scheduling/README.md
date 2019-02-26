# scheduling

## 使用

+ 开启定时任务

  在配置类或启动类上标注`@EnableScheduling`

+ 配置线程池

  ```properties
  # 定时任务线程池大小，默认1
  spring.task.scheduling.pool.size=1
  # 定时任务线程池中线程名称前缀，默认 scheduling-
  spring.task.scheduling.thread-name-prefix=scheduling-
  ```

+ 将某方法定时执行

  ```java
  @Component
  @Transactional(rollbackFor = Exception.class)
  public class TokenClearTask {
      @Scheduled(cron = "0 0 0 * * ?")
      public void task(){
          tokenMapper.deleteAllExpiredToken(tokenExpireTime);
      }
  }
  ```

+ cron表达式

  参见[cron表达式](https://github.com/Mshuyan/springboot-quartz/tree/master/quartz#73-crontrigger)  

