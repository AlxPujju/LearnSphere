package com.divyanshu.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data


public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private String action;


    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference
    private  User user;


    @OneToOne(mappedBy = "log" , cascade = CascadeType.ALL,orphanRemoval = true)
    private Report report;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getAction() {
        return action;
    }


    public void setAction(String action) {
        this.action = action;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }


    public Report getReport() {
        return report;
    }


    public void setReport(Report report) {
        this.report = report;
    }
}
