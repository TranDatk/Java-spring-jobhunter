package vn.datk.jobhunter.domain;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name" ,length = 100, nullable = false)
    private String name;

    @Column(name = "phoneNumber" ,length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "email" ,length = 50, nullable = false)
    private String email;

    @Column(name = "address" ,length = 200)
    private String address;

    @Column(name = "password" ,length = 200)
    private String password;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
}
