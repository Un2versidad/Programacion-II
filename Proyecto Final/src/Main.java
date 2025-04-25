import java.io.*;
import java.util.*;
import Colmena.*;
import static Colmena.Colmena.*;
import static Colmena.Inspeccion.*;
import static Colmena.Utilidades.*;

public class Main {
    // Método principal que ejecuta el sistema de gestión apícola.
    public static void main(String[] args) {
        // Inicialización de listas para almacenar colmenas y apicultores.
        List<Colmena> colmenas = new ArrayList<>();
        List<Apicultor> apicultores = new ArrayList<>();
        
        // Archivo donde se guardarán y cargarán los datos.
        String dataFile = "apicola_data.dat";
        
        // Mapa que asigna un apicultor a una colmena por su ID.
        Map<String, Apicultor> asignaciones = new HashMap<>();
        
        // Intento de cargar los datos previos desde el archivo.
        try {
            Utilidades.cargarDatos(colmenas, apicultores, asignaciones, dataFile);
            // Mensaje si los datos se cargaron correctamente.
            printWithDelay("📂 Datos cargados exitosamente.", 50);
        } catch (Exception e) {
            // Si no se pueden cargar los datos, se muestra un mensaje de advertencia.
            printWithDelay("⚠️ No se encontraron datos previos o están corruptos. Iniciando desde cero.", 50);
        }
        
        // Menú principal del sistema, que se repite mientras el usuario no elija salir.
        while (true) {
            // Animación de abejas para dar una experiencia visual divertida.
            Utilidades.animacionAbejas();
            // Menú de opciones del sistema con un formato atractivo.
            printWithDelay("""
                ================================
                🍯 SISTEMA DE GESTIÓN APÍCOLA 🍯
                ================================
                🐝 1. Registrar Colmena
                🧑‍🌾 2. Registrar Apicultor
                👑 3. Asignar Abeja Reina
                🔍 4. Realizar Inspección
                📜 5. Mostrar Información
                🔗 6. Asignar Apicultor a Colmena
                ✏️ 7. Editar Información
                💾 8. Guardar y Salir
                """, 50);
            // Solicita al usuario que seleccione una opción del menú.
            String opcion = Utilidades.getValidInput("Selecciona una opción: ", "[1-8]");

            // Estructura switch para manejar las opciones seleccionadas por el usuario.
            switch (opcion) {
                case "1" -> {  // Registrar Colmena
                    // Solicita al usuario los datos necesarios para registrar una nueva colmena.
                    String id = Utilidades.getValidInput("🐝 ID (formato CXXX): ", "C\\d{3}");
                    // Verifica si ya existe una colmena con el mismo ID.
                    if (colmenas.stream().anyMatch(c -> c.id.equals(id))) {
                        printWithDelay("⚠️ El ID de colmena " + id + " ya está registrado. Usa otro ID.\n", 50);
                        break;
                    }
                    // Se solicita más información sobre la colmena.
                    String ubicacion = Utilidades.getValidInput("📍 Ubicación: ", ".+");
                    String estadoSalud = Utilidades.getValidInput("❤️ Estado de Salud (Buena/Regular/Mala): ", "Buena|Regular|Mala");
                    int cantidadAbejas = Integer.parseInt(Utilidades.getValidInput("🐝 Cantidad de abejas: ", "[1-9]\\d*"));
                    double produccionMiel = Double.parseDouble(Utilidades.getValidInput("🍯 Producción de miel (kg): ", "^(?!0(\\.0+)?$)(\\d+(\\.\\d+)?|\\.\\d+)$"));

                    // Añade la nueva colmena a la lista de colmenas.
                    colmenas.add(new Colmena(id, ubicacion, estadoSalud, cantidadAbejas, produccionMiel));
                    // Muestra mensaje de éxito.
                    printWithDelay("\n✅ Colmena registrada.", 50);
                }
                case "2" -> {  // Registrar Apicultor
                    // Solicita los datos necesarios para registrar un nuevo apicultor.
                    String nombre = Utilidades.getValidInput("🧑‍🌾 Nombre del Apicultor: ", ".+");
                    String telefono = Utilidades.getValidInput("📞 Teléfono (formato 10 dígitos): ", "\\d{10}");

                    // Verifica si el teléfono ya está registrado.
                    boolean telefonoExistente = apicultores.stream().anyMatch(apicultor -> apicultor.getTelefono().equals(telefono));
                    if (telefonoExistente) {
                        printWithDelay("\n⚠️ El número de teléfono ya está registrado.", 50);
                    } else {
                        // Si el teléfono no está registrado, añade el apicultor.
                        apicultores.add(new Apicultor(nombre, telefono));
                        printWithDelay("\n✅ Apicultor registrado.", 50);
                    }
                }
                case "3" -> {  // Asignar Abeja Reina
                    if (colmenas.isEmpty()) {
                        printWithDelay("⚠️ No hay colmenas registradas. Registra una colmena primero.\n", 50);
                        break;
                    }
                    // Solicita el ID de la colmena a la que se le asignará una abeja reina.
                    String id = Utilidades.getValidInput("🐝 ID de la Colmena para asignar Abeja Reina: ", "C\\d{3}");
                    Optional<Colmena> colmena = colmenas.stream().filter(c -> c.id.equals(id)).findFirst();
                    if (colmena.isPresent()) {
                        // Si la colmena existe, se solicita la información de la abeja reina.
                        int edad = Integer.parseInt(Utilidades.getValidInput("🕰️ Edad de la Abeja Reina (en años, entre 1 y 3): ", "[1-3]"));
                        String estadoSalud = Utilidades.getValidInput("❤️ Estado de Salud (Buena/Regular/Mala): ", "Buena|Regular|Mala");
                        double productividad = Double.parseDouble(Utilidades.getValidInput("🍯 Productividad (kg de miel): ", "^(?!0(\\.0+)?$)(\\d+(\\.\\d+)?|\\.\\d+)$"));

                        // Añade la abeja reina a la colmena seleccionada.
                        colmena.get().agregarAbejaReina(new AbejaReina(edad, estadoSalud, productividad));
                        printWithDelay("\n✅ Abeja Reina asignada.", 50);
                    } else {
                        printWithDelay("⚠️ Colmena no encontrada.\n", 50);
                    }
                }
                case "4" -> {  // Realizar Inspección
                    if (colmenas.isEmpty()) {
                        printWithDelay("⚠️ No hay colmenas registradas. Registra una colmena primero.\n", 50);
                        break;
                    }
                    // Llama a la función para realizar inspecciones en todas las colmenas.
                    realizarInspecciones(colmenas);
                    printWithDelay("✅ Todas las inspecciones automáticas han finalizado.\n", 50);
                }
                case "5" -> {  // Mostrar Información
                    if (colmenas.isEmpty() && apicultores.isEmpty()) {
                        printWithDelay("⚠️ No hay información registrada aún.\n", 50);
                        break;
                    }
                    printWithDelay("\r\uD83D\uDCDF Buscando información...", 50);
                    Utilidades.animacionBusqueda();

                    // Muestra la información de todas las colmenas registradas.
                    int numeroColmena = 1;
                    for (Colmena colmena : colmenas) {
                        colmena.mostrarInfo(numeroColmena, asignaciones);
                        numeroColmena++;
                    }
                }
                case "6" -> {  // Asignar Apicultor a Colmena
                    String idColmena = Utilidades.getValidInput("🐝 ID de la Colmena para asignar Apicultor: ", "C\\d{3}");
                    Optional<Colmena> colmena = colmenas.stream().filter(c -> c.id.equals(idColmena)).findFirst();
                    if (colmena.isEmpty()) {
                        printWithDelay("⚠️ Colmena no encontrada.\n", 50);
                        break;
                    }

                    if (apicultores.isEmpty()) {
                        printWithDelay("⚠️ No hay apicultores registrados. Registra uno primero.\n", 50);
                        break;
                    }

                    printWithDelay("👥 Apicultores disponibles:\n", 50);
                    // Muestra la lista de apicultores disponibles.
                    for (int i = 0; i < apicultores.size(); i++) {
                        System.out.printf("%d. %s (Tel: %s)\n", i + 1, apicultores.get(i).nombre, apicultores.get(i).telefono);
                    }

                    // Permite seleccionar un apicultor para asignarlo a la colmena.
                    int indiceApicultor = Integer.parseInt(Utilidades.getValidInput("Selecciona un apicultor (número): ", "\\d+")) - 1;
                    if (indiceApicultor < 0 || indiceApicultor >= apicultores.size()) {
                        printWithDelay("⚠️ Selección inválida.\n", 50);
                        break;
                    }

                    Apicultor apicultorSeleccionado = apicultores.get(indiceApicultor);
                    asignaciones.put(idColmena, apicultorSeleccionado);
                    printWithDelay("\n✅ Apicultor asignado a la colmena " + idColmena + ".", 50);
                }
                case "7" -> {  // Editar Información
                    if (colmenas.isEmpty() && apicultores.isEmpty()) {
                        printWithDelay("⚠️ No hay datos registrados para editar.\n", 50);
                        break;
                    }
                    printWithDelay("""
                        \r📝 ¿Qué deseas editar?
                        1. Información de una Colmena
                        2. Información de un Apicultor
                        """, 50);
                    String opcionEditar = Utilidades.getValidInput("Selecciona una opción: ", "[1-2]");
                    switch (opcionEditar) {
                        case "1" -> {  // Editar Colmena
                            if (colmenas.isEmpty()) {
                                printWithDelay("⚠️ No hay colmenas registradas. Registra una colmena primero.\n", 50);
                                break;
                            }
                            // Solicita el ID de la colmena a editar y la busca en la lista.
                            String idColmena = Utilidades.getValidInput("🐝 ID de la Colmena a editar: ", "C\\d{3}");
                            Optional<Colmena> colmena = colmenas.stream().filter(c -> c.id.equals(idColmena)).findFirst();
                            if (colmena.isEmpty()) {
                                printWithDelay("⚠️ Colmena no encontrada.\n", 50);
                            } else {
                                editarColmena(colmena.get());
                            }
                        }
                        case "2" -> {  // Editar Apicultor
                            // Solicita el nombre del apicultor y busca los apicultores con ese nombre.
                            String nombreApicultor = Utilidades.getValidInput("🧑‍🌾 Nombre del Apicultor a editar: ", ".+");
                            List<Apicultor> apicultoresConNombre = apicultores.stream()
                                    .filter(a -> a.nombre.equals(nombreApicultor)).toList();

                            if (apicultoresConNombre.isEmpty()) {
                                printWithDelay("⚠️ Apicultor no encontrado.\n", 50);
                            } else {
                                if (apicultoresConNombre.size() > 1) {
                                    // Si hay más de un apicultor con el mismo nombre, permite elegir uno de la lista.
                                    printWithDelay("⚠️ Se encontraron varios apicultores con el nombre '" + nombreApicultor + "'.\n", 50);
                                    for (int i = 0; i < apicultoresConNombre.size(); i++) {
                                        Apicultor a = apicultoresConNombre.get(i);
                                        System.out.printf("[%d] %s (Tel: %s)\n", i + 1, a.nombre, a.telefono);
                                    }
                                    int numApicultor = Integer.parseInt(Utilidades.getValidInput("\nSelecciona el apicultor por número: ", "\\d+")) - 1;
                                    if (numApicultor >= 0 && numApicultor < apicultoresConNombre.size()) {
                                        editarApicultor(apicultoresConNombre.get(numApicultor), apicultores);
                                    } else {
                                        printWithDelay("⚠️ Opción no válida.\n", 50);
                                    }
                                } else {
                                    // Si solo hay uno, edita directamente el apicultor.
                                    editarApicultor(apicultoresConNombre.getFirst(), apicultores);
                                }
                            }
                        }
                        default -> printWithDelay("⚠️ Opción no válida. Volviendo al menú principal.\n", 50);
                    }
                }
                case "8" -> {  // Guardar y Salir
                    try {
                        // Guarda los datos en el archivo para persistencia.
                        Utilidades.guardarDatos(colmenas, apicultores, asignaciones, dataFile);
                        printWithDelay("✅ Datos guardados. ¡Hasta pronto!\n", 50);
                    } catch (IOException e) {
                        printWithDelay("⚠️ Error al guardar datos.\n", 50);
                    }
                    return;
                }
                default -> printWithDelay("⚠️ Opción no válida. Intenta nuevamente.\n", 50);
            }
        }
    }
}
