/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procces_scheduling;

import Model.Proceso;
import Model.Scheduler;
import Model.Tiempo;
import View.GUI;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Jil
 */
public class Menu {

    private Scanner in;
    private String response;
    private ArrayList<Proceso> procesos;
    private Scheduler scheduler;
    private int modo = -1, rango = -1;
    
    public Menu() {
        procesos = new ArrayList();
        scheduler = new Scheduler();
        in = new Scanner(System.in);
        inicio();
    }
    
    public void inicio(){
        do{
            System.out.print("Para menu m / para interfaz i / exit para salir: ");
            response = in.nextLine();

            if(response.equals("m")){
                opciones();
                return;
            }
            else if(response.equals("i")){
                GUI gui = new GUI();
                gui.show(true);
                return;
            }
            else if(response.trim().toUpperCase().equals("EXIT")){
                System.out.println("cya");
                return;
            }
        }while(true);
    }
    
    public void opciones(){
        
        int r;
        do{
            System.out.println("1-Ingresar proceso");
            System.out.println("2-Borrar proceso");
            System.out.println("3-Listar procesos");
            System.out.println("4-Elegir algoritmo de scheduling 0 = Rate Montonic, 1 = EDF");
            System.out.println("5-Rango de tiempo");
            System.out.println("6-Ejecutar");
            System.out.println("7-Salir");
            System.out.print("--->");
            
            try{
                r = Integer.parseInt(in.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
              System.out.println("la respuesta debe ser un número");
              continue;
            }
            switch(r){
                case 1:
                    ingresarProceso();
                    break;
                case 2:
                    borrarProceso();
                    break;
                case 3:
                    listadoDeProcesos();
                    break;
                case 4:
                    elegirAlgoritmo();
                    break;
                case 5:
                    rangoTiempo();
                    break;
                case 6:
                    ejecutar();
                    break;
                case 7:
                    System.out.println("cya");
                    return;
            }
        }while(true);
    }
    
    public void ingresarProceso(){
        
        int p, d, t;
        do{
            try{
                System.out.println("ingrese los datos para el proceso");
                System.out.print("Periodo:");
                p = Integer.parseInt(in.nextLine().trim());
                System.out.print("Deadline:");
                d = Integer.parseInt(in.nextLine().trim());
                System.out.print("Tiempo:");
                t = Integer.parseInt(in.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
              System.out.println("la respuesta debe ser un número");
              continue;
            }
            
            this.procesos.add(new Proceso(p, d, t));
            return;
        }while(true);
    }
    
    public void borrarProceso(){
    
        int r;
        do{
            try{
                System.out.println("ingrese el numero del proceso a borrar");
                System.out.print("--->");
                r = Integer.parseInt(in.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
              System.out.println("la respuesta debe ser un número");
              continue;
            }
            
            for(Proceso p: this.procesos){
                if(p.getNumero() == r){
                    procesos.remove(p);
                    System.out.println("se borró el proceso");
                    return;
                }
            }
            System.out.println("no se encontró el proceso");
            return;
        }while(true);
    }
    
    public void listadoDeProcesos(){
        System.out.println("----------------------------------------------");
        for(Proceso p: procesos){
            System.out.println(p.toStringGrafico());
        }
        System.out.println("----------------------------------------------");
    }
    
    public void elegirAlgoritmo(){
    
        int r;
        do{
            try{
                System.out.println("Ingrese el modo de ordenamiento 0 = Rate Montonic, 1 = EDF");
                System.out.print("--->");
                r = Integer.parseInt(in.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
              System.out.println("la respuesta debe ser un número");
              continue;
            }
            
            if(r == 1){
                System.out.println("EDF");
            }
            else{
                System.out.println("Rate montonic");
            }
            modo = r;
            return;
        }while(true);
    }
    
    private void rangoTiempo() {
        int r;
        do{
            try{
                System.out.println("Ingrese el rango");
                System.out.print("--->");
                r = Integer.parseInt(in.nextLine().trim());
            }
            catch (NumberFormatException e)
            {
              System.out.println("la respuesta debe ser un número");
              continue;
            }
            
            rango = r;
            return;
        }while(true);
    }
    
    private void ejecutar() {
        if(modo < 0 | rango < 0 | this.procesos.size() == 0){
            System.out.println("no se pudo iniciar la ejecución, debe seleccionar modo tiempo total y por lo menos 1 proceso para iniciar");
            return;
        }
        this.scheduler = new Scheduler();
        this.scheduler.setProcesos(cloneProcesos());
        this.scheduler.setModo(modo);
        this.scheduler.setTiempoTotal(rango);
        this.scheduler.ejecutar();
        System.out.println("");
        System.out.println("");
        System.out.println("-----------Ejecución-----------");
        for(Tiempo t: this.scheduler.getLineaTiempo()){
            System.out.println(t.toString());
        }   
        System.out.println("-------------------------------");
        System.out.println("Informe:");
        System.out.println(this.scheduler.getInfo());
        System.out.println("");
        System.out.println("");
    }
            
    public ArrayList<Proceso> cloneProcesos(){
        ArrayList<Proceso> clon = new ArrayList();
        for(Proceso p: this.procesos){
            clon.add(new Proceso(p.getNumero(), p.getPeriodo(), p.getDeadline(), p.getTiempo(), p.getColor()));
        }
        return clon;
    }

    

    
    
    
}
