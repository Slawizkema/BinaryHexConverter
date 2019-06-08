
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by slawi_000 on 15.03.2019.
 */
public class ControllerGUI {
    private AppFrame appFrame;
    private static final Logger log = Logger.getLogger(ControllerGUI.class);

    private ModelConverter model;



    public ControllerGUI() {
        appFrame = new AppFrame();
        appFrame.saveBinaryButton.setEnabled(false);
        appFrame.saveHexButton.setEnabled(false);

        appFrame.browseBinaryFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileDir = selectFileFromFS(DialogueType.OPEN_BINARY);
                appFrame.binaryFileDir.setText(fileDir);
                appFrame.hexFileDir.setText("");
                appFrame.saveHexButton.setEnabled(true);

            }
        });

        appFrame.browseHexFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    appFrame.hexFileDir.setText(selectFileFromFS(DialogueType.OPEN_HEX));
                    appFrame.binaryFileDir.setText("");
                    // check correct file extension
                } catch (IllegalArgumentException ill) {
                    log.error("Wrong file", ill);
                    JOptionPane.showMessageDialog(appFrame, "File is incorrect, try again");

                }
                appFrame.saveHexButton.setEnabled(false);
                appFrame.saveBinaryButton.setEnabled(true);
            }
        });

        appFrame.saveHexButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFileInFS(DialogueType.SAVE_HEX);
            }
        });

        appFrame.saveBinaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFileInFS(DialogueType.SAVE_BINARY);
            }
        });

        appFrame.resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                appFrame.hexFileDir.setText("");
                appFrame.binaryFileDir.setText("");
                appFrame.hexDumpText.setText("");
                appFrame.hashText.setText("");
                appFrame.saveHexButton.setEnabled(false);
                appFrame.saveBinaryButton.setEnabled(false);
            }
        });

        appFrame.pack();
        appFrame.setVisible(true);
        appFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        model = new ModelConverter();


    }

    /*
    Choose files from user's file system
    input - fileType
     */


    private String selectFileFromFS(DialogueType dt){
       File selectedFile = getFileFromFS(dt);
       String extension = selectedFile.getName().substring(selectedFile.getName().lastIndexOf("."));

       //check that the correct extension is selected for the dump file
        if (dt == DialogueType.OPEN_HEX && !extension.equals(".dump")) {
            JOptionPane.showMessageDialog(appFrame, String.format("Файл %s не является .dump файлом. Попробуйте еще раз", selectedFile.getAbsolutePath()));
            selectFileFromFS(dt);
        }
        model.loadFile(selectedFile, dt);

        // print text info of files
        appFrame.hexDumpText.setText(model.printHexText());
        appFrame.hashText.setText(model.printHash());
        log.info(String.format("Файл %s открыт", selectedFile.getAbsolutePath()));
        return selectedFile.getAbsolutePath();

    }
    //Save converted file in user's file system
    private void saveFileInFS (DialogueType dt){
        File selectedFile = getFileFromFS(dt);
        model.saveFile(selectedFile);
        JOptionPane.showMessageDialog(appFrame, String.format("Файл %s сохранен", selectedFile.getAbsolutePath()));
        log.info(String.format("Файл %s сохранен", selectedFile.getAbsolutePath()));

    }


    private File getFileFromFS (DialogueType dialogueType) throws IllegalStateException {
        File resFile = new File("src/test/resources/log.log");

        //if file is hex - set filter
        JFileChooser fileChooser = new JFileChooser();
        if (dialogueType == DialogueType.OPEN_HEX || dialogueType == DialogueType.SAVE_HEX) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("Dump files", "dump"));
        }

        //set title
        switch (dialogueType) {
            case OPEN_HEX:
                fileChooser.setDialogTitle("Выбор файла дампа");
                break;
            case OPEN_BINARY:
                fileChooser.setDialogTitle("Выбор бинарного файла");
                break;
            case SAVE_BINARY:
                fileChooser.setDialogTitle("Сохранение бинарного файла");
                break;
            case SAVE_HEX:
                fileChooser.setDialogTitle("Сохранение файла дампа");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dialogueType);
        }

        // Mode - File
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        //if open file - set showOpenDialog, else - save
        if (dialogueType == DialogueType.OPEN_HEX || dialogueType == DialogueType.OPEN_BINARY) {

            if (fileChooser.showOpenDialog(appFrame) == JFileChooser.APPROVE_OPTION) {
                resFile =  fileChooser.getSelectedFile();
            }

        } else {

            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                resFile =  fileChooser.getSelectedFile();
            }
        }

        return resFile;
    }



}
