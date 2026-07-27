package EscapeHouse;

public class Habitacion implements Comparable {
    private String codigo;
    private String nombre;
    private int planta;
    private double metrosCuadrados;
    private boolean tieneSalida;
    private boolean esEntrada; // para saber si es de entrada por que "Para las
    // habitaciones solo se pueden agregar intermedias,
    // las habitaciones de entrada y salida no cambian."?
    private ArbolAVL desafios;

    public Habitacion(String codigo, String nombre, int planta, double metrosCuadrados, boolean tieneSalida,
            boolean esEntrada) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.planta = planta;
        this.metrosCuadrados = metrosCuadrados;
        this.tieneSalida = tieneSalida;
        this.esEntrada = esEntrada;
        this.desafios = new ArbolAVL();
    }

    public Habitacion(String codigo) {
        this.codigo = codigo;
        this.nombre = "";
        this.planta = 0;
        this.metrosCuadrados = 0;
        this.tieneSalida = false;
        this.esEntrada = false;
        this.desafios = new ArbolAVL();
    }

    public String getCodigo() {
        return codigo;
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

    public boolean tieneSalida() {
        return tieneSalida;
    }

    public ArbolAVL getDesafios() {
        return desafios;
    }

    public void setDesafios(ArbolAVL desafios) {
        this.desafios = desafios;
    }

    public boolean esEntrada() {
        return esEntrada;
    }

    public boolean esIntermedia() {
        return !this.esEntrada && !this.tieneSalida;
    }

    public int compareTo(Object o) {
        return this.codigo.compareTo(((Habitacion) o).getCodigo());
    }

    @Override
    public String toString() {
        String s = "";

        s += "Código: " + this.codigo + "\n";
        s += "Nombre: " + this.nombre + "\n";
        s += "Planta: " + this.planta + "\n";
        s += "Metros cuadrados: " + this.metrosCuadrados + "\n";
        s += "Entrada: " + this.esEntrada + "\n";
        s += "Salida: " + this.tieneSalida + "\n";
        s += "Desafíos:\n";
        s += desafios.listar();

        return s;
    }

}
