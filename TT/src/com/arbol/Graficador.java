/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arbol;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bruno
 */
public class Graficador {
    String path = "/home/bruno/NetBeansProjects/TT/TT/src/com/arbol";
    List<String> relacionArbol;
    
    public Graficador(List<String> relacionArbol) {
        this.relacionArbol = relacionArbol;
    }
    
    public void crearGraficaArbol() {
        int oraciones = 0, borrar = 0;
        
        //Recorre el arreglo para saber cuantas oraciones vienen
        for (int i = 0; i < relacionArbol.size(); i++) {
            if (relacionArbol.get(i).equalsIgnoreCase("NuevaOracion")) {
                oraciones++;
            }
        }
        
        for(int i = 0; i < oraciones; i++) {
            File archivo = new File(path + "/Arbol" + i + ".txt");
            String padre, hijo;
            int separador;
            relacionArbol.remove(0);

            try {
                FileWriter writer = new FileWriter(archivo);
                PrintWriter print = new PrintWriter(writer);
                //Encabezado del archivo que será leido por graphviz
                print.append("digraph G \n" + " { \n");

                for(int j = 0; j < relacionArbol.size(); j++) {
                    if(!relacionArbol.get(j).equalsIgnoreCase("NuevaOracion")) {
                        separador = relacionArbol.get(j).indexOf(',');
                        padre = relacionArbol.get(j).substring(0, separador);
                        hijo = relacionArbol.get(j).substring(separador + 1);

                        if(!padre.equalsIgnoreCase(" ")) {
                            print.append("\"" + padre + "\"->\"" + hijo + "\"; \n");
                        }
                    } else {
                        borrar = j;
                        break;
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
        compilarGrafo(oraciones);
        
    }
    
    public void compilarGrafo(int oraciones) {
        for(int i = 0; i < oraciones; i++) {
            try {
                String cmd = "dot -Tjpg " + path + "/Arbol" + i +".txt -o " + path + "/outfile" + i + ".jpg";
                Runtime.getRuntime().exec(cmd);

                Desktop.getDesktop().open(new File(path + "/outfile" + i + ".jpg"));
            } catch (IOException e) {
                System.out.println("Error al generar el árbol. " + e.getMessage());
            }
        }
    }
}
