/*
 * Copyright (c) 2011, Leonid Bogdanov
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.lbogdanov.swing;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import com.jidesoft.swing.DefaultOverlayable;
import com.jidesoft.swing.JideButton;


/**
 * <code>TextFieldUtils</code> class provides a functionality to add an overlayable "Clear" button to the specified
 * text component.
 * 
 * @author Leonid Bogdanov
 */
public class TextFieldUtils {

    /**
     * Adds an overlayable "Clear" button to the specified text component and returns a compound component that should
     * be actually added to a container (instead of the specified text component).
     * 
     * @param tc the text component
     * @return the compound component to be added to a container
     */
    public static JComponent addClearButton(JTextComponent tc) {
        final AbstractButton clearButton = new JideButton(new ClearTextAction(tc));
        final DefaultOverlayable overlayable = new DefaultOverlayable(tc, clearButton, computeOverlayLocation(tc));
        tc.getDocument().addDocumentListener(new DocumentHelper(tc, overlayable));
        tc.addPropertyChangeListener("componentOrientation", new PropertyChangeListener() {

            public void propertyChange(PropertyChangeEvent pce) {
                overlayable.setOverlayLocation(clearButton, computeOverlayLocation((Component) pce.getSource()));
            }

        });
        return overlayable;
    }

    /**
     * Returns a location of an overlayable component relatively to a parent component.
     * 
     * @param component the parent component
     * @return <code>SwingConstants.EAST</code> or <code>SwingConstants.WEST</code> value
     */
    private static int computeOverlayLocation(Component component) {
        return component.getComponentOrientation().isLeftToRight() ? SwingConstants.EAST : SwingConstants.WEST;
    }

    private TextFieldUtils() {};

}
