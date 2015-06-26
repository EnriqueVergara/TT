package com.TT.controller;

import com.TT.model.Ontologia;
import java.util.List;
import java.util.concurrent.Callable;
import javax.swing.JLabel;

public class GenerarArbol extends Analizar implements Callable<List<JLabel>>{ /*   Clase encargada de generar la representacion grafica de un árbol de dependencias    */
   
    @Override
    public List<JLabel> call(){
        return this.generarArbolGrafico();
    }

    public GenerarArbol(Freeling analizador, Ontologia ontologia, boolean originalFreeling) {
        super(analizador, ontologia, originalFreeling);
    }


    
}
