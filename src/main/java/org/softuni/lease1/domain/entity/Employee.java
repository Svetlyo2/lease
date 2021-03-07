package org.softuni.lease1.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity{
    private String fullName;
    private String department;
    private String position;
    private User user;

    public Employee() {
    }

    @Column(name = "full_name", columnDefinition = "TEXT", nullable = false)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Column(name = "department", columnDefinition = "TEXT", nullable = false)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "position", columnDefinition = "TEXT", nullable = false)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
