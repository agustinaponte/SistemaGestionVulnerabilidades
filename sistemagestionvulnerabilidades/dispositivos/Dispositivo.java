// Dispositivo.java
// La clase Dispositivo representa un dispositivo en el sistema de gestión de vulnerabilidades.

package sistemagestionvulnerabilidades.dispositivos;
import sistemagestionvulnerabilidades.auditoria.Auditable;
import sistemagestionvulnerabilidades.OcurrenciaVulnerabilidad;

import java.util.ArrayList;
import java.util.List;

public abstract class Dispositivo implements Auditable { // Clase abstracta para representar un dispositivo genérico
    protected String id;
    protected String nombre;
    private static int contadorId = 1; // Atributo estático para garantizar que los IDs sean únicos
    private List<OcurrenciaVulnerabilidad> ocurrencias = new ArrayList<>();

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

    public List<OcurrenciaVulnerabilidad> getOcurrencias() {
        return ocurrencias;
    }

    public void addOcurrencia(OcurrenciaVulnerabilidad o) {
        ocurrencias.add(o);
    }

    @Override
    public String toString() { // Método toString para representar el dispositivo como una cadena de texto
        return "Dispositivo [ID: " + id + ", Nombre: " + nombre + ", Detalles: " + getDetallesEspecificos() + "]";
    }

    @Override
        public String generarAuditoriaCompleta() {
            long vulnerabilidadesCount = ocurrencias.stream()
                .filter(o -> o.getDispositivo().equals(this))
                .count();
                
            return String.format(
                "[Dispositivo %s] %s | Tipo: %s | %s | Total vulnerabilidades: %d",
                getId(), getNombre(), getClass().getSimpleName(), getDetallesEspecificos(), vulnerabilidadesCount
            );
        }
}