package rpgingles;

import Niveles.Nivel;

public class Personaje {

    // Datos del Usuario
    private String nombre;
    private String nivelIngles;
    private int edad;
    private int avanceDiario;
    private String tipoUsuario;
    private String sexo;

    // Estadísticas (Stats)
    private double resistencia;
    private double energia;
    private int fuerza;
    private long experiencia;
    private int nivelPersonaje;
    
    // Progreso del Juego
    private int maxNivelDesbloqueado;

    public Personaje(String nombre, String nivelIngles, int edad, int avanceDiario, String tipoUsuario, String sexo) {
        this.nombre = nombre;
        this.nivelIngles = nivelIngles;
        this.edad = edad;
        this.avanceDiario = avanceDiario;
        this.tipoUsuario = tipoUsuario;
        this.sexo = sexo;
        
        this.experiencia = 0;
        this.nivelPersonaje = 1;
        this.energia = 100.0;
        this.maxNivelDesbloqueado = 1;
        
        inicializarStatsPorNivel();
    }

    private void inicializarStatsPorNivel() {
        switch (this.nivelIngles) {
            case "Básico": this.resistencia = 100.0; this.fuerza = 10; break;
            case "Normal": this.resistencia = 110.0; this.fuerza = 12; break;
            case "Difícil": this.resistencia = 120.0; this.fuerza = 15; break;
            case "Migthy Force": this.resistencia = 150.0; this.fuerza = 20; break;
            default: this.resistencia = 100.0; this.fuerza = 10;
        }
    }

    // LÓGICA DE RECURSOS
    public void consumirResistencia(int costo) {
        this.resistencia -= costo;
        if (this.resistencia < 0) this.resistencia = 0;
    }

    public void perderEnergia(double cantidad) {
        this.energia -= cantidad;
        if (this.energia < 0) this.energia = 0;
    }

    public void restaurarEnergia() {
        this.energia = 100.0;
    }

    // LÓGICA DE PROGRESO
    public boolean puedeJugarNivel(Nivel nivel) {
        return !nivel.isBloqueado() && this.resistencia >= nivel.getCostoResistencia();
    }
    
    public int nivelCompletado(Nivel nivelCompletado) {
        if (nivelCompletado.getId() == this.maxNivelDesbloqueado) {
            this.maxNivelDesbloqueado++;
            return this.maxNivelDesbloqueado;
        }
        return -1;
    }

    // --- Getters para que la interfaz muestre los datos ---
    
    public String getNombre() { 
        return nombre; 
    }
    
    public double getResistencia() { 
        return resistencia; 
    }
    
    public int getMaxNivelDesbloqueado() { 
        return maxNivelDesbloqueado; 
    }
    
    /**
     * MÉTODO AÑADIDO: Permite que otras clases lean el nivel de inglés del personaje.
     */
    public String getNivelIngles() {
        return this.nivelIngles;
    }
}