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
        for(Proceso p: this.procesos){p.generateColor();}
        if(this.modo == 0){
            return EjecutarMontonic();
        }
        else{
            return EjecutarEDF();
        }
    }
    
    private ArrayList<Tiempo> EjecutarEDF(){
        System.out.println("---------------------------------------------------");
        asignarDeadlines();
        for(Proceso p: this.procesos){
            p.generateColor();
        }
        int multiplo = 1,
            maxMultiple = this.getMaxMultiple(),
            nextEarliest,
            timeline = 0;
        Proceso toSort;
        while(multiplo <= maxMultiple){ 
            for(int i = 0; i < this.procesos.size(); i++){
                nextEarliest = this.getNextEarliestDeadline(timeline);
                System.out.println("nextEarliest: " + nextEarliest);
                if(nextEarliest < 0){
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
            multiplo++;
        }
        return this.lineaTiempo;
    }
    
    private int getNextEarliestDeadline(int tiempo){
        System.out.println("tiempo: " + tiempo);
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
        
        for(Proceso p: procesos){
            System.out.print("p: " + p.getNumero() + " ejecuciones: " + p.getEjecuciones() + "--");
        }
        System.out.println("");
        if(tmp != null){
            System.out.println(tmp.toStrinG());
        }
        
        if(tmp == null){
            return -1;
        }
        if(tmp.getProcesos().size() == 1){
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
            System.out.println("max--->" + max);
            Random r = new Random();
            while(true){
                for(int i = 0; i < result.size(); i++){
                    if(result.get(i) == max  && r.nextDouble() >= 0.5){
                        return tmp.getProcesos().get(i);
                    }
                }
            }
        }
    }
    
    private ArrayList<Tiempo> EjecutarEDF2(){
        asignarDeadlines();
        
        int nextEarliest, timeline = 0;
        Proceso toSort;
        while(timeline < tiempoTotal){ 
            for(int i = 0; i < this.procesos.size(); i++){
                nextEarliest = this.getNextEarliestDeadline2(timeline);System.out.println("nextEarliest: " + nextEarliest);System.out.println("");
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
        }
        return this.lineaTiempo;
    }
    
    private int getNextEarliestDeadline2(int tiempo){
        
        Deadline tmp = null;
        for(Tiempo t: this.lineaTiempo){
            if(t.isPeriodo() 
               && t.getUnidadTiempo() >= tiempo){
                tmp = (Deadline) t;
                break;
            }
        }
        if(tmp == null){
            return -1;
        }
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
        
        if(max == 0 && tiempo != tmp.getUnidadTiempo()){
            return -1;
        }
        
        Random r = new Random();
        while(true){
            for(int i = 0; i < result.size(); i++){
                if(result.get(i) == max && r.nextDouble() >= 0.5){
                    return tmp.getProcesos().get(i);
                }
            }
        }
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
                //multiplo++;
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
