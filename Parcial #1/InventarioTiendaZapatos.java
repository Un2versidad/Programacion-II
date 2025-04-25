import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;

public class InventarioTiendaZapatos {
    private static final List<Producto> productos = new ArrayList<>();

    public static void main(String[] args) {
        mostrarSeccion("SISTEMA DE GESTIÓN DE INVENTARIO DE ZAPATOS");
        boolean salir = false;

        try (Scanner sc = new Scanner(System.in)) {
            while (!salir) {
                mostrarMenu();
                int opcion = getValidInput(sc, "Seleccione una opción: ", Integer::parseInt, input -> input >= 1 && input <= 4);

                switch (opcion) {
                    case 1 -> agregarProducto(sc);
                    case 2 -> venderProducto(sc);
                    case 3 -> mostrarInventario();
                    case 4 -> salir = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error inesperado: " + e.getMessage());
        }
        System.out.println("¡Hasta luego!");
    }

    private static void mostrarSeccion(String titulo) {
        System.out.printf("\n╔═%s═╗\n║ %s ║\n╚═%s═╝\n", "═".repeat(titulo.length()), titulo, "═".repeat(titulo.length()));
    }

    private static void mostrarMenu() {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│        MENÚ DE INVENTARIO          │");
        System.out.println("├────────────────────────────────────┤");
        System.out.println("│ 1. Agregar producto                │");
        System.out.println("│ 2. Vender producto                 │");
        System.out.println("│ 3. Mostrar inventario              │");
        System.out.println("│ 4. Salir                           │");
        System.out.println("└────────────────────────────────────┘");
    }

    private static void agregarProducto(Scanner sc) {
        mostrarSeccion("AGREGAR NUEVO PRODUCTO");
        String nombre = getValidInput(sc, "Ingrese el nombre del producto: ", Function.identity(), input -> !input.isEmpty());
        int cantidadInicial = getValidInput(sc, "Ingrese la cantidad inicial: ", Integer::parseInt, input -> input > 0);
        double precioVenta = getValidInput(sc, "Ingrese el precio de venta: ", Double::parseDouble, input -> input >= 0);

        Optional<Producto> productoExistente = buscarProducto(nombre);

        if (productoExistente.isPresent()) {
            productoExistente.get().aumentarInventario(cantidadInicial);
            productoExistente.get().setPrecioVenta(precioVenta); // Actualizar precio de venta
            System.out.println("\nCantidad y precio añadidos al producto existente.");
        } else {
            productos.add(new Producto(nombre, cantidadInicial, precioVenta));
            System.out.println("\nProducto agregado correctamente.");
        }
    }

    private static void venderProducto(Scanner sc) {
        mostrarSeccion("VENTA DE PRODUCTOS");
        String nombre = getValidInput(sc, "Ingrese el nombre del producto a vender: ", Function.identity(), input -> !input.isEmpty());
        Optional<Producto> producto = buscarProducto(nombre);

        if (producto.isPresent()) {
            int cantidad = getValidInput(sc, "Ingrese la cantidad a vender: ", Integer::parseInt, input -> input > 0);

            if (producto.get().vender(cantidad)) {
                producto.get().duplicarInventarioSiAgotado();
                System.out.println("\nVenta realizada con éxito.");
            } else {
                System.out.println("\nStock insuficiente para esta venta.");
            }
        } else {
            System.out.println("\nProducto no encontrado.");
        }
    }

    private static Optional<Producto> buscarProducto(String nombre) {
        return productos.stream()
                .filter(producto -> producto.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    private static void mostrarInventario() {
        mostrarSeccion("INVENTARIO ACTUAL");
        if (productos.isEmpty()) {
            System.out.println("No hay productos en el inventario.");
        } else {
            for (int i = 0; i < productos.size(); i++) {
                productos.get(i).mostrarInformacion();
                if (i < productos.size() - 1) {
                    System.out.println("+ ------------------------------------------------ +");
                }
            }
        }
    }

    private static <T> T getValidInput(Scanner sc, String msg, Function<String, T> parser, Function<T, Boolean> validator) {
        T result;
        do {
            System.out.print(msg);
            String input = sc.nextLine().trim();
            result = null;
            try {
                result = parser.apply(input);
            } catch (Exception e) {
                System.out.println("Entrada no válida. Intente de nuevo.");
            }
        } while (result == null || !validator.apply(result));
        return result;
    }
}