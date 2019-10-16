import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.*;

class MainFrame extends JFrame {
    private static int width = 500;
    private static int height = 400;

    private JButton button = new JButton("Encode/Decode");
    private JButton btnSelectFile = new JButton("Select ciphering file");
    private JLabel lblCurrentFile = new JLabel("");
    private JTextField input = new JTextField("", 40);
    private JLabel label = new JLabel("Key:");
    private JRadioButton radioTableMethod = new JRadioButton("Column method");
    private JRadioButton radioRotatingSquare = new JRadioButton("Rotating square");
    private JRadioButton radioVigenere = new JRadioButton("Vigenere");

    private JRadioButton radioIsEncrypt = new JRadioButton("Encode");
    private JRadioButton radioIsDecrypt = new JRadioButton("Decode");

    private String filePath = null;

    MainFrame() {
        super("Perfect encoder/decoder");
        this.setVisible(true);
        this.setBounds(100, 100, 500, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(7, 1));
        container.add(label);
        container.add(input);

        lblCurrentFile.setVerticalAlignment(JLabel.CENTER);
        container.add(btnSelectFile);
        container.add(lblCurrentFile);

        ButtonGroup isEncGroup = new ButtonGroup();
        Panel encOrDecPanel = new Panel(new GridLayout(1, 2));
        isEncGroup.add(radioIsDecrypt);
        isEncGroup.add(radioIsEncrypt);
        encOrDecPanel.add(radioIsDecrypt);
        encOrDecPanel.add(radioIsEncrypt);
        container.add(encOrDecPanel);

        ButtonGroup cipherGroup = new ButtonGroup();
        cipherGroup.add(radioTableMethod);
        cipherGroup.add(radioRotatingSquare);
        cipherGroup.add(radioVigenere);

        Panel cipherPanel = new Panel(new GridLayout(1, 3));

        cipherPanel.add(radioTableMethod);
        cipherPanel.add(radioRotatingSquare);
        cipherPanel.add(radioVigenere);

        container.add(cipherPanel);

        radioTableMethod.setSelected(true);
        radioIsEncrypt.setSelected(true);

        btnSelectFile.addActionListener(new SelectFile());
        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    void dialogMSG(String message, String title) {
        JOptionPane.showMessageDialog(null,
                message,
                title,
                JOptionPane.PLAIN_MESSAGE);
    }

    class SelectFile implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileOpen = new JFileChooser();
            fileOpen.setCurrentDirectory(new File(System.getProperty("user.dir")));
            int ret = fileOpen.showDialog(null, "OpenFile");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                filePath = file.getName();
                lblCurrentFile.setText(file.getName());
            }
        }
    }

    private String readFile() {
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));

        } catch (IOException e) {
            dialogMSG("IOException with reading file.", "Exception");
        }
        return null;
    }

    private void writeFile(String text) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            dialogMSG("IOException with writing file.", "Exception");
        }
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            boolean correctFlag = true;

            StringBuilder messageBuild = new StringBuilder();

            if (filePath == null) {
                dialogMSG("You did not specify a file", "Error");
            } else {

                String text = readFile();
                String key = input.getText();

                if (radioVigenere.isSelected()) {
                    text = onlyRus(text);
                    key = onlyRus(key);
                    if (key.equals("")) {
                        dialogMSG("Empty key field.", "Error");
                        correctFlag = false;
                    } else {
                        if (radioIsEncrypt.isSelected()) {
                            messageBuild.append("Encode completed\n");
                            messageBuild.append("Key is " + cutoffString(key) + "\n");
                            messageBuild.append("Plaintext is " + cutoffString(text) + "\n");
                            text = VigenereSipher.encode(text, key);
                            messageBuild.append("Plaintext is " + cutoffString(text) + "\n");

                        } else {
                            messageBuild.append("Decode completed\n");
                            messageBuild.append("Key is " + cutoffString(key) + "\n");
                            messageBuild.append("Cipher text is " + cutoffString(text) + "\n");
                            text = VigenereSipher.decode(text, key);
                            messageBuild.append("Plaintext is " + cutoffString(text) + "\n");
                        }
                    }
                }
                if (radioTableMethod.isSelected()) {
                    text = onlyEng(text);
                    key = onlyEng(key);
                    if (key.equals("")) {
                        dialogMSG("Empty key field.", "Error");
                        correctFlag = false;
                    } else {
                        if (radioIsEncrypt.isSelected()) {
                            messageBuild.append("Encode completed\n");
                            messageBuild.append("Key is " + cutoffString(key) + "\n");
                            messageBuild.append("Plaintext is " + cutoffString(text) + "\n");
                            text = ColumnMethod.Companion.encode(text, key);
                            messageBuild.append("Cipher text is " + cutoffString(text) + "\n");
                        } else {
                            messageBuild.append("Decode completed\n");
                            messageBuild.append("Key is " + cutoffString(key) + "\n");
                            messageBuild.append("Cipher text is " + cutoffString(text) + "\n");
                            text = ColumnMethod.Companion.decode(text, key);
                            messageBuild.append("Plaintext is " + cutoffString(text) + "\n");
                        }
                    }
                }
                if (radioRotatingSquare.isSelected()) {
                    text = onlyEng(text);
                    if (radioIsEncrypt.isSelected()) {
                        messageBuild.append("Encode completed\n");
                        messageBuild.append("Key is " + cutoffString(key) + "\n");
                        messageBuild.append("Plaintext is " + cutoffString(text) + "\n");
                        text = RotatingSquare.encode(text);
                        messageBuild.append("Cipher text is " + cutoffString(text) + "\n");
                    } else {
                        messageBuild.append("Decode completed\n");
                        messageBuild.append("Cipher text is " + cutoffString(text) + "\n");
                        text = RotatingSquare.decode(text);
                        messageBuild.append("Plaintext is " + cutoffString(text) + "\n");
                    }
                }
                writeFile(text);
                if (correctFlag) {
                    dialogMSG(messageBuild.toString(), "Success");
                }
            }

        }
    }


    static String onlyEng(String s) {
        return s.replaceAll("[^A-Za-zА]", "").toUpperCase();
    }

    static String onlyRus(String s) {
        return s.replaceAll("[^А-Яа-яёЁА]", "").toUpperCase();
    }

    static String cutoffString(String s) {
        if (s.length() > 24) {
            s = s.substring(0, 24);
            s = s + "...";
        }
        return s;
    }


}