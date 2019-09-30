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
        asignarDeadlinesPeriodos();
        Proceso toSort;
        int timeline = 0, nextEarliest;
        while(timeline < this.tiempoTotal){ 
            nextEarliest = this.nextProcess(getNextEarliestDeadline(timeline), timeline);
            //System.out.println("next earliest: " + nextEarliest);
            //System.out.println("------------------------------------------------");
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
    
    private Tiempo getNextEarliestDeadline(int tiempo){
        
        //System.out.println("tiempo: " + tiempo);
        for(Tiempo nextDeadline: this.lineaTiempo){
            if(nextDeadline.isDeadline() 
               && nextDeadline.getUnidadTiempo() >= tiempo){
                for(Proceso p: this.procesos){
                    if(((Deadline) nextDeadline).isDeadlineOf(p.getNumero()) 
                        && p.getEjecuciones() < ((Deadline) nextDeadline).getMultiploOfProceso(p.getNumero())){
                        //System.out.println(p.toStringD());
                        for(Tiempo nextPeriodo: this.lineaTiempo){
                            if(nextPeriodo.isPeriodo()
                               && nextPeriodo.getUnidadTiempo() > tiempo
                               && ((Periodo) nextPeriodo).isPeriodoOf(p.getNumero())
                               && ((Periodo) nextPeriodo).getUnidadTiempo() - p.getPeriodo() <= tiempo){
                                //System.out.println(((Deadline) nextDeadline).toStrinG());
                                //System.out.println(((Periodo) nextPeriodo).toStrinG());
                                if(((Periodo) nextPeriodo).getMultiploOfProceso(p.getNumero()) >= ((Deadline) nextDeadline).getMultiploOfProceso(p.getNumero())){
                                    return nextDeadline;
                                }
                                else if(((Periodo) nextPeriodo).getMultiploOfProceso(p.getNumero()) > p.getEjecuciones()){
                                    return nextPeriodo;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        for(Proceso p: this.procesos){//si no quedan más deadlines pero aún procesos sin ejecutar después de su último deadline(atrasados)
            for(Tiempo nextPeriodo: this.lineaTiempo){
                if(nextPeriodo.isPeriodo()
                   && nextPeriodo.getUnidadTiempo() > tiempo
                   && ((Periodo) nextPeriodo).isPeriodoOf(p.getNumero())
                   && ((Periodo) nextPeriodo).getUnidadTiempo() - p.getPeriodo() <= tiempo){
                    //System.out.println(((Periodo) nextPeriodo).toStrinG());
                    if(((Periodo) nextPeriodo).getMultiploOfProceso(p.getNumero()) > p.getEjecuciones()){
                        return nextPeriodo;
                    }
                }
            }
        }
        
        return null;
    }
    
    public int nextProcess(Tiempo t, int tiempo){

        if(t == null){
            return -1;
        }
               
        if(t.getProcesos().size() == 1){
            return t.getProcesos().get(0);
        }
        else{            
            ArrayList<Integer> result = new ArrayList();
            int max = -1;
            for(int i = 0; i < t.getProcesos().size(); i++){
                result.add(t.getMultiploPeriodo().get(i) - this.getProceso(t.getProcesos().get(i)).getEjecuciones());
                if(t.isDeadline() && t.getUnidadTiempo() - this.getProceso(t.getProcesos().get(i)).getDeadline() > tiempo){
                    result.set(i, 0);
                }
                else if(t.isPeriodo() && t.getUnidadTiempo() - this.getProceso(t.getProcesos().get(i)).getPeriodo() > tiempo){
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
                        return t.getProcesos().get(i);
                    }
                }
            }
        }
        
    
    }
    
    public void setEjecutions(Proceso p){
    
        int multiplo = 1;
        p.setEjecuciones(0);
        for(Tiempo t: this.lineaTiempo){
            if(t.isEjecucion() && ((Ejecucion) t).getP().getNumero() == p.getNumero()){
                if(((Ejecucion) t).getUnidadTiempo() + ((Ejecucion) t).getP().getTiempo() <= ((Ejecucion) t).getP().getPeriodo() * multiplo){
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
    
    private ArrayList<Tiempo> EjecutarMontonic(){
        return null;
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
                if(t.isEjecucion() && ((Ejecucion) t).getP().getNumero() == p.getNumero()){
                    e = ((Ejecucion) t);
                    if(e.getUnidadTiempo() + e.getP().getTiempo() <= e.getP().getPeriodo() * multiplo){
                        p.addEjecucion();
                    }
                    else{
                        p.addEjecucionPerdida();
                    }
                    if(e.getUnidadTiempo() + e.getP().getTiempo() > (p.getPeriodo() * multiplo - (p.getPeriodo() - p.getDeadline()))){
                        p.addDeadlinePerdida();
                    }
                    multiplo++;
                }
            }
            multiplo = getMaxMultipleOf(p.getNumero());
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
  
    private void asignarDeadlinesPeriodos(){
        
        int multiploTiempo = 1;
        
        for(Proceso p: this.procesos){
            while(p.getPeriodo() * multiploTiempo <= this.tiempoTotal){
                sortTiempo(new Deadline(p.getPeriodo() * multiploTiempo - (p.getPeriodo() - p.getDeadline()), multiploTiempo, p.getNumero()));
                sortTiempo(new Periodo(p.getPeriodo() * multiploTiempo, multiploTiempo, p.getNumero()));
                multiploTiempo++;
            }
            multiploTiempo = 1;
        }
    }
    
    /*public int getMaxMultiple(){
    
        int max = -1;
        for(Tiempo t: this.lineaTiempo){
            if(t.isDeadline() && ((Deadline) t).getMaxMultiplo() > max){
                max = ((Deadline) t).getMaxMultiplo();
            }
        }
        return max;
    }*/
    
    public int getMaxMultipleOf(int proceso){
    
        int max = -1;
        for(Tiempo t: this.lineaTiempo){
            if(t.isPeriodo() 
               && ((Periodo) t).isPeriodoOf(proceso)
               && ((Periodo) t).getMultiploOfProceso(proceso) > max){
                max = ((Periodo) t).getMultiploOfProceso(proceso);
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
            else if(toSort.isDeadline()
                    && this.lineaTiempo.get(i).isDeadline()
                    && this.lineaTiempo.get(i).getUnidadTiempo() == toSort.getUnidadTiempo()){
                
                ((Deadline) this.lineaTiempo.get(i)).addProceso((Deadline) toSort);
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
