import java.util.ArrayList;
import java.util.List;

public class BinaryTree<K extends Comparable<K>, V> {

    private class Node {
        K key;
        V value;
        Node left, right;

        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private class RemovalResult {
        Node updatedRoot;
        Node deletedNode;

        RemovalResult(Node updatedRoot, Node deletedNode) {
            this.updatedRoot = updatedRoot;
            this.deletedNode = deletedNode;
        }
    }

    private Node root;

    // Insertar un nodo
    public void insert(K key, V value) {
        root = insertRecursive(root, key, value);
    }

    //Actualización
    private Node insertRecursive(Node current, K key, V value) {
        if (current == null) return new Node(key, value);

        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            current.left = insertRecursive(current.left, key, value);
        } else if (cmp > 0) {
            current.right = insertRecursive(current.right, key, value);
        } else {
            current.value = value; 
        }
        return current;
    }

    // Buscar en un nodo
    public V search(K key) {
        Node node = searchRecursive(root, key);
        return (node != null) ? node.value : null;
    }

    private Node searchRecursive(Node current, K key) {
        if (current == null) return null;

        int cmp = key.compareTo(current.key);
        if (cmp == 0) return current;
        if (cmp < 0) return searchRecursive(current.left, key);
        return searchRecursive(current.right, key);
    }

    // Eliminar un nodo
    public V remove(K key) {
        RemovalResult result = removeRecursive(root, key);
        root = result.updatedRoot;
        return (result.deletedNode != null) ? result.deletedNode.value : null;
    }

    private RemovalResult removeRecursive(Node current, K key) {
        if (current == null) return new RemovalResult(null, null);

        int cmp = key.compareTo(current.key);
        if (cmp < 0) {
            RemovalResult result = removeRecursive(current.left, key);
            current.left = result.updatedRoot;
            return new RemovalResult(current, result.deletedNode);
        } else if (cmp > 0) {
            RemovalResult result = removeRecursive(current.right, key);
            current.right = result.updatedRoot;
            return new RemovalResult(current, result.deletedNode);
        } else {
            if (current.left == null) return new RemovalResult(current.right, current);
            if (current.right == null) return new RemovalResult(current.left, current);

            Node minNode = findMin(current.right);
            current.key = minNode.key;
            current.value = minNode.value;
            RemovalResult result = removeRecursive(current.right, minNode.key);
            current.right = result.updatedRoot;
            return new RemovalResult(current, current);
        }
    }

    private Node findMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    // Recorrido del programa
    public List<V> inOrderValues() {
        List<V> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(Node node, List<V> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.value);
            inOrderTraversal(node.right, result);
        }
    }
}
