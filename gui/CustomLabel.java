package gui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dinervoid on 1/14/16.
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
