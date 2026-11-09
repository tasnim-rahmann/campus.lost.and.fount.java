package model;

public class FoundItem extends Item {
    private static final long serialVersionUID = 1L;
    public FoundItem(String id, String name, String category, String description,
                     String foundLocation, String date, String reporterId) {
        super(id, name, category, description, foundLocation, date, "Found", reporterId);
    }
    @Override public String getType() { return "Found"; }
}
