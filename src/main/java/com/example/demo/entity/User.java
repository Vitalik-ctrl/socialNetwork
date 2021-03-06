package com.example.demo.entity;

import com.example.demo.entity.enums.ERole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Data
@Entity
@Table(name="\"User\"")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String lastname;
    @Column(unique = true, updatable = false)
    private String username;
    @Column(unique = true)
    private String email;
    @Column(columnDefinition = "text")
    private String bio;
    @Column(length = 3000)
    private String password;
    @Column(updatable = false)
    private LocalDate Birthday;

    @ElementCollection(targetClass = ERole.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    // https://javarush.ru/groups/posts/2147-hashset-v-java
    private Set<ERole> role = new HashSet<>();
    // Каскадная модель one to many - к одному посту один пользователь, к одному пользователю много постов.
    //https://vladmihalcea.com/orphanremoval-jpa-hibernate/
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user", orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @Transient
    private Collection<? extends GrantedAuthority> authorities;

    public User(){
    }

    public User(Long id,
                String username,
                String email,
                String password,
                Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    // @PrePersist — аннотация используется для указания метода обратного вызова,
    // который срабатывает до того, как объект будет сохранен.
    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }


    //SECURITY

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
