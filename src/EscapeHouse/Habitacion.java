package EscapeHouse;

public class Habitacion implements Comparable {
    private Object codigo;
    private String nombre;
    private int planta;
    private double metrosCuadrados;
    private boolean tieneSalida;
    private ArbolAVL desafios;

    public Habitacion(Object codigo, String nombre, int planta, double metrosCuadrados, boolean tieneSalida) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metrosCuadrados = metrosCuadrados;
        this.tieneSalida = tieneSalida;
        this.desafios = new ArbolAVL();
    }

    // constructor "liviano", solo para buscar por codigo (compareTo solo mira
    // codigo)
    public Habitacion(Object codigo) {
        this.codigo = codigo;
        this.nombre = "";
        this.planta = 0;
        this.metrosCuadrados = 0;
        this.tieneSalida = false;
        this.desafios = new ArbolAVL();
    }

    public Object getCodigo() {
        return codigo;
    }

    public void setCodigo(Object codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPlanta() {
        return planta;
    }

    public void setPlanta(int planta) {
        this.planta = planta;
    }

    public double getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(double metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public boolean getTieneSalida() {
        return tieneSalida;
    }

    public void setTieneSalida(boolean tieneSalida) {
        this.tieneSalida = tieneSalida;
    }

    public ArbolAVL getDesafios() {
        return desafios;
    }

    public void setDesafios(ArbolAVL desafios) {
        this.desafios = desafios;
    }

    public int compareTo(Object o) {
        return this.codigo.toString().compareTo(((Habitacion) o).codigo.toString());
    }
}
