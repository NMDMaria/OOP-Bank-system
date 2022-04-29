package com.company.utils;

import com.company.appointment.Appointment;
import com.company.appointment.Status;
import com.company.procedure.Severity;
import com.company.procedure.affliction.Affliction;
import com.company.procedure.checkup.Checkup;
import com.company.procedure.medicalprocedure.MedicalProcedure;
import com.company.procedure.surgery.Surgery;
import com.company.procedure.treatment.Treatment;
import com.company.user.Gender;
import com.company.user.doctor.Doctor;
import com.company.user.patient.Patient;
import com.company.user.user.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class CSVWriter<T> {
    String separator = ",";

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    private String toCSV(T object, Class<T> classType)
    {
        List<String> writeAux = new ArrayList<>();
        List<Field> fields = this.getAllFields(classType).stream().filter(x->!x.getType().isInterface()).collect(Collectors.toList());
        try {
            for (Field field:fields)
            {
                field.setAccessible(true);
                Class<?> type = field.getType();
                if(field.get(object) == null)
                    writeAux.add("null");
                else if (String.class.equals(type)) {
                    writeAux.add((String) field.get(object));
                } else if (Integer.class.equals(type)) {
                    writeAux.add(field.get(object).toString());
                } else if (Double.class.equals(type)) {
                    writeAux.add(field.get(object).toString());
                } else if (Float.class.equals(type)) {
                    writeAux.add(field.get(object).toString());
                } else  if (LocalDateTime.class.equals(type)) {
                    LocalDateTime aux = (LocalDateTime) field.get(object);
                    writeAux.add(aux.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                } else if (LocalTime.class.equals(type)) {
                    LocalTime aux = (LocalTime) field.get(object);
                    writeAux.add(aux.format(DateTimeFormatter.ofPattern("HH:mm")));
                } else if (LocalDate.class.equals(type)) {
                    LocalDate aux = (LocalDate) field.get(object);
                    writeAux.add(aux.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                } else if (Gender.class.equals(type)) {
                    writeAux.add(field.get(object).toString());
                } else if (Severity.class.equals(type)) {
                    writeAux.add(field.get(object).toString());
                } else if (Status.class.equals(type)) {
                    writeAux.add(field.get(object).toString());
                } else if (Affliction.class.equals(type)){
                    writeAux.add(((Affliction) field.get(object)).getId().toString());
                } else if (Appointment.class.equals(type)) {
                    writeAux.add(((Appointment) field.get(object)).getId().toString());
                } else if (Checkup.class.equals(type)) {
                    writeAux.add(((Checkup) field.get(object)).getId().toString());
                }else if (Surgery.class.equals(type)) {
                    writeAux.add(((Surgery) field.get(object)).getId().toString());
                }else if (MedicalProcedure.class.equals(type)) {
                    writeAux.add(((MedicalProcedure) field.get(object)).getId().toString());
                }else if (Treatment.class.equals(type)) {
                    writeAux.add(((Treatment) field.get(object)).getId().toString());
                }else if (Patient.class.equals(type)) {
                    writeAux.add(((Patient) field.get(object)).getId().toString());
                }else if (Doctor.class.equals(type)) {
                    writeAux.add(((Doctor) field.get(object)).getId().toString());
                }else if (User.class.equals(type)) {
                    writeAux.add(((User) field.get(object)).getId().toString());
                }
            }
            return writeAux.stream().collect(Collectors.joining(this.separator));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void toCSV(String directoryPath, List<T> objects, Class<T> classType, Boolean hasHeader)
    {
        try {
            String plural = classType.getSimpleName().toLowerCase().endsWith("y") ?
                    classType.getSimpleName().toLowerCase().substring(0, classType.getSimpleName().length() - 1).concat("ies")
                    : classType.getSimpleName().toLowerCase().concat("s");
            File file = new File(directoryPath + "\\" + plural + ".csv");
            file.createNewFile(); // create file if it doesn't exist
            FileOutputStream fout = new FileOutputStream(file);
            StringBuilder result = new StringBuilder();
            if (hasHeader == Boolean.TRUE)
            {
                result.append(getAllFields(classType).stream().filter(x->!x.getType().isInterface()).map(x->x.getName().toLowerCase()).collect(Collectors.joining(this.separator)));
                result.append("\n");
            }
            result.append(objects.stream().map(x->this.toCSV(x, classType)).collect(Collectors.joining("\n")));
            fout.write(result.toString().getBytes());
            fout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Field> getAllFields(Class<T> classType)
    {
        List<Field> result = new ArrayList<>();
        Class<?> clazz = classType;
        while (clazz != null) {
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
            result.addAll(0, fields);
            clazz = clazz.getSuperclass();
        }
        return result;
    }
}
