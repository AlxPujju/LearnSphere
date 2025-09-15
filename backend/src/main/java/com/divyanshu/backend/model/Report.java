package com.divyanshu.backend.model;




import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private String timeStamp;


    @OneToOne
    @JoinColumn(name = "log_id")
    private Log log;


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


    public String getTimeStamp() {
        return timeStamp;
    }


    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


    public Log getLog() {
        return log;
    }


    public void setLog(Log log) {
        this.log = log;
    }
}
