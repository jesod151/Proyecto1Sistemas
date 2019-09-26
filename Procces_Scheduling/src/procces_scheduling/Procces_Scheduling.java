/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procces_scheduling;

import Model.Ejecucion;
import Model.Periodo;
import Model.Proceso;
import Model.Scheduler;
import Model.Tiempo;
import View.GUI;
import java.util.ArrayList;

/**
 *
 * @author USUARIO
 */
public class Procces_Scheduling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       
        ArrayList<Proceso> a = new ArrayList();
        a.add(new Proceso(4, 4, 1));
        a.add(new Proceso(6, 6, 2));
        a.add(new Proceso(8, 8, 3));
        
        
        /*a.add(new Proceso(5, 5, 2));
        a.add(new Proceso(6, 6, 2));
        a.add(new Proceso(7, 7, 2));
        a.add(new Proceso(8, 8, 2));*/
        
        Scheduler sche = new Scheduler(a, 24, 1);
        
        GUI gui = new GUI(sche.ejecutar(), sche.getProcesos());
        gui.show(true);
       
        sche.printLineaTiempo();
        
    }
    
}
