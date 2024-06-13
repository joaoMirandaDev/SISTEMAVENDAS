package com.example.Sistema.Role.controller;


import com.example.Sistema.Role.model.Role;
import com.example.Sistema.Role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role/")
@Slf4j
public class RoleController {

    private final RoleService roleService;

    @GetMapping(produces = "application/json")
    @Operation(summary = "Listar roles", description = "Metodo utilizado para listar", tags = "Role")
    public List<Role> findAllRole() {
        return roleService.findAll();
    }

}


