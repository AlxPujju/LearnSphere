package com.divyanshu.backend.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String subjects;
    private String description;


    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Course> courses = new ArrayList<>();




    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getSubjects() {
        return subjects;
    }


    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public List<Course> getCourses() {
        return courses;
    }


    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }
}
