import java.util.*;

public class PriorityQueue <T extends Comparable> {
    private List<T> heap = null;
    
    private Map<T, TreeSet<Integer>> map = new HashMap<>();
    
    public PriorityQueue() {
        this(1);
    }
    
    public PriorityQueue(int size) {
        heap = new ArrayList<>(size);
    }

    // Construct a priority queue using heapify in O(n) time
    public PriorityQueue(T[] elems) {
        int size = elems.length;
        heap = new ArrayList<>(size);
        
        for(int i=0; i<size; i++) {
            heap.add(elems[i]);
            mapAdd(elems[i], i);
        }
        
        // Heapify
        for(int i=Math.max(0, size/2-1); i>=0; i--) swim(i);
    }

    // Priority queue construction, O(nlog(n))
    public PriorityQueue(Collection<T> elems) {
        this(elems.size());
        for (T elem : elems) add(elem);
    }
    
    public int getSize() {
        return heap.size();
    }
    
    public boolean isEmpty() {
        return getSize() == 0;
    }

    // Clears everything inside the heap, O(n)
    public void clear() {
        heap.clear();
        map.clear();
    }

    // Add a node value and its index to the map
    private void mapAdd(T value, int index) {
        TreeSet<Integer> set = map.get(value);
        
        if(set == null) {
            set = new TreeSet<>();
            set.add(index);
            map.put(value, set);
        }
        else {
            set.add(index);
        }
    }

    // Removes the index at a given value, O(log(n))
    private void mapRemove(T value, int lastElemIndex) {
        TreeSet<Integer> set = map.get(value);
        set.remove(lastElemIndex);
        
        if(set.isEmpty()) map.remove(value);
    }

    // Extract an index position for the given value
    // NOTE: If a value exists multiple times in the heap the lowest
    // index is returned (this has arbitrarily been chosen)
    private int mapGet(T obj) {
        TreeSet<Integer> set = map.get(obj);
        
        return set.first();
    }

    // Swap two nodes. Assumes i & j are valid, O(1)
    private void swap(int i,  int j) {
        T x = heap.get(i);
        T y = heap.get(j);
        
        heap.set(i, y);
        heap.set(j, x);
        
        mapSwap(x, y, i, j);
    }

    // Exchange the index of two nodes internally within the map
    private void mapSwap(T x, T y, int i, int j) {
        TreeSet<Integer> set1 = map.get(x);
        TreeSet<Integer> set2 = map.get(y);
        
        set1.remove(i);
        set2.remove(j);
        
        set1.add(j);
        set2.add(i);
    }

    // Tests if the value of node i <= node j
    // This method assumes i & j are valid indices, O(1)
    private boolean isLess(int i, int j) {
        T obj1 = heap.get(i);
        T obj2 = heap.get(j);

        return obj1.compareTo(obj2) <= 0;
    }

    // Top down node sink, O(log(n))
    private void sink(int index) {
        int size = getSize();
        
        while(true) {
            int left = 2 * index + 1;    // left child
            int right = 2 * index + 2;    // right child
            int smallest = left;   // assuming left is smallest

            if(left >= size) break;   // break if children out of bounds
            
            if(right < size && isLess(right, left)) smallest = right;   // store right child as smallest
            
            // break if element at index is smaller than smallest otherwise swap
            if(index < smallest) break;
            else {
                swap(index, smallest);
                index = smallest;
            }
        }
    }

    // Perform bottom up node swim, O(log(n))
    private void swim(int index) {
        while(true) {
            int parent = (index - 1) / 2;

            if(parent < 0 || isLess(parent, index)) break;
            else {
                swap(index, parent);
                index = parent;
            }
        }
    }

    // Test if an element is in heap, O(1)
    public boolean contains(T elem) {
        // Map lookup to check containment, O(1)
        if (elem == null) return false;
        return map.containsKey(elem);

        // Linear scan to check containment, O(n)
        // for(int i = 0; i < heapSize; i++)
        //   if (heap.get(i).equals(elem))
        //     return true;
        // return false;
    }

    // Returns the value of the element with the lowest
    // priority in this priority queue. If the priority
    // queue is empty null is returned.
    public T peek() {
        if (isEmpty()) return null;
        return heap.getFirst();
    }

    // Adds an element to the priority queue, the
    // element must not be null, O(log(n))
    public void add(T obj) {
        if(obj == null) throw new IllegalArgumentException("Can't add null value");
        
        heap.add(obj);
        int lastElemIndex = getSize() - 1;
        swim(lastElemIndex);
    }

    // Removes the root of the heap, O(log(n))
    public T poll() {
        return removeAt(0);
    }

    // Removes a node at particular index, O(log(n))
    public T removeAt(int index) {
        if(isEmpty()) throw new NoSuchElementException("Can't remove from an empty queue");

        int lastElemIndex = getSize() - 1;
        swap(index, lastElemIndex);
        T removed = heap.remove(lastElemIndex);
        mapRemove(removed, lastElemIndex);
        sink(index);

        return removed;
    }

    // Removes a particular element in the heap, O(log(n))
    public boolean remove(T obj) {
        return removeAt(mapGet(obj)) != null;
    }

    // Recursively checks if this heap is a min heap
    // This method is just for testing purposes to make
    // sure the heap invariant is still being maintained
    // Called this method with k=0 to start at the root
    public boolean isMinHeap(int k) {
        // If we are outside the bounds of the heap return true
        int heapSize = getSize();
        if (k >= heapSize) return true;

        int left = 2 * k + 1;
        int right = 2 * k + 2;

        // Make sure that the current node k is less than
        // both of its children left, and right if they exist
        // return false otherwise to indicate an invalid heap
        if (left < heapSize && !isLess(k, left)) return false;
        if (right < heapSize && !isLess(k, right)) return false;

        // Recurse on both children to make sure they're also valid heaps
        return isMinHeap(left) && isMinHeap(right);
    }
}
