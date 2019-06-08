import org.apache.log4j.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;


import static javax.xml.bind.DatatypeConverter.printHexBinary;


/**
 * Created by slawi_000 on 07.01.2019.
 */
public class BinaryFile extends InputFile {
    private static final Logger log = Logger.getLogger(BinaryFile.class);
    private File file;
    private String pathname;
    private byte byteArray[];
    private String hash;

    public BinaryFile(String pathname) {
        log.info("Binary file opened: "+ pathname);
        this.pathname = pathname;
        file = new File(pathname);


        try (FileInputStream fileInput = new FileInputStream(file)) {   //open file
            System.out.println(fileInput.available());
            byteArray = new byte[fileInput.available()];
            fileInput.read(byteArray, 0, fileInput.available());
            hash = DigestUtils.md5Hex(byteArray);



        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BinaryFile(@NotNull File file) {
        this(file.getAbsolutePath());

    }

    @Override
    public File getFile() {
        return file;
    }

    @Override
    public byte[] getByteArray() {
        return byteArray;
    }

    @Override
    public String getPathname() {
        return pathname;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Override
    public void saveConvert (File file) {
        log.info(String.format("Binary file %s%n convert to %s%n", getPathname(), file.getAbsolutePath()));
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(printHexBinary(byteArray));

        } catch (FileNotFoundException e) {
            log.error("sdce", e);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("sdce", e);
            e.printStackTrace();
        }


    }



}
