public class EmployeeConstructorChain {

    abstract static class EmployeeBase {
        String id;
        String name;

        public EmployeeBase(String id, String name) {
            System.out.println("Constructor: EmployeeBase");
            this.id = id;
            this.name = name;
        }

        public abstract double calculatePay();
    }

    static class FullTimeEmployee extends EmployeeBase {
        private double monthlySalary;

        public FullTimeEmployee(String id, String name, double monthlySalary) {
            super(id, name);
            System.out.println("Constructor: FullTimeEmployee");
            this.monthlySalary = monthlySalary < 0 ? 0 : monthlySalary;
        }

        @Override
        public double calculatePay() {
            return monthlySalary;
        }
    }

    static class PartTimeEmployee extends EmployeeBase {
        private double hourlyRate;
        private double hoursWorked;

        public PartTimeEmployee(String id, String name, double hourlyRate, double hoursWorked) {
            super(id, name);
            System.out.println("Constructor: PartTimeEmployee");
            this.hourlyRate = hourlyRate < 0 ? 0 : hourlyRate;
            this.hoursWorked = hoursWorked < 0 ? 0 : hoursWorked;
        }

        @Override
        public double calculatePay() {
            return hourlyRate * hoursWorked;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 建立全職員工 ---");
        EmployeeBase ft = new FullTimeEmployee("FT01", "Alice", 45000);
        System.out.println(ft.name + " 實發薪資: " + ft.calculatePay());

        System.out.println();

        System.out.println("--- 建立兼職員工 (含負數邊界測試) ---");
        EmployeeBase pt = new PartTimeEmployee("PT01", "Bob", -200, 40);
        System.out.println(pt.name + " 實發薪資: " + pt.calculatePay());
    }
}