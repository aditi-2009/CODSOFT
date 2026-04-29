package StudentManagement;

import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_NAME = "students.txt";

    public static void save(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Student s : students) {
                writer.println(s.getRollNumber() + "," + s.getName() + "," + s.getGrade());
            }
        } catch (IOException e) {
            System.out.println("Error saving data");
        }
    }

    public static List<Student> load() {
        List<Student> students = new ArrayList<>();

        File file = new File(FILE_NAME);
        if (!file.exists()) return students;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 3) {
                    int roll = Integer.parseInt(data[0]);
                    String name = data[1];
                    String grade = data[2];

                    students.add(new Student(name, roll, grade));
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading data");
        }

        return students;
    }
}
