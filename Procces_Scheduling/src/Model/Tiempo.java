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
public abstract class Tiempo {
    
    private int unidadTiempo;

    public Tiempo(int unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }

    public int getUnidadTiempo() {
        return unidadTiempo;
    }

    public void setUnidadTiempo(int unidadTiempo) {
        this.unidadTiempo = unidadTiempo;
    }
    
    public abstract boolean isDeadline();
    public abstract boolean isPeriodo();
    public abstract boolean isEjecucion();
    public abstract ArrayList<Integer> getProcesos();
    public abstract ArrayList<Integer> getMultiploPeriodo();
    
    
}
