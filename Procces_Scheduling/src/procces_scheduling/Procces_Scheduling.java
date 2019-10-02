/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procces_scheduling;

import Model.Ejecucion;
import Model.Deadline;
import Model.FileManager;
import Model.Proceso;
import Model.Scheduler;
import Model.Tiempo;
import View.GUI;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

/**
 *
 * @author USUARIO
 */
public class Procces_Scheduling {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        //C:\Users\USUARIO\AndroidStudioProjects\Proyecto1Sistemas\Procces_Scheduling\dist
        //java -jar "Procces_Scheduling.jar" -i C:\Users\USUARIO\Desktop\input.txt -o output.txt -a EDF -t 120
        //Menu menu = new Menu(args);
        GUI gui = new GUI();
        gui.setVisible(true);
    }
    
}
