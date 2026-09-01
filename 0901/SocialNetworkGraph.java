import java.util.*;

public class SocialNetworkGraph {
    private Map<String, Set<String>> adjList;

    public SocialNetworkGraph() {
        adjList = new HashMap<>();
    }

    public void addUser(String user) {
        adjList.putIfAbsent(user, new HashSet<>());
    }

    public void addFriendship(String user1, String user2) {
        if (user1.equals(user2)) return;
        addUser(user1);
        addUser(user2);
        adjList.get(user1).add(user2);
        adjList.get(user2).add(user1);
    }

    public void removeFriendship(String user1, String user2) {
        if (adjList.containsKey(user1) && adjList.containsKey(user2)) {
            adjList.get(user1).remove(user2);
            adjList.get(user2).remove(user1);
        }
    }

    public Set<String> getMutualFriends(String user1, String user2) {
        if (!adjList.containsKey(user1) || !adjList.containsKey(user2)) {
            return Collections.emptySet();
        }
        Set<String> mutual = new HashSet<>(adjList.get(user1));
        mutual.retainAll(adjList.get(user2));
        return mutual;
    }

    public List<String> getIsolatedUsers() {
        List<String> isolated = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : adjList.entrySet()) {
            if (entry.getValue().isEmpty()) {
                isolated.add(entry.getKey());
            }
        }
        return isolated;
    }

    public static void main(String[] args) {
        SocialNetworkGraph network = new SocialNetworkGraph();
        network.addUser("David");
        network.addFriendship("Alice", "Bob");
        network.addFriendship("Alice", "Charlie");
        network.addFriendship("Bob", "Charlie");

        System.out.println("Alice 與 Bob 的共同好友: " + network.getMutualFriends("Alice", "Bob")); // [Charlie]
        System.out.println("孤立使用者: " + network.getIsolatedUsers());
        network.removeFriendship("Alice", "Bob");
        System.out.println("解除 Alice 與 Bob 好友後的共同好友: " + network.getMutualFriends("Alice", "Bob")); // [Charlie]
    }
}