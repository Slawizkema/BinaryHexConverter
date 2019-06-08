import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.util.Arrays;



class BinaryFileTest {

    @Test
    public void getPathname() {
        BinaryFile testFile = new BinaryFile("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer.txt");
        Assertions.assertEquals(testFile.getPathname(), "D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer.txt");

    }

    @Test
    public void getHash() {
        BinaryFile testFile = new BinaryFile("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer.txt");
        Assertions.assertEquals(testFile.getHash().toUpperCase(), "63F772C00E4DCC2F304742362CC7B16C");

    }

    @Test
    public void saveConvert() {
        BinaryFile testFile = new BinaryFile("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer.txt");
        testFile.saveConvert(new File("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer.dump"));
        HexDumpFile tesDumpFile = new HexDumpFile("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer.dump");
        Assertions.assertEquals(Arrays.toString(testFile.getByteArray()), Arrays.toString(tesDumpFile.getByteArray()));

    }
}