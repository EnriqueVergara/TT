package analizador;

import com.arbol.Graficador;
import edu.upc.freeling.Analysis;
import edu.upc.freeling.Depnode;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.ListWord;
import edu.upc.freeling.ListWordIterator;
import edu.upc.freeling.PreorderIteratorDepnode;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.TreeDepnode;
import edu.upc.freeling.Util;
import edu.upc.freeling.VectorWord;
import edu.upc.freeling.Word;
import java.util.ArrayList;
import java.util.List;

public class Analizar implements Runnable {
    private List<Concepto>[] indexOnto = new ArrayList[24];
    private List<Relacion> indexRel = new ArrayList<>();
    private final Freeling analizador; 
    public static boolean termino = false;
    String line;

    @Override
    public void run() {
        termino = false;
        String oracion = analizarOracion();
        Analizador.tagged.setText(oracion);
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e){
            System.err.println( e.getMessage() );
        }
        termino = true;
    }
    
    public Analizar(String line,Freeling analizador,List<Concepto>[] indexOnto,List<Relacion> indexRel) { 
       this.indexOnto=indexOnto;
       this.indexRel=indexRel; 
       this.analizador=analizador;
       this.line = line;
    }

    public String analizarOracion() { //Inicia el proceso de analisis de una oracion, devuelve la oracion con informacion adicional        
        ListWord l = analizador.tk.tokenize(line);
        ListSentence listAux=new ListSentence();
        ListSentence ls = analizador.sp.split(l, false);    //Lista de oraciones analizadas por freeling
        analizador.mf.analyze(ls);  /*   Llama al etiquetador de Freeling   */
        String desambiguado;
                    
        Analysis analisis;
        Word wordAux=new Word();    
        Sentence sentAux=new Sentence();
         ListSentenceIterator sIt = new ListSentenceIterator(ls); 
         while(sIt.hasNext()){
            Sentence sent=sIt.next();
            VectorWord vec=sent.getWords();            
            sentAux=new Sentence();
            for(int i=0;i<vec.size();i++){
                if(vec.get(i).getTag().startsWith("SP")){
                     analisis=new Analysis();
                     wordAux=new Word();
                     analisis.setTag("SPS00");
                    // analisis.setTag(vec.get(i).getTag());
                     desambiguado=des.desambiguaTripleta(
                                     busca.buscarEnIndice(indexOnto, buscaPrevio(vec, i)), 
                                     busca.buscarEnIndice(indexOnto, buscaPosterior(vec, i)), 
                                     busca.buscarEnIndiceRel(indexRel, vec.get(i).getLemma()));
                                          
                     analisis.setLemma(desambiguado);
                     wordAux.setAnalysis(analisis);
                     wordAux.setForm(vec.get(i).getForm());
                     sentAux.pushBack(wordAux);
                }
                else{           
                    sentAux.pushBack(vec.get(i));
                }
            }
            listAux.pushBack(sentAux);
        }
        
       
        ListSentenceIterator sIta = new ListSentenceIterator(listAux); //Iterador de oraciones
        List<String> result = new ArrayList(); //Cada oración con informacion semantica adicional se guarda en un arreglo
        while (sIta.hasNext()) {
            Sentence s = sIta.next();
            //Iterador de palabras
            ListWordIterator wIt = new ListWordIterator(s);
            List<Word> palabras = new ArrayList();
            while (wIt.hasNext()) {
                Word w = wIt.next();
                System.out.println(w.getForm());
            }
        }
         
        analizador.tg.analyze(listAux);
        
        // Perform named entity (NE) classificiation.
        // Chunk parser
        analizador.parser.analyze(listAux);
        
        // Dependency parser
        analizador.dep.analyze(listAux);
        
        Analizador.labelEstado.setText("Análisis de Dependencias: Completado");
        
        System.out.println("-------- DEPENDENCY PARSER results -----------");

        ListSentenceIterator sIte = new ListSentenceIterator(listAux);
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
        return "";
    }

    private String buscaPrevio(VectorWord vec, int index) {
        int i;
        for (i = index; i >= 0; i--) {
            if (vec.get(i).getTag().startsWith("NC")) {
                return vec.get(i).getLemma();
            }
        }
        return "";
    }

    private String buscaPosterior(VectorWord vec, int index) {
        int i;
        for (i = index; i < vec.size(); i++) {
            if (vec.get(i).getTag().startsWith("NC")) {
                return vec.get(i).getLemma();
            }
        }
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

    private BuscadorConceptos busca = new BuscadorConceptos();
    private Desambiguador des = new Desambiguador();
    private static List<String> relacionArbol = new ArrayList<>();
}
