package FileBrows;

import java.io.File;

public class DirectoryPrintVisitor implements DirectoryVisitor {
    static int tab = 1;

    @Override
    public void enterDirectory(File dir) {
        System.out.printf("%" + tab + "s%s%s\n", "", "Verzeichnisname:", dir.getName());
        tab += 5;
    }

    @Override
    public void leaveDirectory(File dir) {
        tab -= 5;

    }

    @Override
    public void visitFile(File file) {
        System.out.printf("%" + tab + "s%s%s\n", "", "Dateiname: ", file.getName());

    }
}