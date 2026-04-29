package StudentManagement;

import java.awt.*;
import javax.swing.*;

public class StudentUI {
    private StudentService service = new StudentService();

    public StudentUI() {
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(650, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Student Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 8, 8));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        JTextField nameField = new JTextField();
        JTextField rollField = new JTextField();
        JTextField gradeField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Roll No:"));
        formPanel.add(rollField);
        formPanel.add(new JLabel("Grade:"));
        formPanel.add(gradeField);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 8, 8));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton searchBtn = new JButton("Search");
        JButton showBtn = new JButton("Show All");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(showBtn);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(centerPanel, BorderLayout.CENTER);

        JTextArea output = new JTextArea(6, 30);
        output.setFont(new Font("Monospaced", Font.PLAIN, 13));
        output.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));
        frame.add(scrollPane, BorderLayout.SOUTH);

        // ================= ACTIONS =================

        addBtn.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String grade = gradeField.getText().trim();
                int roll = Integer.parseInt(rollField.getText().trim());

                // ✅ FIX 1: validation improved
                if (name.isEmpty() || grade.isEmpty()) {
                    output.setText("All fields are required");
                    return;
                }

                // ✅ FIX 2: prevent duplicate roll
                if (service.searchStudent(roll) != null) {
                    output.setText("Roll number already exists");
                    return;
                }

                service.addStudent(new Student(name, roll, grade));
                output.setText("Student Added");

                // ✅ FIX 3: clear fields
                nameField.setText("");
                rollField.setText("");
                gradeField.setText("");
                nameField.requestFocus();

            } catch (Exception ex) {
                output.setText("Invalid Input");
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                int roll = Integer.parseInt(rollField.getText().trim());
                String name = nameField.getText().trim();
                String grade = gradeField.getText().trim();

                // ✅ FIX 4: validation added
                if (name.isEmpty() || grade.isEmpty()) {
                    output.setText("All fields are required");
                    return;
                }

                boolean updated = service.updateStudent(roll, name, grade);

                output.setText(updated ? "Updated Successfully" : "Student Not Found");

                // ✅ FIX 5: clear fields after update
                nameField.setText("");
                rollField.setText("");
                gradeField.setText("");

            } catch (Exception ex) {
                output.setText("Invalid Input");
            }
        });

        deleteBtn.addActionListener(e -> {
            try {
                int roll = Integer.parseInt(rollField.getText().trim());

                // ✅ FIX 6: proper delete check
                Student s = service.searchStudent(roll);
                if (s != null) {
                    service.removeStudent(roll);
                    output.setText("Student Deleted");
                } else {
                    output.setText("Student Not Found");
                }

                // clear fields
                nameField.setText("");
                rollField.setText("");
                gradeField.setText("");

            } catch (Exception ex) {
                output.setText("Invalid Input");
            }
        });

        searchBtn.addActionListener(e -> {
            try {
                int roll = Integer.parseInt(rollField.getText().trim());
                Student s = service.searchStudent(roll);

                // ✅ FIX 7: better output formatting
                if (s != null) {
                    output.setText(
                            "Roll: " + s.getRollNumber() +
                            "\nName: " + s.getName() +
                            "\nGrade: " + s.getGrade()
                    );
                } else {
                    output.setText("Student Not Found");
                }

            } catch (Exception ex) {
                output.setText("Invalid Input");
            }
        });

        showBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();

            for (Student s : service.getAllStudents()) {
                sb.append("Roll: ").append(s.getRollNumber())
                        .append(", Name: ").append(s.getName())
                        .append(", Grade: ").append(s.getGrade())
                        .append("\n");
            }

            output.setText(sb.toString());
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new StudentUI();
    }
}