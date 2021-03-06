package com.github.mengzz.jdbc.wrapper.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author mengzz
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Table
public class User {
    @Id
    private Long id;
    private String name;

}
