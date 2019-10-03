/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Model.Ejecucion;
import Model.Deadline;
import Model.FileManager;
import Model.Periodo;
import Model.Proceso;
import Model.Scheduler;
import Model.Tiempo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author USUARIO
 */
public class GUI extends javax.swing.JFrame {

    private Graphics g;
    private ArrayList<Proceso> procesos;
    private Scheduler scheduler = new Scheduler();
    private FileManager fm = new FileManager("");
    //private DefaultTableModel model;
    
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        this.g = this.panel.getGraphics();
        this.g.setColor(Color.WHITE);
        this.g.fillRect(0, 0, this.panel.getWidth(), this.panel.getHeight());
        this.fm.setOutput("output.txt");
        this.procesos = new ArrayList();
        this.panel.setVisible(false);
        this.tableData.setVisible(false);
        this.tableProcesos.setVisible(false);
        
        ///-------
        this.procesos.add(new Proceso(20, 7, 3));this.procesos.add(new Proceso(5, 4, 2));this.procesos.add(new Proceso(10, 8, 2));this.procesos.add(new Proceso(10, 7, 3));this.lineaTiempoSpinner.setValue(20);//video
        ///-------

        
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
        jScrollPane3 = new javax.swing.JScrollPane();
        tableData = new javax.swing.JTable(){
            @Override
            public Component prepareRenderer (TableCellRenderer renderer, int rowIndex, int columnIndex){
                Component c = super.prepareRenderer(renderer, rowIndex, columnIndex);
                Object value = getModel().getValueAt(rowIndex,columnIndex);

                if(value != null){

                    c.setBackground(getProcessColor((String) value));
                    c.setForeground(Color.WHITE);
                }
                else{
                    c.setBackground(Color.WHITE);
                }

                return c;
            }
        }
        ;
        jScrollPane4 = new javax.swing.JScrollPane();
        tableProcesos = new javax.swing.JTable();
        jToggleButton1 = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(102, 102, 102));

        panel.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
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

        jLabel6.setText("En la linea de tiempo correspondiente a cada proceso se puede ver en rojo los deadlines, amarillo los periodos y naranja cuando un periodo y un deadline son iguales");

        jLabel7.setText("Rango de tiempo");

        tableData.setBackground(new java.awt.Color(0, 0, 0));
        tableData.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableData);

        tableProcesos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tableProcesos);

        jToggleButton1.setText("Manual de usuario");
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(procesosList, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(PeriodoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete)
                                .addGap(58, 58, 58))
                            .addGroup(layout.createSequentialGroup()
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
                                        .addComponent(TiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(radioMontonic)
                                    .addComponent(radioEDF, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnEjecutar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lineaTiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1047, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                            .addComponent(jScrollPane4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(97, 97, 97)
                                .addComponent(btnDelete))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(procesosList, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(PeriodoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(DeadlineSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(TiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnAdd))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel7)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lineaTiempoSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioMontonic)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(radioEDF)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(btnEjecutar))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(243, 243, 243)
                                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jToggleButton1))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
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
            
            ArrayList<Tiempo> result = this.scheduler.ejecutar();
            this.setResult(result);
            
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
        Proceso toAdd = new Proceso((int) this.PeriodoSpinner.getValue(),
                (int) this.DeadlineSpinner.getValue(),
                (int) this.TiempoSpinner.getValue());
        if(toAdd.getPeriodo() >= toAdd.getDeadline() && toAdd.getDeadline() >= toAdd.getTiempo()){
            this.procesos.add(toAdd);
            setProcesos();
        }
        else{
            Proceso.decInstances();
            JOptionPane.showMessageDialog(null, "los procesos deben cumplir tiempo <= deadline <= periodo", "", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if(this.procesosList.getSelectedIndex() >= 0){
            this.procesos.remove(this.procesosList.getSelectedIndex());
            this.reSetProcesos();
            this.setProcesos();
        }
        else{
            JOptionPane.showMessageDialog(null, "debe seleccionar un proceso", "", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton1ActionPerformed
        // TODO add your handling code here:
        Info info = new Info();
        info.setVisible(true);
    }//GEN-LAST:event_jToggleButton1ActionPerformed

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
    
    public void reSetProcesos(){
        Proceso.resetInstances();
        ArrayList<Proceso> result = new ArrayList();
        for(Proceso p: procesos){
            result.add(new Proceso(p.getPeriodo(), p.getDeadline(), p.getTiempo()));
        }
        procesos = result;
    }
    
    private void setResult(ArrayList<Tiempo> result) {
        //this.tableData.setValueAt(value, row, col);
        
        String column_names[] = new String[this.scheduler.getTiempoTotal() + 1];
        for(int i = 0; i < this.scheduler.getTiempoTotal() + 1; i++){
            column_names[i] = Integer.toString(i);
        }
        
        DefaultTableModel model = new DefaultTableModel(column_names, 0);
        model.setRowCount(this.procesos.size());
        model.setColumnCount(this.scheduler.getTiempoTotal() + 1);
        this.tableData.setModel(model);
        this.tableData.setFocusable(false);
        this.tableData.setEnabled(false);
        this.tableData.setRowSelectionAllowed(false);
        this.tableData.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        TableColumnModel clm = this.tableData.getColumnModel();
        for(int i = 0; i < this.scheduler.getTiempoTotal() + 1; i++){
            clm.getColumn(i).setPreferredWidth(30);
        }
        
        DefaultTableModel model2 = new DefaultTableModel();
        model2.setRowCount(this.procesos.size());
        model2.setColumnCount(1);
        this.tableProcesos.setModel(model2);
        
        this.tableData.setVisible(true);
        this.tableProcesos.setVisible(true);
        
        for(int i = 0; i < this.procesos.size(); i++){
            this.tableProcesos.setValueAt(this.procesos.get(i).toString(), i, 0); 
        }
        
        for(Tiempo t: result){
            
            if(t.isEjecucion()){
                for(int i = t.getUnidadTiempo(); i < (t.getUnidadTiempo() + ((Ejecucion) t).getExcecutedTime()); i++){
                    if(this.tableData.getValueAt(((Ejecucion) t).getP().getNumero() - 1, i) != null){
                        this.tableData.setValueAt(Integer.toString(((Ejecucion) t).getP().getNumero()) + this.tableData.getValueAt(((Ejecucion) t).getP().getNumero() - 1, i), ((Ejecucion) t).getP().getNumero() - 1, i);
                    }
                    else{
                        this.tableData.setValueAt(Integer.toString(((Ejecucion) t).getP().getNumero()), ((Ejecucion) t).getP().getNumero() - 1, i);
                    }
                }
            }
            if(t.isPeriodo()){
                for(int i: ((Periodo) t).getProcesos()){
                    if(this.tableData.getValueAt(i - 1, t.getUnidadTiempo() - 1) != null){
                        this.tableData.setValueAt(this.tableData.getValueAt(i - 1, t.getUnidadTiempo() - 1) + "P", i - 1, t.getUnidadTiempo() - 1);
                    }
                    else{
                        this.tableData.setValueAt("P", i - 1, t.getUnidadTiempo() - 1);
                    }
                }
            }
            if(t.isDeadline()){
                for(int i: ((Deadline) t).getProcesos()){
                    if(this.tableData.getValueAt(i - 1, t.getUnidadTiempo() - 1) != null){
                        this.tableData.setValueAt(this.tableData.getValueAt(i - 1, t.getUnidadTiempo() - 1) + "D", i - 1, t.getUnidadTiempo() - 1);
                    }   
                    else{
                        this.tableData.setValueAt("D", i - 1, t.getUnidadTiempo() - 1);
                    }
                }
            }
        }
    }
    
    public Color getProcessColor(String pr){
        
        for(Proceso p: this.scheduler.getProcesos()){
            if(pr.contains(Integer.toString(p.getNumero()))){
                return p.getColor();
            }
        }
        
        if(pr.contains("D") && pr.contains("P")){
            return Color.orange;
        }
       
        if(pr.contains("D")){
            return Color.RED;
        }
        if(pr.contains("P")){
            return Color.BLACK;
        }
        return Color.WHITE;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JToggleButton jToggleButton1;
    private javax.swing.JSpinner lineaTiempoSpinner;
    private javax.swing.JPanel panel;
    private javax.swing.JTextArea panelInfoPeriodos;
    private javax.swing.JTextArea panelInfoProcesos;
    private java.awt.List procesosList;
    private javax.swing.JRadioButton radioEDF;
    private javax.swing.JRadioButton radioMontonic;
    public javax.swing.JTable tableData;
    private javax.swing.JTable tableProcesos;
    // End of variables declaration//GEN-END:variables

    public void graphResults(ArrayList<Tiempo> result){
        
        //grafico de procesos
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.panel.getWidth(), this.panel.getHeight());
        int timeline = 0, yoffset = this.panel.getHeight() - 25, xoffset = 125;
        int scaleX = (int) Math.floor(((this.panel.getWidth() - xoffset) / this.scheduler.getTiempoTotal()) + 1),
                scaleHeight = 20;//(int) Math.floor((this.panel.getHeight() / this.scheduler.getProcesos().size()) + 1); //20;
                
        xoffset -= 50;
        for(Tiempo t: result){
            if(t.isEjecucion()){
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
            if(t.isDeadline()){
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
    
}

