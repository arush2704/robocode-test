package org.example;


public class Student {
    String name;
    double[] grades;

    public Student(String name, double[] grades) {
        this.name = name;
        this.grades = grades;
    }

    public double calculateGPA() {
            if (grades == null || grades.length == 0) {
                return 0.0;
            }
            double total = 0;
            for (double grade : grades) {
                total += grade;
            }
            return total / grades.length;
        }

    public static void main(String[] args) {
        Student s = new Student("Alice", new double[]{});
        System.out.println("GPA: " + s.calculateGPA());
    }
}
