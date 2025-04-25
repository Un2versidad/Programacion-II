package Colmena;

// La clase AbejaReina extiende la clase Abeja, añadiendo atributos específicos de la abeja reina.
// La abeja reina tiene una edad y una productividad que afectan la colonia de abejas.
public class AbejaReina extends Abeja {

    // Atributo que representa la edad de la abeja reina en años.
    int edad;

    // Atributo que representa la productividad de la abeja reina en kg de miel.
    double productividad;

    // Constructor que recibe la edad, estado de salud y productividad de la abeja reina.
    // Utiliza el constructor de la clase base (Abeja) para establecer el estado de salud.
    public AbejaReina(int edad, String estadoSalud, double productividad) {
        super(estadoSalud);  // Llama al constructor de la clase base para establecer el estado de salud.
        this.edad = edad;     // Establece la edad de la abeja reina.
        this.productividad = productividad;  // Establece la productividad de la abeja reina.
    }

    // Método que devuelve la información completa de la abeja reina.
    // Llama al método getInfo() de la clase base (Abeja) y le agrega los detalles específicos de la abeja reina.
    public String getInfo() {
        // Se agrega la información heredada de la clase Abeja y luego se concatena la información propia de la abeja reina.
        return super.getInfo() + String.format("""
                🕰️ Edad: %d años
                🍯 Productividad: %.2f kg de miel
                """, edad, productividad);
    }
}
