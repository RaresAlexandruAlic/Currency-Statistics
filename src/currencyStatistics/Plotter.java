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

        String title = compareInfo[1] + "/" + compareInfo[2] + "/" + compareInfo[0];
        this.setTitle("Plotted Values (" + title + ")" );
        this.setSize(800,550);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());


        ImageIcon appImage = new ImageIcon(Plotter.class.getResource("res/plotter_logo.png")); //create ImageIcon for the logo of the app from resources
        this.setIconImage(appImage.getImage());

        //Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(800, 50));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10, 10));

        saveImageButton = new JButton("Save Plot as Image");
        saveImageButton.setFocusable(false);
        saveImageButton.addActionListener(this);

        buttonPanel.add(saveImageButton);
        this.add(buttonPanel,BorderLayout.SOUTH);

        //Plotter panel
        chart = QuickChart.getChart(title, xAxisName, yAxisName, functionName, xData, yData);
        JPanel plotPanel = new XChartPanel<>(chart);
        plotPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));

        this.add(plotPanel);
        this.setVisible(true);

    }

    private void saveImage(XYChart chart) throws Exception{
        String title = compareInfo[1] + "_" + compareInfo[2] + "_" + compareInfo[0];
        BitmapEncoder.saveBitmap(chart, "./" + title, BitmapEncoder.BitmapFormat.PNG);
        BitmapEncoder.saveBitmapWithDPI(chart, "./" + title +"_150_DPI", BitmapEncoder.BitmapFormat.PNG, 150);
        BitmapEncoder.saveBitmapWithDPI(chart, "./" + title +"_300_DPI", BitmapEncoder.BitmapFormat.PNG, 300);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveImageButton){
            try{
                saveImage(chart);
                JOptionPane.showMessageDialog(new JFrame(), "Image saved succesfully!", "", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception exception) {
                exception.printStackTrace();
                JOptionPane.showMessageDialog(new JFrame(), "Failed to save image", "", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
