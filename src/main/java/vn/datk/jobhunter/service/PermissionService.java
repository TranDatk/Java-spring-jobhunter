package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.Permission;
import vn.datk.jobhunter.domain.Skill;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.repository.PermissionRepository;
import vn.datk.jobhunter.util.error.IdInvalidException;
import vn.datk.jobhunter.util.response.FormatResultPagaination;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public Permission create(Permission permission) throws Exception{
        if(this.permissionRepository.existsByModuleAndApiPathAndMethod(
                permission.getModule(),
                permission.getApiPath(),
                permission.getMethod()
        )){
            throw new DataIntegrityViolationException("Permission already exists");
        }
        return this.permissionRepository.save(permission);
    }

    public Permission fetchPermissionById(Long id) throws Exception {
        Optional<Permission> permission = this.permissionRepository.findById(id);
        if(permission.isPresent()){
            return permission.get();
        }else{
            throw new IdInvalidException("The specified Permission ID is invalid");
        }
    }

    public Permission update(Permission permission) throws Exception{
        Permission currentPermission = this.fetchPermissionById(permission.getId());

        if(this.permissionRepository.existsByModuleAndApiPathAndMethod(
                permission.getModule(),
                permission.getApiPath(),
                permission.getMethod()
        )){
            throw new DataIntegrityViolationException("Permission already exists");
        }

        if(this.permissionRepository.existsByName(permission.getName())){
            throw new DataIntegrityViolationException("Permission name already exists");
        }

        currentPermission.setApiPath(permission.getApiPath());
        currentPermission.setName(permission.getName());
        currentPermission.setModule(permission.getModule());
        currentPermission.setMethod(permission.getMethod());

        return this.permissionRepository.save(currentPermission);
    }

    public void delete(Long id) throws Exception {
        Permission permission = this.fetchPermissionById(id);

        permission.getRoles().forEach(
                role -> role.getPermissions().remove(permission)
        );
        this.permissionRepository.delete(permission);
    }

    public ResultPaginationResponse fetchAll(Specification<Permission> spec, Pageable pageable){
        Page<Permission> permissionPage = this.permissionRepository.findAll(spec, pageable);
        ResultPaginationResponse response = FormatResultPagaination.createPaginationResponse(permissionPage);
        return response;
    }
}
