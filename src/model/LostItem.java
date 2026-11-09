package model;

public class LostItem extends Item {
    private static final long serialVersionUID = 1L;
    public LostItem(String id, String name, String category, String description,
                    String lastSeenLocation, String date, String reporterId) {
        super(id, name, category, description, lastSeenLocation, date, "Lost", reporterId);
    }
    @Override public String getType() { return "Lost"; }
}
