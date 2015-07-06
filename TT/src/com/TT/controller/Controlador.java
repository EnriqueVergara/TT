package com.TT.controller;

import com.TT.view.Analizador;
import com.TT.view.FrameAcercaDe;
import com.TT.view.PanelImagenFondo;
import com.arbol.Graficador;
import com.arbol.Zoom;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Controlador implements ActionListener, KeyListener{  /* Actua como controlador del sistema   */
    private Analizador vista;
    private Analizar modelo;
    Zoom zoom;
    
    public Controlador(Analizador vista,Analizar modelo){
        this.vista=vista;
        this.modelo=modelo;
    }
    public void iniciarVista(){
//        vista.panelImagenes.setLayout(new java.awt.GridLayout(0, 1));
        vista.buttonNuevo.setEnabled(false);
        vista.botonAnalizar.addActionListener(this);
        vista.buttonNuevo.addActionListener(this);
        vista.zoom150.addActionListener(this);
        vista.zoom125.addActionListener(this);
        vista.zoom75.addActionListener(this);
        vista.zoom50.addActionListener(this);
        vista.zoom25.addActionListener(this);
        vista.menuRestaurar.addActionListener(this);
        vista.menuAcercaDe.addActionListener(this);
        vista.line.addKeyListener(this);
        vista.setVisible(true);  
        
        //Coloca la imagen de fondo en el Frame
        JPanel objPanel = new PanelImagenFondo();
        objPanel.setSize(vista.panelImgFondo.getWidth(), vista.panelImgFondo.getHeight());
        vista.panelImgFondo.add(objPanel);
        vista.panelImgFondo.validate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(vista.botonAnalizar==e.getSource()){
            if(vista.line.getText().endsWith(".")){
                vista.botonAnalizar.setEnabled(false);
                boolean originalFreeling = false;
                final ExecutorService service;
                final Future<List<JLabel>> task;
                List<JLabel> imagenes=new ArrayList();

                //Revisa si el checkbox esta seleccionado
                if(vista.checkBoxAnalisisOriginal.isSelected()) originalFreeling = true;
                service=Executors.newFixedThreadPool(1);
                GenerarArbol arbol = new GenerarArbol(modelo.analizador, modelo.ontologia, originalFreeling);
                arbol.setOracion(vista.line.getText());
                task = service.submit(arbol);
                try{
                    imagenes=task.get();
                    //System.out.println(task.get().size());
                }catch(InterruptedException | ExecutionException ex){
                    System.out.println(ex.getMessage());
                }
                
                try {
                    if(originalFreeling) {
                        Desktop.getDesktop().open(new File(Graficador.path + "/outfileFreeling.jpg"));
                    } else {
                            BufferedImage bi = ImageIO.read(new File(Graficador.path + "/outfile0.jpg"));
                            zoom = new Zoom(bi);
                            vista.panelImagenes.add(zoom);
                            vista.panelImagenes.setSize(bi.getWidth(), bi.getHeight());
                            vista.panelImagenes.updateUI();

                    }
                } catch (IOException ex) {
                    Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                        
                JOptionPane.showMessageDialog(vista, "El texto debe terminar con punto final, por favor ingresa nuevamente el texto","Atención", JOptionPane.WARNING_MESSAGE);
            }
        }
        if(vista.buttonNuevo==e.getSource()){
            vista.botonAnalizar.setEnabled(true);
            vista.line.setText("");
           // System.out.println("Hola");
            Analizar.nodosArbol.clear();
            Analizar.contGlobal = 0;
            vista.panelImagenes.removeAll();
            vista.panelImagenes.updateUI();
            
            //Elimina las imagenes que se hayan crado
            int cont = 0;
            File f = new File(Graficador.path + "/outfile"+cont+".jpg");
            while(f.exists()) {
                f.delete();
                cont++;
                f = new File(Graficador.path + "/outfile"+cont+".jpg");
            }
        }
        if(vista.zoom150 == e.getSource()) {
            zoom.Aumentar(150);
        }
        if(vista.zoom125 == e.getSource()) {
            zoom.Aumentar(125);
        }
        if(vista.zoom75 == e.getSource()) {
            zoom.Disminuir(25);
        }
        if(vista.zoom50 == e.getSource()) {
            zoom.Disminuir(50);
        }
        if(vista.zoom25 == e.getSource()) {
            zoom.Disminuir(75);
        }
        if(vista.menuRestaurar == e.getSource()) {
            zoom.Restaurar();
        }
        if(vista.menuAcercaDe == e.getSource()) {
            FrameAcercaDe about = new FrameAcercaDe();
            about.setVisible(true);
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
