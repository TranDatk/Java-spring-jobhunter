package vn.datk.jobhunter.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import vn.datk.jobhunter.domain.Permission;
import vn.datk.jobhunter.domain.Role;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.service.UserService;
import vn.datk.jobhunter.util.error.IdInvalidException;
import vn.datk.jobhunter.util.error.PermissionException;
import vn.datk.jobhunter.util.security.SecurityUtils;

import java.util.List;

@Transactional
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {
        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        String email = SecurityUtils.getCurrentUserLogin().isPresent()
                ? SecurityUtils.getCurrentUserLogin().get()
                : "";
        if(!email.isEmpty()){
            User user = this.userService.handleGetUserByUsername(email);
            if(user != null){
                Role role = user.getRole();
                if(role != null){
                    List<Permission> permissions = role.getPermissions();
                    boolean isAllow = permissions.stream().anyMatch(
                            permission -> permission.getApiPath().equals(path)
                                            &&
                                            permission.getMethod().equals(httpMethod)
                    );
                    if(!isAllow){
                        throw new PermissionException("You do not have permission to access this endpoint!!!");
                    }
                }else{
                    throw new PermissionException("You do not have permission to access this endpoint!!!");
                }
            }
        }

        return true;
    }
}
