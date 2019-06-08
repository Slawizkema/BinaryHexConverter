
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by slawi_000 on 15.03.2019.
 */
public class ControllerGUI {
    final static int BINARY_FILE = 1;
    final static int HEX_FILE = 2;
    private AppFrame appFrame;
    private static final Logger log = Logger.getLogger(ControllerGUI.class);

    private ModelConverter model;

    public ControllerGUI() {
        appFrame = new AppFrame();
        appFrame.saveBinaryButton.setEnabled(false);
        appFrame.saveHexButton.setEnabled(false);

        appFrame.browseBinaryFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String fileDir = chooseFileFromFS(BINARY_FILE);
                appFrame.binaryFileDir.setText(fileDir);
                appFrame.hexFileDir.setText("");
                appFrame.saveHexButton.setEnabled(true);

            }
        });

        appFrame.browseHexFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    appFrame.hexFileDir.setText(chooseFileFromFS(HEX_FILE));
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
                saveFileInDirectory(HEX_FILE);
            }
        });

        appFrame.saveBinaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveFileInDirectory(BINARY_FILE);
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

    //Choose files from user's file system
    public String chooseFileFromFS ( int fileType){
        JFileChooser fileChooser = new JFileChooser();

        //if choose .dump files - enable extension filter, if binary file - w/o filter
        if (fileType == 2) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("Dump files", "dump"));
        }
        fileChooser.setDialogTitle("Выбор файла");

        // Mode - File + Directory
        fileChooser.setFileSelectionMode(JFileChooser.OPEN_DIALOG);
        int result = fileChooser.showOpenDialog(appFrame);
        model.loadFile(fileChooser.getSelectedFile(), fileType);

        // print text info of files
        appFrame.hexDumpText.setText(model.printHexText());
        appFrame.hashText.setText(model.printHash());

        return fileChooser.getSelectedFile().getAbsolutePath();

    }
    //Save files in user's file system
    public void saveFileInDirectory ( int fileType){
        JFileChooser fileChooser = new JFileChooser();
        if (fileType == 2) {
            fileChooser.setFileFilter(new FileNameExtensionFilter("Dump files", "dump"));
        }
        fileChooser.setDialogTitle("Сохранение файла");
        // Mode - File
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {

            model.saveFile(fileChooser.getSelectedFile());

            JOptionPane.showMessageDialog(appFrame,
                    "Файл '" + fileChooser.getSelectedFile() +
                            " сохранен");
        }
    }

}
