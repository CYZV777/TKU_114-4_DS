import java.util.*;

public class LoginActivityReport {
    public static class LogEntry {
        String username;
        String ip;

        public LogEntry(String username, String ip) {
            this.username = username;
            this.ip = ip;
        }
    }

    public static void generateReport(List<LogEntry> logs, int alertThreshold) {
        Map<String, Integer> userLoginCount = new HashMap<>();
        Map<String, Set<String>> userUniqueIPs = new HashMap<>();
        Set<String> allDistinctIPs = new HashSet<>();

        for (LogEntry entry : logs) {
            String user = entry.username;
            String ip = entry.ip;

            userLoginCount.put(user, userLoginCount.getOrDefault(user, 0) + 1);

            userUniqueIPs.putIfAbsent(user, new HashSet<>());
            userUniqueIPs.get(user).add(ip);

            allDistinctIPs.add(ip);
        }

        System.out.println("========== 登入活動分析報告 ==========");
        System.out.println("全系統獨立 IP 總數: " + allDistinctIPs.size() + " " + allDistinctIPs);
        System.out.println("--------------------------------------");

        System.out.println("各使用者登入統計:");
        for (String user : userLoginCount.keySet()) {
            int count = userLoginCount.get(user);
            int ipCount = userUniqueIPs.get(user).size();
            System.out.printf("帳號: %-10s | 登入次數: %2d 次 | 使用不同 IP 數: %d%n", user, count, ipCount);
        }

        System.out.println("--------------------------------------");
        System.out.println("⚠️ 異常重複登入警示 (超過 " + alertThreshold + " 次):");
        boolean hasAlert = false;
        for (Map.Entry<String, Integer> entry : userLoginCount.entrySet()) {
            if (entry.getValue() > alertThreshold) {
                System.out.printf("  [警告] 帳號 %s 登入次數達 %d 次 (可能為暴力破解/異常刷登入)%n", 
                        entry.getKey(), entry.getValue());
                hasAlert = true;
            }
        }
        if (!hasAlert) {
            System.out.println("  無異常登入帳號。");
        }
        System.out.println("======================================");
    }

    public static void main(String[] args) {
        List<LogEntry> logs = Arrays.asList(
            new LogEntry("alice", "192.168.1.10"),
            new LogEntry("bob", "192.168.1.11"),
            new LogEntry("alice", "192.168.1.10"),
            new LogEntry("attacker", "10.0.0.1"),
            new LogEntry("attacker", "10.0.0.2"),
            new LogEntry("attacker", "10.0.0.3"),
            new LogEntry("attacker", "10.0.0.4"),
            new LogEntry("alice", "192.168.1.12"),
            new LogEntry("charlie", "192.168.1.15")
        );

        generateReport(logs, 3);
    }
}