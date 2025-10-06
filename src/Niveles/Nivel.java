package Niveles;

/**
 * Representa un único nivel en el mapa del juego.
 * Contiene información sobre su estado (bloqueado/desbloqueado),
 * el rendimiento del jugador (estrellas) y su costo.
 */
public class Nivel {

    private final int id; // Identificador numérico (1, 2, 3...)
    private final String nombre; // Nombre visible como "Valle de ratas"
    private boolean bloqueado;
    private int estrellasObtenidas; // Almacena de 0 a 3 estrellas
    private boolean estrellaSecretaObtenida; // true si se completó en menos de 35s
    private final int costoResistencia = 10; // Costo para empezar a jugar el nivel

    public Nivel(int id, String nombre, boolean bloqueado) {
        this.id = id;
        this.nombre = nombre;
        this.bloqueado = bloqueado;
        this.estrellasObtenidas = 0;
        this.estrellaSecretaObtenida = false;
    }

    // --- MÉTODOS DE CONSULTA (Getters) ---
    
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isBloqueado() { return bloqueado; }
    public int getEstrellasObtenidas() { return estrellasObtenidas; }
    public boolean isEstrellaSecretaObtenida() { return estrellaSecretaObtenida; }
    public int getCostoResistencia() { return costoResistencia; }

    // --- MÉTODOS DE ACCIÓN (Setters y lógica) ---

    /**
     * Marca el nivel como desbloqueado para que el jugador pueda acceder.
     */
    public void desbloquear() {
        this.bloqueado = false;
    }

    /**
     * Registra el resultado de una prueba, calculando las estrellas obtenidas.
     * @param puntaje El puntaje obtenido en la prueba (ej. 0-100).
     * @param tiempoSegundos El tiempo que tardó en completar la prueba.
     */
    public void registrarResultado(int puntaje, double tiempoSegundos) {
        // Asigna estrellas basado en el puntaje
        if (puntaje >= 95) {
            this.estrellasObtenidas = 3;
        } else if (puntaje >= 70) {
            this.estrellasObtenidas = 2;
        } else if (puntaje > 50) {
            this.estrellasObtenidas = 1;
        } else {
            this.estrellasObtenidas = 0;
        }
        
        // Verifica si se ganó la estrella secreta por tiempo
        if (tiempoSegundos < 35) {
            this.estrellaSecretaObtenida = true;
        }
    }
}