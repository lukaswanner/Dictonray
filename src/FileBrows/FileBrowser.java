

package FileBrows;
// O. Bittel
// 13.4.2011

import java.io.File;
import java.util.List;

public class FileBrowser {

    public static void main(String[] args) {
        File dir = new File("/home/student/Schreibtisch");
        traverse(dir, new DirectoryPrintVisitor());

        DirectorySearchVisitor v = new DirectorySearchVisitor();
        v.setExtension(".txt");
        traverse(dir, v);
        List<String> list = v.getSearchList();
        System.out.println(list);
    }

    public static void dirPrint(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getAbsolutePath());

                if (files[i].isDirectory()) {
                    System.out.print("(Ordner)" + file.getName() + file.length() + "\n");
                    dirPrint(files[i]);
                } else {
                    System.out.print("(Datei)" + file.getName() + file.length() + "\n");
                }
            }
        }
    }

    public static void dirSearch(File file, String extension, List<String> l) {
        //File[] files = file.listFiles();

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files)
                dirSearch(f, extension, l);
        } else {
            if (file.getName().endsWith(extension)) {
                l.add(file.getName() + " " + file.getPath());
            }
        }
    }
    public static void traverse(File dir, DirectoryVisitor dv) {
        if(dir.isDirectory()) {
            dv.enterDirectory(dir);
            File[] child = dir.listFiles();
            for(File f : child) {
                traverse(f, dv);
            }
            dv.leaveDirectory(dir);
        } else {
            dv.visitFile(dir);
        }
    }
}
