/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author USUARIO
 */
public class Deadline extends Tiempo{
    
    private ArrayList<Integer> procesos, multiploPeriodo;
    private ArrayList<Boolean> isChecked;

    public Deadline(int unidadTiempo, int multiploPeriodo, int proceso) {
        super(unidadTiempo);
        this.multiploPeriodo = new ArrayList();
        this.multiploPeriodo.add(multiploPeriodo);
        this.procesos = new ArrayList();
        this.procesos.add(proceso);
        this.isChecked = new ArrayList();
        this.isChecked.add(false);
    }

    public ArrayList<Integer> getProcesos() {
        return procesos;
    }

    public void setProcesos(ArrayList<Integer> procesos) {
        this.procesos = procesos;
    }
    
    public void addProceso(Deadline t){
        this.procesos.add(t.getProcesos().get(0));
        this.multiploPeriodo.add(t.getMultiploPeriodo().get(0));
        this.isChecked.add(false);
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
    
    public boolean isDeadlineOf(int proceso) {
        return this.procesos.contains(proceso);
    }
    
    public Boolean isChecked(int proceso){
        return this.isChecked.get(this.procesos.indexOf(proceso));
    }
    
    public void check(int proceso){
        this.isChecked.set(this.procesos.indexOf(proceso), true);
    }
    
    public boolean allChecked(){
        for(boolean b: this.isChecked){
            if(b == false){
                return false;
            }
        }
        return true;
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
        return "Deadline t: " + this.getUnidadTiempo() + " deadline: " + super.getUnidadTiempo() + " procesos: " + procesos;
    }
    
    
    public String toStrinG() {
        return "Deadline t: " + super.getUnidadTiempo() + " procesos: " + procesos + " multiplos: " + multiploPeriodo;
    }
    
    @Override
    public boolean isDeadline() {
        return true;
    }

    @Override
    public boolean isPeriodo() {
        return false;
    }

    @Override
    public boolean isEjecucion() {
        return false;
    }
}
