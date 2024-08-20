import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private class Node<T> {
        T data;
        Node<T> next;

        public Node(T data) {
            this.data = data;
        }

        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

    public SinglyLinkedList() {
    }

    public void clear() {
        Node<T> traverse = head;
        while (traverse != null) {
            Node<T> next = traverse.next;
            traverse.next = null;
            traverse.data = null;
            traverse = next;
        }
        size = 0;
        head = tail = null;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(T obj) {
        Node<T> node = new Node<T>(obj);
        if (this.isEmpty()) {
            head = tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
    }

    public void addLast(T obj) {
        Node<T> node = new Node<T>(obj);
        if (this.isEmpty()) {
            head = tail = node;
        }
        tail.next = node;
        tail = node;
        size++;
    }

    public T removeFirst() {
        if (this.isEmpty()) {
            throw new RuntimeException("Can't remove from an empty linked list");
        }

        head.data = null;
        head = head.next;
        T removed = head.data;
        size--;
        return removed;
    }

    public T removeLast() {
        if (this.isEmpty()) {
            throw new RuntimeException("Can't remove from an empty linked list");
        }

        Node<T> traverse = head;
        T removed = null;
        while (traverse != null) {
            if (traverse.next == tail) {
                traverse.next = null;
                tail.data = null;
                tail = traverse;
                removed = tail.data;
                break;
            }
            traverse = traverse.next;
        }
        size--;
        return removed;
    }

    private boolean removeNode(Node<T> node) {
        if (this.isEmpty()) {
            throw new RuntimeException("Can't remove from an empty linked list");
        } else if (head == node) removeFirst();

        else if (tail == node) removeLast();

        else {
            Node<T> traverse = head;
            while (traverse != null) {
                if (traverse.next == node) {
                    traverse.next = node.next;
                    break;
                }
                traverse = traverse.next;
            }
        }
        size--;
        return true;
    }

    public boolean remove(T obj) {
        // Support searching for null
        for (Node<T> trav = head; trav != null; trav = trav.next) {
            if (trav.data == null || obj.equals(trav.data)) {
                removeNode(trav);
                return true;
            }
        }

        throw new NoSuchElementException("Element does not exist in linked list");
    }

    public boolean removeAt(int index) {
        if (index > size || index < 1) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        Node<T> trav = head;
        while (index > 0) {
            trav = trav.next;
            index--;
        }
        removeNode(trav);
        return true;
    }

    public int indexOf(T obj) {
        int index = 1;
        Node<T> trav = head;
        while (index <= size) {
            if (obj.equals(trav.data)) {
                break;
            }
            trav = trav.next;
            index++;
        }
        
        if(index > size) {
            return -1;
        }
        
        return index;
    }
    
    public boolean contains(T obj) {
        return indexOf(obj) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> currNode = head;

            @Override
            public boolean hasNext() {
                return currNode.next != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No element exists on next index");
                }
                currNode = currNode.next;
                return currNode.data;
            }
        };
    }
}
