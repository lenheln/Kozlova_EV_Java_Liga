package com.example.rest.repository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerDAO implements  ICustomerDAO{

    private final JdbcTemplate jdbcTemplate;

    /**
     * Возвращает id клиента, который сейчас подключен
     * Сейчас здесь просто заглушка. Всегда возвращается id = 1;
     */
    @Override
    public int getCurrentCustomerId(){
        return 1;
    }
}
