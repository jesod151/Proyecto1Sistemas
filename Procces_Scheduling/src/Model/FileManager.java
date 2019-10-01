/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

/**
 *
 * @author Jil
 */
public class FileManager {

    private String path, output;
    
    public FileManager(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
    
    public ArrayList<Proceso> readProcesos(){
        BufferedReader br = null;
        String lista = "";
        try {
            br = new BufferedReader(new java.io.FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            lista = sb.toString();
        } catch (IOException ex) {
            System.out.println("formato del archivo incorrecto");
        }
        
        JSONArray json = new JSONArray(lista);
        ArrayList<Proceso> result = new ArrayList();
        for(int i = 0; i < json.length(); i++){
            result.add(new Proceso(json.getJSONObject(i).getInt("periodo"),
                                   json.getJSONObject(i).getInt("deadline"),
                                   json.getJSONObject(i).getInt("tiempo")));
        }
        System.out.println(result);
        return result;
    
    }
    
    public void write(String response){
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(this.output));
            writer.write(response);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
