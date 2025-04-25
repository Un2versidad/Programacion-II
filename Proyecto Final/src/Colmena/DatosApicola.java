package Colmena;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DatosApicola implements Serializable {
    // Lista de colmenas registradas en el sistema.
    public List<Colmena> colmenas;
    
    // Lista de apicultores disponibles en el sistema.
    public List<Apicultor> apicultores;
    
    // Mapa que asocia el ID de cada colmena con el apicultor asignado.
    public Map<String, Apicultor> asignaciones;

    // Constructor que inicializa los atributos de la clase DatosApicola.
    // Asegura que los datos sobre colmenas, apicultores y asignaciones se gestionen juntos.
    public DatosApicola(List<Colmena> colmenas, List<Apicultor> apicultores, Map<String, Apicultor> asignaciones) {
        this.colmenas = colmenas;
        this.apicultores = apicultores;
        this.asignaciones = asignaciones;
    }
}
