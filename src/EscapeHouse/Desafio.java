package EscapeHouse;

public class Desafio implements Comparable {
    private int puntaje;
    private String nombre;
    private String tipo;

    public Desafio(int puntaje, String nombre, String tipo) {
        this.puntaje = puntaje;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Desafio(int puntaje) {
        this.puntaje = puntaje;
        this.nombre = "";
        this.tipo = "";
    }

    public int getPuntaje() {
        return puntaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int compareTo(Object o) {
        return this.puntaje - ((Desafio) o).puntaje;
    }

    @Override
    public String toString() {
        String s = "";

        s += "Puntaje: " + this.puntaje + "\n";
        s += "Nombre: " + this.nombre + "\n";
        s += "Tipo: " + this.tipo + "\n";

        return s;
    }

}
