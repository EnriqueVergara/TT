package com.TT.controller;

import com.TT.view.Analizador;
import com.arbol.HiloProgressBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JLabel;

public class Controlador implements ActionListener, KeyListener{  /* Actua como controlador del sistema   */
    private Analizador vista;
    private Analizar modelo;
    
    
    public Controlador(Analizador vista,Analizar modelo){
        this.vista=vista;
        this.modelo=modelo;
    }
    public void iniciarVista(){
        vista.panelImagenes.setLayout(new java.awt.GridLayout(0, 1));
        vista.botonAnalizar.addActionListener(this);
        vista.buttonNuevo.addActionListener(this);
        vista.line.addKeyListener(this);
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(vista.botonAnalizar==e.getSource()){
            final ExecutorService service;
            final Future<List<JLabel>> task;
            List<JLabel> imagenes=new ArrayList();
            service=Executors.newFixedThreadPool(1);
            GenerarArbol arbol=new GenerarArbol(modelo.analizador, modelo.ontologia);
            arbol.setOracion(vista.line.getText());
            task=service.submit(arbol);
            try{
                imagenes=task.get();
                //System.out.println(task.get().size());
            }catch(InterruptedException | ExecutionException ex){
                System.out.println(ex.getMessage());
            }
            
            for (JLabel imagene : imagenes) {
                vista.panelImagenes.add(imagene);
                imagene.setVisible(true);
                vista.panelImagenes.updateUI();
            }
//            vista.panelImagenes.removeAll();
//            vista.panelImagenes.setLayout(new java.awt.GridLayout(0, imagenes.size()));
//            for (JLabel imagene : imagenes) {
//                vista.panelImagenes.add(imagene);
//                imagene.setVisible(true);
//                vista.panelImagenes.updateUI();
//            }
        }
        if(vista.buttonNuevo==e.getSource()){
            vista.line.setText("");
            System.out.println("Hola");
            Analizar.nodosArbol.clear();
            Analizar.contGlobal = 0;
            vista.panelImagenes.removeAll();
            vista.panelImagenes.updateUI();
            
            //Elimina las imagenes que se hayan crado
            int cont = 0;
            File f = new File("/home/bruno/NetBeansProjects/TT/TT/src/com/arbol/outfile"+cont+".jpg");
            while(f.exists()) {
                f.delete();
                cont++;
                f = new File("/home/bruno/NetBeansProjects/TT/TT/src/com/arbol/outfile"+cont+".jpg");
            }
//                    hiloAnalizar.interrupt();
//        hiloProgressBar.interrupt();
//        
//        panelImagenes.removeAll();
//        panelImagenes.updateUI();
//        progressBar.setValue(0);
//        buttonNuevo.setEnabled(false);
        }
        
    }

    @Override
    public void keyTyped(KeyEvent e) {  /*  Si se ha añadido texto se activa el boton nuevo */
        if(!vista.line.getText().equals(""))
            vista.buttonNuevo.setEnabled(true);
        else
            vista.buttonNuevo.setEnabled(false);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }
}
