package Colmena;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.*;

public class Utilidades {
    // Usamos un lock para evitar condiciones de carrera en las operaciones de entrada/salida
    private static final Lock inputLock = new ReentrantLock();

    // Método para imprimir texto con un retraso, simulando una escritura progresiva
    public static void printWithDelay(String text, int delayMs) {
        inputLock.lock(); // Bloqueamos para garantizar que no haya acceso concurrente
        try {
            // Iteramos sobre cada carácter en el texto y lo imprimimos con un retraso
            for (char c : text.toCharArray()) {
                System.out.print(c); // Imprimimos el carácter
                try {
                    Thread.sleep(delayMs); // Retraso entre caracteres
                } catch (InterruptedException ignored) {
                    // Si ocurre una interrupción, simplemente la ignoramos
                }
            }
            System.out.println(); // Nueva línea después de imprimir el texto
        } finally {
            inputLock.unlock(); // Liberamos el lock
        }
    }

    // Método que muestra una animación de abejas con un retraso dinámico basado en una función trigonométrica
    public static void animacionAbejas() {
        inputLock.lock(); // Lock para evitar acceso concurrente
        try {
            String[] frames = {
                    "      🌺     ",
                    "   🐝         ",
                    "         🐝   ",
                    "      🍯     ",
            };

            // Usamos una función matemática (seno) para alterar la velocidad de la animación
            for (int i = 0; i < 5; i++) {
                for (String frame : frames) {
                    // Calculamos un factor de tiempo basado en la función seno para crear una animación más dinámica
                    double timeFactor = Math.sin(i + frame.length()) * 150 + 150;  // Ajuste dinámico del tiempo
                    System.out.print("\r" + frame); // Sobrescribimos la línea para crear el efecto de animación
                    try {
                        Thread.sleep((long) timeFactor); // Usamos el tiempo calculado para la espera
                    } catch (InterruptedException ignored) {
                    }
                }
            }
            System.out.println("\r🐝 Las abejas están listas para trabajar.\n");
        } finally {
            inputLock.unlock(); // Liberamos el lock
        }
    }

    // Método que muestra una animación de búsqueda con un retraso constante
    public static void animacionBusqueda() {
        inputLock.lock(); // Bloqueo para evitar acceso concurrente
        try {
            String[] frames = {
                    "      🔍     ",
                    "   🔎         ",
                    "         🔍   ",
                    "      🔎     ",
            };

            // Animación simple de búsqueda con un tiempo constante entre cuadros
            for (int i = 0; i < 5; i++) {
                for (String frame : frames) {
                    System.out.print("\r" + frame); // Imprimimos el cuadro de animación
                    try {
                        Thread.sleep(300); // Espera constante de 300 ms
                    } catch (InterruptedException ignored) {}
                }
            }
        } finally {
            inputLock.unlock(); // Liberamos el lock
        }
    }

    // Método que obtiene una entrada válida del usuario, asegurándose de que coincida con un patrón regex
    public static String getValidInput(String prompt, String regex) {
        inputLock.lock(); // Lock para asegurar que no haya condiciones de carrera en la entrada del usuario
        try {
            Scanner scanner = new Scanner(System.in); // Scanner para leer la entrada del usuario
            String input;
            while (true) {
                System.out.print(prompt); // Pedimos la entrada
                input = scanner.nextLine().trim(); // Limpiamos espacios al inicio y al final

                // Normalizamos la entrada (capitalizamos la primera letra y convertimos el resto a minúsculas)
                if (!input.isEmpty()) {
                    input = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
                }

                // Verificamos si la entrada coincide con el patrón proporcionado
                if (input.matches(regex)) {
                    return input; // Si es válida, la devolvemos
                }
                System.out.println("⚠️ Entrada inválida. Inténtalo de nuevo.\n"); // Mensaje de error si la entrada es inválida
            }
        } finally {
            inputLock.unlock(); // Liberamos el lock
        }
    }

    // Método que ejecuta una tarea en un hilo separado y espera su finalización
    public static void runWithBlock(Runnable task) {
        Thread thread = new Thread(task); // Creamos un nuevo hilo
        thread.start(); // Iniciamos el hilo
        try {
            thread.join(); // Esperamos a que termine la ejecución del hilo
        } catch (InterruptedException ignored) {
            // Si el hilo es interrumpido, simplemente lo ignoramos
        }
    }

    // Método que verifica la integridad de los datos de las colmenas y apicultores
    public static void verificarDatos(List<Colmena> colmenas, List<Apicultor> apicultores, Map<String, Apicultor> asignaciones) {
        Scanner scanner = new Scanner(System.in);

        runWithBlock(() -> printWithDelay("🗃️ Verificando integridad de datos...", 50)); // Mensaje de verificación
        animacionBusqueda(); // Animación de búsqueda

        // Validamos cada colmena
        for (Colmena colmena : colmenas) {
            // Comprobamos que cada colmena tenga un ID válido
            if (colmena.id == null || !colmena.id.matches("C\\d{3}")) {
                printWithDelay("\r⚠️ Falta el ID de una colmena. Proporcione un nuevo ID (formato CXXX): ", 50);
                colmena.id = getValidInput("ID: ", "C\\d{3}");
            }
            // Validamos la ubicación de la colmena
            if (colmena.ubicacion == null || colmena.ubicacion.isEmpty()) {
                printWithDelay("\r⚠️ Falta la ubicación de la colmena con ID " + colmena.id + ". Proporcione una ubicación: ", 50);
                colmena.ubicacion = scanner.nextLine();
            }
            // Validamos el estado de salud de la colmena
            if (colmena.estadoSalud == null || !colmena.estadoSalud.matches("Buena|Regular|Mala")) {
                printWithDelay("\r⚠️ Falta el estado de salud de la colmena con ID " + colmena.id + ". Proporcione un estado (Buena/Regular/Mala): ", 50);
                colmena.estadoSalud = getValidInput("Estado de Salud: ", "Buena|Regular|Mala");
            }
            // Si no hay apicultor asignado, asignamos uno
            if (!asignaciones.containsKey(colmena.id)) {
                printWithDelay("\r⚠️ No hay un apicultor asignado para la colmena con ID " + colmena.id + ".", 50);
                printWithDelay("\n🧑‍🌾 Asignando un apicultor existente:\n", 50);

                // Listamos apicultores disponibles para asignar
                for (int i = 0; i < apicultores.size(); i++) {
                    System.out.printf("[%d] %s (Tel: %s)\n", i + 1, apicultores.get(i).nombre, apicultores.get(i).telefono);
                }

                int opcion = Integer.parseInt(getValidInput("\nSelecciona un apicultor por número: ", "\\d+")) - 1;
                if (opcion >= 0 && opcion < apicultores.size()) {
                    asignaciones.put(colmena.id, apicultores.get(opcion)); // Asignamos el apicultor seleccionado
                    printWithDelay("\n✅ Apicultor asignado correctamente.\n", 50);
                } else {
                    printWithDelay("⚠️ Opción no válida. Se omitió la asignación.\n", 50);
                }
            }
        }

        // Validamos los datos de los apicultores
        for (Apicultor apicultor : apicultores) {
            // Validamos el nombre del apicultor
            if (apicultor.nombre == null || apicultor.nombre.isEmpty()) {
                printWithDelay("\r⚠️ Falta el nombre de un apicultor. Proporcione un nombre: ", 50);
                apicultor.nombre = scanner.nextLine();
            }
            // Validamos el teléfono del apicultor
            if (apicultor.telefono == null || !apicultor.telefono.matches("\\d{10}")) {
                printWithDelay("\r⚠️ Falta el teléfono del apicultor " + apicultor.nombre + ". Proporcione un teléfono (10 dígitos): ", 50);

                String telefono = getValidInput("Teléfono: ", "\\d{10}");
                boolean telefonoExistente = apicultores.stream().anyMatch(a -> a.telefono != null && a.telefono.equals(telefono));

                // Verificamos si el teléfono ya está registrado por otro apicultor
                if (telefonoExistente) {
                    printWithDelay("\n⚠️ El teléfono " + telefono + " ya está registrado por otro apicultor. Intenta con otro número.", 50);
                } else {
                    apicultor.telefono = telefono; // Asignamos el teléfono
                }
            }
        }

        printWithDelay("\r✅ Verificación completa.\n", 50); // Mensaje de verificación completada
    }

    // Método para cargar los datos desde un archivo serializado
    public static void cargarDatos(List<Colmena> colmenas, List<Apicultor> apicultores, Map<String, Apicultor> asignaciones, String dataFile) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            // Leemos el archivo serializado y extraemos los datos
            DatosApicola datos = (DatosApicola) ois.readObject();
            colmenas.addAll(datos.colmenas);
            apicultores.addAll(datos.apicultores);
            asignaciones.putAll(datos.asignaciones);
            verificarDatos(colmenas, apicultores, asignaciones); // Verificamos los datos cargados
        }
    }

    // Método para guardar los datos a un archivo serializado
    public static void guardarDatos(List<Colmena> colmenas, List<Apicultor> apicultores, Map<String, Apicultor> asignaciones, String dataFile) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            // Guardamos los datos serializados en el archivo
            oos.writeObject(new DatosApicola(colmenas, apicultores, asignaciones));
            printWithDelay("✅ Datos guardados correctamente.\n", 50); // Confirmación de guardado
        }
    }
}
