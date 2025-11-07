import java.util.*;

public class Business {
    private String id;
    private String name;
    private String category;
    private Set<String> keywords;
    private String location;
    private double rating;
    private String contact;

    // Constructor
    public Business(String id, String name, String category, Set<String> keywords, String location, double rating, String contact) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.keywords = new HashSet<>(keywords);
        this.location = location;
        this.rating = rating;
        this.contact = contact;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public Set<String> getKeywords() { return keywords; }
    public String getLocation() { return location; }
    public double getRating() { return rating; }
    public String getContact() { return contact; }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %.1f | %s", id, name, category, location, rating, contact);
    }
}
