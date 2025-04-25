package Colmena;

import java.io.Serializable;
import java.util.*;
import static Colmena.Utilidades.animacionAbejas;
import static Colmena.Utilidades.runWithBlock;

public class Inspeccion implements Serializable {
    // Atributos de la clase Inspección, guardamos la fecha de la inspección, el resultado y las acciones recomendadas.
    public Date fecha;
    public String resultado;
    public String acciones;

    // Constructor que inicializa la inspección con los valores proporcionados.
    public Inspeccion(Date fecha, String resultado, String acciones) {
        this.fecha = fecha;
        this.resultado = resultado;
        this.acciones = acciones;
    }

    // Método recursivo para generar la información de inspecciones de una lista, con sangría según el nivel de profundidad.
    public static String getInfo(List<Inspeccion> inspecciones, int nivel) {
        if (inspecciones.isEmpty()) {
            return ""; // Caso base: si no hay más inspecciones, devolvemos una cadena vacía.
        }

        // Obtenemos la primera inspección y construimos el resultado formateado.
        Inspeccion inspeccion = inspecciones.getFirst();
        String result = " ".repeat(nivel * 4) + // Añadimos sangría dinámica según el nivel de profundidad.
                String.format("📅 Fecha: %s\n", inspeccion.fecha) +
                " ".repeat(nivel * 4) +
                String.format("🔍 Resultado: %s\n", inspeccion.resultado) +
                " ".repeat(nivel * 4) +
                String.format("🛠️ Acciones: %s\n", inspeccion.acciones);

        // Si hay más inspecciones, agregamos una línea divisoria.
        if (inspecciones.size() > 1) {
            result += " ".repeat(nivel * 4) + "----------------------------------\n";
        }

        // Llamada recursiva para las siguientes inspecciones, aumentando el nivel de sangría.
        return result + getInfo(inspecciones.subList(1, inspecciones.size()), nivel + 1);
    }

    // Método para realizar inspecciones en una lista de colmenas, con concurrencia y manejo de hilos.
    public static void realizarInspecciones(List<Colmena> colmenas) {
        // Si no hay colmenas, informamos al usuario y terminamos el proceso.
        if (colmenas.isEmpty()) {
            runWithBlock(() -> Utilidades.printWithDelay("⚠️ No hay colmenas registradas para inspeccionar.\n", 50));
            return;
        }

        // Listas para almacenar los hilos de ejecución y los mensajes de resultado de cada inspección.
        List<Thread> hilos = new ArrayList<>();
        List<String> mensajesResultado = Collections.synchronizedList(new ArrayList<>());

        // Iteramos sobre cada colmena para crear un hilo que realice la inspección.
        for (Colmena colmena : colmenas) {
            Thread hilo = new Thread(() -> {
                try {
                    // Mostramos un mensaje de inicio de la inspección para cada colmena.
                    runWithBlock(() -> Utilidades.printWithDelay("🕵️ Inspeccionando la colmena " + colmena.id + "...", 50));

                    // Simulamos el tiempo de inspección con un retraso aleatorio
                    Random random = new Random();
                    int tiempoInspeccion = random.nextInt(1000) + 500;
                    Thread.sleep(tiempoInspeccion);

                    // Cálculo de puntos para determinar el estado de la colmena, basado en su abeja reina y el estado general.
                    int estadoPuntos = (colmena.abejaReina != null && colmena.abejaReina.estadoSalud.equals("Buena")) ? 2 : 0;
                    estadoPuntos += switch (colmena.estadoSalud) {
                        case "Buena" -> 2;
                        case "Regular" -> 1;
                        default -> 0;
                    };

                    // Evaluamos el estado de la colmena en función de los puntos obtenidos.
                    String resultado = estadoPuntos >= 3 ? "Estado óptimo" :
                            estadoPuntos == 2 ? "Necesita monitoreo" : "Urgente atención";

                    // Definimos las acciones a tomar dependiendo del resultado de la inspección.
                    String acciones = switch (resultado) {
                        case "Estado óptimo" -> "Revisión en 6 meses";
                        case "Necesita monitoreo" -> "Revisar en 3 meses";
                        default -> "Reparaciones urgentes y suplementar alimentación";
                    };

                    // Creamos un objeto Inspección con la fecha actual y los resultados.
                    Inspeccion inspeccion = new Inspeccion(new Date(), resultado, acciones);
                    colmena.agregarInspeccion(inspeccion);

                    // Determinamos el mensaje final según el resultado de la inspección.
                    String mensajeFin = resultado.equals("Estado óptimo")
                            ? "\r✅ Inspección completada con éxito para la colmena " + colmena.id + "."
                            : "\r⚠️ La inspección terminó con algunos problemas para la colmena " + colmena.id + ".";

                    // Añadimos el mensaje a la lista de resultados.
                    mensajesResultado.add("\n" + mensajeFin);
                } catch (InterruptedException e) {
                    // En caso de error durante la inspección, mostramos un mensaje de error.
                    runWithBlock(() -> Utilidades.printWithDelay("\r⚠️ Error durante la inspección de la colmena " + colmena.id + ".\n", 50));
                }
            });

            // Añadimos el hilo a la lista y lo iniciamos.
            hilos.add(hilo);
            hilo.start();
        }

        // Esperamos a que todos los hilos terminen antes de continuar.
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            } catch (InterruptedException e) {
                // En caso de error al esperar la finalización de un hilo, mostramos un mensaje.
                runWithBlock(() -> Utilidades.printWithDelay("\r⚠️ Error esperando la finalización de un hilo.\n", 50));
            }
        }

        // Después de que todas las inspecciones han terminado, mostramos los resultados.
        runWithBlock(() -> {
            mensajesResultado.forEach(mensaje -> Utilidades.printWithDelay(mensaje, 50));
            animacionAbejas(); // Animación de abejas al finalizar las inspecciones.
        });
    }
}
