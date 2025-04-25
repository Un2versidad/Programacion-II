import java.util.Scanner;
import java.util.function.Function;

public class CalculadoraGeometrica {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            boolean salir = false;
            while (!salir) {
                int figura = getValidMenuInput(sc, "Seleccione una opción (1-6): ", 6, CalculadoraGeometrica::mostrarMenuFiguras);
                switch (figura) {
                    case 1, 2, 3, 4, 5 -> {
                        int operacion = getValidMenuInput(sc, "Seleccione una operación (1-2): ", 2, CalculadoraGeometrica::mostrarMenuOperaciones);
                        switch (figura) {
                            case 1 -> calcularCirculo(sc, operacion);
                            case 2 -> calcularCuadrado(sc, operacion);
                            case 3 -> calcularTriangulo(sc, operacion);
                            case 4 -> calcularRectangulo(sc, operacion);
                            case 5 -> calcularPentagono(sc, operacion);
                        }
                    }
                    case 6 -> salir = true;
                }
            }
        }
        System.out.println("¡Hasta luego!");
    }

    private static void mostrarMenuFiguras() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║   CALCULADORA GEOMÉTRICA    ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 1. Círculo                  ║");
        System.out.println("║ 2. Cuadrado                 ║");
        System.out.println("║ 3. Triángulo                ║");
        System.out.println("║ 4. Rectángulo               ║");
        System.out.println("║ 5. Pentágono                ║");
        System.out.println("║ 6. Salir                    ║");
        System.out.println("╚═════════════════════════════╝");
    }

    private static void mostrarMenuOperaciones() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║       SELECCIONAR OPCIÓN    ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 1. Área                     ║");
        System.out.println("║ 2. Perímetro                ║");
        System.out.println("╚═════════════════════════════╝");
    }

    private static void calcularCirculo(Scanner sc, int operacion) {
        System.out.println("\n     ▓▓▓▓▓▓▓");
        System.out.println("   ▓▓       ▓▓");
        System.out.println(" ▓▓           ▓▓");
        System.out.println("▓▓             ▓▓  ← Radio");
        System.out.println(" ▓▓           ▓▓");
        System.out.println("   ▓▓       ▓▓");
        System.out.println("     ▓▓▓▓▓▓▓\n");
        double radio = getValidInput(sc, "Ingrese el radio del círculo: ", Double::parseDouble, input -> input > 0);
        if (operacion == 1) {
            System.out.printf("Área del círculo: %.2f\n", Math.PI * Math.pow(radio, 2));
        } else {
            System.out.printf("Perímetro del círculo: %.2f\n", 2 * Math.PI * radio);
        }
    }

    private static void calcularCuadrado(Scanner sc, int operacion) {
        System.out.println("\n┌──────────┐");
        System.out.println("│          │");
        System.out.println("│          │  ← Lado");
        System.out.println("│          │");
        System.out.println("└──────────┘\n");
        double lado = getValidInput(sc, "Ingrese el lado del cuadrado: ", Double::parseDouble, input -> input > 0);
        if (operacion == 1) {
            System.out.printf("Área del cuadrado: %.2f\n", lado * lado);
        } else {
            System.out.printf("Perímetro del cuadrado: %.2f\n", 4 * lado);
        }
    }

    private static void calcularTriangulo(Scanner sc, int operacion) {
        System.out.println("\n      /\\");
        System.out.println("     /  \\  ← Altura");
        System.out.println("    /____\\");
        System.out.println("     Base\n");
        if (operacion == 1) {
            double base = getValidInput(sc, "Ingrese la base del triángulo: ", Double::parseDouble, input -> input > 0);
            double altura = getValidInput(sc, "Ingrese la altura del triángulo: ", Double::parseDouble, input -> input > 0);
            System.out.printf("Área del triángulo: %.2f\n", (base * altura) / 2);
        } else {
            double lado1 = getValidInput(sc, "Ingrese el primer lado del triángulo: ", Double::parseDouble, input -> input > 0);
            double lado2 = getValidInput(sc, "Ingrese el segundo lado del triángulo: ", Double::parseDouble, input -> input > 0);
            double lado3 = getValidInput(sc, "Ingrese el tercer lado del triángulo: ", Double::parseDouble, input -> input > 0);
            System.out.printf("Perímetro del triángulo: %.2f\n", lado1 + lado2 + lado3);
        }
    }

    private static void calcularRectangulo(Scanner sc, int operacion) {
        System.out.println("\n┌──────────────┐");
        System.out.println("│              │");
        System.out.println("│              │  ← Largo");
        System.out.println("│              │");
        System.out.println("└──────────────┘");
        System.out.println("     ↑ Ancho\n");
        double largo = getValidInput(sc, "Ingrese el largo del rectángulo: ", Double::parseDouble, input -> input > 0);
        double ancho = getValidInput(sc, "Ingrese el ancho del rectángulo: ", Double::parseDouble, input -> input > 0);
        if (operacion == 1) {
            System.out.printf("Área del rectángulo: %.2f\n", largo * ancho);
        } else {
            System.out.printf("Perímetro del rectángulo: %.2f\n", 2 * (largo + ancho));
        }
    }

    private static void calcularPentagono(Scanner sc, int operacion) {
        System.out.println("\n      /\\ ");
        System.out.println("     /  \\  ← Apotema");
        System.out.println("   /______\\");
        System.out.println("   \\      / ");
        System.out.println("    \\____/ ");
        System.out.println("       Lado\n");
        double lado = getValidInput(sc, "Ingrese el lado del pentágono: ", Double::parseDouble, input -> input > 0);
        if (operacion == 1) {
            double apotema = getValidInput(sc, "Ingrese la apotema del pentágono: ", Double::parseDouble, input -> input > 0);
            System.out.printf("Área del pentágono: %.2f\n", (5 * lado * apotema) / 2);
        } else {
            System.out.printf("Perímetro del pentágono: %.2f\n", 5 * lado);
        }
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