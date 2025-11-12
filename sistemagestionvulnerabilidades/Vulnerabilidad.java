// Vulnerabilidad.java
// La clase Vulnerabilidad representa una vulnerabilidad, que puede estar asociada a múltiples ocurrencias en diferentes dispositivos.

package sistemagestionvulnerabilidades;

public class Vulnerabilidad {
    private int id; //Identificador único de la vulnerabilidad
    private String nombre; //Nombre o descripción breve de la vulnerabilidad
    private String descripcion; //Descripción detallada de la vulnerabilidad

    public Vulnerabilidad(int id, String nombre) { // Constructor de la clase Vulnerabilidad
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}