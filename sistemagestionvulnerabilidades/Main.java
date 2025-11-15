package sistemagestionvulnerabilidades;

import sistemagestionvulnerabilidades.dispositivos.Dispositivo;
import sistemagestionvulnerabilidades.dispositivos.Endpoint;
import sistemagestionvulnerabilidades.dispositivos.Impresora;
import sistemagestionvulnerabilidades.dispositivos.Router;
import sistemagestionvulnerabilidades.dispositivos.Servidor;
import sistemagestionvulnerabilidades.auditoria.Auditable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main { // Clase principal del sistema de gestión de vulnerabilidades
    private static List<Vulnerabilidad> vulnerabilidades = new ArrayList<>();
    private static List<Dispositivo> dispositivos = new ArrayList<>();
    private static List<OcurrenciaVulnerabilidad> ocurrencias = new ArrayList<>();
    private static List<Accion> acciones = new ArrayList<>();
    private static int idOcurrencia = 1;
    private static int idAccion = 1;
    private static int idVulnerabilidad = 1;

    public static void main(String[] args) { // Método principal
        Scanner scanner = new Scanner(System.in);

        System.out.println("¿Desea cargar los datos de demostración? (S/N)");
        String respuesta = scanner.nextLine().trim().toUpperCase();
        while (!respuesta.equals("S") && !respuesta.equals("N")) {
            System.out.println("Por favor, ingrese 'S' para sí o 'N' para no:");
            respuesta = scanner.nextLine().trim().toUpperCase();
        }
        if (respuesta.equals("S")) {
            cargarDatosDemo();
            System.out.println("Datos de demostración cargados exitosamente.");
        } else {
            System.out.println("No se cargaron datos de demostración.");
        }

        boolean salir = false;

        while (!salir) {
            mostrarPantallaPrincipal();
            int opcion = mostrarMenu(scanner);

            switch (opcion) {
                case 1:
                    cargarVulnerabilidad(scanner);
                    break;
                case 2:
                    cargarOcurrencia(scanner);
                    break;
                case 3:
                    cargarAccion(scanner);
                    break;
                case 4:
                    generarInformeCompleto(scanner);
                    break;
                case 5:
                    generarAuditoriaCompleta(scanner);
                    break;
                case 6:
                    salir = true;
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
        scanner.close();
    }

    private static void mostrarPantallaPrincipal() { // Método para mostrar la pantalla principal
        System.out.println("=== Sistema de Gestión de Vulnerabilidades ===");
        System.out.println("Lista de Vulnerabilidades:");
        if (vulnerabilidades.isEmpty()) {
            System.out.println("  (No hay vulnerabilidades registradas)");
        } else {
            for (Vulnerabilidad v : vulnerabilidades) {
                System.out.println("  ID: " + v.getId() + " | Nombre: " + v.getNombre());
            }
        }
        System.out.println("=====================================");
    }

    private static int mostrarMenu(Scanner scanner) { // Método para mostrar el menú de opciones y obtener la selección del usuario
        String[] opciones = {
            "1. Cargar una nueva vulnerabilidad",
            "2. Cargar una nueva ocurrencia de vulnerabilidad",
            "3. Cargar una nueva acción",
            "4. Generar informe completo",
            "5. Generar informe de auditoria",
            "6. Salir",
        };
        int seleccion = 0;

        System.out.println("\nMenú de Opciones:");
        for (String opcion : opciones) {
            System.out.println(opcion);
        }
        System.out.print("Seleccione una opción (1-6): ");
        try {
            seleccion = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // limpia el buffer
            System.out.println("Entrada inválida. Por favor, ingrese un número.");
            return 0;
        }

        return seleccion;
    }

    private static void cargarVulnerabilidad(Scanner scanner) { // Método para cargar una nueva vulnerabilidad
        System.out.print("Ingrese el nombre de la vulnerabilidad: ");
        String nombre = scanner.nextLine();
        Vulnerabilidad vulnerabilidad = new Vulnerabilidad(idVulnerabilidad++, nombre);
        System.out.print("Ingrese la descripción detallada: ");
        String descripcion = scanner.nextLine();
        vulnerabilidad.setDescripcion(descripcion);
        vulnerabilidades.add(vulnerabilidad);
        System.out.println("Vulnerabilidad cargada exitosamente con ID: " + vulnerabilidad.getId());
        System.out.println("Presione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    private static void cargarOcurrencia(Scanner scanner) { // Método para cargar una nueva ocurrencia de vulnerabilidad
        if (vulnerabilidades.isEmpty()) {
            System.out.println("No hay vulnerabilidades registradas. Cargue una primero.");
            System.out.println("Presione Enter para volver al menú principal...");
            scanner.nextLine();
            return;
        }
        if (dispositivos.isEmpty()) {
            System.out.println("No hay dispositivos registrados. Vamos a cargar uno.");
            cargarDispositivo(scanner);
        }

        System.out.println("Vulnerabilidades disponibles:");
        for (Vulnerabilidad v : vulnerabilidades) {
            System.out.println(v.getId() + ": " + v.getNombre());
        }
        System.out.print("Seleccione el ID de la vulnerabilidad: ");
        int idVul;
        try {
            idVul = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("ID inválido. Volviendo al menú principal...");
            System.out.println("Presione Enter para continuar...");
            scanner.nextLine();
            return;
        }
        Vulnerabilidad vulnerabilidad = buscarVulnerabilidad(idVul);
        if (vulnerabilidad == null) {
            System.out.println("Vulnerabilidad no encontrada.");
            System.out.println("Presione Enter para volver al menú principal...");
            scanner.nextLine();
            return;
        }

        System.out.println("Dispositivos disponibles:");
        for (Dispositivo d : dispositivos) {
            System.out.println(d.getId() + ": " + d.getNombre() + " (" + d.getDetallesEspecificos() + ")");
        }
        System.out.print("Seleccione el ID del dispositivo (o ingrese 'nuevo' para crear uno): ");
        String inputDis = scanner.nextLine();
        Dispositivo dispositivo;
        if (inputDis.equalsIgnoreCase("nuevo")) {
            dispositivo = cargarDispositivo(scanner);
        } else {
            dispositivo = buscarDispositivo(inputDis);
            if (dispositivo == null) {
                System.out.println("Dispositivo no encontrado.");
                System.out.println("Presione Enter para volver al menú principal...");
                scanner.nextLine();
                return;
            }
        }

        System.out.print("Ingrese la criticidad (Alta/Media/Baja): ");
        String criticidad = scanner.nextLine();
        LocalDateTime momento = LocalDateTime.now();
        OcurrenciaVulnerabilidad ocurrencia = new OcurrenciaVulnerabilidad(idOcurrencia++, vulnerabilidad, dispositivo, criticidad, momento);
        System.out.print("Ingrese la descripción de la ocurrencia: ");
        String descripcion = scanner.nextLine();
        ocurrencia.setDescripcion(descripcion);
        ocurrencias.add(ocurrencia);
        System.out.println("Ocurrencia cargada exitosamente con ID: " + ocurrencia.getId());
        System.out.println("Presione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    private static void cargarAccion(Scanner scanner) { // Método para cargar una nueva acción
        if (ocurrencias.isEmpty()) {
            System.out.println("No hay ocurrencias registradas. Cargue una primero.");
            System.out.println("Presione Enter para volver al menú principal...");
            scanner.nextLine();
            return;
        }

        System.out.print("Ingrese el tipo de acción (ej: parche, actualización): ");
        String tipo = scanner.nextLine();
        System.out.print("Ingrese el responsable: ");
        String responsable = scanner.nextLine();
        System.out.print("Ingrese la descripción de la acción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese el estado inicial (Pendiente/En progreso/Completada): ");
        String estado = scanner.nextLine();
        Accion accion = new Accion(idAccion++, tipo, responsable, descripcion, estado);

        System.out.println("Ocurrencias disponibles para asociar:");
        for (OcurrenciaVulnerabilidad o : ocurrencias) {
            System.out.println(o.getId() + ": " + o.getVulnerabilidad().getNombre() + " en " +
                               o.getDispositivo().getNombre() + " (" + o.getCriticidad() + ")");
        }
        System.out.print("Ingrese los IDs de ocurrencias a asociar (separados por coma, o 'ninguna'): ");
        String idsOcurr = scanner.nextLine();
        if (!idsOcurr.equalsIgnoreCase("ninguna")) {
            String[] ids = idsOcurr.split(",");
            for (String idStr : ids) {
                try {
                    int idOcc = Integer.parseInt(idStr.trim());
                    OcurrenciaVulnerabilidad occ = buscarOcurrencia(idOcc);
                    if (occ != null) {
                        accion.addOcurrenciaVulnerabilidad(occ);
                    } else {
                        System.out.println("Ocurrencia con ID " + idOcc + " no encontrada. Saltando...");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ID inválido: " + idStr + ". Saltando...");
                }
            }
        }
        acciones.add(accion);
        System.out.println("Acción cargada exitosamente con ID: " + accion.getId());
        System.out.println("Presione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    private static void generarInformeCompleto(Scanner scanner) { // Método para generar el informe completo y mostrarlo por pantalla
        System.out.println("\n=================================================================");
        System.out.println("Informe Completo del Sistema de Gestión de Vulnerabilidades");
        System.out.println("=================================================================\n");

        System.out.println("\n1. Vulnerabilidades Ordenadas por Cantidad de Ocurrencias (Más Frecuentes Primero):");
        if (vulnerabilidades.isEmpty()) {
            System.out.println("  (No hay vulnerabilidades registradas)");
        } else {
            Map<Vulnerabilidad, Integer> conteoVul = new HashMap<>();
            for (Vulnerabilidad vul : vulnerabilidades) {
                conteoVul.put(vul, 0);
            }
            for (OcurrenciaVulnerabilidad occ : ocurrencias) {
                Vulnerabilidad vul = occ.getVulnerabilidad();
                conteoVul.put(vul, conteoVul.getOrDefault(vul, 0) + 1);
            }
            List<Vulnerabilidad> vulOrdenadas = new ArrayList<>(vulnerabilidades);
            vulOrdenadas.sort(Comparator.comparingInt((Vulnerabilidad v) -> conteoVul.get(v)).reversed());
            for (Vulnerabilidad vul : vulOrdenadas) {
                System.out.println("  - ID: " + vul.getId() + " | Nombre: " + vul.getNombre() +
                                   " | Ocurrencias: " + conteoVul.get(vul));
            }
        }

        System.out.println("\n2. Dispositivos Ordenados por Cantidad de Ocurrencias (Más Vulnerables Primero):");
        if (dispositivos.isEmpty()) {
            System.out.println("  (No hay dispositivos registrados)");
        } else {
            Map<Dispositivo, Integer> conteoDis = new HashMap<>();
            for (Dispositivo dis : dispositivos) {
                conteoDis.put(dis, 0);
            }
            for (OcurrenciaVulnerabilidad occ : ocurrencias) {
                Dispositivo dis = occ.getDispositivo();
                conteoDis.put(dis, conteoDis.getOrDefault(dis, 0) + 1);
            }

            List<Dispositivo> disOrdenados = new ArrayList<>(dispositivos);
            disOrdenados.sort(Comparator.comparingInt((Dispositivo d) -> conteoDis.get(d)).reversed());

            for (Dispositivo dis : disOrdenados) {
                System.out.println("  - " + dis); // Usa toString() con getDetallesEspecificos
            }
        }

        System.out.println("=================================================================\n");

        System.out.println("Presione Enter para volver al menú principal...");
        scanner.nextLine();
    }

    private static void cargarDatosDemo() { // Método para cargar datos de demostración 
        Vulnerabilidad vul1 = new Vulnerabilidad(idVulnerabilidad++, "Inyección SQL");
        vul1.setDescripcion("Permite a un atacante ejecutar consultas SQL arbitrarias...");
        vulnerabilidades.add(vul1);

        Vulnerabilidad vul2 = new Vulnerabilidad(idVulnerabilidad++, "Cross-Site Scripting (XSS)");
        vul2.setDescripcion("Permite a un atacante inyectar scripts maliciosos...");
        vulnerabilidades.add(vul2);

        Vulnerabilidad vul3 = new Vulnerabilidad(idVulnerabilidad++, "Software Desactualizado");
        vul3.setDescripcion("Uso de versiones de software con vulnerabilidades conocidas...");
        vulnerabilidades.add(vul3);

        Vulnerabilidad vul4 = new Vulnerabilidad(idVulnerabilidad++, "Configuración Insegura");
        vul4.setDescripcion("Configuraciones predeterminadas o débiles...");
        vulnerabilidades.add(vul4);

        Vulnerabilidad vul5 = new Vulnerabilidad(idVulnerabilidad++, "Apache Struts < 2.3.35 Vulnerabilidad");
        vul5.setDescripcion("Vulnerabilidad en Apache Struts que permite ejecución remota...");
        vulnerabilidades.add(vul5);

        Vulnerabilidad vul6 = new Vulnerabilidad(idVulnerabilidad++, "OpenSSL < 1.1.1k Heartbleed");
        vul6.setDescripcion("Fallo en OpenSSL que permite a atacantes leer memoria...");
        vulnerabilidades.add(vul6);

        Vulnerabilidad vul7 = new Vulnerabilidad(idVulnerabilidad++, "SMBv1 EternalBlue");
        vul7.setDescripcion("Vulnerabilidad en SMBv1 explotada por EternalBlue...");
        vulnerabilidades.add(vul7);

        Vulnerabilidad vul8 = new Vulnerabilidad(idVulnerabilidad++, "Log4j < 2.16.0 RCE");
        vul8.setDescripcion("Vulnerabilidad en Apache Log4j que permite ejecución remota...");
        vulnerabilidades.add(vul8);

        Vulnerabilidad vul9 = new Vulnerabilidad(idVulnerabilidad++, "PHP < 7.4.3 RCE");
        vul9.setDescripcion("Vulnerabilidad en versiones antiguas de PHP...");
        vulnerabilidades.add(vul9);

        Vulnerabilidad vul10 = new Vulnerabilidad(idVulnerabilidad++, "WordPress < 5.7.2 Escalamiento de Privilegios");
        vul10.setDescripcion("Fallo en WordPress que permite a usuarios con roles bajos...");
        vulnerabilidades.add(vul10);

        dispositivos.add(new Servidor("Servidor Web Principal", "Ubuntu 20.04"));
        dispositivos.add(new Endpoint("Estación de Trabajo Finanzas", "Juan Perez"));
        dispositivos.add(new Router("Router Perimetral", 8));
        dispositivos.add(new Servidor("Servidor de Base de Datos", "CentOS 7"));
        dispositivos.add(new Servidor("Servidor de Aplicaciones CRM", "Windows Server 2019"));
        dispositivos.add(new Endpoint("Estación de Trabajo RRHH", "Ana Gomez"));
        dispositivos.add(new Router("Firewall Central", 4));
        dispositivos.add(new Servidor("Servidor de Correo", "Ubuntu 18.04"));
        dispositivos.add(new Router("Switch de Red Core", 24));
        dispositivos.add(new Endpoint("Estación de Trabajo Desarrollo", "Carlos Lopez"));
        dispositivos.add(new Servidor("Servidor de Backups", "Red Hat 8"));
        dispositivos.add(new Router("Access Point Corporativo", 2));
        dispositivos.add(new Servidor("Servidor de Archivos", "Debian 10"));
        dispositivos.add(new Endpoint("Estación de Trabajo Gerencia", "Maria Torres"));

        OcurrenciaVulnerabilidad occ1 = new OcurrenciaVulnerabilidad(idOcurrencia++, vul1, dispositivos.get(0), "Alta", LocalDateTime.now());
        occ1.setDescripcion("Inyección SQL detectada en el formulario de login...");
        ocurrencias.add(occ1);

        OcurrenciaVulnerabilidad occ2 = new OcurrenciaVulnerabilidad(idOcurrencia++, vul2, dispositivos.get(1), "Media", LocalDateTime.now());
        occ2.setDescripcion("XSS encontrado en el portal interno...");
        ocurrencias.add(occ2);

        OcurrenciaVulnerabilidad occ3 = new OcurrenciaVulnerabilidad(idOcurrencia++, vul5, dispositivos.get(4), "Crítica", LocalDateTime.now());
        occ3.setDescripcion("Apache Struts vulnerable detectado...");
        ocurrencias.add(occ3);

        OcurrenciaVulnerabilidad occ4 = new OcurrenciaVulnerabilidad(idOcurrencia++, vul6, dispositivos.get(3), "Alta", LocalDateTime.now());
        occ4.setDescripcion("OpenSSL Heartbleed detectado...");
        ocurrencias.add(occ4);

        OcurrenciaVulnerabilidad occ5 = new OcurrenciaVulnerabilidad(idOcurrencia++, vul7, dispositivos.get(5), "Crítica", LocalDateTime.now());
        occ5.setDescripcion("EternalBlue explotable...");
        ocurrencias.add(occ5);

        OcurrenciaVulnerabilidad occ6 = new OcurrenciaVulnerabilidad(idOcurrencia++, vul8, dispositivos.get(6), "Crítica", LocalDateTime.now());
        occ6.setDescripcion("Log4j vulnerable en el Firewall...");
        ocurrencias.add(occ6);

        OcurrenciaVulnerabilidad occ7 = new OcurrenciaVulnerabilidad(idOcurrencia++, vul9, dispositivos.get(7), "Alta", LocalDateTime.now());
        occ7.setDescripcion("PHP vulnerable detectado...");
        ocurrencias.add(occ7);

        OcurrenciaVulnerabilidad occ8 = new OcurrenciaVulnerabilidad(idOcurrencia++, vul10, dispositivos.get(0), "Media", LocalDateTime.now());
        occ8.setDescripcion("Escalamiento de privilegios en WordPress...");
        ocurrencias.add(occ8);

        Accion accion1 = new Accion(idAccion++, "Parche", "Equipo de Seguridad",
            "Aplicar parches de seguridad para Apache Struts y Log4j", "Pendiente");
        accion1.addOcurrenciaVulnerabilidad(occ3);
        accion1.addOcurrenciaVulnerabilidad(occ6);
        acciones.add(accion1);

        Accion accion2 = new Accion(idAccion++, "Actualización", "Equipo de Infraestructura",
            "Actualizar OpenSSL a versión 1.1.1k o superior", "En progreso");
        accion2.addOcurrenciaVulnerabilidad(occ4);
        acciones.add(accion2);

        Accion accion3 = new Accion(idAccion++, "Configuración", "Equipo de Redes",
            "Deshabilitar SMBv1 en estaciones de trabajo", "Pendiente");
        accion3.addOcurrenciaVulnerabilidad(occ5);
        acciones.add(accion3);
    }

    private static Vulnerabilidad buscarVulnerabilidad(int id) { // Método para buscar una vulnerabilidad por ID
        for (Vulnerabilidad v : vulnerabilidades) {
            if (v.getId() == id) {
                return v;
            }
        }
        return null;
    }

    private static Dispositivo buscarDispositivo(String id) { // Método para buscar un dispositivo por ID
        for (Dispositivo d : dispositivos) {
            if (d.getId().equals(id)) {
                return d;
            }
        }
        return null;
    }

    private static OcurrenciaVulnerabilidad buscarOcurrencia(int id) { // Método para buscar una ocurrencia por ID
        for (OcurrenciaVulnerabilidad o : ocurrencias) {
            if (o.getId() == id) {
                return o;
            }
        }
        return null;
    }

    private static void generarAuditoriaCompleta(Scanner scanner) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("           AUDITORÍA COMPLETA DEL SISTEMA");
        System.out.println("=".repeat(70));

        List<Auditable> auditables = new ArrayList<>();

        // Añadimos uno por uno
        for (OcurrenciaVulnerabilidad o : ocurrencias) {
            auditables.add(o);
        }
        for (Accion a : acciones) {
            auditables.add(a);
        }
        for (Dispositivo d : dispositivos) {
            auditables.add(d);
        }
        for (Vulnerabilidad v : vulnerabilidades) {
            auditables.add(v);
        }

        // Ordenamos por tipo de clase
        auditables.sort(Comparator.comparing(a -> a.getClass().getSimpleName()));

        // Imprimimos
        for (Auditable a : auditables) {
            System.out.println(a.generarAuditoriaCompleta());
        }

        System.out.println("=".repeat(70));
        System.out.println("Presione Enter para volver...");
        scanner.nextLine();
    }

    public static List<OcurrenciaVulnerabilidad> getOcurrencias() {
        return ocurrencias;
    }

    private static Dispositivo cargarDispositivo(Scanner scanner) { // Método para cargar un nuevo dispositivo
        System.out.print("Ingrese el nombre del dispositivo: ");
        String nombre = scanner.nextLine();
        System.out.println("Seleccione el tipo de dispositivo:");
        System.out.println("1. Servidor");
        System.out.println("2. Endpoint");
        System.out.println("3. Router");
        System.out.println("4. Impresora");
        System.out.print("Opción (1-4): ");
        int tipo;
        try {
            tipo = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Opción inválida. Usando Servidor por defecto.");
            tipo = 1;
        }

        Dispositivo dispositivo;
        switch (tipo) {
            case 1:
                System.out.print("Ingrese el sistema operativo (ej: Ubuntu 20.04): ");
                String sistemaOperativo = scanner.nextLine();
                dispositivo = new Servidor(nombre, sistemaOperativo);
                break;
            case 2:
                System.out.print("Ingrese el usuario asignado (ej: Juan Perez): ");
                String usuarioAsignado = scanner.nextLine();
                dispositivo = new Endpoint(nombre, usuarioAsignado);
                break;
            case 3:
                System.out.print("Ingrese el número de puertos (ej: 8): ");
                int numeroPuertos;
                try {
                    numeroPuertos = scanner.nextInt();
                    scanner.nextLine();
                } catch (Exception e) {
                    scanner.nextLine();
                    numeroPuertos = 4;
                    System.out.println("Entrada inválida. Usando 4 puertos por defecto.");
                }
                dispositivo = new Router(nombre, numeroPuertos);
                break;
            case 4:
                System.out.print("Ingrese el modelo de la impresora (ej: HP LaserJet): ");
                String modelo = scanner.nextLine();
                dispositivo = new Impresora(nombre, modelo);
                break;
            default:
                System.out.println("Opción inválida. Creando Servidor por defecto.");
                dispositivo = new Servidor(nombre, "Ubuntu 20.04");
                break;
        }
        dispositivos.add(dispositivo);
        System.out.println("Dispositivo cargado exitosamente con ID: " + dispositivo.getId());
        return dispositivo;
    }
}