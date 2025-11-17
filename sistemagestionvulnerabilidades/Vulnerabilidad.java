// Vulnerabilidad.java
// La clase Vulnerabilidad representa una vulnerabilidad, que puede estar asociada a múltiples ocurrencias en diferentes dispositivos.

package sistemagestionvulnerabilidades;
import sistemagestionvulnerabilidades.auditoria.Auditable;

public class Vulnerabilidad implements Auditable {
    private int id; //Identificador único de la vulnerabilidad
    private String nombre; //Nombre o descripción breve de la vulnerabilidad
    private String descripcion; //Descripción detallada de la vulnerabilidad

    public Vulnerabilidad(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = ""; // por si no se establece luego
    }

    // Constructor adicional para el importador
    public Vulnerabilidad(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    @Override
    public String generarAuditoriaCompleta() {
        long count = Main.getOcurrencias().stream()
            .filter(o -> o.getVulnerabilidad().equals(this))
            .count();
        String desc = descripcion != null ? descripcion : "Sin descripción";
        return String.format(
            "[Vulnerabilidad ID:%d] Nombre: '%s' | Ocurrencias: %d | Desc: %s",
            id, nombre, count, desc
        );
    }

}