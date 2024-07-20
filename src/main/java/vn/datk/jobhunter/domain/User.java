package vn.datk.jobhunter.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name" ,length = 100, nullable = false)
    private String name;

    @Column(name = "phone_number" ,length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "email" ,length = 50, nullable = false)
    private String email;

    @Column(name = "address" ,length = 200)
    private String address;

    @Column(name = "password" ,length = 200)
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "facebook_account_id")
    private int facebookAccountId;

    @Column(name = "google_account_id")
    private int googleAccountId;
}
