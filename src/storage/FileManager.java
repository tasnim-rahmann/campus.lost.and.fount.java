package storage;

import model.Admin;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    private static final String DATA_FILE = "data/appdata.dat";

    public AppData load() {
        try {
            Path path = Path.of(DATA_FILE);
            if (Files.exists(path)) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                    return (AppData) in.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load saved data: " + e.getMessage());
        }
        AppData data = new AppData();
        data.getUsers().add(new Admin("A001", "System Administrator",
                "admin@campus.com", "admin123", "Main Administrator"));
        return data;
    }

    public void save(AppData data) {
        try {
            Files.createDirectories(Path.of("data"));
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                out.writeObject(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not save data: " + e.getMessage(), e);
        }
    }
}
