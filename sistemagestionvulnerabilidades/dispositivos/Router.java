// Router.java
// La clase Router representa un dispositivo de red tipo router en el sistema de gestión de vulnerabilidades.

package sistemagestionvulnerabilidades.dispositivos;

public class Router extends Dispositivo {
    private int numeroPuertos;

    public Router(String nombre, int numeroPuertos) {
        super(nombre);
        this.numeroPuertos = numeroPuertos;
    }

    @Override
    public String getDetallesEspecificos() {
        return "Tipo: Router, Número de Puertos: " + numeroPuertos;
    }
}