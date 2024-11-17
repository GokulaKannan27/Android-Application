package com.example.beat_box;

public class task {
    private String taskinput;
    private String description;

    public task(String taskinput, String description) {
        this.taskinput = taskinput;
        this.description = description;
    }

    public task() {
    }

    public String getTaskinput() {
        return taskinput;
    }

    public void setTaskinput(String taskinput) {
        this.taskinput = taskinput;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
