/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;

/**
 *
 * @author Jil
 */
public class FileReader {

    private String path;

    public FileReader(String path) {
        this.path = path;
    }
    
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
            result.add(new Proceso(json.getJSONObject(i).getInt("Periodo"),
                                   json.getJSONObject(i).getInt("Deadline"),
                                   json.getJSONObject(i).getInt("Tiempo")));
        }

        return result;
    
    }
    
}
