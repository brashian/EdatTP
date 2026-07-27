public class Diccionario {

    private static final int TAM = 13;
    private NodoHashDicc[] tabla;
    private int cant = 0;

    public Diccionario() {
        this.tabla = new NodoHashDicc[TAM];
    }

    private int funcionHash(Object dominio) {
        return Math.floorMod(dominio.hashCode(), TAM);
    }

    public boolean insertar(Object clave, Object dato) {
        int pos = funcionHash(clave);
        NodoHashDicc aux = this.tabla[pos];
        boolean encontrado = false;

        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }

        if (!encontrado) {
            this.tabla[pos] = new NodoHashDicc(clave, dato, this.tabla[pos]);
            this.cant++;
        }

        return !encontrado;
    }

    public boolean eliminar(Object clave) {
        int pos = funcionHash(clave);
        NodoHashDicc aux = this.tabla[pos];
        boolean encontrado = false;
        NodoHashDicc prev = null;

        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            if (!encontrado) {
                prev = aux;
                aux = aux.getEnlace();
            }

        }
        if (encontrado) {
            if (prev != null) {
                prev.setEnlace(aux.getEnlace());
            } else {
                this.tabla[pos] = aux.getEnlace();
            }
            this.cant--;
        }

        return encontrado;
    }

    public boolean existeClave(Object clave) {
        int pos = funcionHash(clave);
        NodoHashDicc aux = this.tabla[pos];
        boolean encontrado = false;

        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            aux = aux.getEnlace();
        }
        return encontrado;
    }

    public Object obtenerInformacion(Object clave) {
        Object dato = null;
        int pos = funcionHash(clave);
        NodoHashDicc aux = this.tabla[pos];
        boolean encontrado = false;
        while (!encontrado && aux != null) {
            encontrado = aux.getClave().equals(clave);
            if (!encontrado)
                aux = aux.getEnlace();
        }
        if (encontrado) {
            dato = aux.getDato();
        }
        return dato;
    }

    // No quedarian ordenados los listar, creo que si es requisito, habria que
    // buscar la manera de
    // insertar manteniendo el orden o extraer todo y ordenar al final (Estilo
    // QuickSort/MergeSort)

    public Lista listarClaves() {
        Lista lista = new Lista();
        for (int i = 0; i < TAM; i++) {
            NodoHashDicc aux = this.tabla[i];
            while (aux != null) {
                lista.insertar(aux.getClave(), lista.longitud() + 1);
                aux = aux.getEnlace();
            }
        }
        return lista;
    }

    public Lista listarDatos() {
        Lista lista = new Lista();
        for (int i = 0; i < TAM; i++) {
            NodoHashDicc aux = this.tabla[i];
            while (aux != null) {
                lista.insertar(aux.getDato(), lista.longitud() + 1);
                aux = aux.getEnlace();
            }
        }
        return lista;
    }

    public boolean esVacio() {
        return this.cant == 0;
    }

    public void vaciar() {
        this.tabla = new NodoHashDicc[TAM];
        this.cant = 0;
    }

    public Diccionario clone() {
        Diccionario clon = new Diccionario();

        for (int i = 0; i < TAM; i++) {

            NodoHashDicc aux = this.tabla[i];
            NodoHashDicc ult = null;

            while (aux != null) {

                NodoHashDicc nuevo = new NodoHashDicc(aux.getClave(), aux.getDato(), null);

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

            NodoHashDicc aux = this.tabla[i];

            while (aux != null) {
                s += aux.getClave() + " --> " + aux.getDato() + "\n";
                aux = aux.getEnlace();
            }
        }

        return s;
    }

}
