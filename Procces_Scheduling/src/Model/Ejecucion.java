/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class Ejecucion extends Tiempo{
    
    private Proceso p;
    private int remainning, excecutedTime;
    

    public Ejecucion(Proceso p, int unidadTiempo) {
        super(unidadTiempo);
        this.p = p;
        this.excecutedTime = p.getTiempo();
    }
    
    public Ejecucion(Proceso p, int unidadTiempo, int remaining){
        super(unidadTiempo);
        this.p = p;
        this.remainning = remaining;
        this.excecutedTime = 0;
    }

    public int getRemainning() {
        return remainning;
    }

    public void setRemainning(int remainning) {
        this.remainning = remainning;
    }

    public int getExcecutedTime() {
        return excecutedTime;
    }

    public void setExcecutedTime(int excecutedTime) {
        this.excecutedTime = excecutedTime;
    }
    
    
    
    public void succRemaining(){
        this.remainning--;
        this.excecutedTime++;
    }
    
    public boolean isReady(){
        return this.remainning == 0;
    }

    public Proceso getP() {
        return p;
    }

    public void setP(Proceso p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "Ejec: " + this.getUnidadTiempo() + " " + p + " re: " + remainning + " exT: " + excecutedTime;
    }
    
    @Override
    public boolean isDeadline() {
        return false;
    }

    @Override
    public boolean isPeriodo() {
        return false;
    }

    @Override
    public boolean isEjecucion() {
        return true;
    }

    @Override
    public ArrayList<Integer> getProcesos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Integer> getMultiploPeriodo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
