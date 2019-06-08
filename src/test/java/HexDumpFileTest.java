import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;


class HexDumpFileTest {

    @Test
    public void getPathname() {
        HexDumpFile testDumpFile = new HexDumpFile("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/test_dump.dump");
        Assertions.assertEquals(testDumpFile.getPathname(), "D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/test_dump.dump");

    }

    @Test
    public void getHash() {
        HexDumpFile testDumpFile = new HexDumpFile("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer.dump");
        Assertions.assertEquals(testDumpFile.getHash().toUpperCase(), "63F772C00E4DCC2F304742362CC7B16C");

    }

    @Test
    public void saveConvert() {
        HexDumpFile testDumpFile = new HexDumpFile("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer.dump");
        testDumpFile.saveConvert(new File("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer_test.txt"));
        BinaryFile tesBinFile = new BinaryFile("D:/YandexDisk/IdeaProjects/BinaryHexConverter/src/main/resources/primer_test.txt");
        Assertions.assertEquals(Arrays.toString(tesBinFile.getByteArray()), Arrays.toString(testDumpFile.getByteArray()));

    }
}