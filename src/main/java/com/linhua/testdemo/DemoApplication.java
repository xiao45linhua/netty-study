package com.linhua.testdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author linhua
 * @version 1.0
 * @Description:
 * @date 2020/8/24$ 11:34 上午$
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DemoApplication {

    public static void main(String[] args) {
            SpringApplication.run(DemoApplication.class, args);
        }
}
