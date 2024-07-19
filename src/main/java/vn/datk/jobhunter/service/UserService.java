package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

}
