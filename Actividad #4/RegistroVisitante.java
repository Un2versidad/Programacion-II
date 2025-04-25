import java.util.Scanner;
import java.util.function.Function;

public class RegistroVisitante {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            String nombre = getValidInput(sc, "Por favor, ingrese el nombre completo del visitante:", input -> input.matches("[a-zA-Z\\s]+") ? input : null, "Nombre inválido. Ingrese solo letras y espacios.");
            char genero = getValidInput(sc, "Indique el género del visitante (M/F):", input -> input.equalsIgnoreCase("M") || input.equalsIgnoreCase("F") ? input.toUpperCase().charAt(0) : null, "Género inválido. Ingrese 'M' o 'F'.");
            int edad = getValidInput(sc, "Indique la edad del visitante (en años):", input -> parseAndValidate(input, Integer::parseInt, 0, Integer.MAX_VALUE), "Edad inválida. Ingrese un número positivo.");
            float altura = getValidInput(sc, "Indique la altura del visitante (en metros, ej: 1.75):", input -> parseAndValidate(input, Float::parseFloat, 0.5f, 3.0f), "Altura inválida. Ingrese un valor entre 0.5 y 3.0 metros.");
            double peso = getValidInput(sc, "Indique el peso del visitante (en kilogramos, ej: 70.5):", input -> parseAndValidate(input, Double::parseDouble, 30.0, 300.0), "Peso inválido. Ingrese un valor entre 30 y 300 kg.");
            String esResidente = getValidInput(sc, "¿El visitante es residente del edificio? (Sí/No):", input -> input.equalsIgnoreCase("Si") || input.equalsIgnoreCase("No") ? input.equalsIgnoreCase("Si") ? "Si" : "No" : null, "Respuesta inválida. Ingrese 'Si' o 'No'.");
            long telefono = getValidInput(sc, "Ingrese el número de teléfono del visitante (solo números):", input -> input.matches("\\d{8,10}") ? Long.parseLong(input) : null, "Teléfono inválido. Ingrese un número válido de 8 a 10 dígitos.");
            byte pisoDestino = getValidInput(sc, "Indique el piso de destino (entre 0 y 10):", input -> parseAndValidate(input, Byte::parseByte, (byte) 0, (byte) 10), "Piso inválido. Ingrese un valor entre 0 y 10.");
            short numeroDepartamento = getValidInput(sc, "Indique el número de departamento (entre 1 y 999):", input -> parseAndValidate(input, Short::parseShort, (short) 1, (short) 999), "Departamento inválido. Ingrese un valor entre 1 y 999.");

            int totalVisitas = 0;
            long tiempoTotalEstadia = 0;

            // Registro de visitas durante una semana
            for (int dia = 1; dia <= 7; dia++) {
                System.out.printf("\nDía %d\n¿Cuántas visitas realizó hoy?: ", dia);
                int visitas = sc.nextInt();
                totalVisitas += visitas;

                for (int visita = 1; visita <= visitas; visita++) {
                    System.out.printf("Duración de la visita %d (en minutos): ", visita);
                    tiempoTotalEstadia += sc.nextLong();
                }
            }

            double tiempoPromedioEstadia = (totalVisitas > 0) ? (double) tiempoTotalEstadia / totalVisitas : 0;

            System.out.printf("""
                \n--- Resumen de Información del Visitante ---
                Nombre completo: %s
                Género: %c
                Edad: %d años (%s)
                Altura: %.2f metros
                Peso: %.2f kilogramos
                ¿Es residente del edificio?: %s
                Número de teléfono: %d
                Piso de destino: %d
                Número de departamento: %d
                Total de visitas en la semana: %d
                Tiempo promedio de estadía: %.2f minutos
                """,
                    nombre, genero, edad, (edad >= 18 ? "Mayor de edad" : "Menor de edad"),
                    altura, peso, esResidente, telefono, pisoDestino, numeroDepartamento, totalVisitas, tiempoPromedioEstadia);
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