package com.example.springhateoas;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.springhateoas.domain.order.OrderRepository;
import com.example.springhateoas.domain.order.Orderstatus;
import com.example.springhateoas.domain.order.model.Order;
import com.example.springhateoas.domain.order.model.Orderline;

@EnableJpaRepositories
@SpringBootApplication
@Slf4j
public class SpringHateoasApplication {

    @Bean
    public CommandLineRunner run(OrderRepository orderRepository) {
        return args -> {
            log.info("Inserting test-data");

            orderRepository.saveAll(Arrays.asList(
                    Order.builder()
                            .orderstatus(Orderstatus.CREATED)
                            .build(),
                    Order.builder()
                            .orderstatus(Orderstatus.CREATED)
                            .orderlines(Collections.singletonList(
                                    Orderline.builder()
                                            .productId("42")
                                            .quantity(5)
                                            .build()
                            )).build(),
                    Order.builder()
                            .orderstatus(Orderstatus.SUBMITTED)
                            .orderlines(Collections.singletonList(
                                    Orderline.builder()
                                            .productId("43")
                                            .quantity(2)
                                            .build()
                            )).build()
            ));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringHateoasApplication.class, args);
    }
}
