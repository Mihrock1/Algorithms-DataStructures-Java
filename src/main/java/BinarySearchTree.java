import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

@SuppressWarnings("unchecked")
public class BinarySearchTree implements Iterable<Double>{
    private Node root;
    private int size;

    class Node {
        Double data;
        Node parent = null;
        Node left = null;
        Node right = null;
        
        public Node(Double data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }
     
    public BinarySearchTree() {
    }

    public BinarySearchTree(Double data) {
        insert(data);
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public Double getMinVal() {
        if(isEmpty()) {
            throw new RuntimeException("Tree is empty");
        }
        Node minValNode = getMinValRec(root);
        return minValNode.data;
    }
    
    private Node getMinValRec(Node node) {
        if(node.left != null) {
            return getMinValRec(node.left);
        }
        else {
            return node;
        }
    }

    public Double getMaxVal() {
        if(isEmpty()) {
            throw new RuntimeException("Tree is empty");
        }
        Node maxValNode = getMaxValRec(root);
        return maxValNode.data;
    }

    private Node getMaxValRec(Node node) {
        if(node.right != null) {
            return getMinValRec(node.right);
        }
        else {
            return node;
        }
    }
    
    public void traverseInOrder() {
        if(this.isEmpty()) {
            throw new NoSuchElementException("The tree is empty");
        }
        
        traverseInOrderRec(root);
    }
    
    private void traverseInOrderRec(Node node) {
        if (node != null) {
            traverseInOrderRec(node.left);
            System.out.print(node.data + " ");
            traverseInOrderRec(node.right);
        }
    }
    
    public boolean search(Double data) {
        if(data == null) {
            throw new IllegalArgumentException("Can't search for NULL value");
        }

        return searchRec(data, root) != null;
    }
    
    private Node searchRec(Double data, Node node) {
        if(data < node.data) {
            if(node.left == null) {
                return null;
            }
            else {
                return searchRec(data, node.left);
            }
        }
        else if(data.equals(node.data)) {
            return node;
        }
        else {
            if(node.right == null) {
                return null;
            }
            else {
                return searchRec(data, node.right);
            }
        }
    }
    
    public boolean insert(Double data) {
        if(data == null) {
            throw new IllegalArgumentException("Can't insert NULL value");
        }
        
        if(this.size == 0) {
            this.root = new Node(data);
        }
        else {
            Node node = insertRec(data, root);
            if(node == null) {
                return false;
            }
        }
        
        this.size++;
        return true;
    }
    
    private Node insertRec(Double data, Node node) {
        if(data < node.data) {
            if(node.left == null) {
                node.left = new Node(data);
                node.left.parent = node;
            }
            else {
                insertRec(data, node.left);
            }
            return node.left;
        }
        else if(data.equals(node.data)) {
            return null;
        }
        else {
            if(node.right == null) {
                node.right = new Node(data);
                node.right.parent = node;
            }
            else {
                insertRec(data, node.right);
            }
            return node.right;
        }
    }
    
    public boolean delete(Double data) {
        if(data == null) {
            throw new IllegalArgumentException("Can't accept NULL value as input");
        }

        Node node = searchRec(data, root);
        if(node == null) {
            return false;
        }
        Node parent = node.parent;

        if(node.left == null && node.right == null) {
            if(parent.left == node) {
                parent.left = null;
            }
            else {
                parent.right = null;
            }
            node.data = null;
            
            this.size--;
            if(this.size == 0) {
                this.root = null;
            }
            return true;
        }
        else if(node.left != null && node.right == null) {
            if(parent.left == node) {
                parent.left = node.left;
            }
            else {
                parent.right = node.left;
            }
            node.data = null;
            
            this.size--;
            if(this.size == 0) {
                this.root = null;
            }
            return true;
        }
        else if(node.left == null && node.right != null) {
            if(parent.left == node) {
                parent.left = node.right;
            }
            else {
                parent.right = node.right;
            }
            node.data = null;
            
            this.size--;
            if(this.size == 0) {
                this.root = null;
            }
            return true;
        }
        else {
            Node rightChildMinValNode = getMinValRec(node.right);
            Node rightChildMinValNodeParent = rightChildMinValNode.parent;
            node.data = rightChildMinValNode.data;
            
            if(rightChildMinValNodeParent.left == rightChildMinValNode) {
                rightChildMinValNodeParent.left = null;
            }
            else {
                rightChildMinValNodeParent.right = null;
            }
            rightChildMinValNode.data = null;
            
            this.size--;
            if(this.size == 0) {
                this.root = null;
            }
            return true;
        }
    }

    @Override
    public Iterator<Double> iterator() {
        return new BinarySearchTreeIterator(root);
    }
    
    private class BinarySearchTreeIterator implements Iterator<Double> {
        private Stack<Node> stack = new Stack<>();
        
        public BinarySearchTreeIterator(Node node) {
            pushAllLeftChildren(node);
        }
        
        private void pushAllLeftChildren(Node node) {
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public Double next() {
            Node curr = stack.pop();
            Double res = curr.data;
            
            if(curr.right != null) {
                pushAllLeftChildren(curr.right);
            }
            return res;
        }
    }
}
