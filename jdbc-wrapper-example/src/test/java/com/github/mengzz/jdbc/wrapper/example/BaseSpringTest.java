package com.github.mengzz.jdbc.wrapper.example;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author mengzz
 **/
@SpringBootTest
@ExtendWith(SpringExtension.class)
// @ActiveProfiles(value = "mysql")
@ActiveProfiles(value = "h2")
public class BaseSpringTest {

    @SpringBootApplication(scanBasePackages = "com.github.mengzz.jdbc.wrapper.example")
    static class InnerConfig {
    }

}
