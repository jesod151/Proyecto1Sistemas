/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author USUARIO
 */
public class Scheduler {
    
    private ArrayList<Tiempo> lineaTiempo;
    private ArrayList<Proceso> procesos;
    private int tiempoTotal , modo;//0 = montonic, 1 = EFD

    public Scheduler() {
        this.lineaTiempo = new ArrayList();
        this.procesos = new ArrayList();
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
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
        
        for(Proceso p: this.procesos){p.generateColor();}
        if(this.modo == 0){
            return EjecutarMontonic();
        }
        else{
            return EjecutarEDF();
        }
    }
    
    private ArrayList<Tiempo> EjecutarEDF(){
        asignarDeadlines();
        Proceso toSort;
        int timeline = 0, nextEarliest;
        while(timeline < this.tiempoTotal){ 
            nextEarliest = this.getNextEarliestDeadline(timeline);
            if(nextEarliest < 0){
                timeline++;
                continue;
            }
            toSort = this.getProceso(nextEarliest);
            if(timeline + toSort.getTiempo() > tiempoTotal){
                timeline += toSort.getTiempo();
                break;
            }
            sortTiempo(new Ejecucion(toSort, timeline));
            timeline += toSort.getTiempo();
            this.setEjecutions(toSort);
        }
        return this.lineaTiempo;
    }
    
    private int getNextEarliestDeadline(int tiempo){
        Deadline dld = null;
        for(Tiempo t: this.lineaTiempo){
            if(t.isPeriodo() 
               && t.getUnidadTiempo() >= tiempo){
                for(Proceso p: this.procesos){
                    if(((Deadline) t).isPeriodoOf(p.getNumero()) 
                        && p.getEjecuciones() < ((Deadline) t).getMultiploOfProceso(p.getNumero())){
                        dld = (Deadline) t;
                        break;
                    }
                }
                if(dld != null){
                    break;
                }
            }
        }
        
        if(dld == null){
            return -1;
        }
        if(dld.getProcesos().size() == 1){
            return dld.getProcesos().get(0);
        }
        else{            
            ArrayList<Integer> result = new ArrayList();
            int max = -1;
            for(int i = 0; i < dld.getProcesos().size(); i++){
                result.add(dld.getMultiploPeriodo().get(i) - this.getProceso(dld.getProcesos().get(i)).getEjecuciones());
                if(dld.getUnidadTiempo() - this.getProceso(dld.getProcesos().get(i)).getDeadline() > tiempo){
                    result.set(i, 0);
                }
                if(result.get(i) > max){
                    max = result.get(i);
                }
            }
            if(max <= 0){
                return -1;
            }
            Random r = new Random();
            while(true){
                for(int i = 0; i < result.size(); i++){
                    if(result.get(i) > 0  && r.nextDouble() >= 0.5){
                        return dld.getProcesos().get(i);
                    }
                }
            }
        }
    }
    
    private ArrayList<Tiempo> EjecutarMontonic(){
        return null;
    }
    
    public void setEjecutions(Proceso p){
    
        int multiplo = 1;
        p.setEjecuciones(0);
        for(Tiempo t: this.lineaTiempo){
            if(!t.isPeriodo() && ((Ejecucion) t).getP().getNumero() == p.getNumero()){
                if(((Ejecucion) t).getUnidadTiempo() + ((Ejecucion) t).getP().getTiempo() <= ((Ejecucion) t).getP().getDeadline() * multiplo){
                    p.addEjecucion();
                    multiplo++;
                }
                else{
                    p.addEjecucion();
                    p.addEjecucion();
                    multiplo++;
                    multiplo++;
                }
            }
        }
    }
    
    public String getInfo(){
        
        int multiplo;
        Ejecucion e;
        String result = "";
        DecimalFormat df = new DecimalFormat("####0.00");
        double missedDeadlinesPercent,
                       missedEjecutionsPercent,
                       ejecutionsPercent;
        for(Proceso p: this.procesos){
            p.setEjecuciones(0);
            multiplo = 1;
            for(Tiempo t: this.lineaTiempo){
                if(!t.isPeriodo() && ((Ejecucion) t).getP().getNumero() == p.getNumero()){
                    e = ((Ejecucion) t);
                    if(e.getUnidadTiempo() + e.getP().getTiempo() <= e.getP().getPeriodo() * multiplo){
                        p.addEjecucion();
                    }
                    else{
                        p.addEjecucionPerdida();
                    }
                    if(e.getUnidadTiempo() + e.getP().getTiempo() > e.getP().getDeadline() * multiplo){
                        p.addDeadlinePerdida();
                    }
                    multiplo++;
                }
            }
                missedDeadlinesPercent = 0;
                missedEjecutionsPercent  = 0;
                ejecutionsPercent = 0;
                         
                if(p.getDeadlinesPerdidas() > 0){
                    missedDeadlinesPercent = (double) p.getDeadlinesPerdidas() / (double) multiplo;
                }
                if(p.getEjecucionesPerdidas() > 0){
                    missedEjecutionsPercent = (double) p.getEjecucionesPerdidas() / (double) multiplo;  
                }
                if(p.getEjecuciones() > 0){
                    ejecutionsPercent = (double) p.getEjecuciones() / (double) multiplo;
                }
                result += p.toStringInforme() + "    ejecuciones esperadas: " + multiplo + "\n" + 
                        "    %deadlines perdidas: " + df.format(missedDeadlinesPercent) + "\n" + 
                        "    %ejecuciones a tiempo: " + df.format(ejecutionsPercent) + "\n" + 
                        "    %ejecuciones perdidas: " + df.format(missedEjecutionsPercent) + "\n";
        }
        return result;
    }
  
    private void asignarDeadlines(){
        
        int multiploTiempo = 1;
        
        for(Proceso p: this.procesos){
            while(p.getDeadline() * multiploTiempo <= this.tiempoTotal){
                sortTiempo(new Deadline(p.getDeadline() * multiploTiempo, multiploTiempo, p.getNumero()));
                multiploTiempo++;
            }
            multiploTiempo = 1;
        }
    }
    
    public int getMaxMultiple(){
    
        int max = -1;
        for(Tiempo t: this.lineaTiempo){
            if(t.isPeriodo() && ((Deadline) t).getMaxMultiplo() > max){
                max = ((Deadline) t).getMaxMultiplo();
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
                
                ((Deadline) this.lineaTiempo.get(i)).addProceso((Deadline) toSort);
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
