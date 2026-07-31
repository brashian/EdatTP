package estructuras.lineales;

public class Lista {
    private Nodo cabecera;

    //CONSTRUCTRO
    public Lista(){
        this.cabecera=null;
    }

    //PROPIAS DEL TIPO
    public boolean insertar(Object nuevoElem, int pos){
        //Inserta el elemento nuevo en la posicion pos, detecta y reporta error posicion invalida
        boolean exito=true;

        if (pos<1 || pos>this.longitud()+1){
            exito=false;
        } else{
            if (pos==1){ //crea un nuevo nodo y se enlaza en la cabecera
                this.cabecera=new Nodo(nuevoElem, this.cabecera);
            } else{
                Nodo aux=this.cabecera;
                int i=1;
                while (i<pos-1){
                    aux=aux.getEnlace();
                    i++;
                }
                //crea el nodo y lo enlaza
                Nodo nuevo=new Nodo(nuevoElem, aux.getEnlace());
                aux.setEnlace(nuevo);
            }
        }
        //nunca hay error de lista llena, entonces devuelve true
        return exito;
    }

    public boolean eliminar(int pos){
        boolean exito=true;
        if (pos<1 || pos>this.longitud()){
            exito=false;
        } else{
            if (pos==1){
                this.cabecera=this.cabecera.getEnlace();
            } else{
                Nodo aux=this.cabecera;
                int i=1;
                while (i<pos-1){
                    aux=aux.getEnlace();
                    i++;
                }
                aux.setEnlace(aux.getEnlace().getEnlace());
            }
        }
        return exito;       
    }

    public Object recuperar(int pos){
        Object elemento=null;
        if (pos>=1 && pos<=this.longitud()){
            Nodo aux=this.cabecera;
            int i=1;
            while (i<pos){
                aux=aux.getEnlace();
                i++;
            }
            elemento=aux.getElem();
        }
        return elemento;
    }

    public int localizar(Object elem){
        int pos=1;
        Nodo aux=this.cabecera;
        boolean encontrado=false;
        while (!encontrado && aux!=null){
            if (elem==null){
                if (aux.getElem()==null){
                    encontrado=true;
                } else{
                    aux=aux.getEnlace();
                    pos++;
                }
            } else{
                if (elem.equals(aux.getElem())){
                    encontrado=true;
                } else{
                    aux=aux.getEnlace();
                    pos++;
                }
            }
        }
        if (!encontrado){
            pos=-1;
        }
        return pos;
    }

    public int longitud(){
        Nodo aux=this.cabecera;
        int contador=0;
        while (aux!=null){
            aux=aux.getEnlace();
            contador++;
        }
        return contador;
    }

    public void vaciar(){
        this.cabecera=null;
    }

    public boolean esVacia(){
        return this.cabecera==null;
    }

    public Lista clone(){
        Lista clon=new Lista();
        if (this.cabecera!=null){
            Nodo aux=this.cabecera.getEnlace();
            Nodo nuevo=new Nodo(this.cabecera.getElem(),null);
            clon.cabecera=nuevo;
            while (aux!=null){
                Nodo anterior=nuevo;
                nuevo=new Nodo(aux.getElem(),null);
                anterior.setEnlace(nuevo);
                aux=aux.getEnlace();
            }
        }
        return clon;
    }

    public String toString(){
        String s="";
        if (this.cabecera==null){
            s="[]";
        } else{
            Nodo aux=this.cabecera;
            s="[";

            while (aux!=null){
                s+=aux.getElem().toString();
                aux=aux.getEnlace();
                if (aux!=null)
                    s+=",";
            }
            s+="]";
        }
        return s;
    }

    /*
    4. Extienda el TDA Lista agregando las siguientes operaciones, cuidando la eficiencia y extentiendo los test
    del TDA:

    a) invertir : modifica la lista original para que los elementos aparezcan en orden invertido, haciendo un
    único recorrido de la estructura y sin usar estructuras auxiliares ni otras operaciones del TDA. Ej:
    si L=[1,2,3,4] debe devolverla modificada como [4,3,2,1]

    b) eliminarApariciones(TipoElemento x): elimina todas las apariciones de elementos iguales a x,
    haciendo un único recorrido de la estructura y sin usar otras operaciones del TDA. Ej: si L1=[1,2,1,3,4]
    debe devolver [2,3,4]. En los casos de prueba considere que el elemento a eliminar puede estar repetido
    varias veces en cualquier posición.
    */

    public void invertir(){
        Nodo prev = null;
        Nodo actual = this.cabecera;
        Nodo sig = null;

        while (actual != null){
            sig = actual.getEnlace();
            actual.setEnlace(prev);
            prev = actual;
            actual = sig;
        }

        this.cabecera = prev;
    }

    public void eliminarApariciones(Object x){
        Nodo aux = this.cabecera;
        Nodo prev = null;

        while (aux != null){
            if ((x.equals(aux.getElem()))){
                if (prev == null){
                    // eliminar cabecera
                    this.cabecera = aux.getEnlace();
                } else{
                    prev.setEnlace(aux.getEnlace());
                }
            } else{
                prev = aux;
            }
            aux = aux.getEnlace();
        }
    }
}