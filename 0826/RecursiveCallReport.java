public class RecursiveCallReport {

    public static int sum(int[] data, int index) {
        if (index == data.length) {
            System.out.printf("index: %d, current value: N/A, recursive result: 0, return value: 0%n", index);
            return 0;
        }

        int currentValue = data[index];
        int recursiveResult = sum(data, index + 1);
        int returnValue = currentValue + recursiveResult;

        System.out.printf("index: %d, current value: %d, recursive result: %d, return value: %d%n",
                index, currentValue, recursiveResult, returnValue);

        return returnValue;
    }

    public static void test(String label, int[] data) {
        System.out.println("=== " + label + " ===");
        int total = sum(data, 0);
        System.out.println("Total Sum: " + total + "\n");
    }

    public static void main(String[] args) {
        test("一般陣列", new int[]{10, 20, 30});
        test("單一元素", new int[]{5});
        test("Empty array", new int[]{});
    }
}