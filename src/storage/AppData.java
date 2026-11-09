package storage;

import model.*;
import java.io.Serializable;
import java.util.ArrayList;

public class AppData implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<ClaimRequest> claims = new ArrayList<>();
    public ArrayList<User> getUsers() { return users; }
    public ArrayList<Item> getItems() { return items; }
    public ArrayList<ClaimRequest> getClaims() { return claims; }
}
