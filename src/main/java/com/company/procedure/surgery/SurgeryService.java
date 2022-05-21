package com.company.procedure.surgery;

public class SurgeryService {
    private static SurgeryService instance = null;

    private SurgeryService() {
    }

    public static SurgeryService getInstance() {
        if (instance == null) {
            instance = new SurgeryService();
        }
        return instance;
    }

}
