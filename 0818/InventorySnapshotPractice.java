import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;

    public InventorySnapshot(String warehouseId, int[] quantities) {
        if (warehouseId == null || warehouseId.trim().isEmpty()) {
            this.warehouseId = "Unknown";
        } else {
            this.warehouseId = warehouseId;
        }

        if (quantities == null) {
            this.quantities = new int[0];
        } else {
            this.quantities = Arrays.copyOf(quantities, quantities.length);
        }
    }

    public String getWarehouseId() {
        return this.warehouseId;
    }

    public int[] getQuantities() {
        return Arrays.copyOf(this.quantities, this.quantities.length);
    }

    public int totalQuantity() {
        int total = 0;
        for (int q : this.quantities) {
            total += q;
        }
        return total;
    }

    public int outOfStockCount() {
        int count = 0;
        for (int q : this.quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "倉庫編號: " + this.warehouseId + ", 庫存明細: " + Arrays.toString(this.quantities);
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] originalStock = {5, 0, 3, 0};
        InventorySnapshot snapshot = new InventorySnapshot("WH001", originalStock);

        System.out.println("=== 初始快照 ===");
        System.out.println(snapshot);
        System.out.println("總數量: " + snapshot.totalQuantity());
        System.out.println("缺貨品項數: " + snapshot.outOfStockCount());

        System.out.println("\n=== 測試 Defensive Copy ===");
        originalStock[0] = 999;
        int[] extractedStock = snapshot.getQuantities();
        if (extractedStock.length > 0) {
            extractedStock[1] = 888;
        }
        System.out.println("外部修改後快照: " + snapshot);
        System.out.println("總數量: " + snapshot.totalQuantity());

        System.out.println("\n=== 測試 null 邊界條件 ===");
        InventorySnapshot nullSnapshot = new InventorySnapshot("WH002", null);
        System.out.println(nullSnapshot);
        System.out.println("null 陣列總數量: " + nullSnapshot.totalQuantity());
        System.out.println("null 陣列缺貨品項數: " + nullSnapshot.outOfStockCount());
    }
}