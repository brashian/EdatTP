package EscapeHouse;
public class NodoConexion {

    private NodoHabitacion habitacionDestino;
    private int puntajeMinimo;
    private NodoConexion sigConexion;

    public NodoConexion(NodoHabitacion habitacionDestino,
            int puntajeMinimo,
            NodoConexion sigConexion) {

        this.habitacionDestino = habitacionDestino;
        this.puntajeMinimo = puntajeMinimo;
        this.sigConexion = sigConexion;
    }

    public NodoHabitacion getHabitacionDestino() {
        return this.habitacionDestino;
    }

    public void setHabitacionDestino(NodoHabitacion habitacionDestino) {
        this.habitacionDestino = habitacionDestino;
    }

    public int getPuntajeMinimo() {
        return this.puntajeMinimo;
    }

    public void setPuntajeMinimo(int puntajeMinimo) {
        this.puntajeMinimo = puntajeMinimo;
    }

    public NodoConexion getSigConexion() {
        return this.sigConexion;
    }

    public void setSigConexion(NodoConexion sigConexion) {
        this.sigConexion = sigConexion;
    }

}