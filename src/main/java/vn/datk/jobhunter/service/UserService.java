package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.dto.UserDTO;
import vn.datk.jobhunter.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public User createUser(User user) throws Exception {
        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        return userRepository.save(user);
    }
}
