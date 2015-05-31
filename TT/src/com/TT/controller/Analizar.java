package com.TT.controller;

import com.TT.view.Analizador;
import com.TT.model.BuscadorConceptos;
import com.TT.model.Relacion;
import com.TT.model.CargaOntologia;
import com.TT.model.Concepto;
import com.arbol.Graficador;
import edu.upc.freeling.Depnode;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.ListWord;
import edu.upc.freeling.PreorderIteratorDepnode;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.TreeDepnode;
import edu.upc.freeling.Word;
import java.util.ArrayList;
import java.util.List;

public class Analizar implements Runnable {
    
    private DesambiguadorPrep desambiguador;
    private ResolvedorConj resolvedor=new ResolvedorConj();
    private final Freeling analizador; 
    public static boolean termino = false;
    String line;
    
    @Override
    public void run() {
        termino = false;
//        Analizador.progressBar.setIndeterminate(true);
        String oracion = analizarOracion();
        Analizador.tagged.setText(oracion);
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e){
            System.err.println( e.getMessage() );
        }
        termino = true;
        Thread.interrupted();
    }
    
    public Analizar(String line,Freeling analizador,List<Concepto>[] indexOnto,List<Relacion> indexRel) { 
        desambiguador=new DesambiguadorPrep(indexOnto, indexRel);
        this.analizador=analizador;
        this.line = line;
    }
    
    
    
    public String analizarOracion() { //Inicia el proceso de analisis de una oracion, devuelve la oracion con informacion adicional        
        ListWord l = analizador.tk.tokenize(line);
        ListSentence ls = analizador.sp.split(l, false);    /*  Lista de oraciones analizadas por freeling  */
        analizador.mf.analyze(ls);                          /*  Llama al etiquetador de Freeling    */ 
        analizador.tg.analyze(ls);
        
        /*  Modulos desarrollados para el TT    */
        
        ls=desambiguador.desambiguarPreposiciones(ls);                    /*  Desambigua preposiciones    */
        
        ls=resolvedor.resuelveConjunciones(ls);                        /*  Resolvedor de conjunciones  */
        
        
        analizador.parser.analyze(ls);
        analizador.dep.analyze(ls);                         /*  Analisis de dependencias    */
        
        Analizador.labelEstado.setText("Análisis de Dependencias: Completado");
        
        System.out.println("-------- DEPENDENCY PARSER results -----------");

        ListSentenceIterator sIte = new ListSentenceIterator(ls);
        while (sIte.hasNext()) {
            String nuevaOracion = "NuevaOracion";
            relacionArbol.add(nuevaOracion);
            Sentence s = sIte.next();
            TreeDepnode tree = s.getDepTree();
            printDepTree(0, tree);
        }
        
        
        /*
        *Se llama a la clase encargada de crear la gráfica del árbol
        */
        Analizador.labelEstado.setText("Dibujando árbol de dependencias...");
        Graficador graficador = new Graficador(relacionArbol);
        graficador.crearGraficaArbol();
        return null;
    }

    private static void printDepTree(int depth, TreeDepnode tr) {
        TreeDepnode child = null;
        TreeDepnode fchild = null;
        Depnode childnode;
        long nch;
        int last, min;
        Boolean trob;
        String nodo;
        
        for (int i = 0; i < depth; i++) {
            System.out.print("  ");
        }

        System.out.print(
                tr.getInfo().getLinkRef().getInfo().getLabel() + "/"
                + tr.getInfo().getLabel() + "/");

        Word w = tr.getInfo().getWord();

        System.out.print(
                "(" + w.getForm() + " " + w.getLemma() + " " + w.getTag());
        // printSenses( w );
        System.out.print(")");

        if(depth == 0) {
            nodo = " ," + w.getForm() + "\\n(" + w.getTag() + ")"; 
        } else {
            PreorderIteratorDepnode nodoPadre = tr.getParent();
            nodo = nodoPadre.getInfo().getWord().getForm() +"\\n(" + nodoPadre.getInfo().getWord().getTag() +
                    ")," + w.getForm() + "\\n(" + w.getTag() + ")";
        }
        relacionArbol.add(nodo);

        
        nch = tr.numChildren();

        if (nch > 0) {
            System.out.println(" [");

            for (int i = 0; i < nch; i++) {
                child = tr.nthChildRef(i);

                if (child != null) {
                    if (!child.getInfo().isChunk()) {
                        printDepTree(depth + 1, child);
                    }
                } else {
                    System.err.println("ERROR: Unexpected NULL child.");
                }
            }

            // Print chunks (in order)
            last = 0;
            trob = true;

                // While an unprinted chunk is found, look for the one with lower
            // chunk_ord value.
            while (trob) {
                trob = false;
                min = 9999;

                for (int i = 0; i < nch; i++) {
                    child = tr.nthChildRef(i);
                    childnode = child.getInfo();

                    if (childnode.isChunk()) {
                        if ((childnode.getChunkOrd() > last)
                                && (childnode.getChunkOrd() < min)) {
                            min = childnode.getChunkOrd();
                            fchild = child;
                            trob = true;
                        }
                    }
                }
                if (trob && (child != null)) {
                    printDepTree(depth + 1, fchild);
                }

                last = min;
            }

            for (int i = 0; i < depth; i++) {
                System.out.print("  ");
            }

            System.out.print("]");
        }

        System.out.println("");
    }

    public static Concepto onto;
    private CargaOntologia load = new CargaOntologia();
    private BuscadorConceptos busca = new BuscadorConceptos();
    private static List<String> relacionArbol = new ArrayList<>();
    private Desambiguador des = new Desambiguador();
}