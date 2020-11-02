package currencyStatistics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class CurrencyComparator {

    private TreeMap<String, Double> mappedValues;
    private int objectStatus;

    public CurrencyComparator(){
        objectStatus = 0;
    }

    public CurrencyComparator(TreeMap<String, Double> treeMap){
        mappedValues = treeMap;
        objectStatus = 1;
    }

    public int getStatus(){
        return objectStatus;
    }

    public double[] getValues(){

        double[] values = new double[mappedValues.size()];
        int i = 0;

        for(Map.Entry<String, Double> item : mappedValues.entrySet()){
            values[i++] = item.getValue();
        }

        return values;
    }

    public double[] getCompares(){
        //Returns number of times it increased, decreased;
        //Highest value of the year and lowest value of the year
        double minValue, maxValue, nrIncreases = 0, nrDecreases = 0, lastValue;

        //Set the initial values for the first element
        Map.Entry<String, Double> entry = mappedValues.entrySet().iterator().next();
        minValue = maxValue = lastValue = entry.getValue();

        for(Map.Entry<String, Double> item : mappedValues.entrySet()){

            if(item.getValue() > lastValue){
                nrIncreases++;
            }
            else if(item.getValue() < lastValue){
                nrDecreases++;
            }

            if(item.getValue() > maxValue) //Find highest value of the year
                maxValue = item.getValue();

            if(item.getValue() < minValue) //Find lowest value of the year
                minValue = item.getValue();

            lastValue = item.getValue();
        }

        return new double[]{nrIncreases, nrDecreases, maxValue, minValue}; //Return the compared results
    }

    public boolean exportData(String[] csvFields){

        File file = new File(csvFields[1] + "_" + csvFields[2] + "_" + csvFields[0] +".csv");  //Filename format Currency1_Currency2_Year.csv
        try {
            file.createNewFile();
            FileOutputStream outStream = new FileOutputStream(file, false);

            String text;
            byte[] myBytes;

            for(Map.Entry<String, Double> item : mappedValues.entrySet()){
                text = item.getKey() + "," + item.getValue() + "\n";
                myBytes = text.getBytes();
                outStream.write(myBytes);
            }

            outStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
