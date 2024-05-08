import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * This panel implements a simple arcade game in which the user tries to blow
 * up a "submarine" (a black oval) by dropping "depth charges" (a red disk) from 
 * a "boat" (a blue roundrect).  The user moves the boat with the left- and 
 * right-arrow keys and drops the depth charge with the down-arrow key.
 * The sub moves left and right erratically along the bottom of the panel.
 * This class contains a main() routine to allow it to be run as a program.
 */

public class SubKiller extends JPanel {
    public static void main(String[] args) {
        JFrame window = new JFrame("Sub Killer Game");
        SubKiller content = new SubKiller();
        window.setContentPane(content);
        window.setSize(600, 480);
        window.setLocation(100,100);
        window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        window.setResizable(false);  // User can't change the window's size.
        window.setVisible(true);        
    }
}
