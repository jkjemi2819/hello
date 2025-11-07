import java.util.*;

public class MainApp {
    static Directory directory = new Directory();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        loadSampleData();
        boolean running = true;

        while (running) {
            System.out.println("\n=== Local Services Search System ===");
            System.out.println("1. Add Business");
            System.out.println("2. Delete Business");
            System.out.println("3. Search by Name");
            System.out.println("4. Search by Category");
            System.out.println("5. Search by Keyword");
            System.out.println("6. Prefix Suggestion");
            System.out.println("7. Show Recent Searches");
            System.out.println("8. Show Search Logs");
            System.out.println("9. Display All Businesses");
            System.out.println("10. Most Searched Category");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1 -> addBusiness();
                case 2 -> deleteBusiness();
                case 3 -> searchByName();
                case 4 -> searchByCategory();
                case 5 -> searchByKeyword();
                case 6 -> prefixSuggestion();
                case 7 -> showRecent();
                case 8 -> showLogs();
                case 9 -> directory.displayAll();
                case 10 -> System.out.println("Most Searched Category: " + directory.mostSearchedCategory());
                case 0 -> running = false;
                default -> System.out.println("Invalid choice!");
            }
        }
        System.out.println("Thank you for using Local Services Search System!");
    }

    // Load sample data
    private static void loadSampleData() {
        directory.addBusiness(new Business("B1", "Mehta Plumbing", "Plumber", Set.of("pipe", "repair"), "Andheri East", 4.3, "999999"));
        directory.addBusiness(new Business("B2", "GlamLook Salon", "Salon", Set.of("hair", "style"), "Bandra", 4.8, "888888"));
        directory.addBusiness(new Business("B3", "TechFix Electronics", "Electrician", Set.of("tv", "repair"), "Kandivali", 4.0, "777777"));
        directory.addBusiness(new Business("B4", "FreshBites Cafe", "Cafe", Set.of("coffee", "snacks"), "Andheri West", 4.5, "666666"));
    }

    // Add new business
    private static void addBusiness() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Category: ");
        String category = sc.nextLine();
        System.out.print("Enter Location: ");
        String location = sc.nextLine();
        System.out.print("Enter Rating: ");
        double rating = Double.parseDouble(sc.nextLine());
        System.out.print("Enter Contact: ");
        String contact = sc.nextLine();
        System.out.print("Enter Keywords (comma separated): ");
        Set<String> keywords = new HashSet<>(Arrays.asList(sc.nextLine().split(",")));

        Business b = new Business(id, name, category, keywords, location, rating, contact);
        boolean added = directory.addBusiness(b);
        System.out.println(added ? "Business added successfully!" : "Business ID already exists!");
    }

    private static void deleteBusiness() {
        System.out.print("Enter ID to delete: ");
        String id = sc.nextLine();
        boolean deleted = directory.deleteBusiness(id);
        System.out.println(deleted ? "Deleted successfully!" : "Business not found!");
    }

    private static void searchByName() {
        System.out.print("Enter business name: ");
        Business b = directory.searchByName(sc.nextLine());
        System.out.println(b != null ? b : "No business found!");
    }

    private static void searchByCategory() {
        System.out.print("Enter category: ");
        directory.searchByCategory(sc.nextLine()).forEach(System.out::println);
    }

    private static void searchByKeyword() {
        System.out.print("Enter keyword: ");
        directory.searchByKeyword(sc.nextLine()).forEach(System.out::println);
    }

    private static void prefixSuggestion() {
        System.out.print("Enter prefix: ");
        List<String> list = directory.suggestByPrefix(sc.nextLine());
        System.out.println("Suggestions: " + list);
    }

    private static void showRecent() {
        System.out.println("Recent Searches: " + directory.getRecentSearches());
    }

    private static void showLogs() {
        System.out.println("Search Logs: " + directory.getSearchLogs());
    }
}
