package EscapeHouse;

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
        boolean exito = this.plano.esPosibleLlegar(hab1, hab2, k); // quizas en vez de boolean que retorne String con
                                                                   // los pasos mas la conclusion "mostrar si es o no
                                                                   // posible"
        if (exito) {
            s = "Si es posible";
        } else {
            s = "No es posible";
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

}
