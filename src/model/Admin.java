package model;

public class Admin extends User {
    private static final long serialVersionUID = 1L;
    private final String adminRole;

    public Admin(String userId, String name, String email, String password, String adminRole) {
        super(userId, name, email, password);
        this.adminRole = adminRole;
    }

    public String getAdminRole() { return adminRole; }
    @Override public String getRole() { return "Admin"; }
    @Override public void displayInfo() {
        System.out.println("Admin: " + getName() + " | " + adminRole);
    }
}
