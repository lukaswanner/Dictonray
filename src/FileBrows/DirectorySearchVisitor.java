package FileBrows;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

public class DirectorySearchVisitor implements DirectoryVisitor {

    private String extension;
    private List<String> searchList = new LinkedList<String>();

    @Override
    public void enterDirectory(File dir) {

    }

    @Override
    public void leaveDirectory(File dir) {

    }

    @Override
    public void visitFile(File file) {
        if (extension != null && file.getName().endsWith(extension)) {
            try {
                searchList.add(file.getCanonicalPath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public List<String> getSearchList() {
        return searchList;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
    public void clearSearchList() {
        this.searchList.clear();
    }
}