import java.util.ArrayList;
import java.util.List;

public class DirectoryTreeReport {

    static class Node {
        String name;
        boolean isDirectory;
        long size;
        List<Node> children = new ArrayList<>();

        Node(String name, long size) {
            this.name = name;
            this.isDirectory = false;
            this.size = size;
        }

        Node(String name) {
            this.name = name;
            this.isDirectory = true;
            this.size = 0;
        }

        void addChild(Node child) {
            children.add(child);
        }
    }

    static class Stats {
        int totalNodes = 0;
        int fileCount = 0;
        int dirCount = 0;
        Node maxFile = null;
    }

    public static long postorderCompute(Node node, Stats stats) {
        if (node == null) return 0;

        stats.totalNodes++;
        if (node.isDirectory) {
            stats.dirCount++;
            long sum = 0;
            for (Node child : node.children) {
                sum += postorderCompute(child, stats);
            }
            node.size = sum;
            return sum;
        } else {
            stats.fileCount++;
            if (stats.maxFile == null || node.size > stats.maxFile.size) {
                stats.maxFile = node;
            }
            return node.size;
        }
    }

    public static int getHeight(Node node) {
        if (node == null) return 0;
        int maxChildHeight = 0;
        for (Node child : node.children) {
            maxChildHeight = Math.max(maxChildHeight, getHeight(child));
        }
        return 1 + maxChildHeight;
    }

    public static void printReport(Node root) {
        Stats stats = new Stats();
        postorderCompute(root, stats);

        System.out.println("=== Directory Tree Report ===");
        System.out.println("Root Directory  : " + root.name);
        System.out.println("Total Capacity  : " + root.size + " bytes");
        System.out.println("Total Nodes     : " + stats.totalNodes);
        System.out.println("File Count      : " + stats.fileCount);
        System.out.println("Directory Count : " + stats.dirCount);
        System.out.println("Tree Height     : " + getHeight(root));
        if (stats.maxFile != null) {
            System.out.println("Largest File    : " + stats.maxFile.name + " (" + stats.maxFile.size + " bytes)");
        } else {
            System.out.println("Largest File    : N/A");
        }
    }

    public static void main(String[] args) {
        Node root = new Node("root");
        Node docs = new Node("docs");
        Node photos = new Node("photos");

        docs.addChild(new Node("resume.pdf", 500));
        docs.addChild(new Node("notes.txt", 150));

        photos.addChild(new Node("banner.png", 2400));
        photos.addChild(new Node("icon.jpg", 300));

        root.addChild(docs);
        root.addChild(photos);
        root.addChild(new Node("config.json", 50));

        printReport(root);
    }
}