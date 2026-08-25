import java.util.ArrayDeque;
import java.util.Deque;

public class TextEditorHistory {
    private Deque<String> undoStack = new ArrayDeque<>();
    private Deque<String> redoStack = new ArrayDeque<>();

    public void addAction(String action) {
        undoStack.push(action);
        redoStack.clear();
        System.out.println("執行: " + action);
        printStatus();
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            System.out.println("無法 Undo: 沒有可復原的操作");
            return;
        }
        String action = undoStack.pop();
        redoStack.push(action);
        System.out.println("復原 (Undo): " + action);
        printStatus();
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("無法 Redo: 沒有可重做的操作");
            return;
        }
        String action = redoStack.pop();
        undoStack.push(action);
        System.out.println("重做 (Redo): " + action);
        printStatus();
    }

    public void printStatus() {
        System.out.println("  Undo Stack: " + undoStack);
        System.out.println("  Redo Stack: " + redoStack);
        System.out.println("------------------------------------");
    }

    public static void main(String[] args) {
        TextEditorHistory editor = new TextEditorHistory();

        editor.addAction("輸入 'Hello'");
        editor.addAction("輸入 ' World'");
        editor.addAction("刪除 'World'");

        editor.undo();
        editor.undo();

        editor.redo();

        editor.addAction("輸入 ' Java'");

        editor.redo();
        editor.undo();
        editor.undo();
        editor.undo();
        editor.undo();
    }
}