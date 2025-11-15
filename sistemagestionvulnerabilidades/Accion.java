// Accion.java
// La clase Accion define a una acción correctiva en el sistema de gestión de vulnerabilidades. Cada acción es un intento de mitigar una o varias vulnerabilidades.

package sistemagestionvulnerabilidades;

import sistemagestionvulnerabilidades.auditoria.Auditable;
import java.util.ArrayList;
import java.util.List;

public class Accion implements Auditable {
    private int id; //Identificador único de la acción
    private String tipo; //Tipo de acción (por ejemplo, parche, actualización, configuración)
    private String responsable; //Persona o equipo responsable de llevar a cabo la acción
    private String descripcion; //Descripción detallada de la acción
    private String estado; //Estado actual de la acción (pendiente, en progreso, completada)
    private List<OcurrenciaVulnerabilidad> ocurrenciasVulnerabilidades; //Lista de ocurrencias de vulnerabilidades asociadas a esta acción

    public Accion(int id, String tipo, String responsable, String descripcion, String estado) { // Constructor de la clase Accion
        this.id = id;
        this.tipo = tipo;
        this.responsable = responsable;
        this.descripcion = descripcion;
        this.estado = estado;
        this.ocurrenciasVulnerabilidades = new ArrayList<>();
    }

    // Metodo para obtener el ID de la acción
    public int getId() {
        return id;
    }

    // Metodo para obtener el tipo de acción
    public String getTipo() {
        return tipo;
    }

    // Método para cambiar el tipo de acción
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Metodo para obtener el responsable de la acción
    public String getResponsable() {
        return responsable;
    }

    // Método para cambiar el responsable de la acción
    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    // Método para obtener la descripción de la acción
    public String getDescripcion() {
        return descripcion;
    }

    // Método para cambiar la descripción de la acción
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Método para obtener el estado de la acción
    public String getEstado() {
        return estado;
    }

    // Método para cambiar el estado de la acción
    public void setEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    // Método para listar las ocurrencias de vulnerabilidades asociadas a esta acción
    public List<OcurrenciaVulnerabilidad> getOcurrenciasVulnerabilidades() {
        return ocurrenciasVulnerabilidades;
    }

    // Métodos para agregar o eliminar ocurrencias de vulnerabilidades asociadas a esta acción
    public void addOcurrenciaVulnerabilidad(OcurrenciaVulnerabilidad ocurrencia) {
        this.ocurrenciasVulnerabilidades.add(ocurrencia);
    }
    public void eliminarOcurrenciaVulnerabilidad(OcurrenciaVulnerabilidad ocurrencia) {
        this.ocurrenciasVulnerabilidades.remove(ocurrencia);
    }

    @Override
    public String generarAuditoriaCompleta() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
            "[Acción ID:%s] Tipo:%s | Responsable:%s | Estado:%s | Ocurrencias asociadas: ",
            getId(), getTipo(), getResponsable(), getEstado()
        ));
        
        if (getOcurrenciasVulnerabilidades().isEmpty()) {
            sb.append("ninguna");
        } else {
            sb.append(getOcurrenciasVulnerabilidades().stream()
                .map(String::valueOf)
                .collect(java.util.stream.Collectors.joining(", ")));
        }
        
        return sb.toString();
    }
}