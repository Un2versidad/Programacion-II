public class Producto {
    private final String nombre;
    private int cantidadInicial;
    private int cantidadVendida;
    private double precioVenta;

    public Producto(String nombre, int cantidadInicial, double precioVenta) {
        this.nombre = capitalizeFirstLetter(nombre);
        this.cantidadInicial = cantidadInicial;
        this.cantidadVendida = 0;
        this.precioVenta = precioVenta;
    }

    private String capitalizeFirstLetter(String input) {
        return (input == null || input.isBlank()) ? input : input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }

    public int getInventarioDisponible() {
        return cantidadInicial - cantidadVendida;
    }

    public boolean vender(int cantidad) {
        if (cantidad > getInventarioDisponible()) {
            return false;
        }
        cantidadVendida += cantidad;
        return true;
    }

    public void duplicarInventarioSiAgotado() {
        if (getInventarioDisponible() == 0) {
            cantidadInicial *= 2;
            System.out.println("El inventario ha sido duplicado para el producto: " + nombre);
        }
    }

    public void aumentarInventario(int cantidad) {
        cantidadInicial += cantidad;
    }

    public void setPrecioVenta(double precio) {
        this.precioVenta = precio;
    }

    public void mostrarInformacion() {
        System.out.printf("""
            ┌──────────────────────────────────────────────────┐
            │ Producto: %-36s   │
            │ Precio de venta: $%-30.2f │
            │ Cantidad vendida: %-30d │
            │ Inventario disponible: %-24d  │
            └──────────────────────────────────────────────────┘
            """, nombre, precioVenta, cantidadVendida, getInventarioDisponible());
    }

    public String getNombre() {
        return nombre;
    }
}