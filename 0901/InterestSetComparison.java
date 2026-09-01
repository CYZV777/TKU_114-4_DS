import java.util.*;

public class InterestSetComparison {

    public static Set<String> getUnion(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set1);
        result.addAll(set2);
        return result;
    }

    public static Set<String> getIntersection(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set1);
        result.retainAll(set2);
        return result;
    }

    public static Set<String> getFirstOnly(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set1);
        result.removeAll(set2);
        return result;
    }

    public static Set<String> getSecondOnly(Set<String> set1, Set<String> set2) {
        Set<String> result = new HashSet<>(set2);
        result.removeAll(set1);
        return result;
    }

    public static void main(String[] args) {
        Set<String> userA = new HashSet<>(Arrays.asList("Music", "Coding", "Gaming", "Reading"));
        Set<String> userB = new HashSet<>(Arrays.asList("Gaming", "Cooking", "Coding", "Travel"));

        System.out.println("User A 興趣: " + userA);
        System.out.println("User B 興趣: " + userB);
        System.out.println("-----------------------------------");

        System.out.println("Union (全部興趣): " + getUnion(userA, userB));
        System.out.println("Intersection (共同興趣): " + getIntersection(userA, userB));
        System.out.println("First-only (僅 A 喜歡): " + getFirstOnly(userA, userB));
        System.out.println("Second-only (僅 B 喜歡): " + getSecondOnly(userA, userB));
        
        System.out.println("-----------------------------------");
        System.out.println("原 User A (未被改動): " + userA);
        System.out.println("原 User B (未被改動): " + userB);
    }
}