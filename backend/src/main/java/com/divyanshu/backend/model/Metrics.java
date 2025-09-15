package com.divyanshu.backend.model;


public class Metrics {
    private long totalUsers;
    private long totalCourses;
    private long totalDocuments;


    public Metrics(long users, long courses, long documents) {
        this.totalUsers = users;
        this.totalCourses = courses;
        this.totalDocuments = documents;
    }
}
