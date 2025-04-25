import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class CalculadoraGeometrica {
    public static void main(String[] args) {
        List<String> resultados = new ArrayList<>();
        try (Scanner sc = new Scanner(System.in)) {
            boolean salir = false;
            while (!salir) {
                int figura = getValidMenuInput(sc, "Seleccione una opción (1-6): ", 6, CalculadoraGeometrica::mostrarMenuFiguras);
                switch (figura) {
                    case 1, 2, 3, 4, 5 -> {
                        int operacion = getValidMenuInput(sc, "Seleccione una operación (1-3): ", 3, CalculadoraGeometrica::mostrarMenuOperaciones);
                        String resultado = switch (figura) {
                            case 1 -> calcularCirculo(sc, operacion);
                            case 2 -> calcularCuadrado(sc, operacion);
                            case 3 -> calcularTriangulo(sc, operacion);
                            case 4 -> calcularRectangulo(sc, operacion);
                            case 5 -> calcularPentagono(sc, operacion);
                            default -> "";
                        };
                        System.out.println("\n════════ RESULTADO ════════\n" + resultado + "\n");
                        resultados.add(resultado);
                    }
                    case 6 -> salir = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error inesperado: " + e.getMessage());
        }
        System.out.println("\n═══════ RESUMEN DE RESULTADOS ═══════");
        resultados.forEach(resultado -> System.out.println("• " + resultado));
        System.out.println("═════════════════════════════════════");
        System.out.println("¡Hasta luego!");
    }

    private static void mostrarMenuFiguras() {
        System.out.println("╔═════════════════════════════╗\n║    CALCULADORA GEOMÉTRICA   ║\n╠═════════════════════════════╣\n║ 1. Círculo                  ║\n║ 2. Cuadrado                 ║\n║ 3. Triángulo                ║\n║ 4. Rectángulo               ║\n║ 5. Pentágono                ║\n║ 6. Salir                    ║\n╚═════════════════════════════╝");
    }

    private static void mostrarMenuOperaciones() {
        System.out.println("╔═════════════════════════════╗\n║      SELECCIONAR OPCIÓN     ║\n╠═════════════════════════════╣\n║ 1. Área                     ║\n║ 2. Perímetro                ║\n║ 3. Potencia                 ║\n╚═════════════════════════════╝");
    }

    private static String calcularCirculo(Scanner sc, int operacion) {
        System.out.println("\n     ▓▓▓▓▓▓▓\n   ▓▓       ▓▓\n ▓▓           ▓▓\n▓▓             ▓▓  ← Radio\n ▓▓           ▓▓\n   ▓▓       ▓▓\n     ▓▓▓▓▓▓▓\n");
        double radio = getValidInput(sc, "Ingrese el radio del círculo: ", Double::parseDouble, input -> input > 0);
        return switch (operacion) {
            case 1 -> String.format("Área del círculo: %.2f", Math.PI * Math.pow(radio, 2));
            case 2 -> String.format("Perímetro del círculo: %.2f", 2 * Math.PI * radio);
            case 3 -> calcularPotencia(sc, radio);
            default -> "";
        };
    }

    private static String calcularCuadrado(Scanner sc, int operacion) {
        System.out.println("\n┌──────────┐\n│          │\n│          │  ← Lado\n│          │\n└──────────┘\n");
        double lado = getValidInput(sc, "Ingrese el lado del cuadrado: ", Double::parseDouble, input -> input > 0);
        return switch (operacion) {
            case 1 -> String.format("Área del cuadrado: %.2f", lado * lado);
            case 2 -> String.format("Perímetro del cuadrado: %.2f", 4 * lado);
            case 3 -> calcularPotencia(sc, lado);
            default -> "";
        };
    }

    private static String calcularTriangulo(Scanner sc, int operacion) {
        System.out.println("\n      /\\\n     /  \\  ← Altura\n    /____\\\n     Base\n");
        if (operacion == 1) {
            double base = getValidInput(sc, "Ingrese la base del triángulo: ", Double::parseDouble, input -> input > 0);
            double altura = getValidInput(sc, "Ingrese la altura del triángulo: ", Double::parseDouble, input -> input > 0);
            return String.format("Área del triángulo: %.2f", (base * altura) / 2);
        } else if (operacion == 2) {
            double lado1 = getValidInput(sc, "Ingrese el primer lado del triángulo: ", Double::parseDouble, input -> input > 0);
            double lado2 = getValidInput(sc, "Ingrese el segundo lado del triángulo: ", Double::parseDouble, input -> input > 0);
            double lado3 = getValidInput(sc, "Ingrese el tercer lado del triángulo: ", Double::parseDouble, input -> input > 0);
            return String.format("Perímetro del triángulo: %.2f", lado1 + lado2 + lado3);
        } else {
            double base = getValidInput(sc, "Ingrese la base del triángulo: ", Double::parseDouble, input -> input > 0);
            return calcularPotencia(sc, base);
        }
    }

    private static String calcularRectangulo(Scanner sc, int operacion) {
        System.out.println("\n┌──────────────┐\n│              │\n│              │  ← Largo\n│              │\n└──────────────┘\n     ↑ Ancho\n");
        double largo = getValidInput(sc, "Ingrese el largo del rectángulo: ", Double::parseDouble, input -> input > 0);
        double ancho = getValidInput(sc, "Ingrese el ancho del rectángulo: ", Double::parseDouble, input -> input > 0);
        return switch (operacion) {
            case 1 -> String.format("Área del rectángulo: %.2f", largo * ancho);
            case 2 -> String.format("Perímetro del rectángulo: %.2f", 2 * (largo + ancho));
            case 3 -> calcularPotencia(sc, largo);
            default -> "";
        };
    }

    private static String calcularPentagono(Scanner sc, int operacion) {
        System.out.println("\n      /\\ \n     /  \\  ← Apotema\n   /______\\\n   \\      / \n    \\____/ \n       Lado\n");
        double lado = getValidInput(sc, "Ingrese el lado del pentágono: ", Double::parseDouble, input -> input > 0);
        return switch (operacion) {
            case 1 -> String.format("Área del pentágono: %.2f", (5 * lado * getValidInput(sc, "Ingrese la apotema del pentágono: ", Double::parseDouble, input -> input > 0)) / 2);
            case 2 -> String.format("Perímetro del pentágono: %.2f", 5 * lado);
            case 3 -> calcularPotencia(sc, lado);
            default -> "";
        };
    }

    private static String calcularPotencia(Scanner sc, double base) {
        int exponente = getValidInput(sc, "Ingrese el exponente: ", Integer::parseInt, input -> input >= 0);
        double resultado = calcularPotenciaRecursiva(base, exponente);
        return String.format("Potencia de %.2f^%d: %.2f", base, exponente, resultado);
    }

    private static double calcularPotenciaRecursiva(double base, int exponente) {
        if (exponente == 0) return 1;
        return base * calcularPotenciaRecursiva(base, exponente - 1);
    }

    private static int getValidMenuInput(Scanner sc, String mensaje, int max, Runnable mostrarMenu) {
        int result;
        do {
            mostrarMenu.run();
            result = getValidInput(sc, mensaje, Integer::parseInt, input -> input >= 1 && input <= max);
        } while (result < 1 || result > max);
        return result;
    }

    private static <T> T getValidInput(Scanner sc, String mensaje, Function<String, T> parser, Function<T, Boolean> validator) {
        T result;
        do {
            System.out.print(mensaje);
            String input = sc.nextLine().trim();
            result = null;
            try {
                result = parser.apply(input);
                if (!validator.apply(result)) throw new IllegalArgumentException("Valor fuera de rango.");
            } catch (Exception e) {
                System.out.println("Entrada no válida. Intente de nuevo.");
            }
        } while (result == null);
        return result;
    }
}