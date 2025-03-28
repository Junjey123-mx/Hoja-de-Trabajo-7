import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Store {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();
        Scanner scanner = new Scanner(System.in);

        // Pedir el nombre del archivo CSV
        System.out.print("------------------------------------------------- Bienvenido -------------------------------------------- \n");
        System.out.print("");
        System.out.print("Ingresa el nombre del archivo CSV para cargar el inventanrio: \n");
        String fileName = scanner.nextLine();
        File file = new File(fileName);
        System.out.println("El inventario ha sido cargado exitosamente desde " + fileName + ". \n");
        if (!file.exists()) {
            System.out.println("El archivo " + fileName + " no existe o puede que lo hayas escrito mal.");
            System.out.println("¡Intentalo de nuevo!");
            scanner.close();
            return;
        }

        try {
            inventory.loadCSV(fileName);
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error al cargar tu archivo.");
            System.out.println("Comprueba su funcionamiento.");
            scanner.close();
            return;
        }

        boolean continuar = true;
        while (continuar) {
            System.out.println("---------------------------------------------- Inventario ------------------------------------------ \n");
            System.out.println("1. Buscar un producto por SKU");
            System.out.println("2. Buscar un producto por nombre");
            System.out.println("3. Eliminar un producto por SKU");
            System.out.println("4. Listar todos los productos del inventario");
            System.out.println("5. Guardar los cambios en un nuevo archivo CSV");
            System.out.println("6. Editar un producto");
            System.out.println("7. Agregar un nuevo producto");
            System.out.println("8. Salir");
            System.out.print("Opción: ");
            String opcion = scanner.nextLine();

            switch (opcion) {
                case "1":
                    System.out.print("\n Ingresa el SKU que deseas buscar: ");
                    String sku = scanner.nextLine();
                    Product p1 = inventory.searchSKU(sku);
                    if (p1 != null) {
                        System.out.println("\nTu producto ha sido encontrado: " + p1);
                    } else {
                        System.out.println("Tu producto no ha sido encontrado.");
                    }
                    break;

                case "2":
                    System.out.print("Ingresa el nombre del producto: ");
                    String name = scanner.nextLine();
                    Product p2 = inventory.searchName(name);
                    if (p2 != null) {
                        System.out.println("Tu producto  ha sido encontrado:\n" + p2);
                    } else {
                        System.out.println("Tu producto no ha sido encontrado.");
                    }
                    break;

                case "3":
                    System.out.print("Ingresa el SKU del producto que deseas eliminar: ");
                    String eliminarSKU = scanner.nextLine();
                    if (inventory.deleteProductSKU(eliminarSKU)) {
                        System.out.println("El producto ha sido eliminado exitosamente.");
                    } else {
                        System.out.println("Error, el producto no ha sido encontrado.");
                    }
                    break;

                case "4":
                    System.out.println("Lista de todo los productos:");
                    for (Product p : inventory.listAllProducts()) {
                        System.out.println(p);
                    }
                    break;

                case "5":
                    System.out.print("Ingresa el nombre del nuevo archivo CSV: ");
                    String outputName = scanner.nextLine();
                    try {
                        inventory.saveToCSV(outputName);
                        System.out.println("El inventario ha sido guardado en '" + outputName + "'.");
                    } catch (IOException e) {
                        System.out.println("Error, no se logro guardar el archivo con sus modificaciones.");
                    }
                    break;

                case "6":
                    System.out.print("Ingresa el SKU del producto que deseas editar: ");
                    String editSKU = scanner.nextLine();
                    Product productToEdit = inventory.searchSKU(editSKU);

                    if (productToEdit != null) {
                        System.out.println("Tu producto ha sido encontrado:");
                        System.out.println(productToEdit);

                        System.out.println("\n Modificaciones: ");
                        System.out.println("1. Descripción");
                        System.out.println("2. Cambiar la cantidad de una talla existente");
                        System.out.println("3. Agregar una nueva talla");
                        System.out.println("4. Eliminar una talla");
                        System.out.print("Opción: ");
                        String editOption = scanner.nextLine();

                        switch (editOption) {
                            case "1":
                                System.out.print("Nueva descripción: ");
                                String newDesc = scanner.nextLine();
                                productToEdit.updateDescription(newDesc);
                                System.out.println("La descripción ha sido ya actualizada.");
                                break;

                            case "2":
                                System.out.print("Talla a editar: ");
                                String tallaEdit = scanner.nextLine();
                                System.out.print("Nueva cantidad: ");
                                int nuevaCantidad = Integer.parseInt(scanner.nextLine());
                                productToEdit.updateSizeQuantity(tallaEdit, nuevaCantidad);
                                System.out.println("La cantidad ha sido ya actualizada.");
                                break;

                            case "3":
                                System.out.print("Nueva talla a agregar: ");
                                String nuevaTalla = scanner.nextLine();
                                System.out.print("Cantidad inicial: ");
                                int cantidadInicial = Integer.parseInt(scanner.nextLine());
                                productToEdit.updateSizeQuantity(nuevaTalla, cantidadInicial);
                                System.out.println("La talla ha sido agregada.");
                                break;

                            case "4":
                                System.out.print("Talla que deseas eliminar: ");
                                String tallaEliminar = scanner.nextLine();
                                if (productToEdit.removeSize(tallaEliminar)) {
                                    System.out.println("La talla ha sido eliminada.");
                                } else {
                                    System.out.println("La talla no fue encontrada.");
                                }
                                break;

                            default:
                                System.out.println("La opción es inválida.");
                                System.out.println("Intenta con una que te ofrecemos.");
                        }
                    } else {
                        System.out.println("El producto no ha sido encontrado.");
                        System.out.println("Prueba de nuevo.");
                    }
                    break;

                case "7":
                    System.out.print("Nuevo SKU: ");
                    String newSKU = scanner.nextLine();

                    if (inventory.searchSKU(newSKU) != null) {
                        System.out.println("Ya existe un producto con ese mismo SKU.");
                        break;
                    }

                    System.out.print("Nombre del producto: ");
                    String newName = scanner.nextLine();

                    if (inventory.searchName(newName) != null) {
                        System.out.println("Ya existe un producto con ese mismo nombre.");
                        break;
                    }

                    System.out.print("Descripción: ");
                    String newDescription = scanner.nextLine();

                    String[] tallasPredeterminadas = { "s", "m", "l", "xl" };
                    Map<String, Integer> newSizes = new HashMap<>();

                    for (String talla : tallasPredeterminadas) {
                        System.out.print("Cantidad para talla " + talla + ": ");
                        int cantidad = Integer.parseInt(scanner.nextLine());
                        newSizes.put(talla, cantidad);
                    }

                    Product nuevoProducto = new Product(newSKU, newName, newDescription, newSizes);
                    inventory.addProduct(nuevoProducto);

                    System.out.println("El producto ha sido agregado con éxito.");
                    break;

                case "8":
                    continuar = false;
                    System.out.println("---------------------------------------------------------------------------------");
                    System.out.println("¡Has salido del sistema!");
                    System.out.println("¡Hasta pronto!");
                    break;

                default:
                    System.out.println("To opción es iválida.");
                    System.out.println("Prueba con una que te ofrecemos.");
            }
        }

        scanner.close();
    }
}
