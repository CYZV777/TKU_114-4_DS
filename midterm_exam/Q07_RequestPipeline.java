import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {
    public static boolean isBalanced(String text) {
        if (text == null) return false;
        if (text.isEmpty()) return true;

        Deque<Character> stack = new ArrayDeque<>();
        for (char ch : text.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) return false;
                char top = stack.pop();
                if (ch == ')' && top != '(') return false;
                if (ch == ']' && top != '[') return false;
                if (ch == '}' && top != '{') return false;
            }
        }
        return stack.isEmpty();
    }

    public static List<String> process(String[] commands) {
        if (commands == null) {
            return new ArrayList<>();
        }

        Deque<String> urgentQueue = new ArrayDeque<>();
        Deque<String> normalQueue = new ArrayDeque<>();
        List<String> result = new ArrayList<>();

        for (String cmd : commands) {
            if (cmd == null || cmd.isBlank()) continue;
            String trimmed = cmd.trim();

            if (trimmed.equals("PROCESS")) {
                if (!urgentQueue.isEmpty()) {
                    result.add(urgentQueue.poll());
                } else if (!normalQueue.isEmpty()) {
                    result.add(normalQueue.poll());
                } else {
                    result.add("EMPTY");
                }
            } else if (trimmed.startsWith("URGENT ")) {
                String id = trimmed.substring(7).trim();
                if (!id.isEmpty()) {
                    urgentQueue.offer(id);
                }
            } else if (trimmed.startsWith("NORMAL ")) {
                String id = trimmed.substring(7).trim();
                if (!id.isEmpty()) {
                    normalQueue.offer(id);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String[] commands = {
            "NORMAL N1", "URGENT U1", "NORMAL N2", "PROCESS", "PROCESS", "PROCESS"
        };
        System.out.println(Q07_RequestPipeline.isBalanced("a{b[c](d)}"));
        System.out.println(Q07_RequestPipeline.isBalanced("( )]"));
        System.out.println(Q07_RequestPipeline.process(commands));
    }
}