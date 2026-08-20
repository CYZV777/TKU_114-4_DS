interface MyExportable {
    void exportFile();
}

interface MyCompressible {
    void compressFile();
}

class BackupDocument implements MyExportable, MyCompressible {
    private String filename;

    public BackupDocument(String filename) {
        this.filename = filename;
    }

    @Override
    public void exportFile() {
        System.out.println("匯出檔案: " + filename);
    }

    @Override
    public void compressFile() {
        System.out.println("壓縮檔案: " + filename);
    }
}

public class DocumentCapabilityDemo {
    public static void main(String[] args) {
        BackupDocument doc = new BackupDocument("backup_2026.zip");

        MyExportable exportRef = doc;
        MyCompressible compressRef = doc;

        System.out.println("是否指向同一個物件: " + (exportRef == compressRef));

        exportRef.exportFile();
        compressRef.compressFile();
    }
}