package com.codecool.dungeoncrawl.model;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

// Button Renderer class
public class ButtonRenderer extends JButton implements TableCellRenderer {

    // Constructor
    public ButtonRenderer() {
        // Set button properties
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                   int row, int column) {
        // Set passed object as button text
        setText("Load");
        return this;
    }

    //Button editor class
    static class ButtonEditor extends DefaultCellEditor {

        protected JButton button;
        private String label;
        private Boolean clicked;

        public ButtonEditor(JTextField textField) {
            super(textField);

            button = new JButton();
            button.setOpaque(true);

            // When clicked
            button.addActionListener(e -> fireEditingStopped());
        }

        // Override a couple of methods

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

            /*
                Set text to button
                Set clicked to true
                Return button
             */

            label = (value == null) ? "Load" : value.toString();
            button.setText(label);
            clicked = true;
            return button;
        }

        // Button cell value changes when button is clicked
        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                JOptionPane.showMessageDialog(button, label + " is clicked");
            }

            // Set clicked to false when it's clicked
            clicked = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {

            // Set clicked to false
            clicked = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

}
