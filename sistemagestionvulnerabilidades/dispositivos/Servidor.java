package sistemagestionvulnerabilidades.dispositivos;

public class Servidor extends Dispositivo { // Clase que representa un Servidor, que es un tipo específico de Dispositivo
    private String sistemaOperativo; // Atributo específico del Servidor

    public Servidor(String nombre, String sistemaOperativo) { // Constructor que inicializa el nombre y el sistema operativo
        super(nombre);
        this.sistemaOperativo = sistemaOperativo;
    }

    @Override
    public String getDetallesEspecificos() { // Implementación del método abstracto para proporcionar detalles específicos del Servidor
        return "Tipo: Servidor, Sistema Operativo: " + sistemaOperativo;
    }
}