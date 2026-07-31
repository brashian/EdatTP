package EscapeHouse;
public class NodoHabitacion {

    private Object codigo;
    private NodoHabitacion sigHabitacion;
    private NodoConexion primeraConexion;

    public NodoHabitacion(Object codigo, NodoHabitacion sigHabitacion) {
        this.codigo = codigo;
        this.sigHabitacion = sigHabitacion;
        this.primeraConexion = null;
    }

    public Object getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Object codigo) {
        this.codigo = codigo;
    }

    public NodoHabitacion getSigHabitacion() {
        return this.sigHabitacion;
    }

    public void setSigHabitacion(NodoHabitacion sigHabitacion) {
        this.sigHabitacion = sigHabitacion;
    }

    public NodoConexion getPrimeraConexion() {
        return this.primeraConexion;
    }

    public void setPrimeraConexion(NodoConexion primeraConexion) {
        this.primeraConexion = primeraConexion;
    }

}