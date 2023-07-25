import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Objects;


public class CipherMainPanel extends JFrame implements ActionListener {
    JRadioButton encryptButton;
    JRadioButton decryptButton1;
    JRadioButton decryptButton2;
    JLabel nameApp;
    JLabel checkBoxLabel;
    JLabel inputFile;
    JButton inFile;
    JTextField inPath;
    JLabel outputFile;
    JButton outFile;
    JTextField outPath;
    JLabel additionalFile;
    JButton addFile;
    JTextField addPath;
    JButton startButton;
    Checkbox punctuationCheckBox;
    JComboBox<String> alphabetType;
    JTextField key;
    JLabel keyCeasar;

    CipherMainPanel()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setSize(500,500);

        encryptButton = new JRadioButton("Encryption mode");
        encryptButton.setSelected(true);
        decryptButton1 = new JRadioButton("Brute force decryption mode");
        decryptButton2 = new JRadioButton("Frequency analysis decryption mode");

        nameApp = new JLabel();
        nameApp.setText("Cipher Ceasar Encrypt/Decrypt App");
        nameApp.setFont(new Font("My Boli",Font.PLAIN,20));
        nameApp.setHorizontalAlignment(JLabel.CENTER);
        nameApp.setBounds(0,0,500,50);

        checkBoxLabel = new JLabel();
        checkBoxLabel.setText("Choose an operating mode:");
        checkBoxLabel.setFont(new Font("My Boli",Font.PLAIN,16));
        checkBoxLabel.setBounds(50,50,200,25);

        String[] items = {
                "Latin",
                "Cyrillic"
        };
        alphabetType = new JComboBox<>(items);
        alphabetType.setFont(new Font("My Boli",Font.PLAIN,16));
        alphabetType.setBounds(60 ,175,200,25);

        keyCeasar = new JLabel();
        keyCeasar.setText("Сipher key");
        keyCeasar.setFont(new Font("My Boli",Font.PLAIN,16));
        keyCeasar.setBounds(300,50,100,25);

        inputFile = new JLabel();
        inputFile.setText("Input File: ");
        inputFile.setFont(new Font("My Boli",Font.PLAIN,16));
        inputFile.setBounds(50,200,200,25);

        outputFile = new JLabel();
        outputFile.setText("Output File: ");
        outputFile.setFont(new Font("My Boli",Font.PLAIN,16));
        outputFile.setBounds(50,250,200,25);

        additionalFile = new JLabel();
        additionalFile.setText("Additional file: ");
        additionalFile.setFont(new Font("My Boli",Font.PLAIN,16));
        additionalFile.setBounds(50,300,200,25);
        additionalFile.setVisible(false);



        this.add(checkBoxLabel);
        this.add(nameApp);
        this.add(alphabetType);
        this.add(inputFile);
        this.add(outputFile);
        this.add(additionalFile);
        this.add(keyCeasar);


        key = new JTextField();
        key.setBounds(300 ,75,100,20);
        this.add(key);

        ButtonGroup group1 = new ButtonGroup();
        group1.add(encryptButton);
        group1.add(decryptButton1);
        group1.add(decryptButton2);
        encryptButton.setBounds(50 ,75,200,25);
        decryptButton1.setBounds(50,100,200,25);
        decryptButton2.setBounds(50,125,250,25);

        encryptButton.addActionListener(this);
        decryptButton1.addActionListener(this);
        decryptButton2.addActionListener(this);

        punctuationCheckBox = new Checkbox("Add Punctuation");
        punctuationCheckBox.setState(true);
        punctuationCheckBox.setFont(new Font("My Boli",Font.PLAIN,16));
        punctuationCheckBox.setBounds(60 ,150,200,25);
        //CheckboxGroup group2 = new CheckboxGroup();
        this.add(punctuationCheckBox);

        inputFile = new JLabel();
        inputFile.setText("Input File: ");
        inputFile.setFont(new Font("My Boli",Font.PLAIN,12));
        inputFile.setBounds(50,175,200,25);


        this.add(encryptButton);
        this.add(decryptButton1);
        this.add(decryptButton2);

        inFile = new JButton("...");
        outFile = new JButton("...");
        addFile = new JButton("...");
        addFile.setVisible(false);

        inFile.setBounds(50 ,225,20,20);
        outFile.setBounds(50 ,275,20,20);
        addFile.setBounds(50 ,325,20,20);

        inFile.addActionListener(e -> {
            if(e.getSource() == inFile)
            {
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showOpenDialog(null);
                if(response == JFileChooser.APPROVE_OPTION)
                    inPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
                else
                   JOptionPane.showMessageDialog(null,"Incorrect input FilePath","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        outFile.addActionListener(e -> {
            if(e.getSource() == outFile)
            {
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showSaveDialog(null);
                if(response == JFileChooser.APPROVE_OPTION)
                    outPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
                else
                    JOptionPane.showMessageDialog(null,"Incorrect output FilePath","Error",JOptionPane.ERROR_MESSAGE);
            }
        });
        addFile.addActionListener(e -> {
            if(e.getSource() == addFile)
            {
                JFileChooser fileChooser = new JFileChooser();
                int response = fileChooser.showOpenDialog(null);
                if(response == JFileChooser.APPROVE_OPTION)
                    addPath.setText(fileChooser.getSelectedFile().getAbsolutePath());
                else
                    JOptionPane.showMessageDialog(null,"Incorrect additional FilePath","Error",JOptionPane.ERROR_MESSAGE);
            }
        });

        this.add(inFile);
        this.add(outFile);
        this.add(addFile);

        inPath = new JTextField();
        outPath = new JTextField();
        addPath = new JTextField();
        addPath.setVisible(false);

        inPath.setBounds(75 ,225,300,20);
        outPath.setBounds(75 ,275,300,20);
        addPath.setBounds(75 ,325,300,20);
        addPath.setVisible(false);

        startButton = new JButton();
        startButton.setText("Сalculation start");
        startButton.setBounds(125,375,200,40);
        startButton.addActionListener(e -> {

        });




        startButton.addActionListener(e -> {
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(inPath.getText()));
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outPath.getText()))
            )
            {       Alphabet alphabet = new Alphabet(Objects.requireNonNull(alphabetType.getSelectedItem()).toString(), punctuationCheckBox.getState()); // инициализация объекта класса Alphabet
                    CipherCeasar cipherCeasar = new CipherCeasar(alphabet); // инициализция объекта класса CipherCeasar
                    cipherCeasar.setInputArray(bufferedReader); // передача ссылки на входной буферизированный поток в объект CipherCeasar
                    if(encryptButton.isSelected()) {
                        cipherCeasar.encode(Integer.parseInt(key.getText()));
                    }
                    else if (decryptButton1.isSelected()) {
                        try(BufferedReader bufferedAdditional = new BufferedReader(new FileReader(addPath.getText())))
                        {
                            cipherCeasar.decode(bufferedAdditional,"DecryptBF");
                        }
                    }
                    else if (decryptButton2.isSelected()) {
                        try(BufferedReader bufferedAdditional = new BufferedReader(new FileReader(addPath.getText())))
                        {
                            cipherCeasar.decode(bufferedAdditional,"DecryptFA");
                        }
                    }
                    for (Character character: cipherCeasar.getOutputArray()) {
                        bufferedWriter.write(character);
                    }


            }
            catch (Exception exception)
            {
                JOptionPane.showMessageDialog(null,exception.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
            }



        });


        this.add(startButton);

        this.add(inPath);
        this.add(outPath);
        this.add(addPath);

        this.setLayout(null);
        this.setVisible(true);


    }




    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == encryptButton) {
            addFile.setVisible(false);
            additionalFile.setVisible(false);
            addPath.setVisible(false);
            keyCeasar.setVisible(true);
            key.setVisible(true);

        }
        else if(e.getSource() == decryptButton1) {

            addFile.setVisible(true);
            additionalFile.setVisible(true);
            addPath.setVisible(true);
            keyCeasar.setVisible(false);
            key.setVisible(false);
        }
        else
        {
            addFile.setVisible(true);
            additionalFile.setVisible(true);
            addPath.setVisible(true);
            keyCeasar.setVisible(false);
            key.setVisible(false);
        }





    }
}
