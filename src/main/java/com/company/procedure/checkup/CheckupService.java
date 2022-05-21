package com.company.procedure.checkup;

import com.company.procedure.affliction.Affliction;
import com.company.procedure.treatment.Treatment;

import java.util.*;

public class CheckupService {
    private static CheckupService instance = null;

    private CheckupService(){
    }

    public static CheckupService getInstance()
    {
        if (instance == null)
        {
            instance = new CheckupService();
        }
        return instance;
    }

    public void updateTreatments(List<Checkup> checkups, List<Treatment> treatments) throws Exception {
        for (Treatment treatment: treatments) {
            Checkup checkup = checkups.stream().filter(x -> x.getId() == treatment.getMedicalProcedure()).findFirst().orElse(null);
            if (checkup == null)
                throw new Exception("Medical procedure doesn't exist");
            else{
                List<Treatment> checkupTreatment = checkup.getTreatments();
                if (!checkupTreatment.contains(treatment))
                {
                    checkupTreatment.add(treatment);
                    checkup.setTreatments(checkupTreatment);
                }
            }
        }
    }

    public void updateAffliction(List<Checkup> checkups, List<Affliction> afflictions)  {
        for (Checkup checkup: checkups) {
            Affliction affliction = afflictions.stream().filter(x -> x.getCheckup() == checkup.getId()).findFirst().orElse(null);
            if (affliction == null)
                checkup.setDiagnosis(null);
            else{
                checkup.setDiagnosis(affliction);
            }
        }
    }
}
