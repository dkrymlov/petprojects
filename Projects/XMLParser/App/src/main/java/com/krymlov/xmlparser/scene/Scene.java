package com.krymlov.xmlparser.scene;

import com.krymlov.xmlparser.object.Inhabitant;
import com.krymlov.xmlparser.parsers.ParserXML;
import com.krymlov.xmlparser.transformer.XMLFromCode;
import com.krymlov.xmlparser.transformer.XSLTransformer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

//Main app Frame
public class Scene extends JFrame {
    private final String XSLPATH = "src\\main\\resources\\db.xsl";
    private final String XMLPATH = "src\\main\\resources\\tempdb.xml";
    private String[] columnNames = new String[]{"Повне ім'я", "Вік", "Факультет", "Кафедра", "Курс", "Адрес" ,"Проплата"};
    private List<Inhabitant> listOfInhabitants;
    private String[] columns = new String[]{};
    private Object[][] data = new Object[][]{{}};
    private DefaultTableModel model;
    private JTable table;
    private JCheckBox checkBox;
    private JMenuBar jMenuBar;
    private JMenu jMenu;
    private JFileChooser fileChooser;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JTextField jTextField;
    private JButton jbtSearch;
    private JButton jbtHelp;
    private JLabel importLabel;
    private JMenuItem jMenuItemOpen;
    private JMenuItem jMenuItemSave;
    private File selectedFile;
    private boolean SAX;

    //Constructor
    public Scene(){
        //init methods
        initOther();
        initFileChooser();
        initMenuBar();
        initButtons();
        initJPanels();
        initMainTable();
        this.setTitle("Парсер XML");
        this.setSize(800, 700);
        this.setLocationRelativeTo((Component) null);
        this.setDefaultCloseOperation(3);
        this.add(jPanel3, "North");
        this.setResizable(true);
        this.setVisible(true);

        //action listener for button help
        this.jbtHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File file = new File("src\\main\\resources\\help.txt");
                try {
                    Scanner in = new Scanner(new FileReader(file));
                    StringBuilder sb = new StringBuilder();
                    while (in.hasNextLine()) {
                        sb.append(in.nextLine());
                        sb.append('\n');
                    }
                    in.close();
                    String finalText = sb.toString();
                    JOptionPane.showMessageDialog(table, finalText);
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        });
        //item listener for checkbox
        this.checkBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (SAX){
                    SAX = false;
                }else {
                    SAX = true;
                }
            }
        });
        //action listener for button search
        this.jbtSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTable();
                String keyword = jTextField.getText();
                if (SAX){
                    listOfInhabitants = ParserXML.parseXML("SAX", selectedFile.toString(), keyword);
                    System.out.println("using sax");
                }else {
                    listOfInhabitants = ParserXML.parseXML("DOM", selectedFile.toString(), keyword);
                    System.out.println("using dom");
                }
                addColumns(columnNames);
                for (int i = 0; i < listOfInhabitants.size(); i++) {
                    setTable(i);
                }
            }
        });
        //action listener for menu item open
        this.jMenuItemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Відкриття файлу xml");
                FileNameExtensionFilter fnef = new FileNameExtensionFilter("XML FILES", "xml");
                fileChooser.setFileFilter(fnef);
                int result = fileChooser.showOpenDialog(Scene.this);
                clearTable();
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedFile = fileChooser.getSelectedFile();
                    importLabel.setText("       Відкритий файл: " + selectedFile.toString());
                    listOfInhabitants = ParserXML.parseXML("DOM", selectedFile.toString(), " ");
                    addColumns(columnNames);
                    for (int i = 0; i < listOfInhabitants.size(); i++) {
                        setTable(i);
                    }
                }
            }
        });
        //action listener for menu item save
        this.jMenuItemSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Збереження html");
                FileNameExtensionFilter fnef = new FileNameExtensionFilter("HTML FILES", "html");
                fileChooser.setFileFilter(fnef);
                int result = fileChooser.showSaveDialog(Scene.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File htmlFile = fileChooser.getSelectedFile();
                    try {
                        Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
                        XMLFromCode.create("src\\main\\resources\\tempdb.xml", listOfInhabitants);
                        XSLTransformer.transform(XSLPATH, XMLPATH, htmlFile.toString());
                        writer.close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    JOptionPane.showMessageDialog(Scene.this,
                            "Файл '" + fileChooser.getSelectedFile() +
                                    " ) збережено!");
                }
            }
        });
    }

    private void setTable(int index){
        addRow();
        table.setValueAt(listOfInhabitants.get(index).getFullname(), index, 0);
        table.setValueAt(listOfInhabitants.get(index).getAge(), index, 1);
        table.setValueAt(listOfInhabitants.get(index).getFaculty(), index, 2);
        table.setValueAt(listOfInhabitants.get(index).getCathedra(), index, 3);
        table.setValueAt(listOfInhabitants.get(index).getGrade(), index, 4);
        table.setValueAt(listOfInhabitants.get(index).getHomeplace(), index, 5);
        table.setValueAt(listOfInhabitants.get(index).getPayment(), index, 6);
    }

    //init method for file chooser
    private void initFileChooser() {
        this.fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    }

    //clear table method
    private void clearTable(){
        model.setColumnCount(0);
        model.setRowCount(0);
    }

    //method to add single row
    private void addRow(){
            Scene.this.model.addRow(new Vector());
    }

    //method to add columns
    private void addColumns(String[] array){
        for (int i = 0; i < array.length; i++) {
            String name = array[i];
            Scene.this.model.addColumn(name);
        }
    }

    //method to init buttons
    private void initButtons(){
        jbtHelp = new JButton("Допомога");
        jbtSearch = new JButton("Пошук");
    }

    //method to init Jpanels
    private void initJPanels(){
        jPanel1 = new JPanel(new GridLayout(1, 3));
        jPanel2 = new JPanel(new GridLayout(1,2));
        jPanel3 = new JPanel(new GridLayout(2,1));

        jPanel1.setPreferredSize(new Dimension(800, 50));
        jPanel1.add(jTextField);
        jPanel1.add(jbtSearch);
        jPanel1.add(jbtHelp);

        jPanel2.add(importLabel);
        jPanel2.add(checkBox);

        jPanel3.add(jPanel1);
        jPanel3.add(jPanel2);
    }

    //method to init menuBar
    private void initMenuBar(){
        jMenuBar = new JMenuBar();

        jMenu = new JMenu("Файл");

        jMenuItemOpen = new JMenuItem("Відкрити");
        jMenuItemSave = new JMenuItem("Зберегти");

        jMenu.add(jMenuItemOpen);
        jMenu.add(jMenuItemSave);
        jMenuBar.add(jMenu);

        setJMenuBar(jMenuBar);
    }

    //method to init JTable
    private void initMainTable(){
        this.model = new DefaultTableModel(this.data, this.columns);
        this.table = new JTable(this.model);
        this.table.setShowGrid(true);
        this.table.setGridColor(Color.BLACK);
        this.add(new JScrollPane(this.table), "Center");
        this.table.setColumnSelectionAllowed(false);
    }

    //method to init some other components
    private void initOther(){
        checkBox = new JCheckBox("DOM/SAX");
        jTextField = new JTextField();
        importLabel = new JLabel("        Жоден файл не відрито");
    }
}
