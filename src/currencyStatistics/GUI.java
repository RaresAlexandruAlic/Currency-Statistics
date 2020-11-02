package currencyStatistics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {

    private JPanel appPanel;
    private JPanel bottomPanel;
    private JScrollPane scrollPanel;
    private JPanel mainPanel;
    private JPanel inputPanel;
    private JPanel statsPanel;
    private JButton plotButton;
    private JButton exportButton;
    private JButton runButton;
    private JLabel chooseYearLabel;
    private JComboBox yearsComboBox;
    private JComboBox currencyComboBox1;
    private JComboBox currencyComboBox2;
    private JLabel chooseFirstCurrencyLabel;
    private JLabel chooseSecondCurrencyLabel;
    private JPanel panel;
    private JLabel increasesLabel;
    private JLabel decreasesLabel;
    private JLabel highestValueLabel;
    private JLabel lowestValueLabel;
    private JLabel yearLabel;
    private JTextField increasesFied;
    private JTextField decreasesField;
    private JTextField highestValueField;
    private JTextField lowestValueField;
    private JPanel logoPanel;
    private JLabel logoLabel;


    //Predifined Currency Arrays
    private final String[] years = {"1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009",
            "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"};
    private final String[] currenciesABV = {"USD","EUR", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN",
            "RON", "SEK", "CHF", "ISK", "NOK", "HRK", "RUB", "TRY",
            "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS", "INR",
            "KRW", "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR"};
    private final String[] currenciesName = {"US Dollar", "Euro", "Japanese Yen", "Bulgarian Lev", "Czech Koruna", "Danish Krone",
            "British Pound", " Hungarian Forint", "Polish Zloty", "Romanian Leu", "Swedish Krona",
            "Swiss Franc", "Icelandic Krona", "Norwegian Krone", "Croatian Kuna", "Russian Rouble",
            "Turkish Lira", "Australian Dollar", "Brazilian Real", "Canadian Dollar", "Chinise Yuan Renminbi",
            "Hong Kong Dollar", "Indonesian Rupiah", "Israeli Shekel", "Indian Rupee", "South Korean Won",
            "Mexican Peso", "Malaysian Ringgit", "New Zealand Dollar", "Philippine Peso", "Singapore Dollar",
            "Thai Baht", "South African Rand"};
    private final String[] selectedData = {"2020", "RON", "EUR"};

    private CurrencyComparator currencyComparatorComparatorResults;

    public GUI(){
        super("Currency Statistics");

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(appPanel);
        this.setResizable(false);
        this.pack();

        inputPanelInit();
        outputPanelInit();
        buttonPanelInit();

        //Get the image using classpath
        //This way the resource will pe found by the .class item when the jar is executed
        ImageIcon image = new ImageIcon(GUI.class.getResource("res/app_logo.png"));
        this.setIconImage(image.getImage()); //Set the frame icon


        this.setVisible(true);
    }

    private void comboBoxInit(JComboBox comboBox, String[] inputFields){
        for(String s : inputFields){
            comboBox.addItem(s); //Populate combo box with options
        }
        comboBox.setFocusable(false);
    }

    //Text fields don't need to be shown without requesting a comparation
    private void outputPanelInit(){
        increasesFied.setVisible(false);
        decreasesField.setVisible(false);
        highestValueField.setVisible(false);
        lowestValueField.setVisible(false);
    }

    private void inputPanelInit(){ //Set up the input comboboxes and the right side logo

        comboBoxInit(yearsComboBox, years);
        yearsComboBox.setSelectedIndex(years.length - 1);
        yearsComboBox.addActionListener(this);

        comboBoxInit(currencyComboBox1, currenciesABV);
        currencyComboBox1.setRenderer(new ComboBoxRenderer(currenciesABV));
        currencyComboBox1.setSelectedIndex(9);
        currencyComboBox1.addActionListener(this);

        comboBoxInit(currencyComboBox2, currenciesABV);
        currencyComboBox2.setRenderer(new ComboBoxRenderer(currenciesABV));
        currencyComboBox2.setSelectedIndex(1);
        currencyComboBox2.addActionListener(this);

        ImageIcon image = new ImageIcon(GUI.class.getResource("res/app_logo_small.png"));
        logoLabel.setIcon(image);
    }

    private void buttonPanelInit(){
        bottomPanel.setLayout(new FlowLayout(FlowLayout.TRAILING,10, 12)); //Layout for the buttons to appear from the  right side of the panel
        plotButton.addActionListener(this);
        runButton.addActionListener(this);
        exportButton.addActionListener(this);
    }

    //Update each output field and label with its coresponding value
    private void updateOutput(String[] data, double[] comparedData){

        yearLabel.setText("Statistics of the year " + data[0]);
        increasesLabel.setText("Number of value increases of " + data[1] + " (" + currenciesName[currencyComboBox1.getSelectedIndex()] + ") compared to " + data[2] + " (" + currenciesName[currencyComboBox2.getSelectedIndex()] + ")"  );
        decreasesLabel.setText("Number of value decreases of " + data[1] + " (" + currenciesName[currencyComboBox1.getSelectedIndex()] + ") compared to " + data[2] + " (" + currenciesName[currencyComboBox2.getSelectedIndex()] + ")"  );
        highestValueLabel.setText("Highest value of " + data[1] + ": ");
        lowestValueLabel.setText("Lowest value of " + data[1] + ": ");

        increasesFied.setVisible(true);
        decreasesField.setVisible(true);
        highestValueField.setVisible(true);
        lowestValueField.setVisible(true);

        increasesFied.setText(String.valueOf((int) comparedData[0]));
        decreasesField.setText(String.valueOf((int) comparedData[1]));
        highestValueField.setText(String.valueOf(comparedData[2]));
        lowestValueField.setText(String.valueOf(comparedData[3]));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == yearsComboBox){
            selectedData[0] = years[yearsComboBox.getSelectedIndex()];
            exportButton.setEnabled(false);
            plotButton.setEnabled(false);
        }
        else if(e.getSource() == currencyComboBox1){
            selectedData[1] = currenciesABV[currencyComboBox1.getSelectedIndex()];
            exportButton.setEnabled(false);
            plotButton.setEnabled(false);
        }
        else if(e.getSource() == currencyComboBox2){
            selectedData[2] = currenciesABV[currencyComboBox2.getSelectedIndex()];
            exportButton.setEnabled(false);
            plotButton.setEnabled(false);
        }
        else if(e.getSource() == runButton){
            JsonParser parser = new JsonParser(); //New parser needed for the API request
            currencyComparatorComparatorResults = parser.parseData(selectedData[0],selectedData[1],selectedData[2]); //Start parsing the API response

            if(currencyComparatorComparatorResults.getStatus() != 0){ //Verify if the API has the requested combination of the currencies for the selected year
                double[] auxValues = currencyComparatorComparatorResults.getCompares();

                updateOutput(selectedData, auxValues);
                exportButton.setEnabled(true); //Enable export as CSV option for the user
                plotButton.setEnabled(true); //Enable plot view option for the user
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(), "No data for this year.\nTry another year or other currencies", "API Error",
                        JOptionPane.ERROR_MESSAGE);

                //Disable export as CSV option because all data from previous usage is lost and plot view option
                exportButton.setEnabled(false);
                plotButton.setEnabled(false);

                //Reset last displayed search
                yearLabel.setText("");
                increasesLabel.setText("");
                decreasesLabel.setText("");
                highestValueLabel.setText("");
                lowestValueLabel.setText("");
            }

        }else if(e.getSource() == exportButton){
            if(!(currencyComparatorComparatorResults.exportData(selectedData))){
                JOptionPane.showMessageDialog(new JFrame(), "Data exporting failed", "",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {
                String filename = selectedData[1] + "_" + selectedData[2] + "_" + selectedData[0] + ".csv";
                JOptionPane.showMessageDialog(new JFrame(), "Exported successfully!\nFilename:" + filename, "",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else if(e.getSource() == plotButton){

            //Prepare the data for plotting f(day) = value;
            double[] yData = currencyComparatorComparatorResults.getValues();
            double[] xData = new double[yData.length];

            for(int i = 0; i < yData.length; i++){
                xData[i] = i+1;
            }

            new Plotter("Days","Value", selectedData[1],xData,yData, selectedData); //We get a new form with the plotted data
        }
    }
}
