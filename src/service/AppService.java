package service;

import model.*;
import storage.AppData;
import storage.FileManager;
import java.util.ArrayList;

public class AppService {
    private final FileManager fileManager = new FileManager();
    private final AppData data;
    private int itemSequence = 1;
    private int claimSequence = 1;

    public AppService() {
        data = fileManager.load();
        for (Item item : data.getItems()) itemSequence = Math.max(itemSequence, parseSequence(item.getItemId()) + 1);
        for (ClaimRequest claim : data.getClaims()) claimSequence = Math.max(claimSequence, parseSequence(claim.getClaimId()) + 1);
    }
    private int parseSequence(String id) {
        try { return Integer.parseInt(id.substring(1)); } catch (Exception e) { return 0; }
    }
    private void save() { fileManager.save(data); }

    public User login(String email, String password) {
        for (User user : data.getUsers()) {
            if (user.getEmail().equalsIgnoreCase(email.trim()) && user.passwordMatches(password)) return user;
        }
        return null;
    }
    public String register(String name, String studentId, String department, String email, String password) {
        if (name.isBlank() || studentId.isBlank() || department.isBlank() || email.isBlank() || password.isBlank())
            return "Please fill in all fields.";
        if (!email.contains("@")) return "Please enter a valid email address.";
        for (User user : data.getUsers()) {
            if (user.getEmail().equalsIgnoreCase(email.trim())) return "This email is already registered.";
            if (user instanceof Student s && s.getStudentId().equalsIgnoreCase(studentId.trim())) return "This student ID is already registered.";
        }
        String id = "U" + String.format("%03d", data.getUsers().size());
        data.getUsers().add(new Student(id, name.trim(), email.trim(), password, studentId.trim(), department.trim()));
        save();
        return null;
    }
    public void addLostItem(String name, String category, String description, String location, String date, Student student) {
        data.getItems().add(new LostItem(nextItemId(), name, category, description, location, date, student.getUserId())); save();
    }
    public void addFoundItem(String name, String category, String description, String location, String date, Student student) {
        data.getItems().add(new FoundItem(nextItemId(), name, category, description, location, date, student.getUserId())); save();
    }
    private String nextItemId() { return "I" + String.format("%03d", itemSequence++); }
    public String submitClaim(Item item, Student student, String message) {
        if (!(item instanceof FoundItem) || !"Found".equals(item.getStatus())) return "Only available found items can be claimed.";
        for (ClaimRequest c : data.getClaims()) {
            if (c.getItemId().equals(item.getItemId()) && c.getStudentId().equals(student.getUserId()) && !c.getStatus().equals("Rejected"))
                return "You already have a claim for this item.";
        }
        data.getClaims().add(new ClaimRequest("C" + String.format("%03d", claimSequence++), item.getItemId(), student.getUserId(), student.getName(), message));
        save(); return null;
    }
    public void updateClaim(String claimId, String status) {
        for (ClaimRequest c : data.getClaims()) if (c.getClaimId().equals(claimId)) { c.setStatus(status); break; }
        save();
    }
    public void markReturned(String itemId) {
        for (Item item : data.getItems()) if (item.getItemId().equals(itemId)) item.setStatus("Returned");
        save();
    }
    public void deleteItem(String itemId) {
        data.getItems().removeIf(i -> i.getItemId().equals(itemId));
        data.getClaims().removeIf(c -> c.getItemId().equals(itemId)); save();
    }
    public ArrayList<User> getUsers() { return data.getUsers(); }
    public ArrayList<Item> getItems() { return data.getItems(); }
    public ArrayList<ClaimRequest> getClaims() { return data.getClaims(); }
    public Item findItem(String id) { for (Item i : data.getItems()) if (i.getItemId().equals(id)) return i; return null; }
    public String getStudentName(String userId) {
        for (User u : data.getUsers()) if (u.getUserId().equals(userId)) return u.getName();
        return "Unknown";
    }
}
