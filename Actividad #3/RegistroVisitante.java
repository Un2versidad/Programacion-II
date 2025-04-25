import java.util.Scanner;
import java.util.function.Function;

public class RegistroVisitante {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.printf("""
                \n--- Resumen de Información del Visitante ---
                Nombre completo: %s
                Género: %c
                Edad: %d años
                Altura: %.2f metros
                Peso: %.2f kilogramos
                ¿Es residente del edificio?: %s
                Número de teléfono: %d
                Piso de destino: %d
                Número de departamento: %d
                """,
                    getValidInput(sc, "Por favor, ingrese el nombre completo del visitante:", input -> input.matches("[a-zA-Z\\s]+") ? input : null, "Nombre inválido. Ingrese solo letras y espacios."),
                    getValidInput(sc, "Indique el género del visitante (M/F):", input -> input.equalsIgnoreCase("M") || input.equalsIgnoreCase("F") ? input.toUpperCase().charAt(0) : null, "Género inválido. Ingrese 'M' o 'F'."),
                    getValidInput(sc, "Indique la edad del visitante (en años):", input -> parseAndValidate(input, Integer::parseInt, 0, Integer.MAX_VALUE), "Edad inválida. Ingrese un número positivo."),
                    getValidInput(sc, "Indique la altura del visitante (en metros, ej: 1.75):", input -> parseAndValidate(input, Float::parseFloat, 0.5f, 3.0f), "Altura inválida. Ingrese un valor entre 0.5 y 3.0 metros."),
                    getValidInput(sc, "Indique el peso del visitante (en kilogramos, ej: 70.5):", input -> parseAndValidate(input, Double::parseDouble, 30.0, 300.0), "Peso inválido. Ingrese un valor entre 30 y 300 kg."),
                    getValidInput(sc, "¿El visitante es residente del edificio? (true/false):", input -> input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false") ? input.equalsIgnoreCase("true") ? "Sí" : "No" : null, "Respuesta inválida. Ingrese 'true' o 'false'."),
                    getValidInput(sc, "Ingrese el número de teléfono del visitante (solo números):", input -> input.matches("\\d{8,10}") ? Long.parseLong(input) : null, "Teléfono inválido. Ingrese un número válido de 8 a 10 dígitos."),
                    getValidInput(sc, "Indique el piso de destino (entre 0 y 10):", input -> parseAndValidate(input, Byte::parseByte, (byte) 0, (byte) 10), "Piso inválido. Ingrese un valor entre 0 y 10."),
                    getValidInput(sc, "Indique el número de departamento (entre 1 y 999):", input -> parseAndValidate(input, Short::parseShort, (short) 1, (short) 999), "Departamento inválido. Ingrese un valor entre 1 y 999."));
        }
    }

    private static <T> T getValidInput(Scanner sc, String msg, Function<String, T> validator, String errorMsg) {
        T result;
        do {
            System.out.println(msg);
            result = validator.apply(sc.nextLine().trim());
            if (result == null) System.out.println(errorMsg);
        } while (result == null);
        return result;
    }

    private static <T extends Number> T parseAndValidate(String input, Function<String, T> parser, T min, T max) {
        try {
            T value = parser.apply(input);
            return value.doubleValue() >= min.doubleValue() && value.doubleValue() <= max.doubleValue() ? value : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}