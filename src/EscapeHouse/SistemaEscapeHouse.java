package EscapeHouse;

import estructuras.conjuntistas.*;
import estructuras.grafos.*;
import estructuras.lineales.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SistemaEscapeHouse {

    private Grafo plano;
    private ArbolAVL habitaciones;
    private Diccionario equipos;
    private MapeoAMuchos desafiosResueltos;
    private String rutaLog = "datianos\\log.txt";

    public SistemaEscapeHouse() {
        this.plano = new Grafo();
        this.habitaciones = new ArbolAVL();
        this.equipos = new Diccionario();
        this.desafiosResueltos = new MapeoAMuchos();
    }

    public Grafo getPlano() {
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
        //banderita
        System.out.println("Cargando datos desde: " + rutaArchivo);

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {

                // para separar
                
                String[] datos = linea.split(";");
                String tipo = datos[0];

                switch (tipo) {
                    case "H":

                        // si viene H ; codigo ; nombre ; planta ; m2 ; tieneSalida ; esEntrada
                        // H;1;Comedor;0;20;false;false

                        Habitacion hab = new Habitacion(datos[1].trim(), datos[2].trim(),
                                Integer.parseInt(datos[3].trim()), Double.parseDouble(datos[4].trim()),
                                Boolean.parseBoolean(datos[5].trim()), Boolean.parseBoolean(datos[6].trim()));
                        this.cargarHabitacion(hab);
                        break;

                    case "P":

                        // Puerta: hab1, hab2, puntaje requerido (peso)
                        // P;1;2;40

                        this.altaPuerta(datos[1].trim(), datos[2].trim(), Integer.parseInt(datos[3].trim()));
                        break;

                    case "D":

                        // Desafío: puntaje que otorga, código de habitación, nombre y tipo     
                        // D;30;1;Acertijo;Lógico

                        Desafio des = new Desafio(Integer.parseInt(datos[1].trim()), datos[3].trim(), datos[4].trim());
                        this.altaDesafio(datos[2].trim(), des);
                        break;

                    case "E":
                        
                       

                       

                        Equipo eq = new Equipo(datos[1].trim(), Integer.parseInt(datos[2].trim()),
                                Integer.parseInt(datos[3].trim()), datos[4].trim(),
                                Integer.parseInt(datos[5].trim()));
                        this.altaEquipo(eq);

                        // datos[6]  desafíos ya resueltos por el equipo,
                        // uno por desafioResuelto, formato "(codigoHabitacion,puntaje)" -- ej: (1,20);(1,50);(2,30)
                        
                        for (int i = 6; i < datos.length; i++) {
                            String desafioResuelto = datos[i].trim();
                            if (!desafioResuelto.isEmpty()) {
                                String contenido = desafioResuelto.substring(1, desafioResuelto.length() - 1); // saca "(" y ")"
                                String[] partesDesafioResuelto = contenido.split(",");
                                String codHabDesafio = partesDesafioResuelto[0].trim();
                                int puntajeDesafio = Integer.parseInt(partesDesafioResuelto[1].trim());
                                String idDesafio = "(" + codHabDesafio + "," + puntajeDesafio + ")";
                                this.desafiosResueltos.asociar(datos[1].trim(), idDesafio);
                            }
                        }

                        break;
                }
            }
            System.out.println("Datos cargados");

            //carga log al terminar la carga
            escribirLog("Sistema recien cargado");
            escribirLog(this.mostrarSistema());

        } catch (Exception e) {
            System.out.println("error al leer " + e.getMessage());
        }
    }

    //metodo archivo log prueba
    /*
    Utilizar un archivo de log (archivo de texto) para guardar la siguiente información: estado
        del sistema al momento de terminar la carga inicial, anotar qué operaciones de Altas y Bajas
    se realizan a lo largo de la ejecución (Ej: “Se crea la habitación 01”, “Se borró el desafío D1”,
    etc), y el estado del sistema completo al momento de terminar de ejecutarse.

     */
    public void crearLog() {
        try (PrintWriter out = new PrintWriter(new FileWriter(rutaLog, false))) {
            out.println("Log Creado");
        } catch (IOException e) {
            System.out.println("Error al crear el log." + e.getMessage());
        }
    }

    public void escribirLog(String msj) {
        try (PrintWriter out = new PrintWriter(new FileWriter(rutaLog, true))) {
            out.println(msj);
        } catch (IOException e) {
            System.out.println("Error al escribir en el log."+e.getMessage());
        }
    }

    // METODOS SOBRE HABITACIONES
    // ABM
    public boolean altaHabitacion(Habitacion hab) {
        boolean exito = false;
        if (hab.esIntermedia()) {
            exito = this.habitaciones.insertar(hab);
            if (exito) {
                this.plano.insertarVertice(hab.getCodigo());
                //escribir en el log
                escribirLog("Se creó la habitación " + hab.getCodigo());
            }
        }
        return exito;
    }

    /*
     * cargarHabitacion: inserta una habitación durante la carga inicial desde
     * archivo, sin la restricción de esIntermedia() que aplica al alta durante
     * el juego (la carga inicial necesita poder cargar también las
     * habitaciones de entrada y de salida, que forman parte del plano de la
     * casa desde el principio).
     */
    private boolean cargarHabitacion(Habitacion hab) {
        boolean exito = this.habitaciones.insertar(hab);
        if (exito) {
            this.plano.insertarVertice(hab.getCodigo());
            escribirLog("Se creó la habitación " + hab.getCodigo());
        }
        return exito;
    }

    public boolean bajaHabitacion(String codigo) {
        boolean exito = false;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigo));
        if (hab != null && hab.esIntermedia()) {
            exito = this.habitaciones.eliminar(hab);
            if (exito) {
                this.plano.eliminarVertice(hab.getCodigo());
                escribirLog("Se eliminó la habitación " + codigo);

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
        return this.plano.adyacentesDe(codigoHabitacion);
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
        int[] puntaje = {0};
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
    // ABM
    public boolean altaDesafio(String codigoHabitacion, Desafio desafio) {
        boolean exito = false;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigoHabitacion));
        if (hab != null) {
            exito = hab.getDesafios().insertar(desafio);
            escribirLog("Se creó desafio ");
        }
        return exito;
    }

    public boolean bajaDesafio(String codigoHabitacion, int puntaje) {
        boolean exito = false;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigoHabitacion));
        if (hab != null) {
            exito = hab.getDesafios().eliminar(new Desafio(puntaje));
            escribirLog("Se eliminó desafio");

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

    // Consultas

    /*
     * mostrarDesafío: Dado un código de desafío y un número de habitación,
     * mostrar toda su información.
     */
    public String mostrarDesafio(int codigoDesafio, String codigoHabitacion) {
        String s;
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigoHabitacion));
        if (hab != null) {
            Desafio des = (Desafio) hab.getDesafios().obtenerDato(new Desafio(codigoDesafio));
            if (des != null) {
                s = des.toString();
            } else {
                s = "La habitación existe, pero el desafío no.";
            }
        } else {
            s = "La habitación no existe.";
        }
        return s;
    }

    /*
     * mostrarDesafíosResueltos: Dado un equipo eq, mostrar todos los desafíos que
     * ya resolvieron
     */
    public String mostrarDesafiosResueltos(String nombreEquipo) {
        String s = "El equipo no existe.";
        Equipo eq = (Equipo) this.equipos.obtenerInformacion(nombreEquipo);
        if (eq != null) {
            Lista desafiosResueltos = this.desafiosResueltos.obtenerValores(nombreEquipo);
            if (desafiosResueltos.esVacia()) {
                s = "Sin desafíos resueltos.";
            } else {
                s = desafiosResueltos.toString();
            }
        }

        return s;
    }

    /*
     * verificarDesafíoResuelto: Dado un equipo, un desafío y una habitación,
     * indicar si el equipo ya lo resolvió
     */
    public String verificarDesafioResuelto(String nombreEquipo, String codigoHabitacion, int puntajeDesafio) {
        String s = "El equipo, la habitación o el desafío no existen.";

        Equipo eq = (Equipo) this.equipos.obtenerInformacion(nombreEquipo);
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codigoHabitacion));

        if (eq != null && hab != null) {
            Desafio des = (Desafio) hab.getDesafios().obtenerDato(new Desafio(puntajeDesafio));

            if (des != null) {
                Lista desafios = this.desafiosResueltos.obtenerValores(nombreEquipo);
                String idDesafio = "(" + codigoHabitacion + "," + puntajeDesafio + ")";

                if (desafios.localizar(idDesafio) != -1) {
                    s = "El equipo " + nombreEquipo + " sí resolvió el desafío " + des.getNombre()
                            + " en la habitación " + codigoHabitacion + ".";
                } else {
                    s = "El equipo no resolvió ese desafío.";
                }
            }
        }

        return s;
    }

    /*
     * mostrarDesafíosTipo: Dada una habitación, dos puntajes a y b y un tipo de
     * desafío X, mostrar todos los desafíos de la habitación que sean de tipo X con
     * puntaje en
     * el rango [a, b] (por ejemplo, listar todos los desafíos de tipo lógico con
     * puntaje entre 30 y 55)
     */
    public String mostrarDesafiosTipo(String codHabitacion, int puntajeA, int puntajeB, String tipoDesafio) {
        String s = "";
        Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(codHabitacion));
        if (hab != null) {
            Lista desafiosEnRango = hab.getDesafios().listarRango(new Desafio(puntajeA), new Desafio(puntajeB));
            int longitud = desafiosEnRango.longitud();
            for (int i = 1; i <= longitud; i++) {
                Desafio elem = (Desafio) desafiosEnRango.recuperar(i);
                if (elem.getTipo().equals(tipoDesafio)) {
                    s += "Desafío (puntaje " + elem.getPuntaje() + "):\n" + elem.toString() + "\n";
                }
            }
            if (s.equals("")) {
                s = "No existen desafíos de ese tipo en ese rango.";
            }
        } else {
            s = "La habitación no existe.";
        }
        return s;
    }

    // METODOS SOBRE EQUIPOS
    // ABM
    public boolean altaEquipo(Equipo equipo) {
        boolean exito = false;
        exito = this.equipos.insertar(equipo.getNombre(), equipo);
        escribirLog("Se creó un equipo " + equipo.getNombre());
        return exito;
    }

    public boolean bajaEquipo(String nombreEquipo) {
        boolean exito = false;
        exito = this.equipos.eliminar(nombreEquipo);
        escribirLog("Se eliminó el equipo " + nombreEquipo);
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

    // Consultas:
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
        String s;
        Equipo eq = (Equipo) this.equipos.obtenerInformacion(nombreE);

        if (eq != null) {
            String habAct = eq.getHabitacionActual();
            int[] puntajeRequerido = {0};
            boolean esAdyacente = this.plano.esAdyacente(habAct, habDest, puntajeRequerido);
            if (esAdyacente) {
                int puntajeAcumulado = eq.getPuntajeHabitacion();
                int faltante = puntajeRequerido[0] - puntajeAcumulado;
                Habitacion hab = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(habAct));
                if (hab != null) {
                    if (faltante > 0) {
                        Lista desafios = hab.getDesafios().listarMayorIgualQue(new Desafio(faltante));
                        Lista noResueltos = filtrarNoResueltos(desafios, nombreE, habDest);
                        if (noResueltos.esVacia()) {
                            s = "No hay desafios que el equipo podria resolver para pasar a  hab resolviendo uno solo";
                        } else {
                            s = noResueltos.toString();
                        }
                    } else {
                        Lista desafios = hab.getDesafios().listar();
                        Lista noResueltos = filtrarNoResueltos(desafios, nombreE, habDest);
                        s = "Es posible acceder a " + habDest
                                + " sin resolver ningun desafio, o podria resolver cualquiera de la lista:\n "
                                + noResueltos.toString();
                    }
                } else {
                    s = "La habitacion destino no existe";
                }

            } else {
                s = "La habitacion " + habDest + " no es adyacente a la habitacion " + habAct
                        + " donde esta ubicado el equipo " + nombreE;
            }

        } else {
            s = "No existe el equipo";
        }

        return s;
    }

    /*
     * filtrarNoResueltos devuelve una nueva Lista con unicamente los desafios que ese equipo todavía NO resolvió 
     */
    private Lista filtrarNoResueltos(Lista desafios, String nombreEquipo, String codHabitacion) {
        Lista resueltos = this.desafiosResueltos.obtenerValores(nombreEquipo);
        Lista noResueltos = new Lista();
        int longitud = desafios.longitud();

        for (int i = 1; i <= longitud; i++) {
            Desafio d = (Desafio) desafios.recuperar(i);
            String idDesafio = "(" + codHabitacion + "," + d.getPuntaje() + ")";
            if (resueltos.localizar(idDesafio) < 0) {
                noResueltos.insertar(d, noResueltos.longitud() + 1);
            }
        }

        return noResueltos;
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

            Desafio Aux = new Desafio(puntajeDesafio);
            Desafio desafioActual = (Desafio) habitacion.getDesafios().obtenerDato(Aux);

            if (desafioActual != null) {

                String idDesafio = "(" + codHabitacion + "," + puntajeDesafio + ")";
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

    private int recalcularPuntajeHabitacion(String nombreEquipo, String codHabitacion) {
    Lista resueltos = this.desafiosResueltos.obtenerValores(nombreEquipo);
    int suma = 0;
    for (int i = 1; i <= resueltos.longitud(); i++) {
        String idDesafio = (String) resueltos.recuperar(i);
        String contenido = idDesafio.substring(1, idDesafio.length() - 1);
        String[] partes = contenido.split(",");
        if (partes[0].trim().equals(codHabitacion)) {
            suma += Integer.parseInt(partes[1].trim());
        }
    }
    return suma;
}

    /*
     * cambiarDeHabitacion: Dado un equipo eq y una habitación hab, verificar si es
     * posible que
     * el equipo eq pase a la habitación hab (considerando si es contigua a la
     * actual y el puntaje
     * acumulado en dicha habitación es suficiente) y en caso afirmativo actualizar
     * los datos del
     * equipo apropiadamente.
     */
    public boolean cambiarDeHabitacion(String nombreEquipo, String habDestino) {
        boolean exito = false;
        Equipo eq = (Equipo) this.equipos.obtenerInformacion(nombreEquipo);

        if (eq != null) {
            String habAct = eq.getHabitacionActual();
            int[] puntajeRequerido = {0};
            boolean esAdyacente = this.plano.esAdyacente(habAct, habDestino, puntajeRequerido);
            if (esAdyacente) {
                int puntajeAcumulado = eq.getPuntajeHabitacion();
                int faltante = puntajeRequerido[0] - puntajeAcumulado;
                if (faltante <= 0) {
                    eq.setHabitacionActual(habDestino);
                    eq.setPuntajeHabitacion(recalcularPuntajeHabitacion(nombreEquipo, habDestino));
                    exito = true;

                }

            }
        }

        return exito;
    }

    /*
     * puedeSalir: Dado el nombre del equipo participante, decir si puede o no salir
     * del juego en
     * base al puntaje acumulado, al puntaje que debe obtener para ganar el juego y
     * si la
     * habitación en la que se encuentra tiene o no salida al exterior
     */
    public boolean puedeSalir(String nombreEquipo) {
        boolean exito = false;
        Equipo eq = (Equipo) this.equipos.obtenerInformacion(nombreEquipo);
        if (eq != null) {
            String habAct = eq.getHabitacionActual();
            Habitacion habitacion = (Habitacion) this.habitaciones.obtenerDato(new Habitacion(habAct));
            if (habitacion != null) {
                if (habitacion.tieneSalida()) {
                    if (eq.getPuntajeNecesario() <= eq.getPuntajeTotal()) {
                        exito = true;
                    }
                }
            }

        }
        return exito;
    }

    // 6. Mostrar sistema
    public String mostrarSistema() {

        String sistema = "";

        sistema = "Plano (grafo): " + this.plano.toString() + "\n"
                + "Habitaciones (avl): " + this.habitaciones.toString() + "\n"
                + "Equipos (diccionario): " + this.equipos.toString() + "\n"
                + "Desafios resueltos (mapeo a muchos): " + this.desafiosResueltos.toString();

        return sistema;
    }

    // puerta - conexion del plano
    public boolean altaPuerta(String codHabitacion1, String codHabitacion2, int puntajeExigido) {
        boolean exito = this.plano.insertarArco(codHabitacion1, codHabitacion2, puntajeExigido);
        if (exito) {
            escribirLog("Se creó puerta entre " + codHabitacion1 + " y " + codHabitacion2);
        }
        return exito;
    }

    public boolean bajaPuerta(String codHabitacion1, String codHabitacion2) {
        boolean exito = this.plano.eliminarArco(codHabitacion1, codHabitacion2);
        if (exito) {
            escribirLog("Se eliminó puerta entre " + codHabitacion1 + " y " + codHabitacion2);
        }
        return exito;
    }
}
