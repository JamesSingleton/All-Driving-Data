package com.example.jamessingleton.chffrapi.com.examples.jamessingleton.chffrapi.data;

/**
 * Created by frank on 8/31/16.
 */

/*
08-31 10:05:28.494 1321-1938/com.example.jamessingleton.chffrapi I/System.out:   "email": "blandonfrank@gmail.com",
08-31 10:05:28.494 1321-1938/com.example.jamessingleton.chffrapi I/System.out:   "id": "0ef38fada103ff73",
08-31 10:05:28.494 1321-1938/com.example.jamessingleton.chffrapi I/System.out:   "points": 0,
08-31 10:05:28.494 1321-1938/com.example.jamessingleton.chffrapi I/System.out:   "upload_video": false,
08-31 10:05:28.494 1321-1938/com.example.jamessingleton.chffrapi I/System.out:   "username": ""
 */
public class PersonalInfo {

    private String email, id, points, username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
