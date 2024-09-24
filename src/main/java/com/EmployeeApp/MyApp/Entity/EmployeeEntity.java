package com.EmployeeApp.MyApp.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Employee")
public class EmployeeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name = "first_name",nullable = false)
    private String firstName;
    @Column(name = "last_Name")
    private String lastname;
    @Column(name = "Email" , unique = true)
    private String Email;

   // private String image;

    @Lob
    @Column(name = "image",columnDefinition = "BLOB")
    private byte[] image;


    public EmployeeEntity(){

    }
    public EmployeeEntity(Long id, String firstName, String lastname, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        Email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
