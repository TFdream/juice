package juice.ftp;

import java.io.File;

/**
 * @author Ricky Fung
 */
public class FileItem {
    private String filename;
    private File file;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
