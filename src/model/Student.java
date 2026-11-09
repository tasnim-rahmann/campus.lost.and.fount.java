package model;

public class Student extends User {
    private static final long serialVersionUID = 1L;
    private final String studentId;
    private String department;

    public Student(String userId, String name, String email, String password,
                   String studentId, String department) {
        super(userId, name, email, password);
        this.studentId = studentId;
        this.department = department;
    }

    public String getStudentId() { return studentId; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    @Override public String getRole() { return "Student"; }
    @Override public void displayInfo() {
        System.out.println("Student: " + getName() + " | " + studentId + " | " + department);
    }
}
