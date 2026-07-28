package EscapeHouse;

import java.io.BufferedReader;
import java.io.FileReader;

public class SistemaEscapeHouse {

    private Plano plano;
    private ArbolAVL habitaciones;
    private Diccionario equipos;
    private MapeoAMuchos desafiosResueltos;

    public SistemaEscapeHouse() {
        this.plano = new Plano();
        this.habitaciones = new ArbolAVL();
        this.equipos = new Diccionario();
        this.desafiosResueltos = new MapeoAMuchos();
    }

    public Plano getPlano() {
        return this.plano;
    }

    public ArbolAVL getHabitaciones() {
        return this.habitaciones;
    }

    public Diccionario getEquipos() {
        return this.equipos;
    }

    public MapeoAMuchos getDesafiosResueltos() {
        return this.desafiosResueltos;
    }

    // carga de datos
    public void cargarDatosDesdeArchivo(String rutaArchivo) {
        System.out.println("Cargando datos desde: " + rutaArchivo + "...");

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {

                // para separar
                // si viene H ; codigo ; nombre ; planta ; m2 ; tieneSalida
                String[] datos = linea.split(";");
                String tipo = datos[0];

                switch (tipo) {
                    case "H":
                        Habitacion hab = new Habitacion(datos[1].trim(), datos[2].trim(),
                                Integer.parseInt(datos[3].trim()), Double.parseDouble(datos[4].trim()),
                                Boolean.parseBoolean(datos[5].trim()));
                        this.altaHabitacion(hab);
                        break;

                    case "P":
                        this.altaPuerta(datos[1].trim(), datos[2].trim(), Integer.parseInt(datos[3].trim()));
                        break;

                    case "D":
                        Desafio des = new Desafio(Integer.parseInt(datos[1].trim()), datos[3].trim(), datos[4].trim());
                        this.altaDesafio(datos[2].trim(), des);
                        break;

                    case "E":
                        Equipo eq = new Equipo(datos[1].trim(), Integer.parseInt(datos[2].trim()),
                                Integer.parseInt(datos[3].trim()), datos[4].trim(),
                                Integer.parseInt(datos[5].trim()));
                        this.altaEquipo(eq);
                        break;
                }
            }
            System.out.println("¡Datos cargados con éxito!");

        } catch (Exception e) {
            System.out.println("Ocurrió un error al leer el archivo de texto: " + e.getMessage());
        }
    }

    // METODOS SOBRE HABITACIONES

    // ABM
    public boolean altaHabitacion(Habitacion hab) {
        boolean exito = false;
        if (hab.esIntermedia()) {
            exito = this.habitaciones.insertar(hab);
            if (exito) {
                this.plano.insertarHabitacion(hab.getCodigo());
            }
        }
        return exito;
    }

    public boolean bajaHabitacion(String codigo) {
        boolean exito = false;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigo));
        if (hab != null && hab.esIntermedia()) {
            exito = this.habitaciones.eliminar(hab);
            if (exito) {
                this.plano.eliminarHabitacion(hab.getCodigo());
            }
        }

        return exito;
    }

    public boolean modificarHabitacion(String codigo, String nombre, int planta, double metrosCuadrados) {
        boolean exito = false;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigo));
        if (hab != null && hab.esIntermedia()) {
            hab.setNombre(nombre);
            hab.setPlanta(planta);
            hab.setMetrosCuadrados(metrosCuadrados);
            exito = true;
        }
        return exito;
    }

    // CONSULTAS

    public String mostrarHabitacion(String codigoHabitacion) {
        String s = "";
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigoHabitacion));
        if (hab != null) {
            s = hab.toString();
        } else {
            s = "La habitación no existe";
        }

        return s;
    }

    public String habitacionesContiguas(String codigoHabitacion) {
        return this.plano.habitacionesContiguas(codigoHabitacion);
    }

    public String esPosibleLlegar(String hab1, String hab2, int k) {
        String s;
        boolean exito = this.plano.esPosibleLlegar(hab1, hab2, k);
        if (exito) {
            s = "Si es posible";
        } else {
            s = "No es posible";
        }
        return s;
    }

    public String minimoPuntaje(String hab1, String hab2) {
        String s;
        int[] puntaje = { 0 };
        Lista caminoMinimo = this.plano.minimoPuntaje(hab1, hab2, puntaje);

        if (!caminoMinimo.esVacia()) {
            s = "Para llegar de " + hab1 + " a " + hab2 + " con " + puntaje[0]
                    + " puntos, debería hacer el camino: " + caminoMinimo.toString();
        } else {
            s = "La/s habitación/es no existe/n o no hay camino posible";
        }
        return s;
    }

    public String sinPasarPor(String hab1, String hab2, String prohibido, int p) {
        String s = "";
        Lista caminos = this.plano.sinPasarPor(hab1, hab2, prohibido, p);
        if (caminos.esVacia()) {
            s = "No existen caminos entre las habitaciones que cumplan las condiciones.";
        } else {
            int longitud = caminos.longitud();
            for (int i = 1; i <= longitud; i++) {
                s += "Camino " + i + ": " + ((Lista) caminos.recuperar(i)) + "\n";
            }
        }
        return s;
    }

    // METODOS SOBRE DESAFÍOS

    public boolean altaDesafio(String codigoHabitacion, Desafio desafio) {
        boolean exito = false;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigoHabitacion));
        if (hab != null) {
            exito = hab.getDesafios().insertar(desafio);
        }
        return exito;
    }

    public boolean bajaDesafio(String codigoHabitacion, int puntaje) {
        boolean exito = false;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigoHabitacion));
        if (hab != null) {
            exito = hab.getDesafios().eliminar(new Desafio(puntaje));
        }
        return exito;
    }

    public boolean modificarDesafio(String codigoHabitacion, int puntaje, String nombre, String tipo) {
        boolean exito = false;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigoHabitacion));

        if (hab != null) {
            Desafio des = (Desafio) hab.getDesafios().obtenerDato(new Desafio(puntaje));

            if (des != null) {
                des.setNombre(nombre);
                des.setTipo(tipo);
                exito = true;
            }

        }

        return exito;
    }

    // METODOS SOBRE EQUIPOS

    public boolean altaEquipo(Equipo equipo) {
        boolean exito = false;
        exito = this.equipos.insertar(equipo.getNombre(), equipo);
        return exito;
    }

    public boolean bajaEquipo(String nombreEquipo) {
        boolean exito = false;
        exito = this.equipos.eliminar(nombreEquipo);
        return exito;
    }

    public boolean modificarEquipo(String nombreEquipo, int puntajeNecesario, String habitacionActual,
            int puntajeTotal, int puntajeHabitacion) {
        boolean exito = false;
        Equipo eq = (Equipo) this.equipos.obtenerInformacion(nombreEquipo);
        if (eq != null) {
            eq.setPuntajeNecesario(puntajeNecesario);
            eq.setPuntajeTotal(puntajeTotal);
            eq.setHabitacionActual(habitacionActual);
            eq.setPuntajeHabitacion(puntajeHabitacion);
            exito = true;
        }
        return exito;
    }

    // puerta - conexion del plano

    public boolean altaPuerta(String habitacion1, String habitacion2, int puntajeExigido) {
        // entro exito
        // la vuelta exito 2
        boolean exito1 = this.plano.insertarConexion(habitacion1, habitacion2, puntajeExigido);
        boolean exito2 = this.plano.insertarConexion(habitacion2, habitacion1, puntajeExigido);
        return exito1 && exito2;
    }

    public boolean bajaPuerta(String habitacion1, String habitacion2) {
        boolean exito1 = this.plano.eliminarConexion(habitacion1, habitacion2);
        boolean exito2 = this.plano.eliminarConexion(habitacion2, habitacion1);
        return exito1 || exito2;
    }

    // 5.Consultas sobre equipos participantes:

    // mostrarInfoEquipo: Dado el nombre del equipo, mostrar todos sus datos.
    public String mostrarInfoEquipo(String nombre) {
        String info = "";
        // obt info de diccionario
        Equipo unEquipo = (Equipo) this.equipos.obtenerInformacion(nombre);
        if (unEquipo != null) {
            info = unEquipo.toString();
        } else {
            info = " Equipo no encontrado";
        }

        return info;
    }

    /*
     * posiblesDesafios: Dado un equipo y una habitación hab, en caso en que hab sea
     * adyacente al lugar donde esté ubicado el equipo, mostrar todos los desafíos
     * que podría
     * resolver el equipo para pasar a hab resolviendo un solo desafío. En caso en
     * que hab no sea
     * adyacente, mostrar un mensaje aclaratorio.
     * 
     */
    public String posiblesDesafios(String nombreE, String habDest) {
        String resultado = "No existe el equipo";
        Equipo eq = (Equipo) this.equipos.obtenerInformacion(nombreE);

        if (eq != null) {
            String habAct = (String) eq.getHabitacionActual();

        }

        return resultado;
    }

    /*
     * 
     * 
     * jugarDesafío: Dado un equipo, una habitación y un desafío, marcar el desafío
     * como ganado
     * y actualizar los datos del equipo apropiadamente.
     * 
     */
    public boolean jugarDesafio(String nombreEquipo, String codHabitacion, int puntajeDesafio) {
        boolean exito = false;

        Equipo unEquipo = (Equipo) this.equipos.obtenerInformacion(nombreEquipo);
        Habitacion habitacion = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codHabitacion));

        boolean existenDatos = (unEquipo != null && habitacion != null);
        boolean estaEnLaHabitacionCorrecta = existenDatos && unEquipo.getHabitacionActual().equals(codHabitacion);

        if (estaEnLaHabitacionCorrecta) {

            Desafio Aux = new Desafio(puntajeDesafio, "", "");
            Desafio desafioActual = (Desafio) habitacion.getDesafios().obtenerDato(Aux);

            if (desafioActual != null) {

                String idDesafio = codHabitacion + "y" + puntajeDesafio;
                boolean esPrimerIntento = this.desafiosResueltos.asociar(nombreEquipo, idDesafio);

                // Si el Hash asocia entonces puntaje al equipo
                if (esPrimerIntento) {
                    unEquipo.setPuntajeTotal(unEquipo.getPuntajeTotal() + puntajeDesafio);
                    unEquipo.setPuntajeHabitacion(unEquipo.getPuntajeHabitacion() + puntajeDesafio);
                    exito = true;
                }
            }
        }

        return exito;
    }

    // 6. Mostrar sistema

    public String mostrarSistema() {

        String sistema = "";

        sistema = "Plano (grafo)" + this.plano.toString() + "\n" +
                "Habitaciones (avl) " + this.habitaciones.toString() + "\n" +
                "Equipos (diccionario/ cambiar nombre) " + this.equipos.toString() + "\n" +
                "Desafios resueltos (mapeo a muchos) cambiar name " + this.desafiosResueltos.toString();

        return sistema;
    }
}