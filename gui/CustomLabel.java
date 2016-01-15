package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Reimplementation of JLabel to directly display labels with the applications color scheme.
 */
public class CustomLabel extends JLabel
{
    public CustomLabel(String content)
    {
        super(content);
        this.setForeground(Color.green);
        this.setBackground(Color.black);
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
