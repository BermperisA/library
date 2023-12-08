package com.app.library.models;

import com.app.library.enums.GenderEnum;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "library_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String firstName;
    private Date memberSince;
    private Date memberTill;
    private GenderEnum gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getMemberSince() {
        return memberSince;
    }

    public void setMemberSince(Date memberSince) {
        this.memberSince = memberSince;
    }

    public Date getMemberTill() {
        return memberTill;
    }

    public void setMemberTill(Date memberTill) {
        this.memberTill = memberTill;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }


    public User(){}
    public User(String name, String firstName, Date memberSince, Date memberTill, GenderEnum gender) {
        this.name = name;
        this.firstName = firstName;
        this.memberSince = memberSince;
        this.memberTill = memberTill;
        this.gender = gender;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name + firstName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof User) {
            return ((User) obj).getFirstName().equals(getFirstName()) && ((User) obj).getName().equals(getName());
        }

        return false;
    }
}
