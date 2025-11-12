// Impresora.java
// La clase Impresora representa un dispositivo de tipo impresora en el sistema de gestión de vulnerabilidades.

package sistemagestionvulnerabilidades.dispositivos;

public class Impresora extends Dispositivo {
    private String modelo;

    public Impresora(String nombre, String modelo) {
        super(nombre);
        this.modelo = modelo;
    }

    @Override
    public String getDetallesEspecificos() {
        return "Tipo: Impresora, Modelo: " + modelo;
    }
}