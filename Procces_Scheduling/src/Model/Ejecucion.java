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

    public Ejecucion(Proceso p, int unidadTiempo) {
        super(unidadTiempo);
        this.p = p;
    }

    public Proceso getP() {
        return p;
    }

    public void setP(Proceso p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "Ejecucion: " + super.getUnidadTiempo() + " " + p;
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
