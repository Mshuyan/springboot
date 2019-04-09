# spring-annotation

## @Profile

+ 作用域：配置类、bean方法上

+ 功能：仅在指定的环境下加载其标注的配置类或bean

+ 属性

  + value

    + 类型：Stirng[]

    + 功能：指定需要进行加载的环境

    + 有效值：`application.properties`文件中`spring.profile.active`的有效值就是`value`的有效值

    + 例

      ```java
      @Configuration
      @Profile("dev")	// 仅在dev环境加载该配置
      public class swaggerConfig{
        ...
      }
      ```

      

      