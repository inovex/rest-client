package org.wiztools.restclient.ui;

import javax.swing.*;
import javax.swing.text.JTextComponent;

/**
 *
 */
interface ScriptEditor {

    /**
     * view component for test script editor
     *
     * @return JComponent object
     */
    public JComponent getEditorView();

    /**
     * The editor component is returned.
     * @return
     */
    public JTextComponent getEditorComponent();

    /**
     * get test script code
     *
     * @return script
     */
    public String getText();

    /**
     * set text script code
     *
     * @param text script code
     */
    public void setText(String text);

    /**
     * set caret position
     *
     * @param offset offset
     */
    public void setCaretPosition(int offset);

    /**
     * set editable mark
     *
     * @param editable editable mark
     */
    public void setEditable(boolean editable);
}