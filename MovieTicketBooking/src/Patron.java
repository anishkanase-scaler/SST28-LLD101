import java.util.Objects;

public class Patron {
    private final String id;
    private final String name;
    private final String email;
    private final PatronRole role;

    public Patron(String id, String name, String email, PatronRole role) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.email = Objects.requireNonNull(email).toLowerCase();
        this.role = Objects.requireNonNull(role);
    }

    public String getId()       { return id; }
    public String getName()     { return name; }
    public String getEmail()    { return email; }
    public PatronRole getRole() { return role; }

    @Override
    public String toString() {
        return name + " (" + role + ")";
    }
}
