package com.company.procedure.treatment;

import com.company.utils.KeyGenerator;

import java.util.*;

import static java.lang.Math.max;

public class TreatmentService{
    private static TreatmentService instance = null;
    public static KeyGenerator<Treatment> treatmentKeyGenerator = new KeyGenerator<>();

    private TreatmentService(){
    }

    public static TreatmentService getInstance()
    {
        if (instance == null)
        {
            instance = new TreatmentService();
        }
        return instance;
    }

    public boolean checkTreatments(List<Treatment> treatments)
    {
        // Checking if ids are distinct
        return treatments.stream().map(Treatment::getId).distinct().count() == treatments.size();
    }

}
