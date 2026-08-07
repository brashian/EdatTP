package estructuras.grafos;

import estructuras.lineales.*;

public class Grafo {

    private NodoVertice inicio;

    public Grafo() {
        this.inicio = null;
    }

    private NodoVertice ubicarVertice(Object elemento) {
        NodoVertice aux = this.inicio;

        while (aux != null && !aux.getElemento().equals(elemento)) {
            aux = aux.getSigVertice();
        }

        return aux;
    }

    public boolean insertarVertice(Object elemento) {
        boolean exito = false;

        NodoVertice aux = this.ubicarVertice(elemento);

        if (aux == null) {
            this.inicio = new NodoVertice(elemento, this.inicio);
            exito = true;
        }

        return exito;
    }

    public boolean eliminarVertice(Object elemento) {

        boolean exito = false;

        NodoVertice vertice = this.inicio;
        NodoVertice anterior = null;

        while (vertice != null && !vertice.getElemento().equals(elemento)) {
            anterior = vertice;
            vertice = vertice.getSigVertice();
        }

        if (vertice != null) {

            exito = true;

            NodoAdyacente adyacente = vertice.getPrimerAdyacente();

            while (adyacente != null) {
                eliminarArcoAux(adyacente.getVertice(), vertice);
                adyacente = adyacente.getSigAdyacente();
            }

            if (anterior == null) {
                this.inicio = vertice.getSigVertice();
            } else {
                anterior.setSigVertice(vertice.getSigVertice());
            }
        }

        return exito;
    }

    public Lista listarEnProfundidad() {

        Lista visitados = new Lista();

        NodoVertice aux = this.inicio;

        while (aux != null) {

            if (visitados.localizar(aux.getElemento()) < 0) {
                listarEnProfundidadAux(aux, visitados);
            }

            aux = aux.getSigVertice();
        }

        return visitados;
    }

    private void listarEnProfundidadAux(NodoVertice vertice, Lista visitados) {

        if (vertice != null) {

            visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);

            NodoAdyacente adyacente = vertice.getPrimerAdyacente();

            while (adyacente != null) {

                if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {
                    listarEnProfundidadAux(adyacente.getVertice(), visitados);
                }

                adyacente = adyacente.getSigAdyacente();
            }
        }
    }

    public Lista listarEnAnchura() {

        Lista visitados = new Lista();

        NodoVertice aux = this.inicio;

        while (aux != null) {

            if (visitados.localizar(aux.getElemento()) < 0) {
                listarEnAnchuraAux(aux, visitados);
            }

            aux = aux.getSigVertice();
        }

        return visitados;
    }

    private void listarEnAnchuraAux(NodoVertice vertice, Lista visitados) {

        if (vertice != null) {

            Cola cola = new Cola();

            visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);

            cola.poner(vertice);

            while (!cola.esVacia()) {

                NodoVertice actual = (NodoVertice) cola.obtenerFrente();
                cola.sacar();

                NodoAdyacente adyacente = actual.getPrimerAdyacente();

                while (adyacente != null) {

                    if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {

                        visitados.insertar(adyacente.getVertice().getElemento(),
                                visitados.longitud() + 1);

                        cola.poner(adyacente.getVertice());
                    }

                    adyacente = adyacente.getSigAdyacente();
                }
            }
        }
    }

    /*
     * Busca origen y destino en UN SOLO RECORRIDO de la lista de vértices.
     * devuelve:
     * [0] = origen
     * [1] = destino
     */
    private NodoVertice[] buscarVertices(Object origenBuscado, Object destinoBuscado) {

        NodoVertice origen = null;
        NodoVertice destino = null;

        NodoVertice aux = this.inicio;

        while ((origen == null || destino == null) && aux != null) {

            if (aux.getElemento().equals(origenBuscado)) {
                origen = aux;
            }

            if (aux.getElemento().equals(destinoBuscado)) {
                destino = aux;
            }

            aux = aux.getSigVertice();
        }

        NodoVertice[] encontrados = new NodoVertice[2];

        encontrados[0] = origen;
        encontrados[1] = destino;

        return encontrados;
    }

    private boolean existeArcoAux(NodoVertice vertice, NodoVertice buscado) {

        NodoAdyacente adyacente = vertice.getPrimerAdyacente();

        boolean encontrado = false;

        while (!encontrado && adyacente != null) {

            encontrado = adyacente.getVertice() == buscado;
            adyacente = adyacente.getSigAdyacente();
        }

        return encontrado;
    }

    public boolean insertarArco(Object origenElem, Object destinoElem, int etiqueta) {

        boolean exito;

        NodoVertice[] encontrados = buscarVertices(origenElem, destinoElem);

        NodoVertice origen = encontrados[0];
        NodoVertice destino = encontrados[1];

        if (origen != null && destino != null && !existeArcoAux(origen, destino)) {

            origen.setPrimerAdyacente(new NodoAdyacente(destino, etiqueta, origen.getPrimerAdyacente()));

            destino.setPrimerAdyacente(new NodoAdyacente(origen, etiqueta, destino.getPrimerAdyacente()));

            exito = true;

        } else {

            exito = false;
        }

        return exito;
    }

    public boolean eliminarArco(Object origenElem, Object destinoElem) {

        boolean exito = false;

        NodoVertice[] encontrados = buscarVertices(origenElem, destinoElem);

        NodoVertice origen = encontrados[0];
        NodoVertice destino = encontrados[1];

        if (origen != null && destino != null) {

            boolean exito1 = eliminarArcoAux(origen, destino);
            boolean exito2 = eliminarArcoAux(destino, origen);

            exito = exito1 && exito2;
        }

        return exito;
    }

    private boolean eliminarArcoAux(NodoVertice origen, NodoVertice destino) {

        NodoAdyacente adyacente = origen.getPrimerAdyacente();
        NodoAdyacente anterior = null;

        boolean encontrado = false;

        while (!encontrado && adyacente != null) {

            encontrado = adyacente.getVertice() == destino;

            if (!encontrado) {
                anterior = adyacente;
                adyacente = adyacente.getSigAdyacente();
            }
        }

        if (encontrado) {

            if (anterior == null) {
                origen.setPrimerAdyacente(adyacente.getSigAdyacente());
            } else {
                anterior.setSigAdyacente(adyacente.getSigAdyacente());
            }
        }

        return encontrado;
    }

    public boolean existeVertice(Object elemento) {
        return ubicarVertice(elemento) != null;
    }

    public boolean existeArco(Object origenElem, Object destinoElem) {

        boolean existe;

        NodoVertice[] encontrados = buscarVertices(origenElem, destinoElem);

        NodoVertice origen = encontrados[0];
        NodoVertice destino = encontrados[1];

        if (origen != null && destino != null) {
            existe = existeArcoAux(origen, destino);
        } else {
            existe = false;
        }

        return existe;
    }

    public boolean esVacio() {
        return this.inicio == null;
    }

    public boolean existeCamino(Object origenElem, Object destinoElem) {

        boolean exito = false;

        NodoVertice[] encontrados = buscarVertices(origenElem, destinoElem);

        NodoVertice origen = encontrados[0];
        NodoVertice destino = encontrados[1];

        if (origen != null && destino != null) {

            Lista visitados = new Lista();

            exito = existeCaminoAux(origen, destinoElem, visitados);
        }

        return exito;
    }

    private boolean existeCaminoAux(NodoVertice vertice, Object destino, Lista visitados) {

        boolean exito = false;

        if (vertice != null) {

            if (vertice.getElemento().equals(destino)) {

                exito = true;

            } else {

                visitados.insertar(vertice.getElemento(), visitados.longitud() + 1);

                NodoAdyacente adyacente = vertice.getPrimerAdyacente();

                while (!exito && adyacente != null) {

                    if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {

                        exito = existeCaminoAux(
                                adyacente.getVertice(),
                                destino,
                                visitados);
                    }

                    adyacente = adyacente.getSigAdyacente();
                }
            }
        }

        return exito;
    }

    /*
     * Hace una búsqueda en anchura desde el vértice origen llenando dos listas:
     * visitados: vértices descubiertos.
     * padres: quién descubrió cada vértice.
     */
    private void caminoMasCortoAux(NodoVertice origen, Object destino, Lista visitados, Lista padres) {

        Cola cola = new Cola();

        boolean encontrado = false;

        visitados.insertar(origen.getElemento(), 1);
        padres.insertar(null, 1);

        cola.poner(origen);

        while (!encontrado && !cola.esVacia()) {

            NodoVertice actual = (NodoVertice) cola.obtenerFrente();
            cola.sacar();

            NodoAdyacente adyacente = actual.getPrimerAdyacente();

            while (!encontrado && adyacente != null) {

                if (visitados.localizar(adyacente.getVertice().getElemento()) < 0) {

                    visitados.insertar(
                            adyacente.getVertice().getElemento(), 1);

                    padres.insertar(
                            actual.getElemento(), 1);

                    cola.poner(adyacente.getVertice());

                    if (adyacente.getVertice().getElemento().equals(destino)) {
                        encontrado = true;
                    }
                }

                adyacente = adyacente.getSigAdyacente();
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

    public Lista caminoMasCorto(Object origen,
            Object destino) {

        Lista camino = new Lista();

        NodoVertice[] encontrados = buscarVertices(origen, destino);

        NodoVertice verticeOrigen = encontrados[0];
        NodoVertice verticeDestino = encontrados[1];

        if (verticeOrigen != null && verticeDestino != null) {

            Lista visitados = new Lista();
            Lista padres = new Lista();

            caminoMasCortoAux(verticeOrigen, destino, visitados, padres);

            if (visitados.localizar(destino) >= 0) {
                camino = reconstruirCamino(origen, destino, visitados, padres);
            }
        }

        return camino;
    }

    public Lista caminoMasLargo(Object origen, Object destino) {

        Lista camino = new Lista();

        NodoVertice[] encontrados = buscarVertices(origen, destino);

        NodoVertice verticeOrigen = encontrados[0];
        NodoVertice verticeDestino = encontrados[1];

        if (verticeOrigen != null && verticeDestino != null) {

            Lista visitados = new Lista();
            Lista caminoActual = new Lista();

            caminoMasLargoAux(
                    verticeOrigen,
                    verticeDestino,
                    visitados,
                    caminoActual,
                    camino);
        }

        return camino;
    }

    private void caminoMasLargoAux(NodoVertice actual, NodoVertice destino, Lista visitados, Lista caminoActual, Lista caminoMasLargo) {

        visitados.insertar(actual.getElemento(), visitados.longitud() + 1);
        caminoActual.insertar(actual.getElemento(), caminoActual.longitud() + 1);

        if (actual == destino) {

            if (caminoActual.longitud() > caminoMasLargo.longitud()) {

                caminoMasLargo.vaciar();

                int longitud = caminoActual.longitud();

                for (int i = 1; i <= longitud; i++) {
                    caminoMasLargo.insertar(caminoActual.recuperar(i), i);
                }
            }

        } else {

            NodoAdyacente adyacente = actual.getPrimerAdyacente();

            while (adyacente != null) {

                if (visitados.localizar(
                        adyacente.getVertice().getElemento()) < 0) {

                    caminoMasLargoAux(
                            adyacente.getVertice(),
                            destino,
                            visitados,
                            caminoActual,
                            caminoMasLargo);
                }

                adyacente = adyacente.getSigAdyacente();
            }
        }

        visitados.eliminar(visitados.longitud());
        caminoActual.eliminar(caminoActual.longitud());
    }

    @Override
    public Grafo clone() {

        Grafo clon = new Grafo();

        if (this.inicio != null) {

            /* ---------- Primera pasada: copiar vértices ---------- */
            NodoVertice auxOriginal = this.inicio;
            NodoVertice ultimoClon = null;

            while (auxOriginal != null) {

                NodoVertice nuevo
                        = new NodoVertice(auxOriginal.getElemento(), null);

                if (clon.inicio == null) {
                    clon.inicio = nuevo;
                } else {
                    ultimoClon.setSigVertice(nuevo);
                }

                ultimoClon = nuevo;
                auxOriginal = auxOriginal.getSigVertice();
            }

            /* ---------- Segunda pasada: copiar arcos ---------- */
            auxOriginal = this.inicio;
            NodoVertice auxClon = clon.inicio;

            while (auxOriginal != null) {

                NodoAdyacente adyacenteOriginal = auxOriginal.getPrimerAdyacente();
                NodoAdyacente ultimoAdyacente = null;

                while (adyacenteOriginal != null) {

                    NodoVertice destino = clon.ubicarVertice(adyacenteOriginal.getVertice().getElemento());
                    NodoAdyacente nuevoAdyacente = new NodoAdyacente(destino, adyacenteOriginal.getEtiqueta(), null);

                    if (auxClon.getPrimerAdyacente() == null) {
                        auxClon.setPrimerAdyacente(nuevoAdyacente);
                    } else {
                        ultimoAdyacente.setSigAdyacente(nuevoAdyacente);
                    }

                    ultimoAdyacente = nuevoAdyacente;
                    adyacenteOriginal = adyacenteOriginal.getSigAdyacente();
                }

                auxOriginal = auxOriginal.getSigVertice();
                auxClon = auxClon.getSigVertice();
            }
        }

        return clon;
    }

    @Override
    public String toString() {

        NodoVertice aux = this.inicio;

        String s = "";

        while (aux != null) {

            s += "Vertice: " + aux.getElemento();

            NodoAdyacente adyacente = aux.getPrimerAdyacente();

            if (adyacente != null) {

                s += " --> Adyacentes: " + adyacente.getVertice().getElemento() + " (" + adyacente.getEtiqueta() + ")";

                adyacente = adyacente.getSigAdyacente();

                while (adyacente != null) {

                    s += ", " + adyacente.getVertice().getElemento() + " (" + adyacente.getEtiqueta() + ")";

                    adyacente = adyacente.getSigAdyacente();
                }
            }

            s += "\n";

            aux = aux.getSigVertice();
        }

        return s;
    }

    // adyacentesDe: Dado un vértice, mostrar los vértices adyacentes a los que se
    // puede acceder, y qué etiqueta (peso del arco) se necesita para pasar a cada uno
    // devuelve un string
    public String adyacentesDe(Object elemento) {
        String s = "";
        NodoVertice v = this.ubicarVertice(elemento);

        if (v != null) {
            s = "Adyacentes al vértice " + elemento + ":\n";

            NodoAdyacente aux = v.getPrimerAdyacente();

            while (aux != null) {
                s += "- Vértice " + aux.getVertice().getElemento() + " (etiqueta: " + aux.getEtiqueta() + ")\n";
                aux = aux.getSigAdyacente();
            }
        } else {
            s = "Vértice inexistente.";
        }

        return s;
    }

    // esPosibleLlegar: Dados un origen y un destino, y un valor k, indica si es o
    // no posible llegar de origen a destino sin acumular más de k en la suma de
    // etiquetas de los arcos recorridos
    public boolean esPosibleLlegar(Object origen, Object destino, int k) {
        boolean exito = false;
        NodoVertice[] encontrados = buscarVertices(origen, destino);
        NodoVertice auxO = encontrados[0];
        NodoVertice auxD = encontrados[1];

        if (auxO != null && auxD != null) {
            Lista visitados = new Lista();
            Lista mejorCosto = new Lista();
            exito = esPosibleLlegarAux(auxO, destino, visitados, mejorCosto, 0, k);
        }
        return exito;
    }

    private boolean esPosibleLlegarAux(NodoVertice n, Object dest, Lista vis, Lista mejorCosto, int acumulado, int k) {
        boolean exito = false;
        if (n != null) {
            if (n.getElemento().equals(dest)) {
                exito = true;
            } else {
                int pos = vis.localizar(n.getElemento());
                if (pos < 0) {
                    vis.insertar(n.getElemento(), vis.longitud() + 1);
                    mejorCosto.insertar(acumulado, mejorCosto.longitud() + 1);
                } else {
                    mejorCosto.eliminar(pos);
                    mejorCosto.insertar(acumulado, pos);
                }

                NodoAdyacente ady = n.getPrimerAdyacente();
                
                while (!exito && ady != null) {
                    Object elemDestino = ady.getVertice().getElemento();
                    int posDestino = vis.localizar(elemDestino);
                    int nuevoAcumulado = acumulado + ady.getEtiqueta();
                    boolean valeLaPena = (posDestino < 0)
                            || (nuevoAcumulado < (Integer) mejorCosto.recuperar(posDestino));

                    if (valeLaPena && nuevoAcumulado <= k) {
                        exito = esPosibleLlegarAux(ady.getVertice(), dest, vis, mejorCosto,
                                nuevoAcumulado, k);
                    }
                    ady = ady.getSigAdyacente();
                }
            }
        }
        return exito;
    }

    // minimoPuntaje: Dados un origen y un destino, calcula la mínima suma de
    // etiquetas necesaria para ir de origen a destino y devuelve el camino
    // (lista de vértices) que logra ese mínimo
    public Lista minimoPuntaje(Object origen, Object destino, int[] resultado) {
        Lista camino = new Lista();
        NodoVertice[] encontrados = buscarVertices(origen, destino);
        NodoVertice auxO = encontrados[0];
        NodoVertice auxD = encontrados[1];

        if (auxO != null && auxD != null) {
            Lista visitados = new Lista();
            Lista[] mejorCamino = {new Lista()};
            int[] mejorAcumulado = {Integer.MAX_VALUE}; // lo utilizo para que el primer camino encontrado sea
            // guardado como el mejor.

            minimoPuntajeAux(auxO, destino, visitados, mejorCamino, 0, mejorAcumulado);

            camino = mejorCamino[0];
            resultado[0] = mejorAcumulado[0];
        }

        return camino;
    }

    private void minimoPuntajeAux(NodoVertice n, Object destino, Lista visitados, Lista[] mejorCamino,
            int acumulado, int[] mejorAcumulado) {

        if (n != null) {
            visitados.insertar(n.getElemento(), visitados.longitud() + 1);
            if (n.getElemento().equals(destino)) {

                if (acumulado < mejorAcumulado[0]) {
                    mejorAcumulado[0] = acumulado;
                    mejorCamino[0] = visitados.clone();
                }

            } else {
                NodoAdyacente ady = n.getPrimerAdyacente();
                while (ady != null) {
                    if (visitados.localizar(ady.getVertice().getElemento()) < 0) {
                        int nuevoAcumulado = acumulado + ady.getEtiqueta();
                        boolean valeLaPena = nuevoAcumulado < mejorAcumulado[0];

                        if (valeLaPena) {
                            minimoPuntajeAux(ady.getVertice(), destino, visitados, mejorCamino, nuevoAcumulado,
                                    mejorAcumulado);
                        }
                    }

                    ady = ady.getSigAdyacente();
                }
            }

            visitados.eliminar(visitados.longitud());
        }
    }

    /*
     * sinPasarPor: Dados un origen, un destino, un vértice prohibido y un valor
     * numérico P, devuelve todas las formas de llegar desde origen a destino sin
     * pasar por el vértice prohibido que no requieran acumular más de P en la
     * suma de etiquetas.
     */
    public Lista sinPasarPor(Object origen, Object destino, Object prohibido, int P) {
        Lista lis = new Lista();
        NodoVertice[] encontrados = buscarVertices(origen, destino);
        NodoVertice auxO = encontrados[0];
        NodoVertice auxD = encontrados[1];

        if (auxO != null && auxD != null && ubicarVertice(prohibido) != null) {
            Lista visitados = new Lista();
            sinPasarPorAux(auxO, destino, prohibido, visitados, lis, P, 0);
        }

        return lis;
    }

    private void sinPasarPorAux(NodoVertice n, Object dest, Object prohibido, Lista visitados, Lista lis, int P,
            int acumulado) {
        if (n != null) {
            visitados.insertar(n.getElemento(), visitados.longitud() + 1);

            if (n.getElemento().equals(dest)) {
                lis.insertar(visitados.clone(), lis.longitud() + 1);
            } else {
                NodoAdyacente ady = n.getPrimerAdyacente();

                while (ady != null) {
                    Object elemDestino = ady.getVertice().getElemento();
                    if (!elemDestino.equals(prohibido) && visitados.localizar(elemDestino) < 0) {
                        int nuevoAcumulado = acumulado + ady.getEtiqueta();
                        if (nuevoAcumulado <= P) {
                            sinPasarPorAux(ady.getVertice(), dest, prohibido, visitados, lis, P,
                                    nuevoAcumulado);
                        }
                    }
                    ady = ady.getSigAdyacente();
                }
            }
            visitados.eliminar(visitados.longitud());
        }
    }

    private boolean esAdyacenteAux(NodoVertice vertice, NodoVertice buscado, int[] etiqueta) {
        NodoAdyacente adyacente = vertice.getPrimerAdyacente();
        boolean encontrado = false;
        while (!encontrado && adyacente != null) {
            encontrado = adyacente.getVertice() == buscado;
            if (!encontrado) {
                adyacente = adyacente.getSigAdyacente();
            } else {
                etiqueta[0] = adyacente.getEtiqueta();
            }
        }
        return encontrado;
    }

    public boolean esAdyacente(Object elementoOrigen, Object elementoDestino, int[] etiqueta) {
        boolean existe;
        NodoVertice[] encontrados = buscarVertices(elementoOrigen, elementoDestino);
        NodoVertice origen = encontrados[0];
        NodoVertice destino = encontrados[1];

        if (origen != null && destino != null) {
            existe = esAdyacenteAux(origen, destino, etiqueta);
        } else {
            existe = false;
        }

        return existe;
    }

}
