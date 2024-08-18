import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {
    @Test
    void getTest() {
        var dynamicArray = new DynamicArray<Integer>(5);
        dynamicArray.push(1);
        dynamicArray.push(2);
        dynamicArray.push(3);

        assertThrows(IndexOutOfBoundsException.class,
                () -> {
                    dynamicArray.get(-1);
                }, "Given index is negative");

        assertThrows(NoSuchElementException.class,
                () -> {
                    dynamicArray.get(4);
                }, "Given index is greater than length of dynamic array");

        assertThrows(NoSuchElementException.class,
                () -> {
                    dynamicArray.get(3);
                }, "Given index is equal to length of dynamic array");
    }
    
    @Test
    void setTest() {
        var dynamicArray = new DynamicArray<Integer>(5);
        dynamicArray.push(1);
        dynamicArray.push(2);
        dynamicArray.push(3);

        assertThrows(IndexOutOfBoundsException.class,
                () -> {
                    dynamicArray.set(-1, 1);
                }, "Given index is negative");

        assertThrows(NoSuchElementException.class,
                () -> {
                    dynamicArray.set(4, 1);
                }, "Given index is greater than length of dynamic array");

        assertThrows(NoSuchElementException.class,
                () -> {
                    dynamicArray.set(3, 1);
                }, "Given index is equal to length of dynamic array");
    }
    
    @Test
    void clearTest() throws NoSuchFieldException, IllegalAccessException {
        var dynamicArray = new DynamicArray<Integer>(5, new Integer[] {1,2,3});
        dynamicArray.clear();
        
        assertEquals(0, dynamicArray.size(), "Length variable is not set to 0");

        Field arrField = DynamicArray.class.getDeclaredField("arr");
        arrField.setAccessible(true);
        
        Object[] arr = (Object[]) arrField.get(dynamicArray);
        
        for (Object element: arr) {
            assertNull(element, "Element in private field arr in dynamic array is not null");
        }
    }

    @Test
    void pushTest() {
        var dynamicArray = new DynamicArray<Integer>(5, new Integer[] {1,2,3});
        dynamicArray.push(4);
        
        assertEquals(3, dynamicArray.indexOf(4), "Data not pushed at the end of dynamic array");
    }
    
    @Test
    void popTest() {
        var dynamicArray = new DynamicArray<Integer>(5);
        
        assertThrows(NoSuchElementException.class,
                () -> {
                    dynamicArray.pop();
                }, "Trying to pop from empty dynamic array");

        dynamicArray.push(1);
        dynamicArray.push(2);
        dynamicArray.push(3);
        
        assertEquals(3, dynamicArray.pop(), "Popped element was not the last element");
    }
    
    
}