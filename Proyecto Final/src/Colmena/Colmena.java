package Colmena;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static Colmena.Utilidades.printWithDelay;

public class Colmena implements Serializable {
    // Identificador único de la colmena.
    public String id;
    
    // Ubicación geográfica de la colmena (por ejemplo, granja o apiario).
    public String ubicacion;
    
    // Estado de salud de la colmena: "Buena", "Regular", "Mala".
    public String estadoSalud;
    
    // Cantidad de abejas dentro de la colmena.
    public int cantidadAbejas;
    
    // Cantidad de miel producida por la colmena (en kilogramos).
    public double produccionMiel;
    
    // Información sobre la abeja reina asociada a esta colmena.
    public AbejaReina abejaReina;
    
    // Lista de inspecciones realizadas a la colmena, registradas cronológicamente.
    public List<Inspeccion> inspecciones;

    // Constructor de la colmena. Se inicializan los valores básicos y la lista de inspecciones.
    public Colmena(String id, String ubicacion, String estadoSalud, int cantidadAbejas, double produccionMiel) {
        this.id = id;
        this.ubicacion = ubicacion;
        this.estadoSalud = estadoSalud;
        this.cantidadAbejas = cantidadAbejas;
        this.produccionMiel = produccionMiel;
        this.inspecciones = new ArrayList<>();  // Inicializamos la lista de inspecciones vacía.
    }

    // Método para asignar una abeja reina a la colmena.
    public void agregarAbejaReina(AbejaReina abejaReina) {
        this.abejaReina = abejaReina;
    }

    // Método para agregar una nueva inspección al historial de la colmena.
    public void agregarInspeccion(Inspeccion inspeccion) {
        inspecciones.add(inspeccion);  // Añadimos la inspección al final de la lista.
    }

    // Método para editar los datos de un apicultor existente.
    // Permite cambiar el nombre o el teléfono del apicultor.
    public static void editarApicultor(Apicultor apicultor, List<Apicultor> apicultores) {
        printWithDelay("""
    📝 ¿Qué deseas editar?
    1. Nombre
    2. Teléfono
    """, 50);
        String opcion = Utilidades.getValidInput("Selecciona una opción: ", "[1-2]");
        switch (opcion) {
            case "1" -> {
                // Se actualiza el nombre del apicultor.
                apicultor.nombre = Utilidades.getValidInput("🧑‍🌾 Nuevo nombre: ", ".+");
                printWithDelay("✅ Nombre actualizado.\n", 50);
            }
            case "2" -> {
                // Se actualiza el teléfono si es válido y no existe ya en otro apicultor.
                String nuevoTelefono = Utilidades.getValidInput("📞 Nuevo teléfono (10 dígitos): ", "\\d{10}");
                boolean telefonoExistente = apicultores.stream().anyMatch(a -> a.telefono != null && a.telefono.equals(nuevoTelefono) && !a.equals(apicultor));

                if (telefonoExistente) {
                    // Si el teléfono ya está registrado, mostramos un mensaje de error.
                    printWithDelay("\n⚠️ El teléfono " + nuevoTelefono + " ya está registrado por otro apicultor. Intenta con otro número.", 50);
                } else {
                    apicultor.telefono = nuevoTelefono;  // Actualizamos el teléfono.
                    printWithDelay("✅ Teléfono actualizado.\n", 50);
                }
            }
            default -> printWithDelay("⚠️ Opción no válida. No se realizaron cambios.\n", 50);
        }
    }

    // Método para editar los datos de la colmena. Permite cambiar la ubicación, estado de salud, cantidad de abejas o producción de miel.
    public static void editarColmena(Colmena colmena) {
        printWithDelay("""
        📝 ¿Qué deseas editar?
        1. Ubicación
        2. Estado de Salud
        3. Cantidad de Abejas
        4. Producción de Miel
        """, 50);
        String opcion = Utilidades.getValidInput("Selecciona una opción: ", "[1-4]");
        switch (opcion) {
            case "1" -> {
                // Se actualiza la ubicación de la colmena.
                colmena.ubicacion = Utilidades.getValidInput("📍 Nueva ubicación: ", ".+");
                printWithDelay("✅ Ubicación actualizada.\n", 50);
            }
            case "2" -> {
                // Se actualiza el estado de salud de la colmena.
                colmena.estadoSalud = Utilidades.getValidInput("❤️ Nuevo estado de salud (Buena/Regular/Mala): ", "Buena|Regular|Mala");
                printWithDelay("✅ Estado de salud actualizado.\n", 50);
            }
            case "3" -> {
                // Se actualiza la cantidad de abejas.
                colmena.cantidadAbejas = Integer.parseInt(Utilidades.getValidInput("🐝 Nueva cantidad de abejas: ", "[1-9]\\d*"));
                printWithDelay("✅ Cantidad de abejas actualizada.\n", 50);
            }
            case "4" -> {
                // Se actualiza la producción de miel en kilogramos.
                colmena.produccionMiel = Double.parseDouble(Utilidades.getValidInput("🍯 Nueva producción de miel (kg): ", "^(?!0(\\.0+)?$)(\\d+(\\.\\d+)?|\\.\\d+)$"));
                printWithDelay("✅ Producción de miel actualizada.\n", 50);
            }
            default -> printWithDelay("⚠️ Opción no válida. No se realizaron cambios.\n", 50);
        }
    }

    // Método para mostrar información detallada de una colmena, incluyendo datos de la colmena, la abeja reina y las inspecciones.
    public void mostrarInfo(int numeroColmena, Map<String, Apicultor> asignaciones) {
        StringBuilder colmenaInfo = new StringBuilder();
        // Agregamos la información básica de la colmena (ID, ubicación, estado de salud, cantidad de abejas y miel).
        colmenaInfo.append(String.format("""
        \r===============================
        # Colmena %d
        ===============================
        🐝 ID: %s
        📍 Ubicación: %s
        ❤️ Estado de Salud: %s
        🐝 Cantidad de Abejas: %d
        🍯 Producción de Miel: %.2f kg
        """, numeroColmena, id, ubicacion, estadoSalud, cantidadAbejas, produccionMiel)).append("\n");

        // Si existe una abeja reina, mostramos su información.
        if (abejaReina != null) {
            colmenaInfo.append("👑 Información de la Abeja Reina:").append("\n");
            colmenaInfo.append(abejaReina.getInfo()).append("\n");
        }

        // Si la colmena tiene un apicultor asignado, mostramos su información.
        if (asignaciones.containsKey(id)) {
            Apicultor apicultor = asignaciones.get(id);
            colmenaInfo.append(String.format("🧑‍🌾 Apicultor Asignado: %s (Tel: %s)\n", apicultor.nombre, apicultor.telefono)).append("\n");
        }

        // Si la colmena tiene inspecciones registradas, las mostramos. Si no, indicamos que no hay inspecciones.
        if (!inspecciones.isEmpty()) {
            colmenaInfo.append("📜 Historial de Inspecciones:").append("\n");
            colmenaInfo.append(Inspeccion.getInfo(inspecciones, 1)).append("\n");
        } else {
            colmenaInfo.append("⚠️ No hay inspecciones registradas para esta colmena.\n");
        }
        // Mostramos toda la información construida.
        System.out.println(colmenaInfo);
    }
}
