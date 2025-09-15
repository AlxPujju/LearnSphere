package com.divyanshu.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String path;
    private String version;
    private boolean encrypted;


    @ManyToOne
    @JoinColumn(name = "sub_module_id")
    private SubModule subModule;


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPath() {
        return path;
    }


    public void setPath(String path) {
        this.path = path;
    }


    public String getVersion() {
        return version;
    }


    public void setVersion(String version) {
        this.version = version;
    }


    public boolean isEncrypted() {
        return encrypted;
    }


    public void setEncrypted(boolean encrypted) {
        this.encrypted = encrypted;
    }


    public SubModule getSubModule() {
        return subModule;
    }


    public void setSubModule(SubModule subModule) {
        this.subModule = subModule;
    }
}


