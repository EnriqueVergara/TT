/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arbol;

import analizador.Analizar;
import analizador.Analizador;

/**
 *
 * @author bruno
 */
public class HiloProgressBar implements Runnable {
    private int i = 1;
    private int value = 50; //retardo en milisegundos
    
    @Override
    public void run() {
        i = 1;
        while(!Analizar.termino) {
            i = (i > 100) ? 1 : i+1;
            Analizador.progressBar.setValue(i);
            Analizador.progressBar.repaint();
            try {
                Thread.sleep(this.value);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            
            if(Analizar.termino) {
//                Analizador.progressBar.setIndeterminate(false);
                Analizador.progressBar.setValue(100);
//                Analizador.progressBar.setString("100%");
                System.out.println("Terminó");
                break;
            }
        }
    }
}
