import java.io.*;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import static javax.xml.bind.DatatypeConverter.*;

/**
 * Created by slawi_000 on 14.03.2019.
 */
public class HexDumpFile extends InputFile {
    private static final org.apache.log4j.Logger log = Logger.getLogger(HexDumpFile.class);
    private File file;
    private String pathname;
    private byte byteArray[];
    private String hash;

    public HexDumpFile(String pathname) {
        log.info("HexDumpFile file open "+ pathname);
        this.pathname = pathname;
        file = new File(pathname);
        try (FileInputStream fileInput = new FileInputStream(file)) {   //open file

            //file toString
            BufferedReader buf = new BufferedReader(new InputStreamReader(fileInput));
            char charArr[] = new char[fileInput.available()];
            buf.read(charArr, 0, fileInput.available());
            String stringHexDump = new String(charArr);

            byteArray = parseHexBinary(stringHexDump);
            hash = DigestUtils.md5Hex(byteArray);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HexDumpFile (@NotNull File file) {
        this(file.getAbsolutePath());
    }


    public void saveConvert (File file) {

        try (FileOutputStream fo = new FileOutputStream(file)) {
            fo.write(byteArray);

        } catch (FileNotFoundException e) {
            log.error("ConvertError", e);
        } catch (IOException e) {
            log.error("ConvertError", e);
        }

        log.info(String.format("Binary file %s%n convert to %s%n", getPathname(), file.getAbsolutePath()));
    }

    public File getFile() {
        return file;
    }

    public String getPathname() {
        return pathname;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public String getHash() {
        return hash;
    }
}
