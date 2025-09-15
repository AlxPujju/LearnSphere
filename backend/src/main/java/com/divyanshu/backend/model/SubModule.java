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
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    private  String title;


    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;


    @OneToMany(mappedBy = "subModule",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Document> documents = new ArrayList<>();


    public void setId(int id) {
        this.id = id;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setModule(Module module) {
        this.module = module;
    }


    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
}


