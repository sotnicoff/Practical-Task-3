package ru.kata.sotnicoff.springrestful.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.sotnicoff.springrestful.model.Role;
import ru.kata.sotnicoff.springrestful.repository.RoleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Set<Role> getRoles(List<Long> roles){
        return roleRepository.findByIdIn(roles);
    }

    public List<Long> rolesToId (Set<Role> roles){
        List<Long> ids = new ArrayList<>();
        for (Role role : roles){
            ids.add(Long.parseLong(role.getName()));
        }
        return ids;
    }
}
