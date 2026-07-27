package EscapeHouse;

public class Plano {

    private NodoHabitacion primeraHabitacion;

    public Plano() {
        this.primeraHabitacion = null;
    }

    private NodoHabitacion ubicarHabitacion(Object codigoHabitacion) {
        NodoHabitacion aux = this.primeraHabitacion;
        while (aux != null && !aux.getCodigo().equals(codigoHabitacion)) {
            aux = aux.getSigHabitacion();
        }
        return aux;
    }

    public boolean insertarHabitacion(Object codigoHabitacion) {
        boolean exito = false;
        NodoHabitacion aux = this.ubicarHabitacion(codigoHabitacion);
        if (aux == null) {
            this.primeraHabitacion = new NodoHabitacion(codigoHabitacion, this.primeraHabitacion);
            exito = true;
        }
        return exito;
    }

    public boolean eliminarHabitacion(Object codigoHabitacion) {
        boolean exito = false;
        NodoHabitacion habitacion = this.primeraHabitacion;
        NodoHabitacion anterior = null;

        while (habitacion != null && !habitacion.getCodigo().equals(codigoHabitacion)) {
            anterior = habitacion;
            habitacion = habitacion.getSigHabitacion();
        }

        if (habitacion != null) {
            exito = true;
            NodoConexion conexion = habitacion.getPrimeraConexion();
            while (conexion != null) {
                eliminarConexionAux(conexion.getHabitacionDestino(), habitacion);
                conexion = conexion.getSigConexion();
            }

            if (anterior == null) {
                this.primeraHabitacion = habitacion.getSigHabitacion();
            } else {
                anterior.setSigHabitacion(habitacion.getSigHabitacion());
            }
        }
        return exito;
    }

    public Lista listarEnProfundidad() {
        Lista visitados = new Lista();
        NodoHabitacion aux = this.primeraHabitacion;
        while (aux != null) {
            if (visitados.localizar(aux.getCodigo()) < 0) {
                listarEnProfundidadAux(aux, visitados);
            }
            aux = aux.getSigHabitacion();
        }

        return visitados;
    }

    private void listarEnProfundidadAux(NodoHabitacion habitacion, Lista visitados) {
        if (habitacion != null) {
            visitados.insertar(habitacion.getCodigo(), visitados.longitud() + 1);
            NodoConexion conexion = habitacion.getPrimeraConexion();
            while (conexion != null) {
                if (visitados.localizar(conexion.getHabitacionDestino().getCodigo()) < 0) {
                    listarEnProfundidadAux(conexion.getHabitacionDestino(), visitados);
                }
                conexion = conexion.getSigConexion();
            }
        }
    }

    public Lista listarEnAnchura() {
        Lista visitados = new Lista();
        NodoHabitacion aux = this.primeraHabitacion;
        while (aux != null) {
            if (visitados.localizar(aux.getCodigo()) < 0) {
                listarEnAnchuraAux(aux, visitados);
            }
            aux = aux.getSigHabitacion();
        }
        return visitados;
    }

    private void listarEnAnchuraAux(NodoHabitacion habitacion, Lista visitados) {
        if (habitacion != null) {
            Cola cola = new Cola();
            visitados.insertar(habitacion.getCodigo(), visitados.longitud() + 1);
            cola.poner(habitacion);
            while (!cola.esVacia()) {
                NodoHabitacion actual = (NodoHabitacion) cola.obtenerFrente();
                cola.sacar();
                NodoConexion conexion = actual.getPrimeraConexion();
                while (conexion != null) {
                    if (visitados.localizar(conexion.getHabitacionDestino().getCodigo()) < 0) {
                        visitados.insertar(conexion.getHabitacionDestino().getCodigo(), visitados.longitud() + 1);
                        cola.poner(conexion.getHabitacionDestino());
                    }
                    conexion = conexion.getSigConexion();
                }
            }
        }
    }

    /*
     * Busca origen y destino en un solo recorrido de la lista de vertices.
     * Devuelve un arreglo de tamaño 2 (estrategia para retornar mas de una cosa):
     * [0]=nodo de origen (o null si no existe),
     * [1]=nodo de destino (o null si no existe).
     */

    private NodoHabitacion[] buscarHabitaciones(Object habitacionOrigen, Object habitacionDestino) {

        NodoHabitacion origen = null;
        NodoHabitacion destino = null;

        NodoHabitacion aux = this.primeraHabitacion;

        while ((origen == null || destino == null) &&
                aux != null) {

            if (aux.getCodigo().equals(habitacionOrigen)) {
                origen = aux;
            }

            if (aux.getCodigo().equals(habitacionDestino)) {
                destino = aux;
            }

            aux = aux.getSigHabitacion();
        }

        NodoHabitacion[] encontradas = new NodoHabitacion[2];

        encontradas[0] = origen;
        encontradas[1] = destino;

        return encontradas;
    }

    private boolean existeConexion(NodoHabitacion habitacion, NodoHabitacion buscada) {
        NodoConexion conexion = habitacion.getPrimeraConexion();
        boolean encontrada = false;
        while (!encontrada && conexion != null) {
            encontrada = conexion.getHabitacionDestino() == buscada;
            conexion = conexion.getSigConexion();
        }
        return encontrada;
    }

    public boolean insertarConexion(Object habitacionOrigen, Object habitacionDestino, int puntajeMinimo) {
        boolean exito;
        NodoHabitacion[] encontradas = buscarHabitaciones(habitacionOrigen, habitacionDestino);
        NodoHabitacion origen = encontradas[0];
        NodoHabitacion destino = encontradas[1];
        if (origen != null && destino != null && !existeConexion(origen, destino)) {
            origen.setPrimeraConexion(new NodoConexion(destino, puntajeMinimo, origen.getPrimeraConexion()));
            destino.setPrimeraConexion(new NodoConexion(origen, puntajeMinimo, destino.getPrimeraConexion()));
            exito = true;
        } else {
            exito = false;
        }

        return exito;
    }

    public boolean eliminarConexion(Object habitacionOrigen, Object habitacionDestino) {
        boolean exito = false;
        NodoHabitacion[] encontradas = buscarHabitaciones(habitacionOrigen, habitacionDestino);
        NodoHabitacion origen = encontradas[0];
        NodoHabitacion destino = encontradas[1];
        if (origen != null && destino != null) {
            boolean exito1 = eliminarConexionAux(origen, destino);
            boolean exito2 = eliminarConexionAux(destino, origen);
            exito = exito1 && exito2;
        }

        return exito;
    }

    private boolean eliminarConexionAux(NodoHabitacion origen, NodoHabitacion destino) {
        NodoConexion conexion = origen.getPrimeraConexion();
        NodoConexion anterior = null;
        boolean encontrada = false;
        while (!encontrada && conexion != null) {
            encontrada = conexion.getHabitacionDestino() == destino;
            if (!encontrada) {
                anterior = conexion;
                conexion = conexion.getSigConexion();
            }
        }

        if (encontrada) {
            if (anterior == null) {
                origen.setPrimeraConexion(conexion.getSigConexion());
            } else {
                anterior.setSigConexion(conexion.getSigConexion());
            }
        }

        return encontrada;
    }

    public boolean existeHabitacion(Object codigoHabitacion) {
        return ubicarHabitacion(codigoHabitacion) != null;
    }

    public boolean estanConectadas(Object codigoOrigen, Object codigoDestino) {
        boolean existe;
        NodoHabitacion[] encontrados = buscarHabitaciones(codigoOrigen, codigoDestino);
        NodoHabitacion origen = encontrados[0];
        NodoHabitacion destino = encontrados[1];

        if (origen != null && destino != null) {
            existe = existeConexion(origen, destino);
        } else {
            existe = false;
        }

        return existe;
    }

    public boolean esVacio() {
        return this.primeraHabitacion == null;
    }

    public boolean existeCamino(Object origen, Object destino) {
        boolean exito = false;
        NodoHabitacion[] encontrados = buscarHabitaciones(origen, destino);
        NodoHabitacion auxO = encontrados[0];
        NodoHabitacion auxD = encontrados[1];

        if (auxO != null && auxD != null) {
            // si ambos vertices existen busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = existeCaminoAux(auxO, destino, visitados);
        }
        return exito;
    }

    private boolean existeCaminoAux(NodoHabitacion n, Object dest, Lista vis) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: HAY CAMINO!
            if (n.getCodigo().equals(dest)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay camino entre n y destino
                vis.insertar(n.getCodigo(), vis.longitud() + 1);
                NodoConexion ady = n.getPrimeraConexion();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getHabitacionDestino().getCodigo()) < 0) {
                        exito = existeCaminoAux(ady.getHabitacionDestino(), dest, vis);
                    }
                    ady = ady.getSigConexion();
                }
            }
        }
        return exito;
    }

    /*
     * Hace una búsqueda en anchura desde 'origen' llenando dos listas en paralelo:
     * 'visitados' guarda las habitaciones descubiertas y 'padres' guarda,
     * en la MISMA posición, quién descubrió cada habitación.
     * Corta en 'destino': por la propiedad de búsqueda en anchura
     * (por niveles), la primera vez que se llega es siempre por
     * el camino más corto posible.
     */
    private void caminoMasCortoAux(NodoHabitacion origen, Object destino, Lista visitados, Lista padres) {
        Cola cola = new Cola();
        boolean encontrado = false;
        visitados.insertar(origen.getCodigo(), 1);
        padres.insertar(null, 1); // la habitación origen no tiene padre
        cola.poner(origen);

        while (!encontrado && !cola.esVacia()) {

            NodoHabitacion actual = (NodoHabitacion) cola.obtenerFrente();
            cola.sacar();

            NodoConexion conexion = actual.getPrimeraConexion();

            while (!encontrado && conexion != null) {

                if (visitados.localizar(conexion.getHabitacionDestino().getCodigo()) < 0) {
                    visitados.insertar(conexion.getHabitacionDestino().getCodigo(), 1);
                    padres.insertar(actual.getCodigo(), 1);
                    cola.poner(conexion.getHabitacionDestino());
                    if (conexion.getHabitacionDestino().getCodigo().equals(destino)) {
                        encontrado = true;
                    }
                }
                conexion = conexion.getSigConexion();
            }
        }
    }

    private Lista reconstruirCamino(Object origen, Object destino, Lista visitados, Lista padres) {
        Lista camino = new Lista();
        Object actual = destino;
        boolean seguir = true;

        while (seguir) {
            camino.insertar(actual, 1);
            if (actual.equals(origen)) {
                seguir = false;
            } else {
                int pos = visitados.localizar(actual);
                actual = padres.recuperar(pos);
            }
        }
        return camino;
    }

    public Lista caminoMasCorto(Object origen, Object destino) {
        Lista camino = new Lista();
        NodoHabitacion[] encontradas = buscarHabitaciones(origen, destino);
        NodoHabitacion habOrigen = encontradas[0];
        NodoHabitacion habDestino = encontradas[1];

        if (habOrigen != null && habDestino != null) {
            Lista visitados = new Lista();
            Lista padres = new Lista();
            caminoMasCortoAux(habOrigen, destino, visitados, padres);
            if (visitados.localizar(destino) >= 0) {
                camino = reconstruirCamino(origen, destino, visitados, padres);
            }
        }

        return camino;
    }

    public Lista caminoMasLargo(Object origen, Object destino) {

        Lista camino = new Lista();

        NodoHabitacion[] encontradas = buscarHabitaciones(origen, destino);

        NodoHabitacion habOrigen = encontradas[0];
        NodoHabitacion habDestino = encontradas[1];

        if (habOrigen != null && habDestino != null) {

            Lista visitados = new Lista();
            Lista caminoActual = new Lista();

            caminoMasLargoAux(habOrigen, habDestino, visitados, caminoActual, camino);
        }

        return camino;
    }

    private void caminoMasLargoAux(NodoHabitacion actual, NodoHabitacion destino, Lista visitados, Lista caminoActual,
            Lista caminoMasLargo) {
        visitados.insertar(actual.getCodigo(), visitados.longitud() + 1);
        caminoActual.insertar(actual.getCodigo(), caminoActual.longitud() + 1);
        if (actual == destino) {
            if (caminoActual.longitud() > caminoMasLargo.longitud()) {
                caminoMasLargo.vaciar();
                int longActual = caminoActual.longitud();
                for (int i = 1; i <= longActual; i++) {
                    caminoMasLargo.insertar(caminoActual.recuperar(i), i);
                }
            }

        } else {
            NodoConexion conexion = actual.getPrimeraConexion();
            while (conexion != null) {
                if (visitados.localizar(conexion.getHabitacionDestino().getCodigo()) < 0) {
                    caminoMasLargoAux(conexion.getHabitacionDestino(), destino, visitados, caminoActual,
                            caminoMasLargo);
                }
                conexion = conexion.getSigConexion();
            }
        }

        visitados.eliminar(visitados.longitud());
        caminoActual.eliminar(caminoActual.longitud());
    }

    public Plano clone() {
        Plano clon = new Plano();
        if (this.primeraHabitacion != null) {
            // ---------- PRIMERA PASADA: copiar habitaciones ----------
            NodoHabitacion auxOriginal = this.primeraHabitacion;
            NodoHabitacion ultimaClon = null;
            while (auxOriginal != null) {
                NodoHabitacion nueva = new NodoHabitacion(auxOriginal.getCodigo(), null);

                if (clon.primeraHabitacion == null) {
                    clon.primeraHabitacion = nueva;
                } else {
                    ultimaClon.setSigHabitacion(nueva);
                }

                ultimaClon = nueva;
                auxOriginal = auxOriginal.getSigHabitacion();
            }

            // ---------- SEGUNDA PASADA: copiar conexiones ----------

            auxOriginal = this.primeraHabitacion;
            NodoHabitacion auxClon = clon.primeraHabitacion;

            while (auxOriginal != null) {
                NodoConexion conexionOriginal = auxOriginal.getPrimeraConexion();
                NodoConexion ultimaConexion = null;
                while (conexionOriginal != null) {
                    NodoHabitacion destino = clon.ubicarHabitacion(conexionOriginal.getHabitacionDestino().getCodigo());
                    NodoConexion nuevaConexion = new NodoConexion(destino, conexionOriginal.getPuntajeMinimo(), null);

                    if (auxClon.getPrimeraConexion() == null) {
                        auxClon.setPrimeraConexion(nuevaConexion);
                    } else {
                        ultimaConexion.setSigConexion(nuevaConexion);
                    }

                    ultimaConexion = nuevaConexion;
                    conexionOriginal = conexionOriginal.getSigConexion();
                }

                auxOriginal = auxOriginal.getSigHabitacion();
                auxClon = auxClon.getSigHabitacion();
            }
        }

        return clon;
    }

    public String toString() {
        NodoHabitacion aux = this.primeraHabitacion;
        String s = "";
        while (aux != null) {
            s += "Habitación: " + aux.getCodigo();
            NodoConexion conexion = aux.getPrimeraConexion();
            if (conexion != null) {
                s += " --> Conectada con: " + conexion.getHabitacionDestino().getCodigo() + " ("
                        + conexion.getPuntajeMinimo() + ")";
                conexion = conexion.getSigConexion();
                while (conexion != null) {
                    s += ", " + conexion.getHabitacionDestino().getCodigo() + " (" + conexion.getPuntajeMinimo() + ")";
                    conexion = conexion.getSigConexion();
                }
            }
            s += "\n";
            aux = aux.getSigHabitacion();
        }

        return s;
    }

    // habitacionesContiguas: Dado un código de habitación, mostrar las habitaciones
    // contiguas a las que se puede acceder, y qué puntaje se necesitaría para pasar
    // a cada una

    public String habitacionesContiguas(String codigoHab) {
        String s = "";
        NodoHabitacion hab = this.ubicarHabitacion(codigoHab);

        if (hab != null) {
            s = "Habitaciones contiguas a la habitación " + codigoHab + ":\n";

            NodoConexion aux = hab.getPrimeraConexion();

            while (aux != null) {
                s += "- Habitación " + aux.getHabitacionDestino().getCodigo() + " (puntaje mínimo: "
                        + aux.getPuntajeMinimo() + ")\n";
                aux = aux.getSigConexion();
            }
        } else {
            s = "Habitación inexistente.";
        }

        return s;
    }

    // esPosibleLlegar: Dados los códigos de hab1 y hab2, y un valor k, mostrar si
    // es o no posible llegar de hab1 a hab2, acumulando k puntos

    public boolean esPosibleLlegar(Object origen, Object destino, int k) {
        boolean exito = false;
        NodoHabitacion[] encontrados = buscarHabitaciones(origen, destino);
        NodoHabitacion auxO = encontrados[0];
        NodoHabitacion auxD = encontrados[1];

        if (auxO != null && auxD != null) {
            // si ambos vertices existen busca si existe camino entre ambos
            Lista visitados = new Lista();
            exito = esPosibleLlegarAux(auxO, destino, visitados, 0, k);
        }
        return exito;
    }

    private boolean esPosibleLlegarAux(NodoHabitacion n, Object dest, Lista vis, int puntajeAcumulado, int k) {
        boolean exito = false;
        if (n != null) {
            // si vertice n es el destino: HAY CAMINO!
            if (n.getCodigo().equals(dest)) {
                exito = true;
            } else {
                // si no es el destino verifica si hay camino entre n y destino
                vis.insertar(n.getCodigo(), vis.longitud() + 1);
                NodoConexion ady = n.getPrimeraConexion();
                while (!exito && ady != null) {
                    if (vis.localizar(ady.getHabitacionDestino().getCodigo()) < 0) {
                        int nuevoPuntaje = puntajeAcumulado + ady.getPuntajeMinimo();
                        if (nuevoPuntaje <= k) {
                            exito = esPosibleLlegarAux(ady.getHabitacionDestino(), dest, vis, nuevoPuntaje, k);
                        }
                    }
                    ady = ady.getSigConexion();
                }
            }
        }
        return exito;
    }

}