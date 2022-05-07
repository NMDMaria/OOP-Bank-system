package com.company.utils;

import com.company.appointment.Status;
import com.company.procedure.Severity;
import com.company.user.Gender;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CSVReader<T> {
    // Generic CSV reader
    String separator = ",";

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public List<T> fromCSV(String file, Class<T> classType, boolean hasHeader)
    {
        // Checks the header, if it exists, with the fields of the class using reflection
        // Reading each record a new instance is made and each field is set and transformed
        // according to the application rules
        try (Scanner scanner = new Scanner(new File(file));) {
            List<T> result = new ArrayList<>();
            String line;
            List<String> stringList;
            List<Field> headerList;

            if (hasHeader == Boolean.TRUE)
            {
                if (!scanner.hasNextLine())
                    return result;
                line = scanner.nextLine();
                stringList = Arrays.asList(line.split(this.separator));
                headerList = new ArrayList<>();
                for (String s : stringList.stream().map(x -> x.toLowerCase()).collect(Collectors.toList())) {
                    Field field = this.getField(s,classType);
                    if (field == null)
                        throw new Exception("Invalid header.");
                    else if (field.getType().isInterface())
                        throw new Exception("Invalid header.");
                    else {
                        headerList.add(field);
                    }
                }
            }
            else
            {
                headerList = this.getAllFields(classType);
            }

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.equals("\n"))
                    break;
                stringList = Arrays.asList(line.split(this.separator));
                if (stringList.size() != headerList.size())
                    throw new Exception("Incorrect csv file.");

                List<Object> values = new ArrayList<>();
                List<Constructor> constructors = Arrays.asList(classType.getConstructors());
                Constructor emptyConstructor = constructors.stream().filter(x->x.getParameterCount() == 0).findFirst().get();
                T newInstance = (T) emptyConstructor.newInstance();

                for (Field field:headerList)
                {
                    if (field.getType().isInterface()) {
                        values.add(null);
                        continue;
                    }
                    for (int i = 0; i < stringList.size(); i++)
                    {
                        if (headerList.get(i).getName().equals(field.getName()))
                        {
                            Class<?> type = field.getType();
                            if (stringList.get(i).equalsIgnoreCase("null")) {
                                values.add(null);
                            } else if (String.class.equals(type)) {
                                values.add(stringList.get(i));
                            } else if (Integer.class.equals(type)) {
                                values.add(Integer.parseInt(stringList.get(i)));
                            } else if (Double.class.equals(type)) {
                                values.add(Double.parseDouble(stringList.get(i)));
                            } else if (Float.class.equals(type)) {
                                values.add(Float.parseFloat(stringList.get(i)));
                            } else if (LocalDateTime.class.equals(type)) {
                                values.add(LocalDateTime.parse(stringList.get(i), DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
                            } else if (LocalTime.class.equals(type)) {
                                values.add(LocalTime.parse(stringList.get(i), DateTimeFormatter.ofPattern("HH:mm")));
                            } else if (LocalDate.class.equals(type)) {
                                values.add(LocalDate.parse(stringList.get(i), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                            } else if (Gender.class.equals(type)) {
                                values.add(Gender.valueOf(stringList.get(i).toUpperCase()));
                            } else if (Severity.class.equals(type)) {
                                values.add(Severity.valueOf(stringList.get(i).toUpperCase()));
                            } else if (Status.class.equals(type)) {
                                values.add(Status.valueOf(stringList.get(i).toUpperCase()));
                            } else {// This is another defined class that will be initialized ulterior.
                                values.add(null);
                            }
                            field.setAccessible(true);
                            field.set(newInstance, values.get(values.size() - 1));
                            break;
                        }

                    }
                }

                if (values.size() != headerList.size())
                    throw new Exception("Not enough data");

                result.add(newInstance);
            }

            return result;
        }
        catch (FileNotFoundException e) {
            System.out.println("File doesn't exist.");
            // e.printStackTrace();
        } catch (NoSuchFieldException e) {
            System.out.println("Incorrect CSV header.");
            // e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private int findIndex(List<Field> fieldList, String fieldName)
    {
        return IntStream.range(0, fieldList.size())
                .filter(i -> fieldList.get(i).getName().equalsIgnoreCase(fieldName))
                .findFirst().orElse(-1);
    }

    private Field getField(String name, Class<T> classType) {
        Field field = null;
        Class<?> clazz = classType;
        while (clazz != null && field == null) {
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
            int index = this.findIndex(fields, name);
            if (index != -1)
                return fields.get(index);
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private List<Field> getAllFields(Class<T> classType)
    {
        // Provides all the fields of a class, including its parent class
        List<Field> result = new ArrayList<>();
        Class<?> clazz = classType;
        while (clazz != null) {
            List<Field> fields = Arrays.asList(clazz.getDeclaredFields());
            result.addAll(fields);
            clazz = clazz.getSuperclass();
        }
        return result;
    }
}
