package sistemagestionvulnerabilidades;

import java.time.LocalDateTime; // Importación para manejar fechas y horas

import sistemagestionvulnerabilidades.dispositivos.Dispositivo;
import sistemagestionvulnerabilidades.auditoria.Auditable;

public class OcurrenciaVulnerabilidad implements Auditable {  // Clase que representa una ocurrencia de vulnerabilidad en un dispositivo
    private int id;
    private String descripcion;
    private Vulnerabilidad vulnerabilidad;
    private Dispositivo dispositivo;
    private String criticidad;
    private LocalDateTime momentoDeteccion;

    public OcurrenciaVulnerabilidad(int id, Vulnerabilidad vulnerabilidad, Dispositivo dispositivo, String criticidad, LocalDateTime momentoDeteccion) { // Constructor de la clase
        this.id = id;
        this.vulnerabilidad = vulnerabilidad;
        this.dispositivo = dispositivo;
        this.criticidad = criticidad;
        this.momentoDeteccion = momentoDeteccion;
    }

    public int getId() { // Getter para el ID
        return id;
    }

    public String getDescripcion() { // Getter para la descripción
        return descripcion;
    }

    public void setDescripcion(String descripcion) { // Setter para la descripción
        this.descripcion = descripcion;
    }
    
    public Vulnerabilidad getVulnerabilidad() { // Getter para la vulnerabilidad en la que se basa esta ocurrencia
        return vulnerabilidad;
    }

    public Dispositivo getDispositivo() { // Getter para el dispositivo afectado por esta ocurrencia
        return dispositivo;
    }

    public String getCriticidad() { // Getter para la criticidad de la ocurrencia
        return criticidad;
    }

    public LocalDateTime getMomentoDeteccion() { // Getter para el momento de detección
        return momentoDeteccion;
    }

    @Override
    public String generarAuditoriaCompleta() {
        return String.format(
            "[Ocurrencia ID:%s] Vul:'%s' | Dispositivo:%s (%s) | Criticidad:%s | Fecha:%s | Desc:%s",
            getId(),
            getVulnerabilidad().getNombre(),
            getDispositivo().getNombre(),
            getDispositivo().getClass().getSimpleName(),
            getCriticidad(),
            getMomentoDeteccion().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")),
            getDescripcion() != null ? getDescripcion() : "Sin descripción"
        );
    }

    public boolean esDuplicada(OcurrenciaVulnerabilidad otra) { // Método para verificar si esta ocurrencia es duplicada de otra
        return this.vulnerabilidad.getId() == otra.getVulnerabilidad().getId() &&
               this.dispositivo.getId().equals(otra.getDispositivo().getId());
    }
}