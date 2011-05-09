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

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.miginfocom.swing.MigLayout;

/**
 * Class <code>DemoApp</code> contains the entry point of a demo application.
 * This application demonstrates usage of native PDF component for Swing, MacOS X.
 * 
 * @author Leonid Bogdanov
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
                final PDFComponent pdfComponent = new PDFComponent();
                final JFrame frame = new JFrame("PDFComponent demo application");
                frame.setLayout(new MigLayout("wrap 1", "10[]10", "10[]para[]10"));
                frame.addWindowListener(new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {
                        pdfComponent.destroy();
                    }

                });
                frame.add(pdfComponent, "dock center");
                frame.add(new JButton(new AbstractAction("Load PDF file...") {

                    public void actionPerformed(ActionEvent e) {
                        JFileChooser fileChooser = new JFileChooser();
                        fileChooser.setFileFilter(new FileNameExtensionFilter("PDF files", "pdf"));
                        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                            pdfComponent.setPDF(fileChooser.getSelectedFile());
                        }
                    }

                }), "align center");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(640, 480);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

}
