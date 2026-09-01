import java.util.*;

public class WebsiteLinkGraph {
    private Map<String, Set<String>> outgoingMap;
    private Map<String, Set<String>> incomingMap;

    public WebsiteLinkGraph() {
        outgoingMap = new HashMap<>();
        incomingMap = new HashMap<>();
    }

    public void addPage(String page) {
        outgoingMap.putIfAbsent(page, new HashSet<>());
        incomingMap.putIfAbsent(page, new HashSet<>());
    }

    public void addLink(String fromPage, String toPage) {
        addPage(fromPage);
        addPage(toPage);
        outgoingMap.get(fromPage).add(toPage);
        incomingMap.get(toPage).add(fromPage);
    }

    public List<String> getNoIncomingPages() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : incomingMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public List<String> getNoOutgoingPages() {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : outgoingMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public void printReport() {
        System.out.println("====== 網站連結圖分析報告 ======");
        for (String page : outgoingMap.keySet()) {
            int incomingCount = incomingMap.get(page).size();
            System.out.printf("頁面: %-10s | 外部連結 (Outgoing): %s | 被連結數 (Incoming Count): %d%n",
                    page, outgoingMap.get(page), incomingCount);
        }
        System.out.println("--------------------------------");
        System.out.println("無 Incoming 頁面 (孤島/進入點): " + getNoIncomingPages());
        System.out.println("無 Outgoing 頁面 (死胡同頁面)  : " + getNoOutgoingPages());
        System.out.println("================================");
    }

    public static void main(String[] args) {
        WebsiteLinkGraph webGraph = new WebsiteLinkGraph();
        webGraph.addLink("Index.html", "About.html");
        webGraph.addLink("Index.html", "Product.html");
        webGraph.addLink("Product.html", "Checkout.html");
        webGraph.addPage("Contact.html");

        webGraph.printReport();
    }
}