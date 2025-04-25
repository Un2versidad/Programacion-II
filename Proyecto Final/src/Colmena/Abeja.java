package Colmena;

import java.io.Serializable;

// La clase Abeja representa una abeja común con un atributo específico para su estado de salud.
// Es la clase base que puede ser extendida por otras clases, como la AbejaReina, para incluir más detalles específicos.
public class Abeja implements Serializable {

    // Atributo que representa el estado de salud de la abeja. 
    // Puede ser "Buena", "Regular" o "Mala", indicando el bienestar de la abeja.
    public String estadoSalud;

    // Constructor que inicializa el estado de salud de la abeja.
    // Recibe un valor de tipo String para el estado de salud.
    public Abeja(String estadoSalud) {
        this.estadoSalud = estadoSalud;  // Establece el estado de salud de la abeja.
    }

    // Método que devuelve la información del estado de salud de la abeja en formato de cadena.
    // La información se muestra con el formato: "❤️ Estado de Salud: [estadoSalud]"
    public String getInfo() {
        // Se utiliza String format para formatear y devolver el estado de salud de la abeja.
        return String.format("❤️ Estado de Salud: %s%n", estadoSalud);
    }
}
