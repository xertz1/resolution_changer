/*
 * @author Kishore Piratheepan
 * ID: Kpirathe 
 * Student Number: 251345243
 * Date: 04/05/24
 * Class: Computer Science Fundmentals II 1027B 
 * Assignment 4
 * 
 * This Class reperesents a node inside the quadrant tree. 
 * Each Node contains information about the pixel.
 */

public class QTreeNode {
    int x, y; // x , y upper coordiante of quadrant
    int size; // Size of qudrant
    int color; // Average colour of quadrant
    QTreeNode parent; // Parent of node
    QTreeNode[] children; // Children of nodes

    /*
     * Constructs Node with no given parameters.
     * Initalizes with default values.
     */
    public QTreeNode() {
        this.x = 0;
        this.y = 0;
        this.size = 0;
        this.color = 0;

        this.parent = null;
        this.children = new QTreeNode[4];
    }

    /*
     * Constructs node given paramters
     * 
     * @param xcoord - the upper left x coordiante of quadrant
     * 
     * @param ycoord - the upper left y coordiante of quadrant
     * 
     * @param theSize - the size of the quadrant
     * 
     * @param theColor - the average colour of the pixels
     * 
     * @param theChildren - the children of the node
     * 
     */
    public QTreeNode(QTreeNode[] theChildren, int xcoord, int ycoord, int theSize, int theColor) {
        this.x = xcoord;
        this.y = ycoord;
        this.size = theSize;
        this.color = theColor;

        this.parent = null;
        this.children = theChildren;

    }

    /*
     * Checks if the x and y position are in the quadrant
     * 
     * @param xcoord - the x coordiante to be checked
     * 
     * @param ycoord - the y coordiante to be checked
     * 
     * @return - returns true if x, y is contained in quadrant else false
     */
    public boolean contains(int xcoord, int ycoord) {

        if ((xcoord >= this.x && xcoord <= (this.x + size - 1))
                && (ycoord >= this.y && ycoord <= (this.y + size - 1))) {
            return true;
        }

        return false;
    }

    /*
     * Returns x positon
     * 
     * @return x
     */
    public int getx() {
        return this.x;
    }

    /*
     * Returns y positon
     * 
     * @return y
     */
    public int gety() {
        return this.y;
    }

    /*
     * Returns size of quadrant
     * 
     * @return size
     */
    public int getSize() {
        return this.size;
    }

    /*
     * Returns colour of qudrant
     * 
     * @return color
     */
    public int getColor() {
        return this.color;
    }

    /*
     * Returns the parent of the node
     * 
     * @return parent
     */
    public QTreeNode getParent() {
        return this.parent;
    }

    /*
     * Returns the children at the specifci index
     * 
     * @param index - index of the children
     * 
     * @return thechildren
     */
    public QTreeNode getChild(int index) {
        if (index < 0 || index > 3) {
            throw new QTreeException("ERROR"); // Throws exception if index does not exist
        } else if (this.children == null) {
            throw new QTreeException("ERROR"); // Throws exception if child does not exist
        } else {
            return this.children[index];
        }
    }

    /*
     * Sets new x value
     * 
     * @param newx - the new x value
     */
    public void setx(int newx) {
        this.x = newx;
    }

    /*
     * Sets new y value
     * 
     * @param newy - the new y value
     */
    public void sety(int newy) {
        this.y = newy;
    }

    /*
     * Sets new size value
     * 
     * @param newSize - the new x value
     */
    public void setSize(int newSize) {
        this.size = newSize;
    }

    /*
     * Sets new colour
     * 
     * @param setColor - the new average colour
     */
    public void setColor(int newColor) {
        this.color = newColor;
    }

    /*
     * Sets new parent value
     * 
     * @param newParent - the new parent node
     */
    public void setParent(QTreeNode newParent) {
        this.parent = newParent;
    }

    /*
     * Sets new child at a specific index in the array
     * 
     * @param newChild - the new child node
     * 
     * @param index - the index wanting to be changed
     */
    public void setChild(QTreeNode newChild, int index) {
        if (index < 0 || index > 3) {
            throw new QTreeException("ERROR"); // Throws exception if index does not exist
        }

        this.children[index] = newChild;
    }

    /*
     * Checks if the ndode is a leaf node
     * 
     * @return true if the node is a leaf otherwise false
     */
    public boolean isLeaf() {
        int count = 0; // Initalizes count to keep track of null nodes

        // Checks if child is null
        if (this.children == null) {
            return true;
        }

        // Checks if each child is null
        for (int i = 0; i < this.children.length; i++) {
            if (this.children[i] == null) {
                count++;
            }
        }

        // returns true if all nodes are null
        if (count == this.children.length) {
            return true;
        }

        return false;

    }

}
