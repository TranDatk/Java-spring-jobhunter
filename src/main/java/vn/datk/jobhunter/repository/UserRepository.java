package vn.datk.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.datk.jobhunter.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    User findByRefreshTokenAndEmail(String token,String email);
}
