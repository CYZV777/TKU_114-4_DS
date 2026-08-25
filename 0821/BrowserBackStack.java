import java.util.ArrayDeque;
import java.util.Deque;

public class BrowserBackStack {
    private Deque<String> historyStack = new ArrayDeque<>();

    public void visit(String url) {
        historyStack.push(url);
        System.out.println("造訪頁面: " + url);
        printStatus();
    }

    public void back() {
        if (historyStack.isEmpty()) {
            System.out.println("返回失敗: 歷史紀錄為空，無法返回！");
            return;
        }

        String poppedUrl = historyStack.pop();
        System.out.println("返回上一頁 (離開: " + poppedUrl + ")");
        
        if (historyStack.isEmpty()) {
            System.out.println("目前已無任何歷史頁面。");
        } else {
            System.out.println("目前停留在: " + historyStack.peek());
        }
        printStatus();
    }

    public void current() {
        if (historyStack.isEmpty()) {
            System.out.println("當前頁面: 空白頁 (No page opened)");
        } else {
            System.out.println("當前頁面: " + historyStack.peek());
        }
    }

    public void printStatus() {
        System.out.println("  當前 Stack 歷程: " + historyStack);
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        BrowserBackStack browser = new BrowserBackStack();

        System.out.println("=== 開始瀏覽器返回功能測試 ===");
        
        browser.current();

        browser.back();

        browser.visit("https://google.com");
        browser.visit("https://github.com");
        browser.visit("https://tku.edu.tw");

        browser.current();

        browser.back();

        browser.visit("https://stackoverflow.com");

        browser.back();
        browser.back();
        browser.back();
        browser.back();
    }
}