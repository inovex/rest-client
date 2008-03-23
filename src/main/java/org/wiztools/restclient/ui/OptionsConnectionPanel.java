package org.wiztools.restclient.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import org.wiztools.restclient.GlobalOptions;

/**
 *
 * @author Subhash
 */
public class OptionsConnectionPanel extends JPanel implements IOptionsPanel {

    private static final String MINUTES = "Minutes";
    private static final String SECONDS = "Seconds";
    private static final String MILLISECONDS = "Milli-seconds";
    private JRadioButton jrb_minutes = new JRadioButton(MINUTES);
    private JRadioButton jrb_seconds = new JRadioButton(SECONDS);
    private JRadioButton jrb_millisecs = new JRadioButton(MILLISECONDS);
    private JFormattedTextField jftf_timeout = new JFormattedTextField(GlobalOptions.DEFAULT_TIMEOUT_MILLIS);
    
    // Holds the previous selection for convertion between units:
    private String lastSelected;
    
    // Last okyed
    private String ok_type = MILLISECONDS;
    private Integer ok_value = GlobalOptions.DEFAULT_TIMEOUT_MILLIS;

    public OptionsConnectionPanel() {
        ButtonGroup bg = new ButtonGroup();
        bg.add(jrb_minutes);
        bg.add(jrb_seconds);
        bg.add(jrb_millisecs);
        jrb_millisecs.setSelected(true);
        lastSelected = MILLISECONDS;

        ConvertListener al = new ConvertListener();
        jrb_minutes.addActionListener(al);
        jrb_seconds.addActionListener(al);
        jrb_millisecs.addActionListener(al);

        JPanel jp_radio = new JPanel();
        jp_radio.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp_radio.add(jrb_minutes);
        jp_radio.add(jrb_seconds);
        jp_radio.add(jrb_millisecs);

        JPanel jp_timeout = new JPanel();
        jp_timeout.setLayout(new FlowLayout(FlowLayout.LEFT));
        jftf_timeout.setColumns(20);
        jp_timeout.add(jftf_timeout);
        
        JPanel jp_label_grid = new JPanel();
        jp_label_grid.setLayout(new GridLayout(2, 1));
        jp_label_grid.add(new JLabel("Timeout in: "));
        jp_label_grid.add(new JLabel("Value: "));
        
        JPanel jp_input_grid = new JPanel();
        jp_input_grid.setLayout(new GridLayout(2, 1));
        jp_input_grid.add(jp_radio);
        jp_input_grid.add(jp_timeout);
        
        JPanel jp_encp = this;
        jp_encp.setLayout(new BorderLayout());
        jp_encp.add(jp_label_grid, BorderLayout.WEST);
        jp_encp.add(jp_input_grid, BorderLayout.CENTER);
    }
    
    int getTimeoutInMillis(){
        int value = (Integer)jftf_timeout.getValue();
        
        if(jrb_seconds.isSelected()){
            return value * 1000;
        }
        else if(jrb_minutes.isSelected()){
            return value * 60 * 1000;
        }
        // is milli-seconds:
        return value;
    }

    class ConvertListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (jrb_millisecs.isSelected()) {
                if (lastSelected.equals(MILLISECONDS)) {
                    return;
                } else if (lastSelected.equals(SECONDS)) {
                    // Convert seconds to millis:
                    int valueInSecs = (Integer) jftf_timeout.getValue();
                    int valueInMillis = valueInSecs * 1000;
                    jftf_timeout.setValue(valueInMillis);
                } else if (lastSelected.equals(MINUTES)) {
                    // Convert mins to millis:
                    int valueInMins = (Integer) jftf_timeout.getValue();
                    int valueInMillis = valueInMins * 60 * 1000;
                    jftf_timeout.setValue(valueInMillis);
                }
                // Update the lastSelected:
                lastSelected = MILLISECONDS;
            } else if (jrb_seconds.isSelected()) {
                if (lastSelected.equals(MILLISECONDS)) {
                    // Convert millis to seconds:
                    int valueInMillis = (Integer) jftf_timeout.getValue();
                    int valueInSecs = valueInMillis / 1000;
                    jftf_timeout.setValue(valueInSecs);
                } else if (lastSelected.equals(SECONDS)) {
                    return;
                } else if (lastSelected.equals(MINUTES)) {
                    // Convert mins to seconds:
                    int valueInMins = (Integer) jftf_timeout.getValue();
                    int valueInSecs = valueInMins * 60;
                    jftf_timeout.setValue(valueInSecs);
                }
                // Update the lastSelected:
                lastSelected = SECONDS;
            } else if (jrb_minutes.isSelected()) {
                if (lastSelected.equals(MILLISECONDS)) {
                    // Convert millis to minutes
                    int valueInMillis = (Integer) jftf_timeout.getValue();
                    int valueInMins = valueInMillis / (1000 * 60);
                    jftf_timeout.setValue(valueInMins);
                } else if (lastSelected.equals(SECONDS)) {
                    // Convert seconds to minutes:
                    int valueInSecs = (Integer) jftf_timeout.getValue();
                    int valueInMins = valueInSecs / 60;
                    jftf_timeout.setValue(valueInMins);
                } else if (lastSelected.equals(MINUTES)) {
                    return;
                }
                // Update the lastSelected:
                lastSelected = MINUTES;
            }
        }
    }

    @Override
    public List<String> validateInput() {
        return null;
    }
    
    @Override
    public boolean saveOptions(){
        int reqTimeout = (Integer)jftf_timeout.getValue();
        
        ok_type = MILLISECONDS;
        if(jrb_minutes.isSelected()){
            reqTimeout = 60 * 1000 * reqTimeout;
            ok_type = MINUTES;
        }
        else if(jrb_seconds.isSelected()){
            reqTimeout = 1000 * reqTimeout;
            ok_type = SECONDS;
        }
        ok_value = reqTimeout;
        
        GlobalOptions options = GlobalOptions.getInstance();
        options.acquire();
        options.setRequestTimeoutInMillis(reqTimeout);
        options.release();
        
        return true;
    }
    
    @Override
    public boolean revertOptions(){
        if(ok_type.equals(MILLISECONDS)){
            jrb_millisecs.setSelected(true);
        }
        else if(ok_type.equals(SECONDS)){
            jrb_seconds.setSelected(true);
        }
        else if(ok_type.equals(MINUTES)){
            jrb_minutes.setSelected(true);
        }
        jftf_timeout.setValue(ok_value);
        return true;
    }
}
