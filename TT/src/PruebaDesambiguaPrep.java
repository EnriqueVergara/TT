
import java.util.ArrayList;
import java.util.List;


public class PruebaDesambiguaPrep {
    public static Concepto onto;    
    private static List<Concepto>[] indexOnto=new ArrayList[24];
    private static List<Relacion> indexRel=new ArrayList<Relacion>();
    private static CargaOntologia load=new CargaOntologia(); 
    private static BuscadorConceptos busca=new BuscadorConceptos();
    private static Desambiguador des=new Desambiguador();
    public static void main(String[] args){
        //Cargando la Ontologia
                
        load.cargaOntologia();
        indexOnto=load.getIndexOnto();
        indexRel=load.getIndexRel();
        String palabra1="café";
        String palabra2="leche";
        String prep="con";
        String desambiguado=des.desambiguaTripleta(
                                                    busca.buscarEnIndice(indexOnto, palabra1), 
                                                    busca.buscarEnIndice(indexOnto, palabra2), 
                                                    busca.buscarEnIndiceRel(indexRel, prep));
      
        System.out.println();
        System.out.println("Yo tomo "+palabra1+" "+prep+" "+palabra2+" -> "+"Yo tomo "+palabra1+" "+desambiguado+" "+palabra2);
      //  System.out.println(con.getHijos().get(0).getNombre());
        
         palabra1="taza";
        palabra2="café";
         prep="con";
         desambiguado=des.desambiguaTripleta(
                                                    busca.buscarEnIndice(indexOnto, palabra1), 
                                                    busca.buscarEnIndice(indexOnto, palabra2), 
                                                    busca.buscarEnIndiceRel(indexRel, prep));
      
        System.out.println();
        System.out.println("Yo tomo "+palabra1+" "+prep+" "+palabra2+" -> "+"Yo tomo "+palabra1+" "+desambiguado+" "+palabra2);
    
           desambiguado=des.desambiguaTripleta(
                                                    busca.buscarEnIndice(indexOnto, palabra1), 
                                                    busca.buscarEnIndice(indexOnto, palabra2), 
                                                    busca.buscarEnIndiceRel(indexRel, prep));
      
        System.out.println();
        System.out.println("Yo tomo "+palabra1+" "+prep+" "+palabra2+" -> "+"Yo tomo "+palabra1+" "+desambiguado+" "+palabra2);
           desambiguado=des.desambiguaTripleta(
                                                    busca.buscarEnIndice(indexOnto, palabra1), 
                                                    busca.buscarEnIndice(indexOnto, palabra2), 
                                                    busca.buscarEnIndiceRel(indexRel, prep));
      
        System.out.println();
        System.out.println("Yo tomo "+palabra1+" "+prep+" "+palabra2+" -> "+"Yo tomo "+palabra1+" "+desambiguado+" "+palabra2);
           desambiguado=des.desambiguaTripleta(
                                                    busca.buscarEnIndice(indexOnto, palabra1), 
                                                    busca.buscarEnIndice(indexOnto, palabra2), 
                                                    busca.buscarEnIndiceRel(indexRel, prep));
      
        System.out.println();
        System.out.println("Yo tomo "+palabra1+" "+prep+" "+palabra2+" -> "+"Yo tomo "+palabra1+" "+desambiguado+" "+palabra2);
           desambiguado=des.desambiguaTripleta(
                                                    busca.buscarEnIndice(indexOnto, palabra1), 
                                                    busca.buscarEnIndice(indexOnto, palabra2), 
                                                    busca.buscarEnIndiceRel(indexRel, prep));
      
        System.out.println();
        System.out.println("Yo tomo "+palabra1+" "+prep+" "+palabra2+" -> "+"Yo tomo "+palabra1+" "+desambiguado+" "+palabra2);
    palabra1="café";
        palabra2="hermano";
         prep="con";
         desambiguado=des.desambiguaTripleta(
                                                    busca.buscarEnIndice(indexOnto, palabra1), 
                                                    busca.buscarEnIndice(indexOnto, palabra2), 
                                                    busca.buscarEnIndiceRel(indexRel, prep));
      
        System.out.println();
        System.out.println("Yo tomo "+palabra1+" "+prep+" "+palabra2+" -> "+"Yo tomo "+palabra1+" "+desambiguado+" "+palabra2);
}}
