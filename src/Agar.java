import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * Agar.io: Take a look at the game agar.io. Try to implement it.
 *
 * @author Kameswar Malladi
 */
public class Agar extends GraphicsProgram {

    /**
     * Width and height of application window in pixels
     */
    private static final int WIDTH = 600;
    private static final int HEIGHT = 600;

    private static final int DELAY = 20;
    private static final int MOUSE_MOVE_SPEED = 10;

    /**
     * Radius of the ball in pixels
     */
    private static final int CELL_RADIUS = 20;
    private static final int PELLET_RADIUS = 10;

    private RandomGenerator rgen = RandomGenerator.getInstance();

    // instance variable go here:
    private boolean isGameOver = false;
    private GOval cell;
    private GImage backgroundImage;

    /**
     * Runs the Agar.io program.
     */
    public void run() {
        setupGame();
        playGame();
    }

    private void playGame() {
        // Game loop
        while (!isGameOver) {
            createRandomPellets();
            checkForCollisions();
            pause(DELAY);
        }
    }

    private void checkForCollisions() {
        GObject obj = getElementAt(cell.getX(), cell.getY());
        if (obj != null) { // make sure we have a collision
            // make sure we are not colliding with ourselves or the background image
            if (obj != cell && obj != backgroundImage) {
                // must be a pellet
                if (obj.getClass() == GOval.class) {
                    GOval collidedPellet = (GOval) obj;
                    //Take the color of the pellet collided
                    cell.setFillColor(collidedPellet.getFillColor());
                    remove(obj);
                    cell.setSize(cell.getWidth() + 1, cell.getHeight() + 1);
                }
            }
        }
    }

    private void createRandomPellets() {
        if (rgen.nextInt(10) < 1) {
            GOval pellet = new GOval(PELLET_RADIUS, PELLET_RADIUS);
            pellet.setFilled(true);
            pellet.setFillColor(rgen.nextColor());
            add(pellet, rgen.nextInt(WIDTH), rgen.nextInt(HEIGHT));
        }
    }

    private void setupGame() {
        setSize(WIDTH, HEIGHT);
        setBackgroundImage();
        createCell();

        addMouseListeners();
    }

    private void setBackgroundImage() {
        backgroundImage = new GImage("background_grid.png");
        backgroundImage.scale(3);
        add(backgroundImage);
    }

    private void createCell() {
        cell = new GOval(CELL_RADIUS, CELL_RADIUS);
        add(cell, WIDTH / 2, HEIGHT / 2);
        cell.setFilled(true);
        cell.setFillColor(Color.RED);
    }

    public void mouseMoved(MouseEvent e) {
        // get distance between mouse and cell
        double dx = e.getX() - cell.getX() - cell.getWidth() / 2;
        double dy = e.getY() - cell.getY() - cell.getHeight() / 2;
        // scale
        dx = dx / MOUSE_MOVE_SPEED;
        dy = dy / MOUSE_MOVE_SPEED;
        // move cell
        cell.move(dx, dy);
    }
}
