package Gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectSerial extends JApplet {

    private JComboBox c = new JComboBox();
    private JButton b = new JButton("Select port");
    private int count = 0;
    public String serialPort = null;

    private String[] availablePorts = null;

    public SelectSerial(String[] availablePorts) {
        this.availablePorts = availablePorts;
    }
    
    public void init() {  
	
        for(int i = 0; i < availablePorts.length; i++) {
            String item = availablePorts[count++];
            /* Do not add empty items */
            if (item != null) {
                c.addItem(item);
            }
        }

        b.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                serialPort = availablePorts[c.getSelectedIndex()];
            }
        });

        Container cp = getContentPane();
        cp.setLayout(new FlowLayout());
        cp.add(c);
        cp.add(b);
    }

    public Boolean isReady() {
        if (this.serialPort != null) {
            return true;
        }
        return false;
    }

    public String getSelection() {
	  return this.serialPort;
    }
    
    public static void run(SelectSerial applet) {
        JFrame frame = new JFrame("Select serial port");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(applet);
        frame.setSize(300, 70);
        applet.init();
        applet.start();
        frame.setVisible(true);
        frame.setResizable(false);
        while(!applet.isReady()) {
        try {
            Thread.sleep(50);
        } catch (Exception e) {}
        }
        applet.stop();
        frame.dispose();
    }

}