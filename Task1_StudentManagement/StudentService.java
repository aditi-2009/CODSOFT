package StudentManagement;

import java.util.*;

public class StudentService {
    private final List<Student> students;

    public StudentService() {
        students = FileHandler.load();
    }

    public void addStudent(Student s) {
        students.add(s);
        FileHandler.save(students);
    }

    public void removeStudent(int rollNo) {
        students.removeIf(s -> s.getRollNumber() == rollNo);
        FileHandler.save(students);
    }

    public Student searchStudent(int rollNo) {
        for (Student s : students) {
            if (s.getRollNumber() == rollNo) {
                return s;
            }
        }
        return null;
    }

    public boolean updateStudent(int roll, String name, String grade) {
        Student s = searchStudent(roll);
        if (s != null) {
            s.setName(name);
            s.setGrade(grade);
            return true;
        }
        return false;
    }

    public List<Student> getAllStudents() {
        return students;
    }
}
