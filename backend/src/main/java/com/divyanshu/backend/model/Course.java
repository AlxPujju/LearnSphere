package com.divyanshu.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;




    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Module> modules;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }




    public Instructor getInstructor() {
        return instructor;
    }


    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public  void addModule(Module module){
        modules.add(module);
        module.setCourse(this);
    }


    public List<Module> getModules() {
        return modules;
    }


    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
}
