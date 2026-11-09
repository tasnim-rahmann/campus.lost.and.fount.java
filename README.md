# Campus Lost & Found Management System

A beginner-friendly Java Swing desktop application for a university campus. It demonstrates OOP, ArrayList, and local file persistence using Java object serialization. No database or external libraries are required.

## Requirements
- JDK 17 or later
- IntelliJ IDEA, Eclipse, or another Java IDE

## Run in IntelliJ IDEA
1. Extract/open the `CampusLostAndFound` folder.
2. Make sure `src` is marked as the Sources Root if your IDE does not detect it automatically.
3. Open `src/Main.java`.
4. Run `Main`.

## Run from a terminal
From the project root:

```bash
javac -d out src/model/*.java src/storage/*.java src/service/*.java src/gui/*.java src/Main.java
java -cp out Main
```

## Demo admin account
- Email: `admin@campus.com`
- Password: `admin123`

Students can create an account from the registration screen.

## Main features
- Student registration and login
- Admin login
- Report lost and found items
- Browse, search, and filter item reports
- Submit claims for available found items
- Admin can approve/reject claims
- Admin can mark an approved item as returned
- Admin can delete item reports
- Dashboard statistics
- Local data persistence

## Data storage
The application stores all users, items, and claims in `data/appdata.dat` using `ObjectOutputStream` / `ObjectInputStream`. This file is created automatically after the first data change. Keep the `data` folder with the project if you want to retain saved records.

## OOP concepts demonstrated
- **Encapsulation:** model fields are private and accessed through methods.
- **Abstraction:** `User` and `Item` are abstract classes.
- **Inheritance:** `Student` and `Admin` extend `User`; `LostItem` and `FoundItem` extend `Item`.
- **Polymorphism:** `getRole()`, `displayInfo()`, and `getType()` are overridden in subclasses; items are stored as `ArrayList<Item>`.
- **Composition/has-a relationship:** `AppData` contains collections of users, items, and claims.

## Notes / limitations
- This is an educational, single-computer demo. It is not production authentication; passwords are stored locally in the serialized data file.
- The admin account is created automatically when no saved data file exists.
- To reset the demo data, close the application and delete `data/appdata.dat`.
- Dates must be entered in `YYYY-MM-DD` format.
# campus-lost-and-found-with-java
# campus.lost.and.fount.java
# campus.lost.and.fount.java
