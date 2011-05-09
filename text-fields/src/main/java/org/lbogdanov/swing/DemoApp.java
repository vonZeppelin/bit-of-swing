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

import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import com.jidesoft.swing.*;

import net.miginfocom.swing.MigLayout;

/**
 * Class <code>DemoApp</code> contains the entry point of a demo application.
 * This application demonstrates creation of "Skypish" text and password fields: a button to quickly clear a field
 * will appear if a field has got some content.
 * <p>
 * JIDE-OSS Swing library and its <code>Overlayable</code> components are used to implement such fields.
 * 
 * @author Leonid Bogdanov
 * @version 0.1
 */
public class DemoApp {

    /**
     * Program entry point.
     * 
     * @param args command line arguments
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignore) {}
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                JTextComponent textField;
                DefaultOverlayable overlayable;
                final JFrame frame = new JFrame("Skype-like text fields demo app");
                frame.setLayout(new MigLayout("wrap 2", "[]15[130::, fill, grow]",
                                              "5[]unrel[]rel[]20[]unrel[]rel[]20:push[]5"));

                frame.add(new TitledSeparator("Text fields"), "span 2, grow");

                // 1. Create an instance of the special subclass of JTextField from JIDE-OSS library (enables usage of
                // Overlayable stuff)
                textField = new OverlayTextField("Example text");
                // 2. Create a concrete instance of Overlayable, provide a component to be overlayed on top of
                // the actual component and its position
                overlayable = new DefaultOverlayable(textField, new JideButton(new ClearTextAction(textField)),
                                                     SwingConstants.EAST);
                // 3. Bind helper class with the text filed and the Overlayable instance
                DocumentHelper.install(textField, overlayable);
                frame.add(new JLabel("RTL text field"));
                // 4. Instance of the Overlayable class must be actually added to a Container! (not a text field itself)
                frame.add(overlayable);

                // code below is similar to the code described above...
                textField = new OverlayTextField();
                overlayable = new DefaultOverlayable(textField, new JideButton(new ClearTextAction(textField)),
                                                     SwingConstants.WEST);
                textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                DocumentHelper.install(textField, overlayable);
                frame.add(new JLabel("LTR text field"));
                frame.add(overlayable);

                frame.add(new TitledSeparator("Password fields"), "span 2, grow");

                textField = new OverlayPasswordField("123456");
                overlayable = new DefaultOverlayable(textField, new JideButton(new ClearTextAction(textField)),
                                                     SwingConstants.EAST);
                DocumentHelper.install(textField, overlayable);
                frame.add(new JLabel("RTL password field"));
                frame.add(overlayable);

                textField = new OverlayPasswordField();
                overlayable = new DefaultOverlayable(textField, new JideButton(new ClearTextAction(textField)),
                                                     SwingConstants.WEST);
                textField.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
                DocumentHelper.install(textField, overlayable);
                frame.add(new JLabel("LTR password field"));
                frame.add(overlayable);

                frame.add(new JButton(new AbstractAction("Close") {

                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                    }

                }), "span 2, align center");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }

        });
    }

}
