import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

public class Product {
    private String sku;
    private String name;
    private String description;
    private Map<String, Integer> sizes;

    //Attributos de un producto.
    public Product(String sku, String name, String description, Map<String, Integer> sizes){
        this.sku = sku;
        this.name = name;
        this.description = description;
        this.sizes = sizes;
    }


    //Métodos de un producto.
    public String getSku() { return sku; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public Map<String, Integer> getSizes() { return sizes; }

    public void setDescription(String description){
        this.description = description;
    }

    public void setSizes(Map<String, Integer> sizes){
        this.sizes = sizes;
    }


    //Reescribe directamente en el main.
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SKU: ").append(sku).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Description: ").append(description).append("\n");
        sb.append("Sizes available:\n");
        for (Map.Entry<String, Integer> entry : sizes.entrySet()) {
            sb.append("  - ").append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }


    //Reescribe en el CSV al momento de agregar productos.
    public String toCSVLine() {
        StringJoiner joiner = new StringJoiner("|");
        for (Map.Entry<String, Integer> entry : sizes.entrySet()) {
            joiner.add(entry.getKey() + ":" + entry.getValue());
        }
        return sku + "," + name + "," + description + "," + joiner.toString();
    }



        //Clase que se encarga de pasar el número específico de tallas a una cadena String
        public static Map<String, Integer> parseSizes(String tallasStr){
        Map<String, Integer> sizes = new HashMap<>();
        String[] tallas = tallasStr.split("\\|");
        for (String talla : tallas) {
            String[] partes = talla.split(":");
            if (partes.length == 2) {
                sizes.put(partes[0], Integer.parseInt(partes[1]));
            }
        }
        return sizes;
    }

    
    //Clase para eliminar una talla.
    public void removeSize(String sizeKey) {
        if (sizes.containsKey(sizeKey)) {
            sizes.remove(sizeKey);
        } else {
            System.out.println("Talla '" + sizeKey + "' no existe.");
        }
    }
    

}

