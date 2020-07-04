package site.zhuhe.file;

import java.io.File;

public class FileNode {

    private File file;
    private String name;

    public FileNode(File file) {
        this.file = file;
        this.name = file.getName();
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

    public FileNode[] listFiles() {
        File[] f = file.listFiles();
        return filesToNodes(f);
    }

    public static FileNode[] listRoots() {
        File[] f = File.listRoots();
        return filesToNodes(f);
    }

    @Override
    public String toString() {
        if (name.equals("")) {
            return file.getPath();
        }
        return name;
    }

    private static FileNode[] filesToNodes(File[] f) {
        if (f == null) {
            return new FileNode[0];
        }
        FileNode[] fileNodes = new FileNode[f.length];
        for(int i = 0; i < f.length; i++) {
            fileNodes[i] = new FileNode(f[i]);
        }
        return fileNodes;
    }
}
