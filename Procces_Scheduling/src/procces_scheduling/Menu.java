/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procces_scheduling;

import Model.FileManager;
import Model.Proceso;
import Model.Scheduler;
import Model.Tiempo;
import View.GUI;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private FileManager fr = null;
    
    public Menu(String[] args) {
        procesos = new ArrayList();
        scheduler = new Scheduler();
        in = new Scanner(System.in);
        
        if(args.length > 0){//. -i entrada.txt -o salida.txt -a EFD –t 1000
            if(args.length == 8){
                fr = new FileManager(args[1]);
                this.scheduler.setProcesos(fr.readProcesos());
                this.procesos = this.scheduler.getProcesos();
                fr.setOutput(args[3]);
                if(args[5].toUpperCase().equals("EDF")){
                    this.modo = 0;
                    this.scheduler.setModo(0);
                }
                else{
                    this.modo = 1;
                    this.scheduler.setModo(1);
                }
                try{
                    this.rango = Integer.parseInt(args[7].trim());
                    this.scheduler.setTiempoTotal(Integer.parseInt(args[7].trim()));
                }
                catch (NumberFormatException e)
                {
                  System.out.println("el parámetro -t no tiene el formato correcto, no se pudo definir el tiempo");
                }
            }
            else if(args.length == 2){
                this.manualDeUsuario();
            }
            else{
                System.out.println("no se declararon los argumentos correctos, deben ser -i path/entrada.txt -o path/salida.txt -a (EDF/MONTONIC) –t int");
            }
        }
        
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
            System.out.println("7-Manual de usuario");
            System.out.println("8-Salir");
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
                    manualDeUsuario();
                    break;
                case 8:
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
        String lineaTiempo = "";
        for(Tiempo t: this.scheduler.getLineaTiempo()){
            lineaTiempo += t.toString() + "\n";
        }
        System.out.println(lineaTiempo);
        System.out.println("-------------------------------");
        System.out.println("Informe:");
        System.out.println(this.scheduler.getInfo());
        System.out.println("");
        System.out.println("");
        
        if(fr != null){
            fr.write("Informe" + "\n" +
                    this.scheduler.getInfo() + "\n" +
                                               "\n" +
                    "Linea de tiempo" + "\n" +
                    lineaTiempo);
        }   
    }
    
    private void manualDeUsuario() {
        System.out.println(
"Manual de uso\n" +
"\n" +
"Interpretación de la información\n" +
"\n" +
"             1 - En la sección de linea de tiempo se ven los datos de la ejecución:\n" +
"               1.1 - Ejecución t: X proc: Y termina en: Z  ---> indica que se ejecutó en el tiempo x el proceso Y que\n" +
"               termina en tiempo Z\n" +
"               1.2 - Deadline: X procesos [A,B,C....]  ---> indica que en el tiempo X hay un deadline de los procesos\n" +
"               A,B,C....\n" +
"               1.3 - Periodo: X procesos [A,B,C....] ---> indica que en el tiempo X hay un periodo de los procesos\n" +
"               A,B,C....\n" +
"\n" +
"             2 - En la sección de informe de ejecución se ven los datos resultantes por proceso:\n" +
"               2.2 - Proceso: N\n" +
"                   periodo: p\n" +
"                   deadline: d\n" +
"                   tiempo: t\n" +
"                   ejecuciones: a\n" +
"                   ejecuciones perdidas: b\n" +
"                   deadlines perdidas: c\n" +
"                   ejecuciones esperadas: d\n" +
"                   %deadlines perdidas: e\n" +
"                   %ejecuciones a tiempo: f\n" +
"                   %ejecuciones perdidas: g\n" +
"             \n" +

"\n" +
"Generalidades de ejecucion de procesos\n" +
"	-Periodo: tiempo que tiene un proceso para ejecutarse una vez\n" +
"	-Deadline: tiempo que tiene un proceso para en un tiempo esperado\n" +
"	-Tiempo: tiempo que dura un proceso ejecutandose	\n" +
"	-Siempre se debe cumplir que Tiempo <= Deadline <= Periodo\n" +
"	\n" +
"Reglas generales\n" +
"	1 - Un proceso sólo se ejecuta una vez por periodo\n" +
"	2 - Un proceso no se ejecuta si va a terminar depués del periodo en el que se encuentra\n" +
"	3 - Si un proceso no se ejecuta en un periodo se toma como una ejecución perdida\n" +
"\n" +
"Reglas especificas\n" +
"	1 - Earliest Deadline First (EDF)\n" +
" 		1 - Se ejecuta el proceso con el deadline más cercano a la unidad de tiempo en el que\n" +
"		se está ejecutando y que no se haya ejecutado ya en ese periodo\n" +
"		2 - Si hay N deadlines más cercanas, se elige un proceso de los N aleatoriamente a ejecutar\n" +
"		3 - Una vez se selecciona un proceso a ejecutar este lo hace hasta terminar con su tiempo\n" +
"		4 - Si un proceso termina de ejecutar después de su deadline se toma como un deadline perdido\n" +
"	2 - Rate Montonic\n" +
"		1 - Los procesos con menor tiempo de ejecución son los que tienen mayor prioridad\n" +
"		2 - En cada instante se selecciona para ejecutar el proceso con mayor prioridad o menor tiempo\n" +
"		de ejecucion restante que no se haya ejecutado en ese periodo\n" +
"		3 - Si se está ejecutando un proceso n y se pasa a ejecutar un proceso m antes de que n haya terminado\n" +
"		este quedará en una cola de espera para seguir ejecutando el tiempo restante una vez haya terminado m\n" +
"		4 - Si hay varios procesos a ejecutar con la misma prioridad se seleccionará uno random\n" +
"	");
    }
    
    public ArrayList<Proceso> cloneProcesos(){
        ArrayList<Proceso> clon = new ArrayList();
        for(Proceso p: this.procesos){
            clon.add(new Proceso(p.getNumero(), p.getPeriodo(), p.getDeadline(), p.getTiempo(), p.getColor()));
        }
        return clon;
    }

    public Scanner getIn() {
        return in;
    }

    public void setIn(Scanner in) {
        this.in = in;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public ArrayList<Proceso> getProcesos() {
        return procesos;
    }

    public void setProcesos(ArrayList<Proceso> procesos) {
        this.procesos = procesos;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public int getModo() {
        return modo;
    }

    public void setModo(int modo) {
        this.modo = modo;
    }

    public int getRango() {
        return rango;
    }

    public void setRango(int rango) {
        this.rango = rango;
    }

    public FileManager getFr() {
        return fr;
    }

    public void setFr(FileManager fr) {
        this.fr = fr;
    }
    
}
