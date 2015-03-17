
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.ListWord;
import edu.upc.freeling.ListWordIterator;
import edu.upc.freeling.Maco;
import edu.upc.freeling.MacoOptions;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;
import edu.upc.freeling.Util;
import edu.upc.freeling.Word;
import java.util.ArrayList;
import java.util.List;
public class Analizar {  
    private static final String FREELINGDIR = "/usr/local";
    private static final String DATA = FREELINGDIR + "/share/freeling/";
    private static final String LANG = "es";
    public Analizar(){
                        
        System.loadLibrary( "freeling_javaAPI" );
        Util.initLocale( "default" );
        load.cargaOntologia();
        indexOnto=load.getIndexOnto();
        indexRel=load.getIndexRel();
    }
    public void analizarOracion(String line){
        MacoOptions op = new MacoOptions( LANG );
        op.setActiveModules(false, true, true, true, 
                               true, true, true, 
                               true, true, true);
        op.setDataFiles(
            "", 
            DATA+LANG+"/locucions.dat", 
            DATA + LANG + "/quantities.dat",
            DATA + LANG + "/afixos.dat",
            DATA + LANG + "/probabilitats.dat",
            DATA + LANG + "/dicc.src",
            DATA + LANG + "/np.dat",
            DATA + "common/punct.dat");
        Splitter sp = new Splitter( DATA + LANG + "/splitter.dat" );
        Maco mf = new Maco( op );
        Tokenizer tk = new Tokenizer( DATA + LANG + "/tokenizer.dat" );        
        ListWord l = tk.tokenize( line );
        ListSentence ls = sp.split( l, false );
        //Realiza etiquetado
        mf.analyze( ls );
        //Recupera la lista de los elementos encontrados  Lista de oraciones 
        ListSentenceIterator sIt = new ListSentenceIterator(ls);               
        List<String> result=new ArrayList();
        while (sIt.hasNext()) {
            Sentence s = sIt.next();
            //Iterador de palabras
            ListWordIterator wIt = new ListWordIterator(s);
            List<Word> palabras=new ArrayList();
            while (wIt.hasNext()) {
                Word w = wIt.next();
                palabras.add(w);
            }
            //Oracion con info semantica adicional
            result.add(desambiguarPreposiciones(palabras));
        }
    }
    private String desambiguarPreposiciones(List<Word> palabras){
        int i;
        String desambiguado="";
        String oraciondes="";
        for(i=0;i<palabras.size();i++){
            if(palabras.get(i).getTag().startsWith("SP")){
                desambiguado=des.desambiguaTripleta(
                                                    busca.buscarEnIndice(indexOnto, buscaPrevio(palabras,i)), 
                                                    busca.buscarEnIndice(indexOnto, buscaPosterior(palabras,i)), 
                                                    busca.buscarEnIndiceRel(indexRel, palabras.get(i).getLemma()));
                oraciondes+=desambiguado+" ";
            }
            else
                oraciondes+=palabras.get(i).getForm()+" ";
        }
        System.out.println(oraciondes);
       return oraciondes;
    }
        
    private String buscaPrevio(List<Word> palabras,int index) {
        int i;
        for(i=index;i>=0;i--){
            if(palabras.get(i).getTag().startsWith("NC"))
                return palabras.get(i).getLemma();
        }
        return "";
    }

    private String buscaPosterior(List<Word> palabras,int index) {
        int i;
        for(i=index;i<palabras.size();i++){
            if(palabras.get(i).getTag().startsWith("NC"))
                return palabras.get(i).getLemma();
        }
        return null;
    }
       
    public static Concepto onto;    
    private static List<Concepto>[] indexOnto=new ArrayList[24];
    private static List<Relacion> indexRel=new ArrayList<Relacion>();
    private static CargaOntologia load=new CargaOntologia(); 
    private static BuscadorConceptos busca=new BuscadorConceptos();
    private static Desambiguador des=new Desambiguador();

}
