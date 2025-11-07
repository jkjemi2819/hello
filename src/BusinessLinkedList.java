import java.util.HashSet;
import java.util.Set;

/**
 * Custom Linked List for managing Business objects
 * Used as a DSA component in Local Services Search System project
 */
public class BusinessLinkedList {

    // Node class to hold business data
    private static class Node {
        Business data;
        Node next;

        Node(Business data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head; // start of list
    private Node tail; // end of list
    private int size;

    // ✅ Add new business at end of list
    public void addBusiness(Business business) {
        Node newNode = new Node(business);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // ✅ Delete a business by its ID
    public boolean deleteBusinessById(String id) {
        if (head == null) return false;

        // if head node to be deleted
        if (head.data.getId().equalsIgnoreCase(id)) {
            head = head.next;
            size--;
            return true;
        }

        Node prev = head;
        Node curr = head.next;

        while (curr != null) {
            if (curr.data.getId().equalsIgnoreCase(id)) {
                prev.next = curr.next;
                if (curr == tail) tail = prev;
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }

        return false; // not found
    }

    // ✅ Search by Business Name
    public Business searchByName(String name) {
        Node current = head;
        while (current != null) {
            if (current.data.getName().equalsIgnoreCase(name)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    // ✅ Display all businesses
    public void displayAll() {
        if (head == null) {
            System.out.println("No businesses in the directory.");
            return;
        }

        Node current = head;
        System.out.println("\n--- Businesses in Linked List ---");
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }

    // ✅ Check if empty
    public boolean isEmpty() {
        return head == null;
    }

    // ✅ Return number of businesses
    public int size() {
        return size;
    }

    // ✅ Convert LinkedList to Set (for use in Directory class)
    public Set<Business> toSet() {
        Set<Business> set = new HashSet<>();
        Node current = head;
        while (current != null) {
            set.add(current.data);
            current = current.next;
        }
        return set;
    }
}
