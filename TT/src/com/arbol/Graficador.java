/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arbol;

import com.TT.view.Analizador;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author bruno
 */
public class Graficador {
    public static String path = "/home/bruno/NetBeansProjects/TT/TT/src/img";
    List<String> relacionArbol;
    List<Nodo> nodosArbol;
    
    public Graficador(List<String> relacionArbol, List<Nodo> nodosArbol) {
        this.relacionArbol = relacionArbol;
        this.nodosArbol = nodosArbol;
    }
    
    public List<JLabel> crearGraficaArbol() {
        int oraciones = 0, borrar = 0;
        
        //Recorre el arreglo para saber cuantas oraciones vienen
        for (String relacion : relacionArbol) {
            if (relacion.equalsIgnoreCase("NuevaOracion")) {
                oraciones++;
            }
        }
        
        for(int i = 0; i < oraciones; i++) {
            File archivo = new File(path + "/Arbol" + i + ".txt");
            relacionArbol.remove(0);

            try {
                FileWriter writer = new FileWriter(archivo);
                PrintWriter print = new PrintWriter(writer);
                //Encabezado del archivo que será leido por graphviz
                print.append("digraph G \n" + " { \n");

                for(Nodo n: nodosArbol) {
                    print.append("\"" + n.getId() + "\" [label=\"" + n.getName() + "\\n(" + n.getTag() + ")\"]; \n");
                }
                print.append("\n");
                for(Nodo n: nodosArbol) {
                    if(!n.getIdPadre().equalsIgnoreCase("")) {
                        print.append("\"" + n.getIdPadre() + "\" -> \"" + n.getId() + "\"; \n");
                    }
                }

                print.append("}");
                writer.close();
                print.close();
                System.out.println("Se creó el arhivo del árbol" + i);
                
                //Elimina los nodos hasta donde se encontró el inicio de la nueva oración
                for(int j = 0; j < borrar; j++) {
                    relacionArbol.remove(0);
                }
                borrar = 0;
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } 
        }

        Analizador.oraciones = oraciones;
        return compilarGrafo(oraciones);
    }
    
    public List<JLabel> compilarGrafo(int oraciones) {
        for(int i = 0; i < oraciones; i++) {
            try {
                String cmd = "dot -Tjpg " + path + "/Arbol" + i +".txt -o " + path + "/outfile" + i + ".jpg";
                Process p = Runtime.getRuntime().exec(cmd);
                p.waitFor();
                //Desktop.getDesktop().open(new File(path + "/outfile" + i + ".jpg"));
            } catch (IOException e) {
                System.out.println("Error al generar el árbol. " + e.getMessage());
            } catch (InterruptedException ex) {
                Logger.getLogger(Graficador.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        List<JLabel> imagenes=new ArrayList();
        for(int i = 0; i < oraciones; i++) {
            JLabel etiqueta = new JLabel();
            String imgPath = path + "/outfile" + i + ".jpg";
            ImageIcon icon = new ImageIcon(imgPath);
            icon.getImage().flush(); //Limpiamos el flujo del jLabel para que se actualice correctamente
            etiqueta.setIcon(icon);
            imagenes.add(etiqueta);
        }
        return imagenes;
    }
}
