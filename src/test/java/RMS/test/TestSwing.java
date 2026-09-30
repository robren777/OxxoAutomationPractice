package RMS.test;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

 
@SuppressWarnings("serial")
public class TestSwing extends JFrame {

    // frame
    static JFrame f;

    // label to display text
    static JLabel l;

    // default constructor
    TestSwing()
    {
    }

    // main class
	public static void main(String[] args)
    {
        Graphics g1 = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB).getGraphics();
        System.out.println(g1.getFont());

//        GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
//        System.out.println(Arrays.toString(ge.getAllFonts()));

        Graphics g = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB).getGraphics();

        Font font = new Font(g.getFont().toString(), 0, 12);
        g.dispose();

 

           String font1 = font.toString();


        // create a new frame to store text field and button
        f = new JFrame("label");

        // create a label to display text
        l = new JLabel();

        // add text to label
        l.setText("I " + l.getFont().toString());

        // create a panel
        JPanel p = new JPanel();

        // add label to panel
        p.add(l);

        // add panel to frame
        f.add(p);

        // set the size of frame
        f.setSize(1000, 300);

        f.setVisible(true);


    }



}