package com.aviato.Types;

// Helper class for appointments table
public class Appointment {
    private String details;
    private String time;

    public Appointment(String details, String time) {
        this.details = details;
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public String getTime() {
        return time;
    }
}
