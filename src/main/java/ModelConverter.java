
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by slawi_000 on 15.03.2019.
 */
public class ModelConverter {
    private InputFile inputFile;



    //create hex text with Ascii
    public String printHexText (){

        byte bytes[] = inputFile.getByteArray();

        String stringHexArray[] = new String[bytes.length];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            stringHexArray[i] = String.format("%02X", bytes[i]);
        }

        sb.append(String.format("%010X | ", 0));

        StringBuilder sbHex = new StringBuilder();
        StringBuilder sbAscii = new StringBuilder();
        Pattern pattern = Pattern.compile("[^0-9A-Za-z-]");

        for (int i = 0; i < stringHexArray.length; i++) {
            sbHex.append(stringHexArray[i] + " ");
            sbAscii.append((char)bytes[i]);
            if (i+1>15 && (i+1)%16 == 0 ){
                Matcher matcher = pattern.matcher(sbAscii.toString());
                sb.append(sbHex);
                sb.append(" | ");
                sb.append(matcher.replaceAll("."));
                sbHex = new StringBuilder();
                sbAscii = new StringBuilder();
                sb.append("\n");
                sb.append(String.format("%010X | ", i+1));

            }
        }
        Matcher matcher = pattern.matcher(sbAscii.toString());
        sb.append(sbHex);

        for (int i = 0; i < (16-stringHexArray.length%16); i++) {
            sb.append("  "+ " ");
        }
        sb.append(" | ");
        sb.append(matcher.replaceAll("."));


        return sb.toString();
    }


    public String printHash (){
        return inputFile.getHash();
    }

    public void loadFile (File file, int type){
        switch (type) {
            case 1:
                inputFile = new BinaryFile(file);
                break;
            case 2:
                inputFile = new HexDumpFile(file);
                break;
        }
    }

    public void saveFile (File file){

        inputFile.saveConvert(file);
    }


}
