package com.purewash.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.purewash.models.Enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", length = 255)
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "account_balance", precision = 10, scale = 2)
    private Long accountBalance;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<WashingMachine> washingMachines;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CustomerAccountManagement> customerAccountManagements ;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<TransactionHistory> transactionHistories ;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Payment> payments;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Order> orderList;

    @Column(name = "status", nullable = false)
    private int status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private RoleEnum role;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "userName", nullable = false)
    private String userName;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return "";
    }
}
