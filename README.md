# jdbc-wrapper


在spring data jdbc的继承上进行二次封装，增强spring data jdbc已有功能


## 特性


- 保留spring data jdbc所有功能，封装常用SQL语句
- 支持通过Java代码构建SQ语句
- 支持Java驼峰字段名自动转为SQL下划线列名
- 支持分页搜索
- 支持SQL拦截器



## 快速使用


### maven


```xml
        <dependency>
            <groupId>com.github.mengzz</groupId>
            <artifactId>jdbc-wrapper-spring-boot-starter</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```


### Java代码


实体类（使用Lombok）


```java
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Table
public class User {
    @Id
    private Long id;
    private String name;
    private String remarkMsg;
    private Integer age;
    @LastModifiedDate
    private Instant modifyTime;
    @CreatedDate
    private Instant createTime;

}

```


服务实现类

```java
@Service
public class UserDemoServiceImpl implements UserDemoService {
    @Autowired
    private JdbcWrapper jdbcWrapper;

    @Override
    public List<User> findByName(String name) {
        Table table = jdbcWrapper.table(User.class);
        ConditionWrapper wrapper = ConditionWrapper.of(table)
                .andEq(User.Fields.name, name);
        return jdbcWrapper.query(wrapper, User.class);
    }

}
```

以上查询的SQL等价语句：

```sql
SELECT user.id, user.name, user.remark_msg, user.age, user.modify_time, user.create_time FROM user WHERE user.name = 'test'
```
