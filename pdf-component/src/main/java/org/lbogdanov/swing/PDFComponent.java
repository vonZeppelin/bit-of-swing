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

import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;
import java.net.URI;
import java.util.concurrent.atomic.AtomicBoolean;

import com.apple.eawt.CocoaComponent;

/**
 * MacOS native PDF component implemented through WebKit calling from Java part.
 * <p>
 * NOTICE: To use <code>PDFComponent</code> component on a <code>JDialog</code> instance, use DOCUMENT_MODAL modality
 * type for a dialog.
 * 
 * @author Leonid Bogdanov
 * @version 0.1
 */
public class PDFComponent extends CocoaComponent {

    private static final String LIB_NAME = "PDFComponent";

    private static final int REDRAW = 1;

    private final long nsViewPtr;

    private AtomicBoolean isDestroyed = new AtomicBoolean();

    static {
        System.loadLibrary(LIB_NAME);
    }

    /**
     * Creates a new instance of <code>PDFComponent</code>.
     */
    public PDFComponent() {
        nsViewPtr = initNSViewLong();
        addComponentListener(new ComponentAdapter() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void componentResized(ComponentEvent e) {
                sendMessage(REDRAW, null);
            }

            /**
             * {@inheritDoc}
             */
            @Override
            public void componentMoved(ComponentEvent e) {
                sendMessage(REDRAW, null);
            }

        });
        addHierarchyListener(new HierarchyListener() {

            /**
             * {@inheritDoc}
             */
            public void hierarchyChanged(HierarchyEvent e) {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) == HierarchyEvent.SHOWING_CHANGED) {
                    if (isShowing()) {
                        sendMessage(REDRAW, null);
                    }
                }
            }

        });
    }

    /**
     * Loads a specified PDF into the component.
     * 
     * @param path the path to PDF to load
     */
    public void setPDF(URI path) {
        setPDF(path.toString());
    }

    /**
     * Loads a specified PDF into the component.
     * 
     * @param path the path to PDF to load
     */
    public void setPDF(File path) {
        setPDF(path.toURI());
    }

    /**
     * Releases a native component.
     * Calling this method more than once has no effect.
     */
    public void destroy() {
        if (isDestroyed.compareAndSet(false, true)) {
            destroyImpl();
        }
    }

    /**
     * Enables usage of plugins in the WebKit instance (e.g., to load Adobe Reader).
     * 
     * @param enable <b>true</b> if plugins should be enabled
     */
    public native void setEnablePlugins(boolean enable);

    /**
     * {@inheritDoc}
     */
    @Override
    public long createNSViewLong() {
        return nsViewPtr;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int createNSView() {
        return (int) createNSViewLong();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getMinimumSize() {
        return new Dimension(5, 5);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Dimension getPreferredSize() {
        return getMinimumSize();
    }

    /**
     * Creates a new instance of WebKit in the native part and returns a pointer to it.
     * 
     * @return the pointer to a newly created WebKit instance
     */
    protected native long initNSViewLong();

    /**
     * Loads a specified PDF into the component.
     * 
     * @param path the path to PDF to load
     */
    protected native void setPDF(String path);

    /**
     * Releases the native component.
     */
    private native void destroyImpl();

}
