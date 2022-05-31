package com.company.procedure.affliction;

import com.company.user.patient.Patient;
import com.company.utils.KeyGenerator;

import java.util.*;

public class AfflictionService{
    private static AfflictionService instance = null;
    public static KeyGenerator<Affliction> afflictionKeyGenerator = new KeyGenerator<>();

    private AfflictionService() {
    }

    public static AfflictionService getInstance()
    {
        if (instance == null)
        {
            instance = new AfflictionService();
        }
        return instance;
    }

    public boolean checkAfflictions(List<Affliction> afflictions, List<Patient> patients)
    {
        if (afflictions.stream().map(Affliction::getId).distinct().count() != afflictions.size())
            return false;
        for (Affliction affliction:afflictions) {
            if (affliction.getPatientId() == null || patients.stream().filter(x->x.getId() == affliction.getPatientId()).count() != 1) {
                // Check each affliction to correspond with one patient
                return false;
            }
        }
        return true;
    }

    public Affliction findById(List<Affliction> afflictions, int diagnosis) {
        return afflictions.stream().filter(x->x.getId() == diagnosis).findFirst().orElse(null);
    }
}
