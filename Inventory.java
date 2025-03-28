import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;

public class Inventory {
    private BinaryTree<String, Product> skuTree = new BinaryTree<>();
    private BinaryTree<String, Product> nameTree = new BinaryTree<>();

    // Agrega un producto a ambos árboles
    public void addProduct(Product product) {
        skuTree.insert(product.getSku(), product);
        nameTree.insert(product.getName(), product);
    }

    // Buscar un producto por SKU
    public Product searchSKU(String sku) {
        return skuTree.search(sku);
    }

    // Buscar un producto por nombre
    public Product searchName(String name) {
        return nameTree.search(name);
    }

    // Elimina un producto por SKU
    public boolean deleteProductSKU(String sku) {
        Product removed = skuTree.remove(sku);
        if (removed != null) {
            nameTree.remove(removed.getName());
            return true;
        }
        return false;
    }

    // Carga los productos desde un archivo CSV
    public void loadCSV(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",", 4);
            if (parts.length == 4) {
                String sku = parts[0];
                String name = parts[1];
                String description = parts[2];
                Map<String, Integer> sizes = Product.parseSizes(parts[3]);
                Product product = new Product(sku, name, description, sizes);
                addProduct(product);
            }
        }
        reader.close();
    }

    // Guarda los productos en un archivo CSV, ordenados por SKU
    public void saveToCSV(String filename) throws IOException {
        FileWriter writer = new FileWriter(filename);
        for (Product p : skuTree.inOrderValues()) {
            writer.write(p.toCSVLine() + "\n");
        }
        writer.close();
    }

    // Lista todos los productos en orden por SKU
    public ArrayList<Product> listAllProducts() {
        return new ArrayList<>(skuTree.inOrderValues());
    }
}
