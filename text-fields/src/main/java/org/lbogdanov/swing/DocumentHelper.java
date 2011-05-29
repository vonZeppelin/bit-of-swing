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

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

import com.jidesoft.swing.Overlayable;

/**
 * <code>DocumentHelper</code> class receives notifications of changes to a text document of a
 * <code>JTextComponent</code> and refreshes visibility of the overlayable button accordingly.
 * 
 * @see Overlayable
 * @author Leonid Bogdanov
 */
final class DocumentHelper implements DocumentListener {

    private JTextComponent component;
    private Overlayable overlayable;

    /**
     * {@inheritDoc}
     */
    public void changedUpdate(DocumentEvent e) {}

    /**
     * {@inheritDoc}
     */
    public void insertUpdate(DocumentEvent e) {
        refreshOverlayableVisibility();
    }

    /**
     * {@inheritDoc}
     */
    public void removeUpdate(DocumentEvent e) {
        refreshOverlayableVisibility();
    }

    DocumentHelper(JTextComponent component, Overlayable overlayable) {
        this.component = component;
        this.overlayable = overlayable;
        refreshOverlayableVisibility();
    }

    private void refreshOverlayableVisibility() {
        overlayable.setOverlayVisible(component.getText().length() > 0);
    }

}
