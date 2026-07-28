package EscapeHouse;

public class Equipo {

    private String nombre;
    private int puntajeNecesario;
    private int puntajeTotal;
    private String habitacionActual;
    private int puntajeHabitacion;

    public Equipo(String nombre) {
        this.nombre = nombre;
        this.puntajeNecesario = 0;
        this.puntajeTotal = 0;
        this.habitacionActual = "";
        this.puntajeHabitacion = 0;
    }

    public Equipo(String nombre, int puntajeNecesario, String habitacionActual) {
        this.nombre = nombre;
        this.puntajeNecesario = puntajeNecesario;
        this.habitacionActual = habitacionActual;
        this.puntajeTotal = 0;
        this.puntajeHabitacion = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntajeNecesario() {
        return puntajeNecesario;
    }

    public void setPuntajeNecesario(int puntajeNecesario) {
        this.puntajeNecesario = puntajeNecesario;
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public String getHabitacionActual() {
        return habitacionActual;
    }

    public void setHabitacionActual(String habitacionActual) {
        this.habitacionActual = habitacionActual;
    }

    public int getPuntajeHabitacion() {
        return puntajeHabitacion;
    }

    public void setPuntajeHabitacion(int puntajeHabitacion) {
        this.puntajeHabitacion = puntajeHabitacion;
    }


    public String toString(){
        return "Equipo: "+ this.nombre + "\n"+
               "Habitacion actual: "+this.habitacionActual + "\n"+
               "Puntos sala: "+this.puntajeHabitacion+ "\n"+
               "Total : " + this.puntajeTotal + "\n"+
               "necesario pts: " + this.puntajeNecesario ;
    }


}
