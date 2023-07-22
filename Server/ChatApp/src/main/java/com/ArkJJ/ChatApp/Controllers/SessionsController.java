package com.ArkJJ.ChatApp.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ArkJJ.ChatApp.model.User;

@RestController
@RequestMapping("sessions")
public class SessionsController {

    @Autowired
    @Qualifier("users")
    public List<User> users;

    @GetMapping("/findAll")
    public ResponseEntity<List<Map<String, String>>> findAll() {
        List<Map<String, String>> data = new ArrayList<>();

        for (User user : users) {
            HashMap<String, String> obj = new HashMap<>();
            obj.put("id", user.getId());
            obj.put("username", user.getUsername());
            data.add(obj);
        }

        return ResponseEntity.ok(data);
    }
}
