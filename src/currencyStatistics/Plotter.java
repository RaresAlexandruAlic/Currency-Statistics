package currencyStatistics;

import org.knowm.xchart.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Plotter extends JFrame implements ActionListener {

    private JButton saveImageButton;
    private XYChart chart;
    private String[] compareInfo;


    public Plotter(String xAxisName, String yAxisName, String functionName, double[] xData, double[] yData, String[] compareInfo){

        this.compareInfo = compareInfo;

        String title = compareInfo[1] + "/" + compareInfo[2] + "/" + compareInfo[0]; //i.e. title = USD/JPY/2018 to get the idea of what is plotted now
        this.setTitle("Plotted Values (" + title + ")" );
        this.setSize(800,550);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //Just dispose of this current frame, we don't want to close the whole app
        this.setLayout(new BorderLayout());

        //Get the resources using classpath
        ImageIcon appImage = new ImageIcon(Plotter.class.getResource("res/plotter_logo.png"));
        this.setIconImage(appImage.getImage());

        //Button panel
        //A normal "Save" button creation and setup with action listener
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(800, 50));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10, 10));

        saveImageButton = new JButton("Save Plot as Image");
        saveImageButton.setFocusable(false);
        saveImageButton.addActionListener(this);

        buttonPanel.add(saveImageButton);
        this.add(buttonPanel,BorderLayout.SOUTH); //Put the panel with the Save Plot Button down in the form

        //Plotter panel
        //Using XChart lib we get a chart created
        chart = QuickChart.getChart(title, xAxisName, yAxisName, functionName, xData, yData);
        JPanel plotPanel = new XChartPanel<>(chart); //Transform the created chard into a panel ready to be added in form
        plotPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED)); //Just aesthetics

        this.add(plotPanel);
        this.setVisible(true);

    }

    private void saveImage(XYChart chart) throws Exception{
        //Set up the image name that has to be created i.e. "USD_JPY_2015" and then the extensions based on the wanted format
        String title = compareInfo[1] + "_" + compareInfo[2] + "_" + compareInfo[0];

        //Save as normal .PNG, other formats can be .JPG, .GIF, .BMP and different resolutions in case if needed
        BitmapEncoder.saveBitmap(chart, "./" + title, BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmapWithDPI(chart, "./" + title +"_150_DPI", BitmapEncoder.BitmapFormat.PNG, 150);
        BitmapEncoder.saveBitmapWithDPI(chart, "./" + title +"_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveImageButton){
            try{
                //save the images if button pressed
                saveImage(chart);
                JOptionPane.showMessageDialog(new JFrame(), "Image saved succesfully!", "", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Failed to save image", "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
