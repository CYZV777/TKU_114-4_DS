public class Q08_RecursiveAudit {
    public static int sumValid(int[] data, int index) {
        if (data == null) return 0;
        if (index < 0) index = 0;
        if (index >= data.length) return 0;

        int current = (data[index] >= 0 && data[index] <= 100) ? data[index] : 0;
        return current + sumValid(data, index + 1);
    }

    public static int countOccurrences(int[] data, int index, int target) {
        if (data == null) return 0;
        if (index < 0) index = 0;
        if (index >= data.length) return 0;

        int match = (data[index] == target) ? 1 : 0;
        return match + countOccurrences(data, index + 1, target);
    }

    public static boolean isPalindrome(String text, int left, int right) {
        if (text == null) return false;
        if (left >= right) return true;

        char c1 = Character.toLowerCase(text.charAt(left));
        char c2 = Character.toLowerCase(text.charAt(right));

        if (c1 != c2) {
            return false;
        }
        return isPalindrome(text, left + 1, right - 1);
    }

    public static void main(String[] args) {
        int[] data = {10, -1, 20, 101, 20};
        System.out.println(Q08_RecursiveAudit.sumValid(data, 0));
        System.out.println(Q08_RecursiveAudit.countOccurrences(data, 0, 20));
        System.out.println(Q08_RecursiveAudit.isPalindrome("Level", 0, 4));
    }
}