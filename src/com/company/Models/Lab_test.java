package com.company.Models;

import java.time.LocalDateTime;

public class Lab_test extends Medical_procedure{
    private Test[] tests;
    private Boolean finished; // analized??
    private Healthcare_employee validated_by;

    public Lab_test(LocalDateTime start_time, Test[] tests) {
        super(start_time);
        this.tests = tests;
    }

    public Lab_test(Integer start_day, Integer start_month, Integer start_year, Integer start_hour, Integer start_minute, Test[] tests) {
        super(start_day, start_month, start_year, start_hour, start_minute);
        this.tests = tests;
    }

    public Test[] getTests() {
        return tests;
    }

    public Boolean getFinished() {
        return finished;
    }

    public Healthcare_employee getValidated_by() {
        return validated_by;
    }

    public void setTests(Test[] tests) {
        this.tests = tests;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public void setValidated_by(Healthcare_employee validated_by) {
        this.validated_by = validated_by;
    }
}
