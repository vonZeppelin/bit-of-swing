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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.text.JTextComponent;

import com.jidesoft.swing.OverlayableIconsFactory;
import com.jidesoft.swing.OverlayableUtils;

/**
 * <code>ClearTextAction</code> class represents an action that clears the content of the specified text component.
 * It provides a short description (e.g. used for tooltips) and an icon that can be used by a component associated
 * with an instance of <code>ClearTextAction</code>.
 * 
 * @see javax.swing.Action
 * @author Leonid Bogdanov
 */
public class ClearTextAction extends AbstractAction {

    private JTextComponent component;

    /**
     * Constructs a new instance of <code>ClearTextACtion</code>.
     * 
     * @param component the <code>JTextComponent</code> to clear when the action will be performed
     */
    public ClearTextAction(JTextComponent component) {
        putValue(SHORT_DESCRIPTION, "Clear"); // i18n!
        putValue(SMALL_ICON, OverlayableUtils.getPredefinedOverlayIcon(OverlayableIconsFactory.ERROR));
        this.component = component;
    }

    /**
     * {@inheritDoc}
     */
    public void actionPerformed(ActionEvent e) {
        component.setText("");
    }

}
