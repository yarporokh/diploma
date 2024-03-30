package com.nau.shop.controller.rest;

import com.nau.shop.dto.WorkerRegisterBody;
import com.nau.shop.model.User;
import com.nau.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @GetMapping("workers")
    public List<User> findALlWorkers() {
        return userService.findALlWorkers();
    }

    @PostMapping
    public void saveNewWorker(@RequestBody WorkerRegisterBody body) {
        System.out.println(body);
        userService.saveNewWorker(body);
    }
}
