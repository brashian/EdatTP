package EscapeHouse;

public class MapeoAMuchos {
    private static final int TAM = 13;

    private NodoHashMapeoM[] tabla;
    private int cant = 0; //cantidad de dominios

    public MapeoAMuchos() {
        this.tabla = new NodoHashMapeoM[TAM];
    }

    private int funcionHash(Object dominio) {
        return Math.floorMod(dominio.hashCode(), TAM);
    }

    public boolean asociar(Object dominio, Object rango) {
        int pos = funcionHash(dominio);
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;
        boolean exito = false;

        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(dominio);
            if (!encontrado) {
                aux = aux.getEnlace();
            }
        }

        if (!encontrado) {
            this.tabla[pos] = new NodoHashMapeoM(dominio, this.tabla[pos]);
            this.tabla[pos].getRango().insertar(rango, 1);
            this.cant++;
            exito = true;
        } else if (aux.getRango().localizar(rango) < 0) {
            aux.getRango().insertar(rango, 1);
            exito = true;
        }

        return exito;
    }

    public boolean desasociar(Object dominio, Object rango) {
        int pos = funcionHash(dominio);
        NodoHashMapeoM aux = this.tabla[pos];
        NodoHashMapeoM prev = null;
        boolean encontrado = false;
        boolean exito = false;

        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(dominio);
            if (!encontrado) {
                prev = aux;
                aux = aux.getEnlace();
            }
        }

        if (encontrado) {
            int posRango = aux.getRango().localizar(rango);

            if (posRango > 0) {
                aux.getRango().eliminar(posRango);

                if (aux.getRango().esVacia()) {
                    if (prev == null) {
                        this.tabla[pos] = aux.getEnlace();
                    } else {
                        prev.setEnlace(aux.getEnlace());
                    }
                    this.cant--;
                }

                exito = true;
            }
        }

        return exito;
    }

    public Lista obtenerValores(Object dominio) {
        Lista conjunto = new Lista();
        int pos = funcionHash(dominio);
        NodoHashMapeoM aux = this.tabla[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getDominio().equals(dominio);
            if (!encontrado)
                aux = aux.getEnlace();
        }
        if (encontrado) {
            conjunto = aux.getRango().clone();
        }
        return conjunto;
    }

    public Lista obtenerConjuntoDominio() {
        Lista lista = new Lista();
        for (int i = 0; i < TAM; i++) {
            NodoHashMapeoM aux = this.tabla[i];
            while (aux != null) {
                lista.insertar(aux.getDominio(), lista.longitud() + 1);
                aux = aux.getEnlace();
            }
        }
        return lista;
    }

    public Lista obtenerConjuntoRango() {
        Lista lista = new Lista();

        for (int i = 0; i < TAM; i++) {

            NodoHashMapeoM aux = this.tabla[i];

            while (aux != null) {

                Lista rango = aux.getRango();

                int longRango = rango.longitud();

                for (int j = 1; j <= longRango; j++) {

                    Object elem = rango.recuperar(j);

                    if (lista.localizar(elem) < 0) {
                        lista.insertar(elem, lista.longitud() + 1);
                    }
                }

                aux = aux.getEnlace();
            }
        }

        return lista;
    }

    public boolean esVacio() {
        return this.cant == 0;
    }

    public void vaciar() {
        this.tabla = new NodoHashMapeoM[TAM];
        this.cant = 0;
    }

    public MapeoAMuchos clone() {
        MapeoAMuchos clon = new MapeoAMuchos();

        for (int i = 0; i < TAM; i++) {

            NodoHashMapeoM aux = this.tabla[i];
            NodoHashMapeoM ult = null;

            while (aux != null) {

                NodoHashMapeoM nuevo = new NodoHashMapeoM(aux.getDominio(), null);
                nuevo.setRango(aux.getRango().clone());

                if (clon.tabla[i] == null) {
                    clon.tabla[i] = nuevo;
                } else {
                    ult.setEnlace(nuevo);
                }

                ult = nuevo;
                aux = aux.getEnlace();
            }
        }

        clon.cant = this.cant;

        return clon;
    }

    public String toString() {
        String s = "";

        for (int i = 0; i < TAM; i++) {

            NodoHashMapeoM aux = this.tabla[i];

            while (aux != null) {
                s += "(" + aux.getDominio() + "," + aux.getRango() + ")\n";
                aux = aux.getEnlace();
            }
        }

        return s;
    }

}
