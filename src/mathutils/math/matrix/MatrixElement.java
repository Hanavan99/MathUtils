package mathutils.math.matrix;

import mathutils.math.vertex.Vertex2i;

/**
 * Represents an element in a matrix. This object stores the value of the
 * element, as well as a vertex location of the element.
 * 
 * @author Hanavan Kuhn
 *
 */
public class MatrixElement {

    private double value;
    private Vertex2i position;

    /**
     * (Default constructor)
     * 
     * @param value
     *            the value of the element
     * @param position
     *            the position of the element in the matrix
     */
    public MatrixElement(double value, Vertex2i position) {
	this.value = value;
	this.position = position;
    }

    /**
     * @return the value of the matrix element
     */
    public double getValue() {
	return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(double value) {
	this.value = value;
    }

    /**
     * @return the position of the element in the matrix
     */
    public Vertex2i getPosition() {
	return position;
    }

    /**
     * @param position
     *            the position to set
     */
    public void setPosition(Vertex2i position) {
	this.position = position;
    }

}
