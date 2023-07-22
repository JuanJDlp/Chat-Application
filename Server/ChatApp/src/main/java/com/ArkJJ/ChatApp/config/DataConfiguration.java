package com.ArkJJ.ChatApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ArkJJ.ChatApp.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataConfiguration {
    @Bean(name = "users")
    @Scope("singleton")
    public List<User> getUsers() {
        return new ArrayList<>();
    }
}