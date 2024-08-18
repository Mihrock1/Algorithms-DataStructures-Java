import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T>{
    private T[] arr;
    private int length = 0;  // length user things the dynamic array is
    private int capacity;   // actual length of dynamic array

    public DynamicArray(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be more than 0");
        }

        this.capacity = capacity;
        arr = (T[]) new Object[capacity];
    }

    public DynamicArray(int capacity, T[] values) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity should be more than 0");
        }

        this.capacity = capacity;
        length = values.length;
        arr = (T[]) new Object[capacity];
        
        for(int i=0; i<length; i++) {
            arr[i] = values[i];
        }
    }
    
    public int size() {
        return length;
    }

    public T get(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Negative Index entered");
        }
        else if (i >= length) {
            throw new NoSuchElementException("No Element exists at this index");
        }

        return arr[i];
    }

    public void set(int i, T n) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Negative Index entered");
        }
        else if (i >= length) {
            throw new NoSuchElementException("No Element exists at this index");
        }

        arr[i] = n;
    }
    
    public void clear() {
        for (int i=0; i<length; i++) {
            arr[i] = null;
        }
        length = 0;
    }

    public void push(T n) {
        if (length == capacity) {
            capacity *= 2;
            T[] newArr = (T[]) new Object[capacity];

            for (int i = 0; i < length; i++) {
                newArr[i] = arr[i];
            }

            arr = newArr;
        }

        arr[length] = n;
        length++;
    }

    public T pop() {
        if (length == 0) {
            throw new NoSuchElementException("Cannot pop from an empty dynamic array");
        }

        length--;
        T poppedElement = arr[length];
        arr[length] = null;
        return poppedElement;
    }
    
    public T removeAt(int index) {
        if (index >= length) {
            throw new NoSuchElementException("No element exists at this index");
        }
        
        T removedElement = arr[index];
        T[] newArr = (T[]) new Object[length-1];
        
        for (int i=0, j=0; i < length; i++, j++) {
            if (i == index) j--;
            else {
                newArr[j] = arr[i];
            }
        }
        
        arr = newArr;
        length--;
        return removedElement;    
    }
    
    public boolean remove(T obj) {
        for (int i=0; i<length; i++) {
            if (arr[i].equals(obj)) {
                removeAt(i);
                return true;
            }
        }
        
        return false;
    }
    
    public int indexOf(T obj) {
        for (int i=0; i<length; i++) {
            if(arr[i].equals(obj)) {
                return i;
            }
        }
        
        return -1;
    }
    
    public boolean contains(T obj) {
        if(indexOf(obj) == -1) return false;
        
        else return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new DynamicArrayIterator();
    }
    
    private class DynamicArrayIterator implements Iterator<T> {
        private int currentIndex = 0;
        
        @Override
        public boolean hasNext() {
            return currentIndex + 1 < length;
        }

        @Override
        public T next() {
            if(!hasNext()) {
                throw new NoSuchElementException("No element exists on next index");
            }   
            
            return arr[++currentIndex];
        }
    }
}
