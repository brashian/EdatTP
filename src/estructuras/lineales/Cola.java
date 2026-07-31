package estructuras.lineales;

public class Cola {
    private Nodo frente;
    private Nodo fin;
    
    //Constructor
    public Cola(){
        this.frente=null;
        this.fin=null;
    }
    
    //metodos de Cola
    public boolean poner(Object elem){
        boolean exito = true;
        Nodo nuevoNodo = new Nodo(elem, null);
        if(this.frente == null){
            this.frente = nuevoNodo;
        } else{
            this.fin.setEnlace(nuevoNodo);          
        }
        this.fin = nuevoNodo;
        return exito;
    }
    
    public boolean sacar(){
        boolean exito = true;
        if(this.frente == null){
            //la cola esta vacía: reporta error
            exito = false;
        } else{
            //al menos hay un elemento:
            //quita el primer elemento y actualiza frente (y fin si queda vacía)
            this.frente = this.frente.getEnlace();
            if(this.frente == null){
                this.fin = null;
            }
        }
        return exito;
    }
    public Object obtenerFrente(){
        Object elem = null;
        if(this.frente != null){
            elem = this.frente.getElem();
        }
        return elem;
    }
    public boolean esVacia(){
        boolean vacia = false;
        if(this.frente == null){
            vacia = true;
        }
        return vacia;
    }
    public void vaciar(){
        this.frente = null;
        this.fin = null;
    }
    
    public Cola clone(){
        Cola clon=new Cola();
        if (this.frente!=null){
            Nodo auxOriginal = this.frente.getEnlace();
            Nodo aux= new Nodo(this.frente.getElem(),null);
            clon.frente=aux;
            clon.fin=aux;
            while (auxOriginal != null){
                Nodo auxAnterior = aux;
                aux = new Nodo(auxOriginal.getElem(),null);
                auxAnterior.setEnlace(aux);
                clon.fin=aux;
                auxOriginal = auxOriginal.getEnlace();
                
            }
        }
        
        return clon;
    }
    
    public String toString(){
        String s="";
        if (this.frente==null)
            s="[]";
        else{
            Nodo aux=this.frente;
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
}
