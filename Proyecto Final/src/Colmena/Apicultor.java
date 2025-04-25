package Colmena;

import java.io.Serializable;

// Clase que representa a un apicultor.
// Esta clase almacena la información básica de un apicultor, como su nombre y teléfono.
// La clase implementa Serializable para permitir la serialización de objetos de tipo Apicultor.
public class Apicultor implements Serializable {
    
    // Atributo que almacena el nombre del apicultor.
    public String nombre;
    
    // Atributo que almacena el número de teléfono del apicultor.
    public String telefono;

    // Constructor que inicializa los atributos 'nombre' y 'telefono' con los valores proporcionados.
    public Apicultor(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    // Método que retorna el número de teléfono del apicultor.
    public String getTelefono() {
        return telefono;
    }
}
