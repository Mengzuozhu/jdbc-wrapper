package com.github.mengzz.jdbc.wrapper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mengzz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {
    private String name;
    private Integer age;
    private Long money;
    private String remarkMsg;

}
