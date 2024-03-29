package org.wiztools.restclient.ui;

import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 *  textarea script editor
 */
class TextAreaScriptEditor implements ScriptEditor {
    private JTextArea textArea = new JTextArea();
    private JScrollPane jsp = new JScrollPane(textArea);

    /**
     * view component for test script editor
     *
     * @return JComponent object
     */
    public JComponent getEditorView() {
        return jsp;
    }

    public JTextComponent getEditorComponent() {
        return textArea;
    }

    /**
     * get test script code
     *
     * @return script
     */
    public String getText() {
        return textArea.getText();
    }

    /**
     * set text script code
     *
     * @param text script code
     */
    public void setText(String text) {
        textArea.setText(text);
    }

    /**
     * set caret position
     *
     * @param offset offset
     */
    public void setCaretPosition(int offset) {
        textArea.setCaretPosition(offset);
    }

    /**
     * set editable mark
     *
     * @param editable editable mark
     */
    public void setEditable(boolean editable) {
        textArea.setEditable(editable);
    }
}
