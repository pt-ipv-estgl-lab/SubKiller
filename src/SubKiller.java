import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * This panel implements a simple arcade game in which the user tries to blow
 * up a "submarine" (a black oval) by dropping "depth charges" (a red disk) from
 * a "boat" (a blue roundrect). The user moves the boat with the left- and
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
        window.setLocation(100, 100);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); // User can't change the window's size.
        window.setVisible(true);
    }

    // ------------------------------------------------------------------------

    private int width, height; // The size of the panel -- the values are set
    // the first time the paintComponent() method
    // is called. This class is not designed to
    // handle changes in size; once the width and
    // height have been set, they are not changed.
    // Note that width and height cannot be set
    // in the constructor because the width and
    // height of the panel have not been set at
    // the time that the constructor is called.

    private Boat boat; // The boat is defined by nested class

    /**
     * The constructor sets the background color of the panel, creates the
     * timer, and adds a KeyListener, FocusListener, and MouseListener to the
     * panel. These listeners are defined by anonymous inner classes.
     * The ActionListener for the Timer is defined by a lambda expression.
     * The timer will run only when the panel has the input focus; it
     * started and stopped in the FocusListener methods.
     */
    public SubKiller() {
        setBackground(new Color(0, 200, 0));

        addMouseListener(new MouseAdapter() {
            // The mouse listener simply requests focus when the user
            // clicks the panel.
            @Override
            public void mousePressed(MouseEvent evt) {
                requestFocus();
            }
        });

        addFocusListener(new FocusListener() {
            // The focus listener starts the timer when the panel gains
            // the input focus and stops the timer when the panel loses
            // the focus. It also calls repaint() when these events occur.
            public void focusGained(FocusEvent evt) {
                repaint();
            }

            public void focusLost(FocusEvent evt) {
                repaint();
            }
        });

        addKeyListener(new KeyAdapter() {
            // The key listener responds to keyPressed events on the panel. Only
            // the left-, right-, and down-arrow keys have any effect. The left- and
            // right-arrow keys move the boat while down-arrow releases the bomb.
            @Override
            public void keyPressed(KeyEvent evt) {
                int code = evt.getKeyCode(); // Which key was pressed?
                if (code == KeyEvent.VK_LEFT) {
                    // Move the boat left. (If this moves the boat out of the frame, its
                    // position will be adjusted in the boat.updateForNewFrame() method.)
                    boat.centerX -= 15;
                } else if (code == KeyEvent.VK_RIGHT) {
                    // Move the boat right. (If this moves boat out of the frame, its
                    // position will be adjusted in the boat.updateForNewFrame() method.)
                    boat.centerX += 15;
                }
                boat.updateForNewFrame(); // TODO: to move elsewere
                repaint();
            }
        });

    } // end of constructor

    /**
     * The paintComponent() method draws the current state of the game. It
     * draws a gray or cyan border around the panel to indicate whether or not
     * the panel has the input focus. It draws the boat, sub, and bomb by
     * calling their respective draw() methods.
     */
    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g); // Fill panel with background color, green.

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (boat == null) {
            // The first time that paintComponent is called, it assigns
            // values to the instance variables.
            width = getWidth();
            height = getHeight();
            boat = new Boat();
        }

        if (hasFocus())
            g.setColor(Color.CYAN);
        else {
            g.setColor(Color.BLACK);
            g.drawString("CLICK TO ACTIVATE", 20, 30);
            g.setColor(Color.GRAY);
        }
        g.drawRect(0, 0, width - 1, height - 1); // Draw a 3-pixel border.
        g.drawRect(1, 1, width - 3, height - 3);
        g.drawRect(2, 2, width - 5, height - 5);

        boat.draw(g);

    } // end paintComponent()

    /**
     * This nested class defines the boat. Note that its constructor cannot
     * be called until the width of the panel is known!
     */
    private class Boat {
        int centerX, centerY; // Current position of the center of the boat.

        Boat() { // Constructor centers the boat horizontally, 80 pixels from top.
            centerX = width / 2;
            centerY = 80;
        }

        void updateForNewFrame() { // Makes sure boat has not moved off screen.
            if (centerX < 43)
                centerX = 43; // zero if consider the center
            else if (centerX > width - 43)
                centerX = width - 43; // only width if consider the center
        }

        void draw(Graphics g) { // Draws the boat at its current location.
            g.setColor(Color.BLUE);
            g.fillRoundRect(centerX - 40, centerY - 20, 80, 40, 20, 20);
        }
    } // end nested class Boat

} // end of SubKiller class
