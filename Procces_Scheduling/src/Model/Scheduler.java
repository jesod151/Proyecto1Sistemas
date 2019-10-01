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
            this.setEjecutionsEDF(toSort);
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
    
    public void setEjecutionsEDF(Proceso p){
    
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
    
    private String getInfoEDF(){
        
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
                    if(e.getUnidadTiempo() + e.getP().getTiempo() > (p.getPeriodo() * multiplo) - (p.getPeriodo() - p.getDeadline())){
                        p.addDeadlinePerdida();
                    }
                    multiplo++;
                }
            }
            
            multiplo = 1;
            for(Tiempo t: this.lineaTiempo){
                if(t.isEjecucion() && ((Ejecucion) t).getP().getNumero() == p.getNumero()){
                    e = ((Ejecucion) t);
                    if(e.getUnidadTiempo() + e.getP().getTiempo() <= e.getP().getPeriodo() * multiplo){
                        p.addEjecucion();
                    }
                }
                if(t.isPeriodo() && ((Periodo) t).isPeriodoOf(p.getNumero())){
                    multiplo++;
                }
            }
            
            multiplo = getMaxMultipleOf(p.getNumero());
            p.setEjecucionesPerdidas(multiplo - p.getEjecuciones());
            missedDeadlinesPercent = 0;
            missedEjecutionsPercent  = multiplo - p.getEjecuciones();
            ejecutionsPercent = 0;

            if(p.getDeadlinesPerdidas() > 0){
                missedDeadlinesPercent = (double) (p.getDeadlinesPerdidas()) / (double) multiplo;
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
    
    private ArrayList<Tiempo> EjecutarMontonic(){
        asignarDeadlinesPeriodos();
        int timeline = 0;
        ArrayList<Ejecucion> stack = new ArrayList();
        Ejecucion inProcess, last;
        
        stack = checkForNewExecutions(stack, timeline);
        inProcess = getHighestPriority(stack, timeline);
        
        this.sortTiempo(inProcess);
        inProcess.succRemaining();
        if(inProcess.isReady()){
            stack.remove(inProcess);
            inProcess = null;
        }
        timeline++;
        last = inProcess;
        
        while(timeline < this.tiempoTotal){
            //System.out.println("-------------------------------------------");
            //System.out.println("t->" + timeline);
            //for(Ejecucion e:stack){
            //    System.out.println(e);
            //}
            stack = checkForNewExecutions(stack, timeline);
            inProcess = getHighestPriority(stack, timeline);

            if(inProcess == null){
                timeline++;
                continue;
            }
            //System.out.println("ejecutando: " + inProcess.toString());
            if(inProcess == last){
                inProcess.succRemaining();
                if(inProcess.isReady()){
                    stack.remove(inProcess);
                    inProcess = null;
                }
            }
            else{
                inProcess.setUnidadTiempo(timeline);
                this.sortTiempo(inProcess);
                inProcess.succRemaining();
                if(inProcess.isReady()){
                    stack.remove(inProcess);
                    inProcess = null;
                }
                stack.remove(last);
                if(last != null && !last.isReady()){
                    stack.add(new Ejecucion(last.getP(), timeline, last.getRemainning()));
                }
            }
            last = inProcess;
            timeline++;
        }
        
        return this.lineaTiempo;
    }
    
    private ArrayList<Ejecucion> checkForNewExecutions(ArrayList<Ejecucion> stack, int timeline) {
        //System.out.println("t: " + timeline);
        double ejecutions;
        for(Proceso p: this.procesos){
            ejecutions = this.getEjecucionesMontonic(p, timeline);
            for(Tiempo t: this.lineaTiempo){
                if(t.isPeriodo() 
                   && t.getUnidadTiempo() > timeline 
                   && ((Periodo) t).isPeriodoOf(p.getNumero())){
                    if(!this.contains(stack, p.getNumero()) && (double)((Periodo) t).getMultiploOfProceso(p.getNumero()) - ejecutions >= 1){
                        //System.out.println(((Periodo) t).getMultiploOfProceso(p.getNumero()) + " - " + ejecutions);
                        //System.out.println(p.toStringD() + " --- se añade al stack en tiempo: " + timeline);
                        stack.add(new Ejecucion(p, timeline, p.getTiempo()));
                    }
                    else{
                        break;
                    }
                }
                
            }
        }
        return stack;
    }
    
    public boolean contains(ArrayList<Ejecucion> stack, int p){
        for(Ejecucion c: stack){
            if(c.getP().getNumero() == p){
                return true;
            }
        }
        return false;
    }
    
    private double getEjecucionesMontonic(Proceso p, int timeline){
        
        int sumatoriaT = 0;
        for(Tiempo t: this.lineaTiempo){
            if(t.isEjecucion() && ((Ejecucion) t).getP().getNumero() == p.getNumero()){
                //sumatoriaT += p.getTiempo() - ((Ejecucion) t).getRemainning();
                sumatoriaT += ((Ejecucion) t).getExcecutedTime();
            }
            if(t.isPeriodo() && t.getUnidadTiempo() > timeline && ((Periodo) t).isPeriodoOf(p.getNumero())){
                return (double) sumatoriaT / p.getTiempo();
            }
        }
        return sumatoriaT;
    }
    
    private Ejecucion getHighestPriority(ArrayList<Ejecucion> stack, int time){
        if(stack.isEmpty()){
            return null;
        }
        
        int min = Integer.MAX_VALUE;
        ArrayList<Integer> minIndexes = new ArrayList();
        for(int i = 0; i < stack.size(); i++){
            if(stack.get(i).getRemainning() <= min){
                min = stack.get(i).getRemainning();
                minIndexes.add(i);
            }
        }

        Random r = new Random();
        while(true){
            for(int i: minIndexes){
                if(stack.get(i).getRemainning() == min && r.nextFloat() >= 0.5){
                    return stack.get(i);
                }
            }
        }
    }
    
    private String getInfoMontonic() {

        double ejecuciones;
        int multiplo;
        String result = "";
        DecimalFormat df = new DecimalFormat("####0.00");
        double missedDeadlinesPercent,
                       missedEjecutionsPercent,
                       ejecutionsPercent;
        for(Proceso p: this.procesos){    
            multiplo = 1;
            for(Tiempo t: this.lineaTiempo){
                if(t.isDeadline() && ((Deadline) t).isDeadlineOf(p.getNumero())){
                    ejecuciones = this.getEjecucionesMontonic(p, t.getUnidadTiempo());
                    if((double) multiplo - ejecuciones > 0){
                        p.addDeadlinePerdida();
                    }
                    multiplo++;
                }
            }
            
            multiplo = getMaxMultipleOf(p.getNumero());
            p.setEjecuciones((int) this.getEjecucionesMontonic(p, this.tiempoTotal) / p.getTiempo());
            p.setEjecucionesPerdidas(multiplo - p.getEjecuciones());
            missedDeadlinesPercent = 0;
            missedEjecutionsPercent  = multiplo - p.getEjecuciones();
            ejecutionsPercent = 0;

            if(p.getDeadlinesPerdidas() > 0){
                missedDeadlinesPercent = (double) (p.getDeadlinesPerdidas()) / (double) multiplo;
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
    
    public String getInfo(){
        if(this.modo == 0){
            return getInfoMontonic();
        }
        else{
            return getInfoEDF();
        }
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
