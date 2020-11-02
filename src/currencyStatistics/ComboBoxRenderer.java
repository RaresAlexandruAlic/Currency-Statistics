package currencyStatistics;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ComboBoxRenderer extends DefaultListCellRenderer { //Rendering Class for Country Flag + Name

    private final Map<String, ImageIcon> iconMap = new HashMap<>();

    public ComboBoxRenderer(String[] currenciesABV) {
        for (String s : currenciesABV) {
            //Set every flag icon coresponding to the currency abreviation key
            //Get image using class path
            iconMap.put(s, new ImageIcon(ComboBoxRenderer.class.getResource("res/countryFlag/" + s + ".png")));
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label =  (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setIcon(iconMap.get(value)); //Set flag for the label
        return label;
    }
}
