/*
 * @author Kishore Piratheepan
 * ID: Kpirathe 
 * Student Number: 251345243
 * Date: 04/05/24
 * Class: Computer Science Fundmentals II 1027B 
 * Assignment 4
 * 
 * This Class Builds a quadrant tree containting information contained in it. 
 * With methods to get nodes, pixels, and finding matching nodes. 
 */

public class QuadrantTree {
    QTreeNode root; // The root of the qudrant tree

    /*
     * Builds the quadrant tree corresponding to the pixels given in the 2D array
     * 
     * @param thePixels - the pixels of the image given in an array
     */
    public QuadrantTree(int[][] thePixels) {
        QTreeNode[] children = new QTreeNode[4]; // Initalizes empty child for root
        root = new QTreeNode(children, 0, 0, thePixels.length,
                Gui.averageColor(thePixels, 0, 0, thePixels.length)); // Initalizes the root of the tree
        buildTree(thePixels, root); // Calls buildTree method
    }

    /*
     * Creates the quadrant tree with the corresponding information
     * 
     * @param thePixels - the pixels of the image in an array
     * 
     * @param root - the root of the tree
     */
    private void buildTree(int[][] thePixels, QTreeNode root) {

        if (root.getSize() != 1) {
            // Creates all the children
            QTreeNode[] c1 = new QTreeNode[4];
            QTreeNode[] c2 = new QTreeNode[4];
            QTreeNode[] c3 = new QTreeNode[4];
            QTreeNode[] c4 = new QTreeNode[4];

            // Create 4 seprate quarant tree nodes to be added to the root
            QTreeNode Q1 = new QTreeNode(c1, root.getx(), root.gety(), root.getSize() / 2,
                    Gui.averageColor(thePixels, root.getx(), root.gety(), root.getSize() / 2));
            QTreeNode Q2 = new QTreeNode(c2, root.getx() + root.getSize() / 2, root.gety(), root.getSize() / 2,
                    Gui.averageColor(thePixels, root.getx() + root.getSize() / 2, root.gety(), root.getSize() / 2));
            QTreeNode Q3 = new QTreeNode(c3, root.getx(), root.gety() + root.getSize() / 2, root.getSize() / 2,
                    Gui.averageColor(thePixels, root.getx(), root.gety() + root.getSize() / 2, root.getSize() / 2));
            QTreeNode Q4 = new QTreeNode(c4, root.getx() + root.getSize() / 2, root.gety() + root.getSize() / 2,
                    root.getSize() / 2, Gui.averageColor(thePixels, root.getx() + root.getSize() / 2,
                            root.gety() + root.getSize() / 2, root.getSize() / 2));

            // Sets the children the the node
            root.setChild(Q1, 0);
            root.setChild(Q2, 1);
            root.setChild(Q3, 2);
            root.setChild(Q4, 3);

            // Sets the nodes to the root
            Q1.setParent(root);
            Q2.setParent(root);
            Q3.setParent(root);
            Q4.setParent(root);

            // Creates each node to build the tree
            buildTree(thePixels, Q1);
            buildTree(thePixels, Q2);
            buildTree(thePixels, Q3);
            buildTree(thePixels, Q4);

        } else {
            return;
        }
    }

    /*
     * Gets the root of the tree
     * 
     * @return root - returns the root of the tree
     */
    public QTreeNode getRoot() {
        return this.root;
    }

    /*
     * This returns a list of all the nodes inside the quadrant tree at a specifc
     * level
     * 
     * @param r - the root
     * 
     * @param theLevel - the level of the nodes to be added to the tree
     * 
     * @return - the signly linked list containing all the nodes on the level
     */
    public ListNode<QTreeNode> getPixels(QTreeNode r, int theLevel) {
        if (r == null) { // Returns null if it is an empty tree
            return null;
        }

        ListNode<QTreeNode> lst = null, newNode = null; // Intalilzes the lst - containg all the nodes, newNode - new
                                                        // node added to the tree

        // Checks if node is on the level
        if (nodeLevel(r) == theLevel) {
            lst = new ListNode<QTreeNode>(r);
        }

        // Checks if node is a leaf and if the level given is greater then the tress
        // level
        if (nodeLevel(r) < theLevel && r.isLeaf()) {
            lst = new ListNode<QTreeNode>(r);
        }

        // Checks each node and adds them to the list recursively
        for (int i = 0; i < 4; i++) {
            // Recursive call storing each value
            ListNode<QTreeNode> x = getPixels(r.getChild(i), theLevel);

            // Adds all the nodes to the linked list
            if (x != null) {
                if (lst == null) {
                    lst = x;
                    newNode = lst;
                } else {
                    newNode.setNext(x);
                }

                while (newNode.getNext() != null) {
                    newNode = newNode.getNext();
                }

            }

        }
        return lst;

    }

    /*
     * This returns an object of the class duple storing a list of nodes of in the
     * quadrant level
     * that was specified, and checks if the node is similar to the colour. It also
     * contains the length of the list.
     * 
     * @param r - the root
     * 
     * @param theColor - the colour that the node is checked with
     * 
     * @param theLevel - the level with the quadrants wanting to be added
     * 
     * @return a duple object containing the correct information
     */
    public Duple findMatching(QTreeNode r, int theColor, int theLevel) {
        if (r == null) { // Checks if tree is empty
            return null;
        }

        Duple matching = new Duple(); // The duple object containg all the information
        Duple newMatch = new Duple(); // New node to be added to the linked list

        // Checks if r is on the correct level and has a similar colour
        if (nodeLevel(r) == theLevel && Gui.similarColor(r.getColor(), theColor) == true) {
            matching.setFront(new ListNode<QTreeNode>(r));
        }

        // Checks if the level is greater than the tree and the colour then stores
        // returns the tree object
        if (r.isLeaf() == true && nodeLevel(r) < theLevel && Gui.similarColor(r.getColor(), theColor) == true) {
            matching.setFront(new ListNode<QTreeNode>(r));
        }

        // Creates the linked list
        for (int i = 0; i < 4; i++) {
            // Recursive call storing each value
            Duple x = findMatching(r.getChild(i), theColor, theLevel);

            // Checks if return item is null, if not it adds it to the linked list
            if (x != null && x.getFront() != null) {
                if (matching.getFront() == null) {
                    matching.setFront(x.getFront());
                    newMatch.setFront(matching.getFront());
                } else {
                    newMatch.getFront().setNext(x.getFront());
                }

                while (newMatch.getFront().getNext() != null) {
                    newMatch.setFront(newMatch.getFront().getNext());
                }

            }
        }

        // Sets the length of the linked list in the Duple object
        matching.setCount(listLength(matching.getFront()));

        return matching;
    }

    /*
     * This returns a node in the subtree rooted at r and at the level that
     * repsersnts that quandrant.
     * 
     * @param r - the root
     * 
     * @param theLevel - the desired level
     * 
     * @param x - the x value wanting to be checked
     * 
     * @param y - the y value wanting to be checked
     * 
     * @return returns the node rooted at r at the level containg the points (x,y)
     */
    public QTreeNode findNode(QTreeNode r, int theLevel, int x, int y) {
        if (r == null) { // Checks if the tree is empty
            return null;
        }

        QTreeNode checkNode = null; // Creates a Node to store the correct node

        // Checks if the node matches the conditions
        if (nodeLevel(r) == theLevel && r.contains(x, y)) {
            checkNode = r;
        }

        // Recursively checks through all the nodes to see if it matches the conditon
        for (int i = 0; i < 4; i++) {
            if (r.getChild(i) == null) { // Checks if it is a null child and skips it
                continue;
            }
            QTreeNode foundNode = findNode(r.getChild(i), theLevel, x, y); // Node to store the found Value

            // Checks if it exists and returns it
            if (foundNode != null) {
                if (checkNode == null) {
                    checkNode = foundNode;
                }
            }

        }

        return checkNode;

    }

    /*
     * This method finds the level of a node
     * 
     * @param root - gets the tree node
     * 
     * @return returns the level of the tree node
     */
    private int nodeLevel(QTreeNode root) {
        if (root.getParent() == null) {
            return 0; // Base case
        } else {
            return 1 + nodeLevel(root.getParent()); // Resucively iterates through the node and adds 1
        }
    }

    /*
     * Method to find the length of a linked list
     * 
     * @param lst - the linked list to be checked
     * 
     * @return the amount of nodes in the list
     */
    private int listLength(ListNode<QTreeNode> lst) {
        int count = 0; // Initalizes count

        // While the list is not null adds one for each node
        while (lst != null) {
            count++;
            lst = lst.getNext();
        }

        return count;
    }

}
