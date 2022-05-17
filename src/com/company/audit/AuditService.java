package com.company.audit;

import com.company.appointment.Appointment;
import com.company.appointment.AppointmentService;
import com.company.utils.KeyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuditService {
    private static AuditService instance = null;
    private String filePath;

    private AuditService(String directoryPath) {
        try {
            File file = new File(directoryPath + "\\audit.csv");
            filePath = directoryPath + "\\audit.csv";
            if (!file.exists()) {
                file.createNewFile();
                try (FileOutputStream fout = new FileOutputStream(file, true)) {
                    List<String> header = Arrays.asList("action", "time");
                    fout.write(header.stream().collect(Collectors.joining(",")).getBytes());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService("./data");
        }
        return instance;
    }

    public void write(String action)
    {
        File file = new File(filePath);
        if (!file.exists())
            try {
                throw new Exception("Audit file doesn't exist.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        else{
            try (FileOutputStream fout = new FileOutputStream(file, true)) {
                StringBuilder record = new StringBuilder("\n");
                record.append(action);
                record.append(",");
                record.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                fout.write(record.toString().getBytes());
                fout.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
