import java.util.*;
import java.util.stream.Collectors;

public class Directory {
    private Map<String, Business> businessMap = new HashMap<>();
    private Map<String, List<Business>> categoryMap = new HashMap<>();
    private Map<String, List<Business>> keywordMap = new HashMap<>();
    private TreeSet<String> nameSet = new TreeSet<>();
    private PriorityQueue<Business> topRated = new PriorityQueue<>((a, b) -> Double.compare(b.getRating(), a.getRating()));

    private Deque<String> recentSearches = new ArrayDeque<>();
    private Queue<String> searchLog = new LinkedList<>();

    // Add a new business
    public boolean addBusiness(Business b) {
        if (businessMap.containsKey(b.getId())) return false;

        businessMap.put(b.getId(), b);
        categoryMap.computeIfAbsent(b.getCategory().toLowerCase(), k -> new ArrayList<>()).add(b);

        for (String kw : b.getKeywords()) {
            keywordMap.computeIfAbsent(kw.toLowerCase(), k -> new ArrayList<>()).add(b);
        }

        nameSet.add(b.getName().toLowerCase());
        topRated.offer(b);
        return true;
    }

    // Delete business by ID
    public boolean deleteBusiness(String id) {
        Business b = businessMap.remove(id);
        if (b == null) return false;

        categoryMap.getOrDefault(b.getCategory().toLowerCase(), new ArrayList<>()).remove(b);
        for (String kw : b.getKeywords()) {
            keywordMap.getOrDefault(kw.toLowerCase(), new ArrayList<>()).remove(b);
        }

        nameSet.remove(b.getName().toLowerCase());
        topRated.remove(b);
        return true;
    }

    // Search by exact name
    public Business searchByName(String name) {
        logSearch("Name", name);
        return businessMap.values().stream()
                .filter(b -> b.getName().equalsIgnoreCase(name))
                .findFirst().orElse(null);
    }

    // Search by category
    public List<Business> searchByCategory(String category) {
        logSearch("Category", category);
        return categoryMap.getOrDefault(category.toLowerCase(), Collections.emptyList());
    }

    // Search by keyword
    public List<Business> searchByKeyword(String keyword) {
        logSearch("Keyword", keyword);
        return keywordMap.getOrDefault(keyword.toLowerCase(), Collections.emptyList());
    }

    // Prefix search suggestions
    public List<String> suggestByPrefix(String prefix) {
        logSearch("Prefix", prefix);
        String low = prefix.toLowerCase();
        String high = low + Character.MAX_VALUE;
        return nameSet.subSet(low, true, high, true)
                .stream().limit(5).collect(Collectors.toList());
    }

    // Filter and sort results
    public List<Business> sortBusinesses(List<Business> list, String sortBy) {
        if (sortBy.equalsIgnoreCase("rating")) {
            list.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        } else {
            list.sort(Comparator.comparing(Business::getName));
        }
        return list;
    }

    // Log and recent search stack handling
    private void logSearch(String type, String term) {
        searchLog.offer(type + ":" + term);
        if (!recentSearches.isEmpty() && recentSearches.peek().equalsIgnoreCase(term)) return;
        recentSearches.push(term);
        if (recentSearches.size() > 5) recentSearches.removeLast();
    }

    // Getters for logs and stacks
    public List<String> getRecentSearches() {
        return new ArrayList<>(recentSearches);
    }

    public List<String> getSearchLogs() {
        return new ArrayList<>(searchLog);
    }

    // Display all businesses
    public void displayAll() {
        businessMap.values().stream()
                .sorted(Comparator.comparing(Business::getName))
                .forEach(System.out::println);
    }

    // Analytics: Most searched category
    public String mostSearchedCategory() {
        Map<String, Integer> freq = new HashMap<>();
        for (String s : searchLog) {
            if (s.startsWith("Category:")) {
                String cat = s.split(":", 2)[1];
                freq.put(cat, freq.getOrDefault(cat, 0) + 1);
            }
        }
        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse("N/A");
    }
}
