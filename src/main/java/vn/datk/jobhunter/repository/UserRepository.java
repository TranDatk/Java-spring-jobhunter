package vn.datk.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.datk.jobhunter.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
}
