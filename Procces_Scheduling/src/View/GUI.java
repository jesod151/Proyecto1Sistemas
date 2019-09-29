/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Ejecucion;
import Model.Deadline;
import Model.Proceso;
import Model.Scheduler;
import Model.Tiempo;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author USUARIO
 */
public class GUI extends javax.swing.JFrame {

    private Graphics g;
    private ArrayList<Proceso> procesos;
    private Scheduler scheduler = new Scheduler();
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        g = this.panel.getGraphics();
        this.procesos = new ArrayList();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, this.panel.getWidth(), this.panel.getHeight());
        
        
        //this.procesos.add(new Proceso(20, 7, 3));this.procesos.add(new Proceso(5, 4, 2));this.procesos.add(new Proceso(10, 8, 2));this.lineaTiempoSpinner.setValue(20);//video
        this.procesos.add(new Proceso(4, 4, 1));this.procesos.add(new Proceso(6, 6, 2));this.procesos.add(new Proceso(8, 8, 3));this.lineaTiempoSpinner.setValue(24);
        //this.procesos.add(new Proceso(5, 5, 2));this.procesos.add(new Proceso(6, 6, 2));this.procesos.add(new Proceso(7, 7, 2));this.procesos.add(new Proceso(8, 8, 2));this.lineaTiempoSpinner.setValue(24);
        //this.procesos.add(new Proceso(1, 1, 1));this.procesos.add(new Proceso(1, 1, 1));this.procesos.add(new Proceso(1, 1, 1));this.procesos.add(new Proceso(1, 1, 1));this.procesos.add(new Proceso(1, 1, 1));this.procesos.add(new Proceso(1, 1, 1));
        //this.procesos.add(new Proceso(10, 5, 1));this.procesos.add(new Proceso(10, 5, 1));this.procesos.add(new Proceso(10, 5, 1));
        
        this.setProcesos();
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup3 = new javax.swing.ButtonGroup();
        panel = new javax.swing.JPanel();
        btnEjecutar = new javax.swing.JButton();
        procesosList = new java.awt.List();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        PeriodoSpinner = new javax.swing.JSpinner();
        jLabel1 = new javax.swing.JLabel();
        DeadlineSpinner = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TiempoSpinner = new javax.swing.JSpinner();
        radioEDF = new javax.swing.JRadioButton();
        radioMontonic = new javax.swing.JRadioButton();
        lineaTiempoSpinner = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelInfoPeriodos = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelInfoProcesos = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(102, 102, 102));
        setResizable(false);

        panel.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 506, Short.MAX_VALUE)
        );

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelete.setText("delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        PeriodoSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel1.setText("Linea de tiempo");

        DeadlineSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel2.setText("Deadline");

        jLabel3.setText("Tiempo");

        TiempoSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        buttonGroup3.add(radioEDF);
        radioEDF.setSelected(true);
        radioEDF.setText("EDF");

        buttonGroup3.add(radioMontonic);
        radioMontonic.setText("Rate Montonic");

        lineaTiempoSpinner.setModel(new javax.swing.SpinnerNumberModel(24, 1, null, 1));

        panelInfoPeriodos.setEditable(false);
        panelInfoPeriodos.setColumns(20);
        panelInfoPeriodos.setRows(5);
        jScrollPane1.setViewportView(panelInfoPeriodos);

        panelInfoProcesos.setEditable(false);
        panelInfoProcesos.setColumns(20);
        panelInfoProcesos.setRows(5);
        jScrollPane2.setViewportView(panelInfoProcesos);

        jLabel4.setText("Periodo");

        jLabel5.setText("Informe de ejecución");

        jLabel6.setText("En la linea de tiempo correspondiente a cada proceso se puede ver en rojo los deadlines y en blanco los periodos ");

        jLabel7.setText("Rango de tiempo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(procesosList, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(65, 65, 65)
                                            .addComponent(btnDelete))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(PeriodoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btnAdd)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(DeadlineSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(TiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(radioMontonic)
                                    .addComponent(radioEDF, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(btnEjecutar)
                                        .addComponent(lineaTiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jLabel6))
                        .addGap(354, 354, 354)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(procesosList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(btnDelete))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(PeriodoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(DeadlineSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(TiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lineaTiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioMontonic)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radioEDF)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEjecutar)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        // TODO add your handling code here:
        this.scheduler = new Scheduler();
        if(this.procesos.size() > 0){
            if(this.radioEDF.isSelected()){
                this.scheduler.setModo(1);
            }
            else{
                this.scheduler.setModo(0);
            }
            this.scheduler.setTiempoTotal((int) this.lineaTiempoSpinner.getValue());
            this.scheduler.setProcesos(this.cloneProcesos());
            
            this.graphResults(this.scheduler.ejecutar());
            
            this.panelInfoProcesos.setText(this.scheduler.getInfo());
            this.panelInfoPeriodos.setText("");
            for(Tiempo t: this.scheduler.getLineaTiempo()){
                    this.panelInfoPeriodos.append(t.toString() + "\n");
            }      
        }
        else{
            JOptionPane.showMessageDialog(null, "debe haber al menos un proceso", "", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        this.procesos.add(new Proceso((int) this.PeriodoSpinner.getValue(),
                (int) this.DeadlineSpinner.getValue(),
                (int) this.TiempoSpinner.getValue()));
        setProcesos();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(this.procesosList.getSelectedIndex() >= 0){
            this.procesos.remove(this.procesosList.getSelectedIndex());
            Proceso.decInstances();
            setProcesos();
        }
        else{
            JOptionPane.showMessageDialog(null, "debe seleccionar un proceso", "", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    public ArrayList<Proceso> cloneProcesos(){
        ArrayList<Proceso> clon = new ArrayList();
        for(Proceso p: this.procesos){
            clon.add(new Proceso(p.getNumero(), p.getPeriodo(), p.getDeadline(), p.getTiempo(), p.getColor()));
        }
        return clon;
    }
    
    public void setProcesos(){
    
        this.procesosList.removeAll();
        for(Proceso p: this.procesos){
            this.procesosList.add(p.toStringGrafico());
        }
    }
    
    public void graphResults(ArrayList<Tiempo> result){
        
        //grafico de procesos
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.panel.getWidth(), this.panel.getHeight());
        int timeline = 0, yoffset = this.panel.getHeight() - 25, xoffset = 125;
        int scaleX = (int) Math.floor(((this.panel.getWidth() - xoffset) / this.scheduler.getTiempoTotal()) + 1),
                scaleHeight = 20;//(int) Math.floor((this.panel.getHeight() / this.scheduler.getProcesos().size()) + 1); //20;
                
        xoffset -= 50;
        for(Tiempo t: result){
            if(!t.isPeriodo()){
                g.setColor(((Ejecucion) t).getP().getColor());
                g.fillRect(xoffset + (((Ejecucion) t).getUnidadTiempo() * scaleX),
                        yoffset - (((Ejecucion) t).getP().getNumero() * scaleHeight),
                        (((Ejecucion) t).getP().getTiempo() * scaleX),
                        scaleHeight);
                timeline += ((Ejecucion) t).getP().getTiempo();
            }
        }
        
        //grafica la escala de tiempo
        g.setColor(Color.WHITE);
        g.drawLine(xoffset, yoffset, xoffset, 0);
        g.drawLine(xoffset, yoffset, this.panel.getWidth(), yoffset);
        for(timeline = 1; timeline <= this.scheduler.getTiempoTotal() + 1; timeline++){
            g.setColor(Color.GRAY);
            g.drawLine(xoffset + (timeline * scaleX), yoffset, xoffset + (timeline * scaleX), 0);
            if(timeline % 3 == 0){
                g.setColor(Color.WHITE);
                g.drawString(Integer.toString(timeline), xoffset + (timeline * scaleX) - 3, yoffset + 12);
            }
            
        }
        
        //grafica deadlines
        g.setColor(Color.RED);
        for(Tiempo t: this.scheduler.getLineaTiempo()){
            if(t.isPeriodo()){
                for(int i: ((Deadline) t).getProcesos()){
                    g.fillRect(xoffset + (t.getUnidadTiempo() * scaleX) - 1,
                               yoffset - scaleHeight * (i),
                               3,
                               scaleHeight);
                }   
            }
        }
        
        //grafica periodos
        int multiplo = 1;
        g.setColor(Color.WHITE);
        for(Proceso p: this.scheduler.getProcesos()){
            while(p.getPeriodo() * multiplo <= this.scheduler.getTiempoTotal()){
                g.fillRect(xoffset + (p.getPeriodo() * multiplo * scaleX) - 1,
                               yoffset - scaleHeight * (p.getNumero()),
                               3,
                               scaleHeight/2);
                multiplo++;
            }
            multiplo = 1;
        }
        
        //grafica de información de procesos
        for(int i = 0; i < this.scheduler.getProcesos().size(); i++){
            g.setColor(Color.WHITE);
            g.drawString(this.scheduler.getProcesos().get(i).toString(),
                        5,
                        yoffset - (10 * i));
            g.setColor(this.scheduler.getProcesos().get(i).getColor());
            g.fillRect(5, yoffset - (10 * i), 55, 10);
            yoffset -= 15;   
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner DeadlineSpinner;
    private javax.swing.JSpinner PeriodoSpinner;
    private javax.swing.JSpinner TiempoSpinner;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner lineaTiempoSpinner;
    private javax.swing.JPanel panel;
    private javax.swing.JTextArea panelInfoPeriodos;
    private javax.swing.JTextArea panelInfoProcesos;
    private java.awt.List procesosList;
    private javax.swing.JRadioButton radioEDF;
    private javax.swing.JRadioButton radioMontonic;
    // End of variables declaration//GEN-END:variables
}
