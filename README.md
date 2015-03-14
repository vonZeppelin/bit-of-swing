# Bit-Of-Swing #

Components for Java Swing framework.

To compile and run, use [Apache Maven](http://maven.apache.org/):

  1. Checkout sources;
  2. Build the project using `mvn clean install` command;
  3. Run the Demo application using `mvn exec:java` command.

Notice that for the Demo app of pdf-component _java.library.path_ variable should contain a path to the **libPDFComponent.jnilib** file.

## Text fields ##
Skype-like text fields (button to clear field's content will appear when the field isn't empty) for Swing.

Requirements: Java 1.5+

[Java Web Start demo](../../releases/download/v0.0.1/text-fields.jnlp)

## PDF component ##
Cocoa based PDF component for Swing.

Requirements: **Apple Java 1.6 only**; MacOS X 10.6+, Intel arch (should work on MacOS X 10.5 too)

[Java Web Start demo](../../releases/download/v0.0.1/pdf-component.jnlp)
