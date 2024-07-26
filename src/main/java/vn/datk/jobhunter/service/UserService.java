package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.response.ResultPaginationResponse;
import vn.datk.jobhunter.repository.UserRepository;
import vn.datk.jobhunter.util.response.FormatResultPagaination;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) throws Exception {
        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();
        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        return userRepository.save(user);
    }

    public User handleGetUserByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public ResultPaginationResponse getAllUser(Pageable pageable, Specification<User> spec){
        Page<User> userPage = this.userRepository.findAll(spec, pageable);
        ResultPaginationResponse response = FormatResultPagaination.createPaginationResponse(userPage);

        return response;
    }
}
