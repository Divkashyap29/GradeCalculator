
 import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GradeCalculator extends JFrame {
 
    private JTextField courseNameField, gradeField, creditsField;
    private JTextArea outputArea;
    private ArrayList<CourseInfo> courseList;



       public GradeCalculator() {
        setTitle("Grade Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 450);
        setLocationRelativeTo(null);

        getRootPane().setBackground(Color.WHITE);

        courseList = new ArrayList<>();

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBackground(Color.YELLOW); // Set background color for inputPanel

        inputPanel.add(new JLabel("Course Name:"));
        courseNameField = new JTextField();
        courseNameField.setForeground(Color.BLACK); // Set text color to white
        inputPanel.add(courseNameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        gradeField.setForeground(Color.BLACK); // Set text color to white
        inputPanel.add(gradeField);

        inputPanel.add(new JLabel("Credits:"));
        creditsField = new JTextField();
        creditsField.setForeground(Color.BLACK); // Set text color to white
        inputPanel.add(creditsField);

        JButton addButton = new JButton("Add Course");
        addButton.addActionListener(new AddCourseHandler());
        inputPanel.add(addButton);

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetHandler());
        inputPanel.add(resetButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setForeground(Color.WHITE); // Set text color to white
        outputArea.setBackground(Color.DARK_GRAY); // Set background color for outputArea

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK); // Set background color for mainPanel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        add(mainPanel);
    }

    private class AddCourseHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String courseName = courseNameField.getText();
            String grade = gradeField.getText();
            String creditsString = creditsField.getText();

            if (!courseName.isEmpty() && !grade.isEmpty() && !creditsString.isEmpty()) {
                double gradeValue = getGradeValue(grade);
                if (gradeValue != -1) {
                    int credits;
                    try {
                        credits = Integer.parseInt(creditsString);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(GradeCalculator.this, "Invalid credits entered.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    courseList.add(new CourseInfo(courseName, grade, gradeValue, credits));
                    courseNameField.setText("");
                    gradeField.setText("");
                    creditsField.setText("");
                    calculateGPA();
                } else {
                    JOptionPane.showMessageDialog(GradeCalculator.this, "Invalid grade entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(GradeCalculator.this, "Please enter a course name, grade, and credits.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class ResetHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            courseList.clear();
            outputArea.setText("");
            courseNameField.setText("");
            gradeField.setText("");
            creditsField.setText("");
        }
    }

    private double getGradeValue(String grade) {
        Map<String, Double> gradeMap = new HashMap<>();
        gradeMap.put("A+", 4.3);
        gradeMap.put("A", 4.0);
        gradeMap.put("A-", 3.7);
        gradeMap.put("B+", 3.3);
        gradeMap.put("B", 3.0);
        gradeMap.put("B-", 2.7);
        gradeMap.put("C+", 2.3);
        gradeMap.put("C", 2.0);
        gradeMap.put("C-", 1.7);
        gradeMap.put("D+", 1.3);
        gradeMap.put("D", 1.0);
        gradeMap.put("F", 0.0);

        if (gradeMap.containsKey(grade.toUpperCase())) {
            return gradeMap.get(grade.toUpperCase());
        } else {
            return -1;
        }
    }

    private void calculateGPA() {
        double totalGradePoints = 0;
        int totalCredits = 0;

        if (courseList.isEmpty()) {
            outputArea.setText("No courses added yet.");
            return;
        }

        StringBuilder resultBuilder = new StringBuilder();
        resultBuilder.append("Courses:\n");

        for (CourseInfo course : courseList) {
            double gradePoints = course.getGradeValue() * course.getCredits();
            totalGradePoints += gradePoints;
            totalCredits += course.getCredits();
            resultBuilder.append(course.getCourseName()).append(": ").append(course.getGrade()).append(" (").append(course.getCredits()).append(" credits)").append("\n");
        }

        double gpa = totalGradePoints / totalCredits;
        resultBuilder.append("\nGPA: ").append(String.format("%.2f", gpa));

        outputArea.setText(resultBuilder.toString());
    }

   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GradeCalculator().setVisible(true);
            }
        });
    }
}
