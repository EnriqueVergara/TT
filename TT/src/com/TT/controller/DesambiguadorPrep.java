package com.TT.controller;

import com.TT.model.Concepto;
import com.TT.model.Ontologia;
import com.TT.model.Relacion;
import edu.upc.freeling.Analysis;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.VectorWord;
import edu.upc.freeling.Word;
import java.util.ArrayList;
import java.util.List;

public class DesambiguadorPrep {
        
    private Desambiguador des = new Desambiguador();
    private List<Concepto>[] indexOnto = new ArrayList[24];
    private List<Relacion> indexRel = new ArrayList<>();
    public static boolean termino = false;
    private Ontologia ontologia;
        
    public DesambiguadorPrep(Ontologia ontologia) { 
      this.ontologia=ontologia;
    }
     
    private String buscaPrevio(VectorWord vec, int index,String tipo) {
        int i;
        for (i = index; i >= 0; i--) {
            if (vec.get(i).getTag().startsWith(tipo)) {
                return vec.get(i).getLemma();
            }
        }
        return "";
    }

    private String buscaPosterior(VectorWord vec, int index,String tipo) {
        int i;
        for (i = index; i < vec.size(); i++) {
            if (vec.get(i).getTag().startsWith(tipo)) {
                return vec.get(i).getLemma();
            }
        }
        return null;
    }
    
    public ListSentence desambiguarPreposiciones(ListSentence ls){
                
        ListSentence listAux=new ListSentence();
        String desambiguado=new String();           
        Relacion rel;
        Concepto agente;
        Concepto pasivo;                                    /*  Declaración de varibles */
        Analysis analisis;
        Word wordAux;   
        Sentence sentAux;
        ListSentenceIterator sIt;
        sIt = new ListSentenceIterator(ls);
         
        while(sIt.hasNext()){
            Sentence sent=sIt.next();
            VectorWord vec=sent.getWords();            
            sentAux=new Sentence();
            for(int i=0;i<vec.size();i++){
                if(vec.get(i).getTag().startsWith("SP")){
                    analisis=new Analysis(); 
                    wordAux=new Word();
                    analisis.setTag("SPS00"); 
                    wordAux.setForm(vec.get(i).getForm());
                    analisis.setLemma(vec.get(i).getForm());
                    try{
                    rel=ontologia.buscarEnIndiceRel(vec.get(i).getLemma()); 
                    for(int cont=0;cont<rel.getHijos().size();cont++){
                        agente=ontologia.buscarEnIndice(buscaPrevio(vec, i,rel.getHijos().get(cont).getTipoAgente())); 
                        pasivo=ontologia.buscarEnIndice(buscaPosterior(vec, i,rel.getHijos().get(cont).getTipoPasivo()));
                        if(des.desambiguaRelacion(agente, pasivo, rel.getHijos().get(cont)))  {
                            desambiguado=rel.getHijos().get(cont).getNombre();    
                            cont=rel.getHijos().size();
                        }
                    } 
                    
                    if(!desambiguado.equals(""))
                    {   
                        analisis.setLemma(desambiguado); 
                        //wordAux.setForm(vec.get(i).getForm()); 
                        wordAux.setForm(desambiguado);
                        desambiguado="";
                    }
                    wordAux.setAnalysis(analisis);
                    sentAux.pushBack(wordAux);
                    }catch(Exception ex){
                       // ex.printStackTrace();
                        sentAux.pushBack(vec.get(i));
                    }
                }
                else{           
                    sentAux.pushBack(vec.get(i));
                }
            }
            listAux.pushBack(sentAux);
        }
        return listAux;
    }
    
}
