/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wiztools.restclient.ui;

import java.awt.AWTEvent;
import java.awt.Frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import org.wiztools.restclient.Util;

/**
 *
 * @author Subhash
 */
public class KeyValMultiEntryDialog extends EscapableDialog {

    private JButton jb_file = new JButton(UIUtil.getIconFromClasspath(RCFileView.iconBasePath + "load_from_file.png"));
    private JButton jb_help = new JButton("Help");
    private JButton jb_add = new JButton("Add");
    private JButton jb_cancel = new JButton("Cancel");
    private JScrollPane jsp_in;
    private JTextArea jta_in = new JTextArea(18, 35);
    private JDialog me;
    private Frame frame;
    private MultiEntryAdd callback;

    public KeyValMultiEntryDialog(Frame f, MultiEntryAdd callback) {
        super(f, true);
        me = this;
        frame = f;
        setTitle("Multi-entry");
        this.callback = callback;

        init();
    }

    private void init() {
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());

        JPanel jp_north = new JPanel();
        jp_north.setLayout(new FlowLayout(FlowLayout.LEFT));
        jb_file.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        loadFromFile();
                    }
                });
                
            }
        });
        jp_north.add(jb_file);
        jp_north.add(jb_help);

        jp.add(jp_north, BorderLayout.NORTH);

        jsp_in = new JScrollPane(jta_in);
        jp.add(jsp_in, BorderLayout.CENTER);

        JPanel jp_south = new JPanel();
        jp_south.setLayout(new FlowLayout());
        jb_add.setMnemonic('a');
        jb_add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        add();
                    }
                });
            }
        });
        jp_south.add(jb_add);
        jb_cancel.setMnemonic('c');
        jb_cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        me.setVisible(false);
                    }
                });
            }
        });
        jp_south.add(jb_cancel);

        jp.add(jp_south, BorderLayout.SOUTH);

        jp.setBorder(BorderFactory.createEmptyBorder(RESTView.BORDER_WIDTH,
                RESTView.BORDER_WIDTH, RESTView.BORDER_WIDTH, RESTView.BORDER_WIDTH));
        setContentPane(jp);
        pack();
    }
    
    private void loadFromFile(){
        File f = ((RESTFrame)frame).getOpenFile(FileChooserType.OPEN_TEST_SCRIPT, me);
        if(f != null){
            try{
                String content = Util.getStringFromFile(f);
                Dimension d = jta_in.getPreferredSize();
                jta_in.setText(content);
                jta_in.setPreferredSize(d);
            }
            catch(IOException ex){
                ((RESTFrame)frame).getView().doError(Util.getStackTrace(ex));
            }
        }
    }

    private void add() {
        String str = jta_in.getText();
        if ("".equals(str.trim())) {
            JOptionPane.showMessageDialog(me, "Please enter input text!", "No Input", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String[] line_arr = str.split("\\n");

        List<String> linesNotMatching = new ArrayList<String>();
        Map<String, String> keyValMap = new LinkedHashMap<String, String>();

        for (String line : line_arr) {
            int index = line.indexOf(':');
            if ((index > -1) && (index != 0) && (index != (line.length() - 1))) {
                String key = line.substring(0, index);
                String value = line.substring(index + 1);
                if ("".equals(key.trim()) || "".equals(value.trim())) {
                    linesNotMatching.add(line);
                } else {
                    keyValMap.put(key, value);
                }
            } else {
                if (!"".equals(line.trim())) { // Add only non-blank line
                    linesNotMatching.add(line);
                }
            }
        }

        me.setVisible(false);
        callback.add(keyValMap, linesNotMatching);
    }

    @Override
    public void doEscape(AWTEvent event) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                me.setVisible(false);
            }
        });
    }
}
