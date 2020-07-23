package Object;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class NewStudyGroup implements Serializable {
    private Scanner reader = new Scanner(System.in);

    private String readString(String name) {
        System.out.print("Введите " + name +": ");
        return reader.nextLine();
    }

    private String readStringNotNull(String name) {
        System.out.print("Введите " + name +": ");
        String n = reader.nextLine();
        if (n.equals("")) {
            System.out.println("Поле не может быть null или пустой строкой ");
            return readStringNotNull(name);
        } else return n;
    }

    private Number readNumber(String name,String format) {
        String n = readStringNotNull(name);
        try {
            switch (format) {
                case "Float":
                    return Float.parseFloat(n);
                case "Integer":
                    return Integer.parseInt(n);
                case "Long":
                    return Long.parseLong(n);
                case "Double":
                    return Double.parseDouble(n);
                default:
                    return 0;
            }
        } catch (NumberFormatException ex) {
            System.out.println("Аргумент не является значением типа " + format);
            return readNumber(name,format);
        }
    }

    private Semester readSemester(String name) {
        String n = readStringNotNull(name);
        try {
            return Semester.valueOf(n);
        } catch (IllegalArgumentException ex) {
            System.out.println("Значение поля неверное");
            return readSemester(name);
        }
    }

    public Person newPerson() {
        System.out.println("Введите groupAdmin: ");
        String name = readStringNotNull("name");
        Double height = (Double) readNumber("height","Double");
        while (height <= 0) {
            System.out.println("Значение поля должно быть больше 0");
            height = (Double) readNumber("height","Double");
        }
        long weight = (long) readNumber("weight","Long");
        while (weight <= 0) {
            System.out.println("Значение поля должно быть больше 0");
            weight = (long) readNumber("weight","Long");
        }
        System.out.println("Введите location: ");
        Location location = newLocation();
        return new Person(name,height,weight,location);
    }

    private Location newLocation() {
        double x = (double) readNumber("x","Double");
        long y = (long) readNumber("y","Long");
        Integer z = (Integer) readNumber("z","Integer");
        String name = readStringNotNull("name");
        return new Location(x,y,z,name);
    }

    /**
     * Получает значения элемента в коллекции
     */
    public StudyGroup newGroup() {
        String name = readStringNotNull("name");
        System.out.println("Введите coordinates: ");
        float x = (Float) readNumber("x","Float");
        Long y = (Long) readNumber("y","Long");
        int studentsCount = (int) readNumber("studentsCount","Integer");
        while (studentsCount <= 0) {
            System.out.println("Значение поля должно быть больше 0");
            studentsCount = (int) readNumber("studentsCount","Integer");
        }
        long expelledStudents = (long) readNumber("expelledStudents","Long");
        while (expelledStudents <= 0) {
            System.out.println("Значение поля должно быть больше 0");
            expelledStudents = (long) readNumber("expelledStudents","Long");
        }
        Float averageMark = (Float) readNumber("averageMark","Float");
        while (averageMark <= 0) {
            System.out.println("Значение поля должно быть больше 0");
            averageMark = (Float) readNumber("averageMark","Float");
        }
        Semester semester = readSemester("Semester (SECOND, THIRD, FOURTH)");
        Person admin = newPerson();

        int id = 0;
        ZonedDateTime creationDate = ZonedDateTime.now();
        System.out.println("Все значения элемента успешно получены");
        return new StudyGroup(id, name, new Coordinates(x, y), creationDate, studentsCount, expelledStudents, averageMark, semester,admin);
    }

}
