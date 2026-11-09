package model;

import java.io.Serializable;

public abstract class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String itemId;
    private String itemName;
    private String category;
    private String description;
    private String location;
    private String date;
    private String status;
    private final String reportedBy;

    public Item(String itemId, String itemName, String category, String description,
                String location, String date, String status, String reportedBy) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.category = category;
        this.description = description;
        this.location = location;
        this.date = date;
        this.status = status;
        this.reportedBy = reportedBy;
    }

    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public String getReportedBy() { return reportedBy; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public void setCategory(String category) { this.category = category; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setDate(String date) { this.date = date; }
    public void setStatus(String status) { this.status = status; }
    public abstract String getType();
}
