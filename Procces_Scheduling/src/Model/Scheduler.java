/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author USUARIO
 */
public class Scheduler {
    
    private ArrayList<Tiempo> lineaTiempo;
    private ArrayList<Proceso> procesos;
    private int tiempoTotal, modo;//0 = montonic, 1 = EFD

    public Scheduler(ArrayList<Proceso> procesos, int tiempoTotal, int modo) {
        this.lineaTiempo = new ArrayList();
        this.procesos = procesos;
        this.tiempoTotal = tiempoTotal;
        this.modo = modo;
    }

    public ArrayList<Tiempo> getLineaTiempo() {
        return lineaTiempo;
    }

    public void setLineaTiempo(ArrayList<Tiempo> lineaTiempo) {
        this.lineaTiempo = lineaTiempo;
    }

    public ArrayList<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }

    public int getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(int tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }
    
    public ArrayList<Tiempo> ejecutar(){
        if(this.modo == 0){
            return EjecutarMontonic();
        }
        else{
            return EjecutarEDF();
        }
    }
    
    private ArrayList<Tiempo> EjecutarEDF(){
        asignarPeriodos();
        
        int multiplo = 1,
            maxMultiple = this.getMaxMultiple(),
            nextEarliest,
            timeline = 0;
        Proceso toSort;
        
        while(multiplo < maxMultiple){            
            for(int i = 0; i < this.procesos.size(); i++){
                nextEarliest = this.getNextEarliestDeadline2(timeline, multiplo);
                if(nextEarliest < 0){
                    continue;
                }
                toSort = this.getProceso(nextEarliest);
                sortTiempo(new Ejecucion(toSort, timeline));
                timeline += toSort.getTiempo();
                toSort.addEjecucion();
            }            
            multiplo++;
        }
        
        return this.lineaTiempo;
    }
    
    private ArrayList<Tiempo> EjecutarMontonic(){
        return null;
    }
    

    
    private int getNextEarliestDeadline2(int tiempo, int multiplo){
        int earliestDeadline = Integer.MAX_VALUE, procesNumber = -1;
        for(Tiempo t: this.lineaTiempo){
            if(t.isPeriodo() 
               && t.getUnidadTiempo() >= tiempo 
               && t.getUnidadTiempo() < earliestDeadline){
                for(Proceso p: this.procesos){
                    if(((Periodo) t).isPeriodoOf(p.getNumero(), multiplo) 
                        && p.getEjecuciones() < ((Periodo) t).getMultiploOfProceso(p.getNumero())){
                        procesNumber = p.getNumero();
                        earliestDeadline = t.getUnidadTiempo();
                    }
                }
            }
        }
        return procesNumber;
    
    }
    
    private void asignarPeriodos(){
        
        int multiploTiempo = 1;
        
        for(Proceso p: this.procesos){
            while(p.getPeriodo() * multiploTiempo <= this.tiempoTotal){
                sortTiempo(new Periodo(p.getPeriodo() * multiploTiempo, multiploTiempo, p.getNumero()));
                multiploTiempo++;
            }
            multiploTiempo = 1;
        }
    }
    
    private int getMaxMultiple(){
    
        int max = -1;
        for(Tiempo t: this.lineaTiempo){
            if(t.isPeriodo() && ((Periodo) t).getMaxMultiplo() > max){
                max = ((Periodo) t).getMaxMultiplo();
            }
        }
        return max;
    }
    
    private void sortTiempo(Tiempo toSort){
        for(int i = 0; i < this.lineaTiempo.size(); i++){
            
            if(toSort.getUnidadTiempo() < this.lineaTiempo.get(i).getUnidadTiempo()){
                this.lineaTiempo.add(i, toSort);
                return;
            }
            else if(toSort.isPeriodo() 
                    && this.lineaTiempo.get(i).isPeriodo()
                    && this.lineaTiempo.get(i).getUnidadTiempo() == toSort.getUnidadTiempo()){
                
                ((Periodo) this.lineaTiempo.get(i)).addProceso((Periodo) toSort);
                return;
            }
        }
        this.lineaTiempo.add(toSort);
    
    }
    
    public void printLineaTiempo(){
        for(Tiempo t: lineaTiempo){
            System.out.println(t.toString());
        }
    }

    private Proceso getProceso(int nextEarliest) {
        for(int i = 0; i < this.procesos.size(); i++){
            if(this.procesos.get(i).getNumero() == nextEarliest){
                return this.procesos.get(i);
            }
        }
        return null;
    }
    
    
    
    
    
}
