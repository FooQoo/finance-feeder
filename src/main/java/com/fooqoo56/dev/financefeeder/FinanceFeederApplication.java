package com.fooqoo56.dev.financefeeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * アプリケーション起動クラス.
 */
@SpringBootApplication
@EnableConfigurationProperties
public class FinanceFeederApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FinanceFeederApplication.class, args);
    }
}
