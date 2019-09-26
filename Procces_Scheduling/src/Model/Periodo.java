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
public class Periodo extends Tiempo{
    
    private ArrayList<Integer> procesos, multiploPeriodo;

    public Periodo(int unidadTiempo, int multiploPeriodo, int proceso) {
        super(unidadTiempo);
        this.multiploPeriodo = new ArrayList();
        this.multiploPeriodo.add(multiploPeriodo);
        this.procesos = new ArrayList();
        this.procesos.add(proceso);
    }

    public ArrayList<Integer> getProcesos() {
        return procesos;
    }

    public void setProcesos(ArrayList<Integer> procesos) {
        this.procesos = procesos;
    }
    
    public void addProceso(Periodo t){
        this.procesos.add(t.getProcesos().get(0));
        this.multiploPeriodo.add(t.getMultiploPeriodo().get(0));
    }

    public ArrayList<Integer> getMultiploPeriodo() {
        return multiploPeriodo;
    }

    public void setMultiploPeriodo(ArrayList<Integer> multiploPeriodo) {
        this.multiploPeriodo = multiploPeriodo;
    }
    
    public int getMultiploOfProceso(int proceso){
        return this.multiploPeriodo.get(this.procesos.indexOf(proceso));
    }
    

    @Override
    public boolean isPeriodo() {
        return true;
    }

    public boolean isPeriodoOf(int proceso, int multiplo) {
        /*if(this.procesos.indexOf(proceso) < 0){
            return false;
        }
        return this.multiploPeriodo.get(this.procesos.indexOf(proceso)) >= multiplo;*/
        return this.procesos.contains(proceso);
    }
    
    public int getMaxMultiplo(){
        int max = -1;
        for(int i: this.multiploPeriodo){
            if(i > max){
                max = i;
            }
        }
        return max;
    }

    @Override
    public String toString() {
        return "Multiplo: " + this.multiploPeriodo + " Periodo: " + super.getUnidadTiempo() + " procesos: " + procesos;
    }
    
    
    
    
}
