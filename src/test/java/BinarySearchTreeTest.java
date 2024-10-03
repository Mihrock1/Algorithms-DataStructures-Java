import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class BinarySearchTreeTest {

    private BinarySearchTree bst;

    @BeforeEach
    void setUp() {
        bst = new BinarySearchTree();
    }

    @Test
    void testIsEmptyInitially() {
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.getSize());
    }

    @Test
    void testInsertAndSize() {
        bst.insert(10.0);
        assertFalse(bst.isEmpty());
        assertEquals(1, bst.getSize());
    }

    @Test
    void testInsertDuplicates() {
        assertTrue(bst.insert(10.0));
        assertFalse(bst.insert(10.0)); // Should not allow duplicate
        assertEquals(1, bst.getSize());
    }

    @Test
    void testGetMinValThrowsExceptionOnEmptyTree() {
        assertThrows(RuntimeException.class, () -> bst.getMinVal());
    }

    @Test
    void testGetMaxValThrowsExceptionOnEmptyTree() {
        assertThrows(RuntimeException.class, () -> bst.getMaxVal());
    }

    @Test
    void testSearch() {
        bst.insert(10.0);
        bst.insert(5.0);
        bst.insert(15.0);
        assertTrue(bst.search(10.0));
        assertFalse(bst.search(20.0));
    }

    @Test
    void testDeleteLeafNode() {
        bst.insert(10.0);
        bst.insert(5.0);
        assertTrue(bst.delete(5.0));
        assertFalse(bst.search(5.0));
        assertEquals(1, bst.getSize());
    }

    @Test
    void testDeleteNodeWithOneChild() {
        bst.insert(10.0);
        bst.insert(5.0);
        bst.insert(3.0);
        assertTrue(bst.delete(5.0));
        assertFalse(bst.search(5.0));
        assertEquals(2, bst.getSize());
    }

    @Test
    void testDeleteNodeWithTwoChildren() {
        bst.insert(10.0);
        bst.insert(5.0);
        bst.insert(15.0);
        bst.insert(12.0);
        assertTrue(bst.delete(10.0)); // Root with two children
        assertFalse(bst.search(10.0));
        assertEquals(3, bst.getSize());
    }

    @Test
    void testGetMinMaxAfterOperations() {
        bst.insert(10.0);
        bst.insert(5.0);
        bst.insert(15.0);
        assertEquals(5.0, bst.getMinVal());
        assertEquals(15.0, bst.getMaxVal());
    }

    @Test
    void testIteratorInOrder() {
        bst.insert(10.0);
        bst.insert(5.0);
        bst.insert(15.0);
        bst.insert(7.0);

        Iterator<Double> it = bst.iterator();
        assertTrue(it.hasNext());
        assertEquals(5.0, it.next());
        assertEquals(7.0, it.next());
        assertEquals(10.0, it.next());
        assertEquals(15.0, it.next());
        assertFalse(it.hasNext());
    }
}