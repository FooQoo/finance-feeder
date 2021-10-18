package com.fooqoo56.dev.financefeeder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * アプリケーション起動クラス.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class FinanceFeederApplication {

    public static void main(final String[] args) {
        SpringApplication.run(FinanceFeederApplication.class, args);
    }
}
