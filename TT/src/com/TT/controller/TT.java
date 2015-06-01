
package com.TT.controller;

import com.TT.model.CargaOntologia;
import com.TT.model.Ontologia;
import com.TT.view.Analizador;

public class TT {
    private static Controlador controlador;
    private static Analizador vista;
    private static Analizar modelo;
    private static Ontologia ontologia;
    
    public static void main(String []args){ /*  Clase principal del sistema */
        ontologia=new CargaOntologia("prueba.xml","relacion.xml").getOntologia();  /*  Carga la Ontologia con los archivos por default  */
        vista=new Analizador();
        modelo=new Analizar(new Freeling(),ontologia);
        controlador=new Controlador(vista,modelo);
        controlador.iniciarVista();
    }
    
}
