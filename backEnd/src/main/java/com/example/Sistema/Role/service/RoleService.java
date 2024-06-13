package com.example.Sistema.Role.service;


import com.example.Sistema.Role.model.Role;
import com.example.Sistema.Role.repository.RoleRepository;
import com.example.Sistema.Utils.Interfaces.LocaleInteface;
import com.example.Sistema.Utils.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final MessageSource messageSource;

    public List<Role> findAll() {
       return roleRepository.findAll();
    }

    public Role findById(Integer id) {
      return  roleRepository.findById(id).orElseThrow(() -> new NotFoundException(
                messageSource.getMessage("error.isEmpty", null, LocaleInteface.BR)
        ));
    }
}
