package com.company.didntmakethecut;

import com.company.Medicine.Doctor;
import com.company.Medicine.Medical_procedure;

import java.time.LocalTime;

public class Lab_test extends Medical_procedure {
    private Test[] tests;
    private Boolean finished; // analized??
    private Doctor validated_by;

    public Lab_test(LocalTime start_time, Test[] tests) {
        super(start_time);
        this.tests = tests;
    }

    public Lab_test(Integer start_hour, Integer start_minute, Test[] tests) {
        super(start_hour, start_minute);
        this.tests = tests;
    }

    public Test[] getTests() {
        return tests;
    }

    public Boolean getFinished() {
        return finished;
    }

    public Doctor getValidated_by() {
        return validated_by;
    }

    public void setTests(Test[] tests) {
        this.tests = tests;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public void setValidated_by(Doctor validated_by) {
        this.validated_by = validated_by;
    }
}
