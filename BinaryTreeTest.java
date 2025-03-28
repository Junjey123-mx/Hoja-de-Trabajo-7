import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

public class BinaryTreeTest {

    @Test
    public void testInsertAndSearchOne() {
        BinaryTree<String, String> tree = new BinaryTree<>();
        tree.insert("SKU1", "Shirt");

        String result = tree.search("SKU1");
        assertNotNull(result);
        assertEquals("Shirt", result);
    }

    @Test
    public void testInsertMultipleAndSearch() {
        BinaryTree<String, String> tree = new BinaryTree<>();
        tree.insert("A", "Apple");
        tree.insert("B", "Banana");
        tree.insert("C", "Cherry");

        assertEquals("Apple", tree.search("A"));
        assertEquals("Banana", tree.search("B"));
        assertEquals("Cherry", tree.search("C"));
    }

    @Test
    public void testSearchNonExistentKey() {
        BinaryTree<String, String> tree = new BinaryTree<>();
        tree.insert("X", "X-ray");

        String result = tree.search("Y");
        assertNull(result);
    }

    @Test
    public void testOverwriteValue() {
        BinaryTree<String, String> tree = new BinaryTree<>();
        tree.insert("Z", "OldValue");
        tree.insert("Z", "NewValue");

        assertEquals("NewValue", tree.search("Z"));
    }
}
