package com.example.springbootroutingdatasource.controller;

import com.example.springbootroutingdatasource.mapper.NameMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/route")
class NameController {

    private NameMapper nameMapper;
    public NameController(NameMapper nameMapper) {
        this.nameMapper = nameMapper;
    }

    @GetMapping("/testMaster")
    public String getA() {
        return nameMapper.getNameMaster();
    }

    @GetMapping("/testSlave")
    public String getB() {
        return nameMapper.getNameSlave();
    }
}
