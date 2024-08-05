package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.Permission;
import vn.datk.jobhunter.domain.Resume;
import vn.datk.jobhunter.domain.Role;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.repository.PermissionRepository;
import vn.datk.jobhunter.repository.RoleRepository;
import vn.datk.jobhunter.util.error.IdInvalidException;
import vn.datk.jobhunter.util.response.FormatResultPagaination;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public Role create(Role role) throws Exception{
        if(this.roleRepository.existsByName(
              role.getName()
        )){
            throw new DataIntegrityViolationException("Role already exists");
        }
        if(role.getPermissions() != null){
            List<Long> reqPermissions = role.getPermissions()
                    .stream().map(
                            permission -> permission.getId()
                    ).collect(Collectors.toList());
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
            role.setPermissions(dbPermissions);
        }
        return this.roleRepository.save(role);
    }

    public Role fetchRoleById(Long id) throws Exception {
        Optional<Role> role = this.roleRepository.findById(id);
        if(role.isPresent()){
            return role.get();
        }else{
            throw new IdInvalidException("The specified Role ID is invalid");
        }
    }

    public Role update(Role role) throws Exception{
        Role currentRole = this.fetchRoleById(role.getId());

        if(role.getPermissions() != null){
            List<Long> reqPermissions = role.getPermissions()
                    .stream().map(
                            Permission::getId
                    ).collect(Collectors.toList());
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(reqPermissions);
            role.setPermissions(dbPermissions);
        }

        currentRole.setName(role.getName());
        currentRole.setActive(role.isActive());
        currentRole.setDescription(role.getDescription());

        return this.roleRepository.save(currentRole);
    }

    public void delete(Long id) throws Exception {
        Role role = this.fetchRoleById(id);

        this.roleRepository.delete(role);
    }

    public ResultPaginationResponse fetchAll(Specification<Role> spec, Pageable pageable){
        Page<Role> rolePage = this.roleRepository.findAll(spec, pageable);
        ResultPaginationResponse response = FormatResultPagaination.createPaginationResponse(rolePage);
        return response;
    }
}
