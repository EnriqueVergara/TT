package com.TT.controller;

import com.TT.view.Analizador;

import com.TT.model.Concepto;
import com.TT.model.Ontologia;
import com.arbol.Graficador;
import com.arbol.Nodo;
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
import javax.swing.JLabel;

public class Analizar {
    
    private DesambiguadorPrep desambiguador;
    private ResolvedorConj resolvedor=new ResolvedorConj();
    public Freeling analizador; 
    public Ontologia ontologia;
    public static boolean termino = false;
    private String oracion;
    public static int contGlobal = 0;
    boolean originalFreeling;
    
    public void setOracion(String oracion){
        this.oracion=oracion;
    }
    
    public Analizar(Freeling analizador,Ontologia ontologia, boolean originalFreeling) { 
        this.ontologia=ontologia;
        desambiguador=new DesambiguadorPrep(ontologia);
        this.analizador=analizador;
        this.originalFreeling = originalFreeling;
    }
    
    public List<JLabel> generarArbolGrafico(){
        int cont = 0;      
        System.out.println("-------- DEPENDENCY PARSER results -----------");

        ListSentenceIterator sIte = new ListSentenceIterator(analizarOracion(oracion));     
        while (sIte.hasNext()) {
            String nuevaOracion = "NuevaOracion";
            relacionArbol.add(nuevaOracion);
            Sentence s = sIte.next();
            TreeDepnode tree = s.getDepTree();
            printDepTree(0, tree, cont);
            contGlobal++;
            cont = contGlobal;
        }
        /*
        *Se llama a la clase encargada de crear la gráfica del árbol
            CU-5: Generar árbol de dependencias.
        */
        Analizador.labelEstado.setText("Dibujando árbol de dependencias...");
        Graficador graficador = new Graficador(relacionArbol, nodosArbol);
        return graficador.crearGraficaArbol();
        //return null;
       // return new JLabel();
        
    }
    
    private ListSentence analizarOracion(String line) { //Inicia el proceso de analisis de una oracion, devuelve la oracion con informacion adicional        
        ListWord l = analizador.tk.tokenize(line);
        ListSentence ls = analizador.sp.split(l, false);    /*  Lista de oraciones analizadas por freeling  */
        
        analizador.mf.analyze(ls);                          /*  Llama al etiquetador de Freeling    */ 
        analizador.tg.analyze(ls);                          /* CU-2: Etiquetar Oración
        
        /*  Modulos desarrollados para el TT    */
        if(!originalFreeling) {
            ls=desambiguador.desambiguarPreposiciones(ls);                    /*  Desambigua preposiciones   CU-3: Desambiguar Preposiciones  */
        
            ls=resolvedor.resuelveConjunciones(ls);         /*  Resolvedor de conjunciones  CU-4: Resolver conjunciones  */
        }
  
        analizador.parser.analyze(ls);
        analizador.dep.analyze(ls);                         /*  Analisis de dependencias    */
        
        return ls;
    }

    private static void printDepTree(int depth, TreeDepnode tr, int cont) {
        TreeDepnode child = null;
        TreeDepnode fchild = null;
        Depnode childnode;
        long nch;
        int last, min;
        Boolean trob;
        String nodo;
        Nodo n = new Nodo();
        
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
            
            n.setIdPadre("");
            n.setNamePadre("");
            n.setTagPadre("");
            n.setId("nodo" + cont);
            n.setName(w.getForm());
            n.setTag(w.getTag());
        } else {
            PreorderIteratorDepnode nodoPadre = tr.getParent();
            nodo = nodoPadre.getInfo().getWord().getForm() +"\\n(" + nodoPadre.getInfo().getWord().getTag() +
                    ")," + w.getForm() + "\\n(" + w.getTag() + ")";
            
            n.setIdPadre("nodo" + (cont));
            n.setNamePadre(nodoPadre.getInfo().getWord().getForm());
            n.setTagPadre(nodoPadre.getInfo().getWord().getTag());
            n.setId("nodo" + contGlobal);
            n.setName(w.getForm());
            n.setTag(w.getTag());
        }
        relacionArbol.add(nodo);
        nodosArbol.add(n);
        
        nch = tr.numChildren();

        if (nch > 0) {
            cont = contGlobal;
            System.out.println(" [");

            for (int i = 0; i < nch; i++) {
                child = tr.nthChildRef(i);

                if (child != null) {
                    if (!child.getInfo().isChunk()) {
                        contGlobal++;
                        printDepTree(depth + 1, child, cont);
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
                    contGlobal++;
                    printDepTree(depth + 1, fchild, cont);
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
   // private BuscadorConceptos busca = new BuscadorConceptos();
    private static List<String> relacionArbol = new ArrayList<>();
    public static List<Nodo> nodosArbol = new ArrayList<>();
    private Desambiguador des = new Desambiguador();
}
