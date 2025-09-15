package com.divyanshu.backend.model;

import jakarta.persistence.*;


import lombok.*;


import java.util.ArrayList;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;




    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;


    @OneToMany(mappedBy = "module",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<SubModule> subModule = new ArrayList<>();


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


    public Course getCourse() {
        return course;
    }


    public void setCourse(Course course) {
        this.course = course;
    }


    public List<SubModule> getSubModule() {
        return subModule;
    }


    public void setSubModule(List<SubModule> subModule) {
        this.subModule = subModule;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
