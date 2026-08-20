class Equipment {
    private String id;
    private String name;
    private int availableCount;

    public Equipment(String id, String name, int availableCount) {
        if (id == null || id.trim().isEmpty()) {
            this.id = "Unknown";
        } else {
            this.id = id;
        }

        if (name == null || name.trim().isEmpty()) {
            this.name = "Unknown";
        } else {
            this.name = name;
        }

        if (availableCount < 0) {
            this.availableCount = 0;
        } else {
            this.availableCount = availableCount;
        }
    }

    public boolean borrowOne() {
        if (this.availableCount > 0) {
            this.availableCount--;
            return true;
        }
        return false;
    }

    public void returnItems(int quantity) {
        if (quantity > 0) {
            this.availableCount += quantity;
        }
    }

    @Override
    public String toString() {
        return "設備編號: " + this.id + ", 名稱: " + this.name + ", 可借數量: " + this.availableCount;
    }
}

public class EquipmentInventory {
    public static void main(String[] args) {
        Equipment eq1 = new Equipment("E001", "筆記型電腦", 1);
        Equipment eq2 = new Equipment("", "投影機", -5);

        System.out.println("=== 初始狀態 ===");
        System.out.println(eq1);
        System.out.println(eq2);

        System.out.println("\n=== 測試借用與歸還 ===");
        System.out.println("eq1 借出 1 次: " + eq1.borrowOne());
        System.out.println("eq1 狀態: " + eq1);

        System.out.println("eq1 再次借出: " + eq1.borrowOne());
        System.out.println("eq1 狀態: " + eq1);

        eq1.returnItems(2);
        System.out.println("eq1 歸還 2 個後狀態: " + eq1);
    }
}