package vn.datk.jobhunter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.datk.jobhunter.domain.Company;
import vn.datk.jobhunter.domain.User;
import vn.datk.jobhunter.domain.dto.UpdateUserDTO;
import vn.datk.jobhunter.domain.res.user.CompanyUser;
import vn.datk.jobhunter.domain.res.user.CreatedUserResponse;
import vn.datk.jobhunter.domain.res.ResultPaginationResponse;
import vn.datk.jobhunter.domain.res.user.UpdatedUserResponse;
import vn.datk.jobhunter.repository.UserRepository;
import vn.datk.jobhunter.util.error.IdInvalidException;
import vn.datk.jobhunter.util.response.FormatResultPagaination;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CompanyService companyService;
    private final PasswordEncoder passwordEncoder;

    public CreatedUserResponse createUser(User user) throws Exception {
        String phoneNumber = user.getPhoneNumber();
        String email = user.getEmail();

        if(user.getCompany() != null){
            Company company = this.companyService.findCompanyById(user.getCompany().getId());
            user.setCompany(company);
        }
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }
        if(userRepository.existsByEmail(email)) {
            throw new DataIntegrityViolationException("Email already exists");
        }

        String hashPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPassword);

        return this.convertToResCreatedUserRes(this.userRepository.save(user));
    }

    public CreatedUserResponse fetchUserById(Long id) throws Exception {
        if(userRepository.existsById(id)){
            CreatedUserResponse res = this.convertToResCreatedUserRes(this.userRepository.findById(id).get());
            return res;
        }else{
            throw new IdInvalidException("The specified User ID is invalid");
        }
    }

    public void deleteUser(Long id) throws Exception {
        if(this.userRepository.existsById(id)){
            this.userRepository.deleteById(id);
        }else{
         throw new IdInvalidException("The specified User ID is invalid");
        }
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }

    public ResultPaginationResponse getAllUser(Pageable pageable, Specification<User> spec){
        Page<User> userPage = this.userRepository.findAll(spec, pageable);
        ResultPaginationResponse response = FormatResultPagaination.createPaginateUserRes(userPage);

        return response;
    }

    public UpdatedUserResponse updateUser(Long id, UpdateUserDTO user) throws IdInvalidException {
        Optional<User> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent()){
            User currentUser = userOptional.get();
            currentUser.setName(user.getName());
            currentUser.setGender(user.getGender());
            currentUser.setAge(user.getAge());
            currentUser.setAddress(user.getAddress());

            if(user.getCompany() != null){
                Company company = this.companyService.findCompanyById(user.getCompany().getId());
                if(company == null){
                    throw new IdInvalidException("Company Id is invalid");
                }
                user.setCompany(company);
            }

            return this.convertToResUpdatedUserRes(this.userRepository.save(currentUser));
        }
        return null;
    }

    public void updateUserToken(String token, String email){
        User user = this.handleGetUserByUsername(email);
        if(user != null){
            user.setRefreshToken(token);
            this.userRepository.save(user);
        }
    }

    public User getUserByRefreshTokenAndEmail(String token, String email){
        return this.userRepository.findByRefreshTokenAndEmail(token, email);
    }

    public CreatedUserResponse convertToResCreatedUserRes(User user){
        CreatedUserResponse res = new CreatedUserResponse();
        CompanyUser companyUser = new CompanyUser();

        res.setId(user.getId());
        res.setEmail(user.getEmail());
        res.setAddress(user.getAddress());
        res.setPhoneNumber(user.getPhoneNumber());
        res.setAge(user.getAge());
        res.setCreatedDate(user.getCreatedDate());
        res.setGender(user.getGender());
        res.setName(user.getName());

        if(user.getCompany() != null){
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            res.setCompany(companyUser);
        }
        return res;
    }

    public UpdatedUserResponse convertToResUpdatedUserRes(User user){
        UpdatedUserResponse res = new UpdatedUserResponse();
        CompanyUser companyUser = new CompanyUser();
        res.setId(user.getId());
        res.setAddress(user.getAddress());
        res.setAge(user.getAge());
        res.setGender(user.getGender());
        res.setName(user.getName());
        res.setLastModifiedDate(user.getLastModifiedDate());
        if(user.getCompany() != null){
            companyUser.setId(user.getCompany().getId());
            companyUser.setName(user.getCompany().getName());
            res.setCompany(companyUser);
        }
        return res;
    }
}
