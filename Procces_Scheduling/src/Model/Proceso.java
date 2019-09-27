/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author USUARIO
 */
public class Proceso {
    
    private static int instances = 1;
    private int numero,
                periodo,
                deadline,
                tiempo,
                deadlinesPerdidas,
                ejecuciones,
                ejecucionesPerdidas;
    private Color color;

    public Proceso(int periodo, int deadline, int tiempo) {
        this.numero = instances;
        this.periodo = periodo;
        this.deadline = deadline;
        this.tiempo = tiempo;
        this.deadlinesPerdidas = 0;
        this.ejecuciones = 0;
        this.ejecucionesPerdidas = 0;
        this.generateColor();
        
        instances++;
    }

    public Proceso(int numero, int periodo, int deadline, int tiempo, Color color) {
        this.numero = numero;
        this.periodo = periodo;
        this.deadline = deadline;
        this.tiempo = tiempo;
        this.deadlinesPerdidas = 0;
        this.ejecuciones = 0;
        this.ejecucionesPerdidas = 0;
        this.color = color;
    }
    
    

    public int getPeriodo() {
        return periodo;
    }

    public void setPeriodo(int periodo) {
        this.periodo = periodo;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getDeadlinesPerdidas() {
        return deadlinesPerdidas;
    }

    public void setDeadlinesPerdidas(int deadlinesPerdidas) {
        this.deadlinesPerdidas = deadlinesPerdidas;
    }

    public int getEjecuciones() {
        return ejecuciones;
    }

    public void setEjecuciones(int ejecuciones) {
        this.ejecuciones = ejecuciones;
    }

    public int getEjecucionesPerdidas() {
        return ejecucionesPerdidas;
    }

    public void setEjecucionesPerdidas(int ejecucionesPerdidas) {
        this.ejecucionesPerdidas = ejecucionesPerdidas;
    }

    public int getNumero() {
        return numero;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public void addEjecucion(){
        this.ejecuciones++;
    }
    
    public void addEjecucionPerdida(){
        this.ejecucionesPerdidas++;
    }
    
    public void addDeadlinePerdida(){
        this.deadlinesPerdidas++;
    }
    
    public void missEjecucion(){
        this.ejecucionesPerdidas++;
    }
    
    public void generateColor(){
        Random r = new Random();
        this.color = new Color(r.nextInt(50),//r
                                r.nextInt(255),//g
                                r.nextInt(255));//b
    }

    
    
    public String toStringInforme() {
        return "Proceso: " + numero + "\n" +
               "    periodo: " + periodo + "\n" +
               "    deadline: " + deadline + "\n" +
               "    tiempo: " + tiempo + "\n" +
               "    ejecuciones: " + ejecuciones + "\n" +
               "    ejecuciones perdidas: " + ejecucionesPerdidas + "\n" + 
               "    deadlines perdidas: " + deadlinesPerdidas + "\n";
    }
    
    @Override
    public String toString(){
        return "Proceso: " + numero + " periodo: " + periodo + " deadline: " + deadline + " tiempo: " + tiempo;
    }
    
    public String toStringGrafico() {
        return "Proceso: " + numero;
    }

    
    
    
    
}
