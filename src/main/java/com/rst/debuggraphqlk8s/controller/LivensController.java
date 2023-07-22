package com.rst.debuggraphqlk8s.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ping")
public class LivensController {
    @GetMapping
    public String pong() {
        return "pong";
    }
}
