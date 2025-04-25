import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

public class ValidacionPassword {

    private static final Pattern MAYUSCULAS = Pattern.compile("(.*[A-Z].*){2,}");
    private static final Pattern MINUSCULAS = Pattern.compile("(.*[a-z].*){3,}");
    private static final Pattern NUMEROS = Pattern.compile(".*\\d.*");
    private static final Pattern CARACTERES_ESPECIALES = Pattern.compile(".*[!@#$%^&*()\\-_=+].*");
    private static final int LONGITUD_MINIMA = 8;
    private static final int PROGRESS_BAR_LENGTH = 20;

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            imprimirEncabezado();
            imprimirRequisitos();

            List<String> contrasenas = new ArrayList<>();
            while (true) {
                System.out.print("\n🔑 Ingrese una contraseña (o 'salir' para finalizar): ");
                String contrasena = scanner.nextLine().trim();
                if ("salir".equalsIgnoreCase(contrasena)) {
                    break;
                }

                while (contrasena.isEmpty()) {
                    System.out.print("⚠️ La contraseña no puede estar vacía. Ingrese nuevamente: ");
                    contrasena = scanner.nextLine().trim();
                }

                contrasenas.add(contrasena);
            }

            if (contrasenas.isEmpty()) {
                imprimirMensaje("⚠️ No se ingresaron contraseñas. Saliendo...", "amarillo");
                return;
            }

            List<String> resultados = new ArrayList<>();
            ExecutorService executor = Executors.newSingleThreadExecutor();

            System.out.println("\n╔══════════════════════════════════════════════════╗");
            System.out.println("║                   VERIFICACIÓN                   ║");
            System.out.println("╚══════════════════════════════════════════════════╝");

            for (int i = 0; i < contrasenas.size(); i++) {
                final int index = i;
                String contrasena = contrasenas.get(i);

                executor.submit(() -> {
                    animarBarraProgreso("Validando contraseña #" + (index + 1) + "...");
                    String resultado = validarContrasena(contrasena);
                    synchronized (resultados) {
                        resultados.add("Contraseña #" + (index + 1) + " (" + contrasena + "): " + resultado);
                    }
                });
            }

            executor.shutdown();
            while (true) {
                if (executor.isTerminated()) break;
            }

            imprimirResultados(resultados);
        }
    }

    private static String validarContrasena(String contrasena) {
        String[] reglas = {
                contrasena.length() >= LONGITUD_MINIMA ? null : "menos de " + LONGITUD_MINIMA + " caracteres",
                MAYUSCULAS.matcher(contrasena).matches() ? null : "menos de 2 letras mayúsculas",
                MINUSCULAS.matcher(contrasena).matches() ? null : "menos de 3 letras minúsculas",
                NUMEROS.matcher(contrasena).matches() ? null : "ningún número",
                CARACTERES_ESPECIALES.matcher(contrasena).matches() ? null : "ningún carácter especial"
        };

        List<String> errores = new ArrayList<>();
        for (String error : reglas) {
            if (error != null) {
                errores.add(error);
            }
        }

        if (errores.isEmpty()) {
            return "✅ Válida.";
        }

        String erroresFormateados = String.join(", ", errores);
        if (errores.size() > 1) {
            int lastComma = erroresFormateados.lastIndexOf(", ");
            erroresFormateados = erroresFormateados.substring(0, lastComma) + " y" + erroresFormateados.substring(lastComma + 1);
        }

        return "❌ No válida - " + erroresFormateados + ".";
    }

    private static void imprimirEncabezado() {
        System.out.println("╔══════════════════════════════════════════════════╗\n║             VALIDADOR DE CONTRASEÑAS             ║\n╚══════════════════════════════════════════════════╝");
        animarTexto();
    }

    private static void imprimirRequisitos() {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║           REQUISITOS PARA CONTRASEÑAS            ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ 1). Al menos 8 caracteres de longitud            ║");
        System.out.println("║ 2). Al menos 2 letras mayúsculas                 ║");
        System.out.println("║ 3). Al menos 3 letras minúsculas                 ║");
        System.out.println("║ 4). Al menos 1 número                            ║");
        System.out.println("║ 5). Al menos 1 carácter especial (!@#$%^&*())    ║");
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    private static final Map<String, String> COLORES = Map.of(
            "verde", "\u001B[32m",
            "rojo", "\u001B[31m",
            "amarillo", "\u001B[33m"
    );

    private static void imprimirMensaje(String mensaje, String color) {
        String prefijoColor = COLORES.getOrDefault(color.toLowerCase(), "\u001B[0m");
        System.out.println(prefijoColor + mensaje + "\u001B[0m");
    }

    private static void animarTexto() {
        String mensaje = "💻 Desarrollado para validar contraseñas de forma segura.";
        mensaje.chars().forEach(c -> {
            System.out.print((char) c);
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        System.out.println();
    }

    private static void animarBarraProgreso(String mensaje) {
        System.out.print(mensaje + "\n[");

        for (int i = 0; i < PROGRESS_BAR_LENGTH; i++) {
            System.out.print("■");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("]");
    }

    private static void imprimirResultados(List<String> resultados) {
        System.out.println("\n╔══════════════════════════════════════════════════╗\n║                    RESULTADOS                    ║\n╚══════════════════════════════════════════════════╝");
        for (String resultado : resultados) {
            imprimirMensaje(resultado, resultado.contains("❌") ? "rojo" : "verde");
        }
    }
}
