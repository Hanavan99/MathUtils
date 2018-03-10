package mathutils.core;

import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Represents an object that can be drawn using a {@code Graphics2D} object.
 * 
 * @author Hanavan Kuhn
 *
 */
public interface Drawable {

	/**
	 * Draws a particular expression using the provided {@code Graphics2D} object
	 * and drawing properties.
	 * 
	 * @param g
	 *            the graphics object
	 * @param props
	 *            the drawing properties to use when drawing
	 * @param x
	 *            the x-coordinate to draw at
	 * @param y
	 *            the y-coordinate to draw at
	 * @return the size of the drawn expression; used for determining the size of
	 *         the parent expressions
	 */
	public Rectangle draw(Graphics2D g, DrawProperties props, int x, int y);

}
