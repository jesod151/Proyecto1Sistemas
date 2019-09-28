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
    private int tiempoTotal, modo;//0 = montonic, 1 = EFD

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
        if(this.modo == 0){
            return EjecutarMontonic();
        }
        else{
            return EjecutarEDF();
        }
    }
    
    private ArrayList<Tiempo> EjecutarEDF(){
        asignarDeadlines();
        for(Proceso p: this.procesos){
            p.generateColor();
        }
        int multiplo = 1,
            maxMultiple = this.getMaxMultiple(),
            nextEarliest,
            timeline = 0;
        Proceso toSort;
        System.out.println("multiplo: " + multiplo + " maxMultiplo: " + maxMultiple);
        while(multiplo < maxMultiple){ 
            for(int i = 0; i < this.procesos.size(); i++){
                nextEarliest = this.getNextEarliestDeadline(timeline);
                if(nextEarliest < 0){
                    continue;
                }
                System.out.println("escoge: " + nextEarliest);
                toSort = this.getProceso(nextEarliest);
                sortTiempo(new Ejecucion(toSort, timeline));
                timeline += toSort.getTiempo();
                this.setEjecutions(toSort);
            }            
            multiplo++;
        }
        System.out.println("temrinaEDF");
        return this.lineaTiempo;
    }

    public void setEjecutions(Proceso p){
    
        int multiplo = 1;
        p.setEjecuciones(0);
        for(Tiempo t: this.lineaTiempo){
            if(!t.isPeriodo() && ((Ejecucion) t).getP().getNumero() == p.getNumero()){
                if(((Ejecucion) t).getUnidadTiempo() + ((Ejecucion) t).getP().getTiempo() <= ((Ejecucion) t).getP().getDeadline() * multiplo){
                    p.addEjecucion();
                }
                else{
                    p.addEjecucion();
                    p.addEjecucion();
                }
                multiplo++;
            }
        }
        System.out.println("P: " + p.getNumero() + " ejecuciones: " + p.getEjecuciones());
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
                    missedDeadlinesPercent = multiplo / p.getDeadlinesPerdidas();
                }
                if(p.getEjecucionesPerdidas() > 0){
                    missedEjecutionsPercent = multiplo / p.getEjecucionesPerdidas();
                    
                }
                if(p.getEjecuciones() > 0){
                    ejecutionsPercent = multiplo / p.getEjecuciones();
                }
                result += p.toStringInforme() + "    %deadlines perdidas: " + df.format(missedDeadlinesPercent) + "\n" + 
                        "    %ejecuciones: " + df.format(ejecutionsPercent) + "\n" + 
                        "    %ejecuciones perdidas: " + missedEjecutionsPercent + "\n";
        }
        return result;
    }
    
    private ArrayList<Tiempo> EjecutarMontonic(){
        return null;
    }
    
    private int getNextEarliestDeadline(int tiempo){
        int earliestDeadline = Integer.MAX_VALUE;
        Deadline tmp = null;
        for(Tiempo t: this.lineaTiempo){
            if(t.isPeriodo() 
               && t.getUnidadTiempo() >= tiempo 
               && t.getUnidadTiempo() < earliestDeadline){
                for(Proceso p: this.procesos){
                    if(((Deadline) t).isPeriodoOf(p.getNumero()) 
                        && p.getEjecuciones() < ((Deadline) t).getMultiploOfProceso(p.getNumero())){
                        earliestDeadline = t.getUnidadTiempo();
                        tmp = (Deadline) t;
                    }
                }
            }
        }
        
        if(tmp == null){
            return -1;
        }
        else if(tmp.getProcesos().size() == 1){
            return tmp.getProcesos().get(0);
        }
        else{            
            ArrayList<Integer> result = new ArrayList();
            for(int i = 0; i < tmp.getMultiploPeriodo().size(); i++){
                result.add(tmp.getMultiploPeriodo().get(i) - this.getProceso(tmp.getProcesos().get(i)).getEjecuciones());
            }
            int max = -1;
            for(int i: result){
                if(i > max){
                    max = i;
                }
            }
            ArrayList<Integer> procesos = new ArrayList();
            ArrayList<Integer> multiplos = new ArrayList();
            for(int i = 0; i < tmp.getProcesos().size(); i++){
                procesos.add(tmp.getProcesos().get(i));
                multiplos.add(tmp.getMultiploPeriodo().get(i));
            }
            for(int i = 0; i < result.size(); i++){
                if(result.get(i) != max){
                    result.remove(i);
                    procesos.remove(i);
                    multiplos.remove(i);
                    i = 0;
                }
            }
            Random r = new Random();
            max = r.nextInt(result.size());
            return procesos.get(max);
        }
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
