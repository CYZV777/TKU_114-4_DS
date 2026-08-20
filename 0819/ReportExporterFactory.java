interface ReportExporter {
    void export(String title, int[] values);
}

class CsvExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("=== CSV Report ===");
        System.out.println("Title: " + title);
        System.out.print("Data: ");
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i] + (i < values.length - 1 ? "," : ""));
            }
        }
        System.out.println();
    }
}

class JsonExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("=== JSON Report ===");
        System.out.println("{");
        System.out.println("  \"title\": \"" + title + "\",");
        System.out.print("  \"values\": [");
        if (values != null && values.length > 0) {
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i] + (i < values.length - 1 ? ", " : ""));
            }
        }
        System.out.println("]");
        System.out.println("}");
    }
}

class TextExporter implements ReportExporter {
    @Override
    public void export(String title, int[] values) {
        System.out.println("=== Text Report ===");
        System.out.println("Title: " + title);
        System.out.print("Values: ");
        if (values != null && values.length > 0) {
            for (int val : values) {
                System.out.print(val + " ");
            }
        }
        System.out.println();
    }
}

public class ReportExporterFactory {

    public static ReportExporter createExporter(String format) {
        if (format == null) {
            return new TextExporter();
        }

        switch (format.toLowerCase()) {
            case "csv":
                return new CsvExporter();
            case "json":
                return new JsonExporter();
            case "text":
            default:
                return new TextExporter();
        }
    }

    public static void exportReport(ReportExporter exporter, String title, int[] values) {
        exporter.export(title, values);
    }

    public static void main(String[] args) {
        int[] data = {10, 20, 30, 40, 50};

        ReportExporter csv = createExporter("csv");
        exportReport(csv, "Sales Data", data);
        System.out.println("--------------------");

        ReportExporter json = createExporter("json");
        exportReport(json, "Sales Data", data);
        System.out.println("--------------------");

        ReportExporter unknown = createExporter("xml");
        exportReport(unknown, "Sales Data", data);
        System.out.println("--------------------");

        ReportExporter nullDataTest = createExporter("text");
        exportReport(nullDataTest, "Empty Report", null);
    }
}