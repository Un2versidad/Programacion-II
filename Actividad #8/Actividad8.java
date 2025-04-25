import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class Actividad8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<String> resultados = new ArrayList<>();

        while (true) {
            int figura = getMenuInput(sc, "Seleccione una figura (1-6): ", 6, Actividad8::mostrarMenuFiguras);
            if (figura == 6) break;

            int operacion = getMenuInput(sc, "Seleccione una operación (1-3): ", 3, Actividad8::mostrarMenuOperaciones);
            FiguraGeometrica figuraGeometrica = crearFigura(sc, figura);

            if (figuraGeometrica != null) {
                String resultado = realizarOperacion(sc, figuraGeometrica, figura, operacion);
                System.out.printf("\n════════ RESULTADO ════════\n%s\n\n", resultado);
                resultados.add(resultado);
            }
        }

        mostrarResumen(resultados);
        System.out.println("¡Hasta luego!");
    }

    private static FiguraGeometrica crearFigura(Scanner sc, int figura) {
        double valor1, valor2;
        return switch (figura) {
            case 1 -> new Circulo(getPositiveInput(sc, "Ingrese el radio del círculo: "));
            case 2 -> new Cuadrado(getPositiveInput(sc, "Ingrese el lado del cuadrado: "));
            case 3 -> new Triangulo(getPositiveInput(sc, "Ingrese la base del triángulo: "), getPositiveInput(sc, "Ingrese la altura del triángulo: "), getPositiveInput(sc, "Ingrese el lado 1 del triángulo: "), getPositiveInput(sc, "Ingrese el lado 2 del triángulo: "), getPositiveInput(sc, "Ingrese el lado 3 del triángulo: ")
            );
            case 4 -> {
                valor1 = getPositiveInput(sc, "Ingrese el largo del rectángulo: ");
                valor2 = getPositiveInput(sc, "Ingrese el ancho del rectángulo: ");
                yield new Rectangulo(valor1, valor2);
            }
            case 5 -> {
                valor1 = getPositiveInput(sc, "Ingrese el lado del pentágono: ");
                valor2 = getPositiveInput(sc, "Ingrese la apotema del pentágono: ");
                yield new Pentagono(valor1, valor2);
            }
            default -> null;
        };
    }

    private static String realizarOperacion(Scanner sc, FiguraGeometrica figura, int figuraTipo, int operacion) {
        String figuraNombre = switch (figuraTipo) {
            case 1 -> "círculo";
            case 2 -> "cuadrado";
            case 3 -> "triángulo";
            case 4 -> "rectángulo";
            case 5 -> "pentágono";
            default -> "figura desconocida";
        };

        return switch (operacion) {
            case 1 -> String.format("Área del %s: %.2f", figuraNombre, figura.calcularArea());
            case 2 -> String.format("Perímetro del %s: %.2f", figuraNombre, figura.calcularPerimetro());
            case 3 -> calcularPotencia(sc, figuraNombre);
            default -> "Operación no válida.";
        };
    }

    private static String calcularPotencia(Scanner sc, String figuraNombre) {
        double base = getPositiveInput(sc, "Ingrese la base para la potencia: ");
        int exponente = getInput(sc, "Ingrese el exponente: ", Integer::parseInt, input -> input >= 0);
        double resultado = calcularPotenciaRecursiva(base, exponente);
        return String.format("Potencia en el %s (base %.2f, exponente %d, %.2f^%d): %.2f", figuraNombre, base, exponente, base, exponente, resultado);
    }

    private static double calcularPotenciaRecursiva(double base, int exponente) {
        if (exponente == 0) return 1;
        return base * calcularPotenciaRecursiva(base, exponente - 1);
    }

    private static int getMenuInput(Scanner sc, String mensaje, int max, Runnable mostrarMenu) {
        return getInput(sc, () -> {
            mostrarMenu.run();
            System.out.print(mensaje);
        }, Integer::parseInt, input -> input >= 1 && input <= max);
    }

    private static double getPositiveInput(Scanner sc, String mensaje) {
        return getInput(sc, mensaje, Double::parseDouble, input -> input > 0);
    }

    private static <T> T getInput(Scanner sc, String mensaje, Function<String, T> parser, Function<T, Boolean> validator) {
        return getInput(sc, () -> System.out.print(mensaje), parser, validator);
    }

    private static <T> T getInput(Scanner sc, Runnable prompt, Function<String, T> parser, Function<T, Boolean> validator) {
        T result;
        do {
            prompt.run();
            try {
                result = parser.apply(sc.nextLine().trim());
                if (validator.apply(result)) return result;
                System.out.println("Valor fuera de rango. Intente de nuevo.");
            } catch (Exception e) {
                System.out.println("Entrada no válida. Intente de nuevo.");
            }
        } while (true);
    }

    private static void mostrarMenuFiguras() {
        System.out.println("""
            ╔═════════════════════════════╗
            ║    CALCULADORA GEOMÉTRICA   ║
            ╠═════════════════════════════╣
            ║ 1. Círculo                  ║
            ║ 2. Cuadrado                 ║
            ║ 3. Triángulo                ║
            ║ 4. Rectángulo               ║
            ║ 5. Pentágono                ║
            ║ 6. Salir                    ║
            ╚═════════════════════════════╝
            """);
    }

    private static void mostrarMenuOperaciones() {
        System.out.println("""
            ╔═════════════════════════════╗
            ║      SELECCIONAR OPCIÓN     ║
            ╠═════════════════════════════╣
            ║ 1. Área                     ║
            ║ 2. Perímetro                ║
            ║ 3. Potencia                 ║
            ╚═════════════════════════════╝
            """);
    }

    private static void mostrarResumen(List<String> resultados) {
        System.out.println("\n═══════ RESUMEN DE RESULTADOS ═══════");
        resultados.forEach(resultado -> System.out.println("• " + resultado));
        System.out.println("═════════════════════════════════════");
    }
}
