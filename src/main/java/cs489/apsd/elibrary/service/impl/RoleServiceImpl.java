package cs489.apsd.elibrary.service.impl;

import cs489.apsd.elibrary.model.Role;
import cs489.apsd.elibrary.repository.RoleRepository;
import cs489.apsd.elibrary.service.RoleService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll(Sort.by("name"));
    }
}
