// Dispositivo.java
// La clase Dispositivo representa un dispositivo en el sistema de gestión de vulnerabilidades.

package sistemagestionvulnerabilidades.dispositivos;

public abstract class Dispositivo { // Clase abstracta para representar un dispositivo genérico
    protected String id;
    protected String nombre;
    private static int contadorId = 1; // Atributo estático para garantizar que los IDs sean únicos

    public Dispositivo(String nombre) { // Constructor que asigna un ID único automáticamente
        this.id = "D" + contadorId++;
        this.nombre = nombre;
    }

    public String getId() { // Getter para el ID
        return id;
    }

    public String getNombre() { // Getter para el nombre
        return nombre;
    }

    public abstract String getDetallesEspecificos(); // Método abstracto para redefinición en subclases

    @Override
    public String toString() { // Método toString para representar el dispositivo como una cadena de texto
        return "Dispositivo [ID: " + id + ", Nombre: " + nombre + ", Detalles: " + getDetallesEspecificos() + "]";
    }
}