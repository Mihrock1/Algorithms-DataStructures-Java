import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<T> implements Iterable<T> {
    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;
    private class Node<T> {
        T data = null;
        Node<T> next = null;
        Node<T> prev = null;
        public Node(T data) {
            this.data = data;
        }
        @Override
        public String toString() {
            return data.toString();
        }
    }
    
    public int getSize() { return size; }
    
    public boolean isEmpty() { return size==0; }

    private Node<T> getNodeByIndex(int index) {
        if(index > size || index < 1) {
            throw new IndexOutOfBoundsException("");
        }
        Node<T> trav = null;
        if(index > size/2) {
            trav = tail;
            int steps = size-index;
            while(steps > 0) {
                trav = trav.prev;
                steps--;
            }
        }
        else {
            trav = head;
            while(index > 0) {
                trav = trav.next;
                index--;
            }
        }
        return trav;
    }

    private Node<T> getNodeByData(T obj) {
        if(obj == head.data) {
            return head;
        }
        if(head != tail && obj == tail.data) {
            return tail;
        }

        Node<T> trav = head.next;
        while(trav.next != null) {
            if(trav.data == obj) {
                return trav;
            }
        }
        return null;
    }
    
    public void addFirst(T obj) {
        Node<T> node = new Node<T>(obj);
        if(isEmpty()) {
            head = node;
            tail = node;
            size++;
        }
        else {
            node.next = head;
            head.prev = node;
            head = node;
            size++;
        }
    }
    
    public void addLast(T obj) {
        Node<T> node = new Node<T>(obj);
        if(isEmpty()) {
            head = node;
            tail = node;
            size++;
        }
        else {
            tail.next = node;
            node.prev = tail;
            tail = node;
            size++;
        }
    }

    public void add(int index, T obj) {
        if(isEmpty() || index == 1) {
            addFirst(obj);
        }
        else if(index == size) {
            addLast(obj);
        }
        else {
            Node<T> prevNode = getNodeByIndex(index);
            Node<T> node = new Node<T>(obj);
            prevNode.prev.next = node;
            node.prev = prevNode.prev;
            prevNode.prev = node;
            node.next = prevNode;
            size++;
        }
    }
    
    public T removeFirst() {
        T removed = null;
        if(isEmpty()) {
            throw new NoSuchElementException("Can't remove from empty Linked List");
        }
        else if(size == 1) {
            removed = head.data;
            head = null;
            tail = null;
            size--;
        }
        else {
            removed = head.data;
            head = head.next;
            head.prev = null;
            size--;
        }
        return removed;
    }
    
    public T removeLast() {
        T removed = null;
        if(isEmpty()) {
            throw new NoSuchElementException("Can't remove from empty Linked List");
        }
        else if(size == 1) {
            removed = head.data;
            head = null;
            tail = null;
            size--;
        }
        else {
            removed = tail.data;
            tail = tail.prev;
            tail.next = null;
            size--;
        }
        return removed;
    }
    
//    public boolean remove(T obj) {
//        
//    }
    
    @Override
    public Iterator<T> iterator() {
        return null;
    }
}
