public class PayrollPolymorphismSystem {

    abstract static class Employee {
        String name;

        public Employee(String name) {
            this.name = name;
        }

        public abstract double calculatePay();
    }

    static class SalariedEmployee extends Employee {
        private double monthlySalary;

        public SalariedEmployee(String name, double monthlySalary) {
            super(name);
            this.monthlySalary = monthlySalary;
        }

        @Override
        public double calculatePay() {
            return monthlySalary;
        }
    }

    static class HourlyEmployee extends Employee {
        private double hourlyRate;
        private double hoursWorked;

        public HourlyEmployee(String name, double hourlyRate, double hoursWorked) {
            super(name);
            this.hourlyRate = hourlyRate;
            this.hoursWorked = hoursWorked;
        }

        @Override
        public double calculatePay() {
            return hourlyRate * hoursWorked;
        }
    }

    static class CommissionEmployee extends Employee {
        private double baseSalary;
        private double salesAmount;
        private double commissionRate;

        public CommissionEmployee(String name, double baseSalary, double salesAmount, double commissionRate) {
            super(name);
            this.baseSalary = baseSalary;
            this.salesAmount = salesAmount;
            this.commissionRate = commissionRate;
        }

        @Override
        public double calculatePay() {
            return baseSalary + (salesAmount * commissionRate);
        }
    }

    public static void main(String[] args) {
        Employee[] employees = new Employee[] {
            new SalariedEmployee("Alice", 50000),
            new HourlyEmployee("Bob", 200, 160),
            new CommissionEmployee("Charlie", 30000, 200000, 0.15)
        };

        double totalPay = 0;
        double maxPay = 0;
        String highestEarner = "";

        for (Employee emp : employees) {
            double pay = emp.calculatePay();
            System.out.println(emp.name + " 薪資: " + pay + " 元");

            totalPay += pay;

            if (pay > maxPay) {
                maxPay = pay;
                highestEarner = emp.name;
            }
        }

        System.out.println("-------------------------");
        System.out.println("薪資總額: " + totalPay + " 元");
        System.out.println("最高薪資: " + highestEarner + " (" + maxPay + " 元)");
    }
}