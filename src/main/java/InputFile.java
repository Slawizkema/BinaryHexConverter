import java.io.File;


/**
 * Created by slawi_000 on 07.01.2019.
 */

//
public abstract class InputFile {
    private File file;
    private String pathname;
    private byte byteArray[];
    private String hash;

    public void saveConvert(File file) {
    }

    public File getFile() {
        return file;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public String getPathname() {
        return pathname;
    }

    public String getHash() {
        return hash;
    }


}
