package com.company.Models;

public class Test {
    private String name;
    private String result = null;
    private String type; // what biological stuff was needed
    private Integer interval_lower_bound = null;
    private Integer interval_higher_bound = null;

    public Test(String name, String type, Integer interval_lower_bound, Integer interval_higher_bound) {
        // Test with both lower + upper bound
        this.name = name;
        this.type = type;
        this.interval_lower_bound = interval_lower_bound;
        this.interval_higher_bound = interval_higher_bound;
    }

    public Test(String name, String type) {
        // Test with positive/negative
        this.name = name;
        this.type = type;
    }

    public Test(String name, String type, Integer bound, Boolean which) {
        this.name = name;
        this.type = type;
        if (which)
            this.interval_higher_bound = bound;
        else this.interval_lower_bound = bound;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getInterval_lower_bound() {
        return interval_lower_bound;
    }

    public void setInterval_lower_bound(Integer interval_lower_bound) {
        this.interval_lower_bound = interval_lower_bound;
    }

    public Integer getInterval_higher_bound() {
        return interval_higher_bound;
    }

    public void setInterval_higher_bound(Integer interval_higher_bound) {
        this.interval_higher_bound = interval_higher_bound;
    }
}
