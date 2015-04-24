package analizador;

import com.arbol.Graficador;
import edu.upc.freeling.ChartParser;
import edu.upc.freeling.DepTxala;
import edu.upc.freeling.Depnode;
import edu.upc.freeling.HmmTagger;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.ListWord;
import edu.upc.freeling.ListWordIterator;
import edu.upc.freeling.Maco;
import edu.upc.freeling.MacoOptions;
import edu.upc.freeling.Nec;
import edu.upc.freeling.PreorderIteratorDepnode;
import edu.upc.freeling.Senses;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;
import edu.upc.freeling.TreeDepnode;
import edu.upc.freeling.Ukb;
import edu.upc.freeling.Util;
import edu.upc.freeling.Word;
import java.util.ArrayList;
import java.util.List;

public class Analizar implements Runnable {

    private static final String FREELINGDIR = "/usr/local";
    private static final String DATA = FREELINGDIR + "/share/freeling/";
    private static final String LANG = "es";
    public static boolean termino = false;
    String line;

    @Override
    public void run() {
        termino = false;
//        PruebaEtiquetador.progressBar.setIndeterminate(true);
        String oracion = analizarOracion();
        PruebaEtiquetador.tagged.setText(oracion);
        try {
            Thread.sleep( 1000 );
        } catch (InterruptedException e){
            System.err.println( e.getMessage() );
        }
        termino = true;
    }
    
    public Analizar(String line) {
        System.loadLibrary("freeling_javaAPI");
        Util.initLocale("default");
        load.cargaOntologia();
        indexOnto = load.getIndexOnto();
        indexRel = load.getIndexRel();
        this.line = line;
    }

    public String analizarOracion() {
        MacoOptions op = new MacoOptions(LANG);
        op.setActiveModules(false, true, true, true,
                true, true, true,
                true, true, true);
        op.setDataFiles(
                "",
                DATA + LANG + "/locucions.dat",
                DATA + LANG + "/quantities.dat",
                DATA + LANG + "/afixos.dat",
                DATA + LANG + "/probabilitats.dat",
                DATA + LANG + "/dicc.src",
                DATA + LANG + "/np.dat",
                DATA + "common/punct.dat");
        Splitter sp = new Splitter(DATA + LANG + "/splitter.dat");
        Maco mf = new Maco(op);
        Tokenizer tk = new Tokenizer(DATA + LANG + "/tokenizer.dat");
        ListWord l = tk.tokenize(line);
        ListSentence ls = sp.split(l, false);
        //Realiza etiquetado
        mf.analyze(ls);
        //Recupera la lista de los elementos encontrados  Lista de oraciones 
        ListSentenceIterator sIt = new ListSentenceIterator(ls);
        List<String> result = new ArrayList();
        while (sIt.hasNext()) {
            Sentence s = sIt.next();
            //Iterador de palabras
            ListWordIterator wIt = new ListWordIterator(s);
            List<Word> palabras = new ArrayList();
            while (wIt.hasNext()) {
                Word w = wIt.next();
                palabras.add(w);
            }
            //Oracion con info semantica adicional
            result.add(desambiguarPreposiciones(palabras));
        }
        String res = "";
        for (int i = 0; i < result.size(); i++) {
            res += result.get(i) + "\n";
        }
        analizarDep(res);
        return res;
    }

    public void analizarDep(String line) {
        System.out.println(line);
        MacoOptions op = new MacoOptions(LANG);
        op.setActiveModules(false, true, true, true,
                true, true, true,
                true, true, true);
        op.setDataFiles(
                "",
                DATA + LANG + "/locucions.dat",
                DATA + LANG + "/quantities.dat",
                DATA + LANG + "/afixos.dat",
                DATA + LANG + "/probabilitats.dat",
                DATA + LANG + "/dicc.src",
                DATA + LANG + "/np.dat",
                DATA + "common/punct.dat");
        // Create analyzers.
        Tokenizer tk = new Tokenizer(DATA + LANG + "/tokenizer.dat");
        Splitter sp = new Splitter(DATA + LANG + "/splitter.dat");
        PruebaEtiquetador.labelEstado.setText("Análisis de Dependencias: En Progreso...");
        Maco mf = new Maco(op);
        ChartParser parser = new ChartParser(
                DATA + LANG + "/chunker/grammar-chunk.dat");
        PruebaEtiquetador.labelEstado.setText("Análisis de Dependencias: Espere...");
        DepTxala dep = new DepTxala(DATA + LANG + "/dep/dependences.dat",
                parser.getStartSymbol());
        PruebaEtiquetador.labelEstado.setText("Análisis de Dependencias: Aguante un poco más...");
        HmmTagger tg = new HmmTagger(DATA + LANG + "/tagger.dat", true, 2);
        Nec neclass = new Nec(DATA + LANG + "/nerc/nec/nec-ab-poor1.dat");
        Senses sen = new Senses(DATA + LANG + "/senses.dat"); // sense dictionary
        PruebaEtiquetador.labelEstado.setText("Análisis de Dependencias: Casi...");
        Ukb dis = new Ukb(DATA + LANG + "/ukb.dat"); // sense disambiguator
        // Extract the tokens from the line of text.
        ListWord l = tk.tokenize(line);
        ListSentence ls = sp.split(l, false);
        // Perform morphological analysis
        mf.analyze(ls);

        // Perform part-of-speech tagging.
        tg.analyze(ls);
        
        // Perform named entity (NE) classificiation.
        // Chunk parser
        parser.analyze(ls);
        
        // Dependency parser
        dep.analyze(ls);
        PruebaEtiquetador.labelEstado.setText("Análisis de Dependencias: Completado");
        
        System.out.println("-------- DEPENDENCY PARSER results -----------");

        ListSentenceIterator sIt = new ListSentenceIterator(ls);
        while (sIt.hasNext()) {
            String nuevaOracion = "NuevaOracion";
            relacionArbol.add(nuevaOracion);
            Sentence s = sIt.next();
            TreeDepnode tree = s.getDepTree();
            printDepTree(0, tree);
        }
        
        /*
        *Se llama a la clase encargada de crear la gráfica del árbol
        */
        PruebaEtiquetador.labelEstado.setText("Dibujando árbol de dependencias...");
        Graficador graficador = new Graficador(relacionArbol);
        graficador.crearGraficaArbol();
    }

    private String desambiguarPreposiciones(List<Word> palabras) {
        int i;
        String desambiguado = "";
        String oraciondes = "";
        for (i = 0; i < palabras.size(); i++) {
            if (palabras.get(i).getTag().startsWith("SP")) {
                desambiguado = des.desambiguaTripleta(
                        busca.buscarEnIndice(indexOnto, buscaPrevio(palabras, i)),
                        busca.buscarEnIndice(indexOnto, buscaPosterior(palabras, i)),
                        busca.buscarEnIndiceRel(indexRel, palabras.get(i).getLemma()));
                oraciondes += desambiguado + " ";
            } else {
                oraciondes += palabras.get(i).getForm() + " ";
            }
        }
        System.out.println(oraciondes);
        PruebaEtiquetador.labelEstado.setText("Desambiguación: Completo.");
        return oraciondes;
    }

    private String buscaPrevio(List<Word> palabras, int index) {
        int i;
        for (i = index; i >= 0; i--) {
            if (palabras.get(i).getTag().startsWith("NC")) {
                return palabras.get(i).getLemma();
            }
        }
        return "";
    }

    private String buscaPosterior(List<Word> palabras, int index) {
        int i;
        for (i = index; i < palabras.size(); i++) {
            if (palabras.get(i).getTag().startsWith("NC")) {
                return palabras.get(i).getLemma();
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

    public static Concepto onto;
    private static List<Concepto>[] indexOnto = new ArrayList[24];
    private static List<Relacion> indexRel = new ArrayList<>();
    private CargaOntologia load = new CargaOntologia();
    private BuscadorConceptos busca = new BuscadorConceptos();
    private Desambiguador des = new Desambiguador();
    private static List<String> relacionArbol = new ArrayList<>();
}
