package org.taqque.indice;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
public class Btree {

    // Each Btree object is a B-tree header.
    // This B-tree is represented as follows: arity is the maximum number
    // of children per node, and root is a link to its root node.
    // Each B-tree node is represented as follows: size contains its size; a
    // subarray items[0...size-1] contains its elements; and a subarray
    // childs[0...size] contains links to its child nodes. For each element
    // items[i], childs[i] is a link to its left child, and childs[i+1] is a
    // link to its right child. In a leaf node, all child links are null.
    // Moreover, for every element x in the left subtree of element y:
    //    x.compareTo(y) < 0
    // and for every element z in the right subtree of element y:
    //    z.compareTo(y) > 0.
    private int arity;
    private Node root;

    public Btree(int k) {
        // Construct an empty B-tree of arity k.
        root = null;
        arity = k;
    }

    public Node getNode(Comparable item) {
        // Find which if any node of this B-tree contains an element equal to item.
        // Return a link to that node, or null if there is no such node.
        if (root == null) {
            return null;
        }
        Node curr = root;
        for (;;) {
            int pos = curr.searchInNode(item);
            if (item.equals(curr.items[pos])) {
                return curr;
            } else if (curr.isLeaf()) {
                return null;
            } else {
                curr = curr.childs[pos];
            }
        }
    }

    public Object get(Comparable item) {
        if (root == null) {
            return null;
        }
        Node node = root;
        for (;;) {
            int pos = node.searchInNode(item);
            if (item.equals(node.items[pos])) {
                return node.items[pos];
            } else if (node.isLeaf()) {
                return null;
            } else {
                node = node.childs[pos];
            }
        }
    }

    public void insert(Comparable item) {
        // Insert element item into this B-tree.
        if (root == null) {
            root = new Node(arity, item, null, null);
            return;
        }
        Stack ancestors = new LinkedStack();
        Node curr = root;
        for (;;) {
            int currPos = curr.searchInNode(item);
            if (item.equals(curr.items[currPos])) {
                return;
            } else if (curr.isLeaf()) {
                curr.insertInNode(item, null, null, currPos);
                if (curr.size == arity) // curr has overflowed
                {
                    splitNode(curr, ancestors);
                }
                return;
            } else {
                ancestors.addLast(new Integer(currPos));
                ancestors.addLast(curr);
                curr = curr.childs[currPos];
            }
        }
    }

    private void splitNode(Node node,
            Stack ancestors) {
        // Split the overflowed node in this B-tree. The stack ancestors contains
        // all ancestors of node, together with the known insertion position in each of
        // these ancestors.
        int medPos = node.size / 2;
        Comparable med = node.items[medPos];
        Node leftSib = new Node(arity,
                node.items, node.childs, 0, medPos);
        Node rightSib = new Node(arity,
                node.items, node.childs, medPos + 1, node.size);
        if (node == root) {
            root = new Node(arity, med, leftSib,
                    rightSib);
        } else {
            Node parent =
                    (Node) ancestors.removeLast();
            int parentIns = ((Integer) ancestors.removeLast()).intValue();
            parent.insertInNode(med, leftSib, rightSib,
                    parentIns);
            if (parent.size == arity) // parent has overflowed
            {
                splitNode(parent, ancestors);
            }
        }
    }

    public void delete(Comparable item) {
        // Delete element item from this B-tree.
        if (root == null) {
            return;
        }
        Stack ancestors = new LinkedStack();
        Node curr = root;
        int currPos;
        for (;;) {
            currPos = curr.searchInNode(item);
            if (item.equals(curr.items[currPos])) {
                break;
            } else if (curr.isLeaf()) {
                return;
            } else {
                ancestors.addLast(new Integer(currPos));
                ancestors.addLast(curr);
                curr = curr.childs[currPos];
            }
        }
        if (curr.isLeaf()) {
            curr.removeFromNode(currPos, currPos);
            if (underflowed(curr)) {
                restock(curr, ancestors);
            }
        } else {
            Node leftmostNode = findLeftmostNode(curr.childs[currPos + 1], ancestors);
            Comparable nextitem = leftmostNode.items[0];
            leftmostNode.removeFromNode(0, 0);
            curr.items[currPos] = nextitem;
            if (underflowed(leftmostNode)) {
                restock(leftmostNode, ancestors);
            }
        }
    }

    private void restock(Node node, Stack ancestors) {
        // Restock node, which has underflowed.
        // The stack ancestors contains all ancestors of node, together
        // with the child position in each of these ancestors.
        if (node == root) {  // node.size == 0
            root = node.childs[0];
            return;
        }
        Node parent = (Node) ancestors.getLast();
        int childPos = 0;
        while (parent.childs[childPos] != node) {
            childPos++;
        }
        int sibMinSize = (arity - 1) / 2;
        if (childPos > 0 && parent.childs[childPos - 1].size > sibMinSize) {
            Node sib = parent.childs[childPos - 1];
            Comparable parentitem = parent.items[childPos - 1];
            Comparable spareitem = sib.items[sib.size - 1];
            Node spareChild = sib.childs[sib.size];
            sib.removeFromNode(sib.size - 1, sib.size);
            node.insertInNode(parentitem, spareChild, node.childs[0], 0);
            parent.items[childPos - 1] = spareitem;
        } else if (childPos < parent.size && parent.childs[childPos + 1].size > sibMinSize) {
            Node sib = parent.childs[childPos + 1];
            Comparable parentitem = parent.items[childPos];
            Comparable spareitem = sib.items[0];
            Node spareChild = sib.childs[0];
            sib.removeFromNode(0, 0);
            node.insertInNode(parentitem, node.childs[node.size], spareChild, node.size);
            parent.items[childPos] = spareitem;
        } else if (childPos > 0) {
            Node sib = parent.childs[childPos - 1];
            Comparable parentitem = parent.items[childPos - 1];
            node.coalesceLeft(sib, parentitem);
            parent.removeFromNode(childPos - 1, childPos - 1);
            if (underflowed(parent)) {
                restock(parent, ancestors);
            }
        } else {  // childPos < parent.size
            Node sib = parent.childs[childPos + 1];
            Comparable parentitem = parent.items[childPos];
            node.coalesceRight(parentitem, sib);
            parent.removeFromNode(childPos, childPos + 1);
            if (underflowed(parent)) {
                restock(parent, ancestors);
            }
        }
    }

    private void removeFromNode(Node node, int itemPos, int childPos) {  // OBSOLETE
        // Remove from node the element whose index is itemPos, and the child
        // whose index is childPos.
        for (int i = itemPos; i < node.size; i++) {
            node.items[i] = node.items[i + 1];
        }
        if (!node.isLeaf()) {
            for (int i = childPos; i < node.size; i++) {
                node.childs[i] = node.childs[i + 1];
            }
        }
        node.size--;
    }

    private Node findLeftmostNode(Node top, Stack ancestors) {
        // Return the leftmost leaf node in the subtree whose topmost node is top.
        // Push the node's ancestors on to the stack ancestors.
        Node curr = top;
        while (!curr.isLeaf()) {
            ancestors.addLast(new Integer(0));
            ancestors.addLast(curr);
            curr = curr.childs[0];
        }
        return curr;
    }

    private boolean underflowed(Node node) {
        // Return true if and only if node has underflowed.
        int minSize = (node == root ? 1 : (arity - 1) / 2);
        return (node.size < minSize);
    }

    //////////// Driver ////////////
    public void print() {
        // Print a textual representation of this B-tree.
        printSubtree(root, "");
    }

    private static void printSubtree(Node top, String indent) {
        // Print a textual representation of the subtree of this B-tree whose
        // topmost node is top, indented by the string of spaces indent.
        if (top == null) {
            System.out.println(indent + "o");
        } else {
            System.out.println(indent + "o-->");
            boolean isLeaf = top.isLeaf();
            String childIndent = indent + "    ";
            for (int i = 0; i < top.size; i++) {
                if (!isLeaf) {
                    printSubtree(top.childs[i], childIndent);
                }
                System.out.println(childIndent + top.items[i]);
            }
            if (!isLeaf) {
                printSubtree(top.childs[top.size], childIndent);
            }
        }
    }

    public static void main(String[] args) {
        Btree bt = new Btree(4);
        boolean deleting = false;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("ins")) {
                deleting = false;
            } else if (arg.equals("del")) {
                deleting = true;
            } else if (arg.equals("pri")) {
                bt.print();
            } else {
                if (deleting) {
                    System.out.println("Deleting " + arg);
                    bt.delete(arg);
                } else {
                    System.out.println("Inserting " + arg);
                    bt.insert(arg);
                }
            }
        }
    }

    //////////// Inner class ////////////
    public static class Node {

        // Each Node object is a B-tree node.
        private int size;
        private Comparable[] items;
        private Node[] childs;

        private Node(int k, Comparable item, Node left, Node right) {
            // Construct a B-tree node of arity k, initially with one element, item,
            // and two children, left and right.
            items = new Comparable[k];
            childs = new Node[k + 1];
            // ... Each array has one extra component, to allow for possible
            // overflow.
            this.size = 1;
            this.items[0] = item;
            this.childs[0] = left;
            this.childs[1] = right;
        }

        private Node(int k, Comparable[] items, Node[] childs, int l, int r) {
            // Construct a B-tree node of arity k, with its elements taken from the
            // subarray items[l...r-1] and its children from the subarray
            // childs[l...r].
            this.items = new Comparable[k];
            this.childs = new Node[k + 1];
            this.size = 0;
            for (int j = l; j < r; j++) {
                this.items[this.size] = items[j];
                this.childs[this.size] = childs[j];
                this.size++;
            }
            this.childs[this.size] = childs[r];
        }

        private boolean isLeaf() {
            // Return true if and only if this node is a leaf.
            return (childs[0] == null);
        }

        private int searchInNode(Comparable item) {
            // Return the index of the leftmost element in this node that is not less
            // than item.
            int l = 0, r = size - 1;
            while (l <= r) {
                int m = (l + r) / 2;
                int comp = item.compareTo(items[m]);
                if (comp == 0) {
                    return m;
                } else if (comp < 0) {
                    r = m - 1;
                } else {
                    l = m + 1;
                }
            }
            return l;
        }

        private void insertInNode(Comparable item, Node leftChild, Node rightChild, int ins) {
            // Insert element item, with children leftChild and rightChild, at
            // position ins in this node.
            for (int i = size; i > ins; i--) {
                items[i] = items[i - 1];
                childs[i + 1] = childs[i];
            }
            size++;
            items[ins] = item;
            childs[ins] = leftChild;
            childs[ins + 1] = rightChild;
        }

        private void coalesceLeft(Node that, Comparable item) {
            // Insert all that node's elements and children, followed by item,
            // as the leftmost elements and children of this node.
            System.arraycopy(this.items, 0, this.items, that.size + 1, this.size);
            System.arraycopy(this.childs, 0, this.childs, that.size + 1, this.size + 1);
            System.arraycopy(that.items, 0, this.items, 0, that.size);
            this.items[that.size] = item;
            System.arraycopy(that.childs, 0, this.childs, 0, that.size + 1);
            this.size += that.size + 1;
        }

        private void coalesceRight(Comparable item, Node that) {
            // Insert all that node's elements and children, preceded by item,
            // as the rightmost elements and children of this node.
            this.items[this.size] = item;
            System.arraycopy(that.items, 0, this.items, this.size + 1, that.size);
            System.arraycopy(that.childs, 0, this.childs, this.size + 1, that.size + 1);
            this.size += that.size + 1;
        }

        private void removeFromNode(int itemPos, int childPos) {
            // Remove from this node the element at position itemPos, and the child
            // at position childPos.
            for (int i = itemPos; i < size; i++) {
                items[i] = items[i + 1];
            }
            if (!isLeaf()) {
                for (int i = childPos; i < size; i++) {
                    childs[i] = childs[i + 1];
                }
            }
            size--;
        }
    }
}
