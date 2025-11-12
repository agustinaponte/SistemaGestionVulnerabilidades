// Endpoint.java
// La clase Endpoint representa un dispositivo de tipo endpoint en el sistema de gestión de vulnerabilidades.

package sistemagestionvulnerabilidades.dispositivos;

public class Endpoint extends Dispositivo { 
    private String usuarioAsignado;

    public Endpoint(String nombre, String usuarioAsignado) {
        super(nombre);
        this.usuarioAsignado = usuarioAsignado;
    }

    @Override
    public String getDetallesEspecificos() {
        return "Tipo: Endpoint, Usuario Asignado: " + usuarioAsignado;
    }
}