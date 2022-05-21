package com.company.procedure.medicalprocedure;

import com.company.app.App;
import com.company.utils.KeyGenerator;

import java.util.List;

public class MedicalProcedureService {
    private List<MedicalProcedure> medicalProcedures;
    private static MedicalProcedureService instance = null;
    public static KeyGenerator<MedicalProcedure> medicalProcedureKeyGenerator = new KeyGenerator<>();

    private MedicalProcedureService(){
        medicalProcedures = App.getInstance().getMedicalProcedures();
    }

    public static MedicalProcedureService getInstance()
    {
        if (instance == null)
        {
            instance = new MedicalProcedureService();
        }
        return instance;
    }


    public MedicalProcedure findById(Integer medicalProcedureId) {
        return medicalProcedures.stream().filter(x->x.getId() == medicalProcedureId).findFirst().orElse(null);
    }

    public boolean checkProcedures(List<MedicalProcedure> medicalProcedures) {
        // Checking if ids are distinct
        return medicalProcedures.stream().map(MedicalProcedure::getId).distinct().count() == medicalProcedures.size();
    }

}
