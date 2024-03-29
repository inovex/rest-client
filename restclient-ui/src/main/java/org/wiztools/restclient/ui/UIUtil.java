package org.wiztools.restclient.ui;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

/**
 *
 * @author Subhash
 */
final class UIUtil {
    
    private UIUtil(){}
    
    public static final Font FONT_DIALOG_12_PLAIN = new Font(Font.DIALOG, Font.PLAIN, 12);
    public static final Font FONT_DIALOG_12_BOLD = new Font(Font.DIALOG, Font.BOLD, 12);
    
    private static final RCFileView fileView = new RCFileView();
    public static final JFileChooser getNewJFileChooser(){
        JFileChooser jfc = new JFileChooser();
        jfc.setFileView(fileView);
        return jfc;
    }
    
    public static ImageIcon getIconFromClasspath(String path){
        URL url = UIUtil.class.getClassLoader().getResource(path);
        return new ImageIcon(url);
    }
    
    public static JPanel getFlowLayoutPanelLeftAligned(Component component){
        return getFlowLayoutPanelLeftAligned(null, component);
    }
    
    public static JPanel getFlowLayoutPanelLeftAligned(String title, Component component){
        JPanel jp = new JPanel();
        jp.setLayout(new FlowLayout(FlowLayout.LEFT));
        if(title != null){
            if(component instanceof JPanel){
                JPanel p = (JPanel)component;
                p.setBorder(BorderFactory.createTitledBorder(title));
            }
        }
        jp.add(component);
        return jp;
    }
    
    public static void clipboardCopy(String str) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(str), null);
    }
}
