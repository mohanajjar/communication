package fr.communication.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Long id;

    private String firstName;

    private String lastName;

    private String mailAdress;

    private String media;

    private String phoneNumber;

    private String passWord;

    @OneToMany(mappedBy = "user")
    public List<Role> roles = new ArrayList<>();


}
