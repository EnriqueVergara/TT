package com.TT.controller;

import com.TT.model.Concepto;
import com.TT.model.Relacion;
import edu.upc.freeling.Analysis;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.VectorWord;
import edu.upc.freeling.Word;

public class ResolvedorConj {
     private boolean compConjuncion(VectorWord vec, int index) {
        boolean noun=false, verb=false;
        boolean prev=false, post=false;
        String verb1="",verb2="";
        int i;
        for(i=0;i<index;i++){
            if(vec.get(i).getTag().startsWith("V")){
                verb=true;
                verb1=vec.get(i).getLemma();
            }
            if(vec.get(i).getTag().startsWith("N"))
                noun=true;
        }
        if(verb==true && noun==true)
            prev=true;
        verb=false;
        noun=false;
        for (i = index; i >= 0; i--) { 
            if(vec.get(i).getTag().startsWith("V")){
                verb=true;
                verb2=vec.get(i).getLemma();
            }
            if(vec.get(i).getTag().startsWith("N"))
                noun=true;

        }
        if(verb==true && noun==true)
            post=true;
        
        if(prev==true && post==true && verb1.equals(verb2))
            return true;
        return false;
    }
    
    public ListSentence resuelveConjunciones(ListSentence ls){
         
        boolean conj=false;
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
                if(vec.get(i).getTag().startsWith("CC")){
                    
                    conj=true;
                    System.out.println("Mira mamá una conjunción");
                    analisis=new Analysis();
                    wordAux=new Word();
                    analisis.setTag("NEWCC");
                    if(compConjuncion(vec, i)){
                        analisis.setLemma("y");
                        wordAux.setAnalysis(analisis);
                        wordAux.setForm("y"); 
                        sentAux.pushBack(wordAux);   
                    }   
                }
                else{                
                    if(conj==true && vec.get(i).getLemma().equals(".")){
                        analisis=new Analysis();
                        wordAux=new Word();
                        analisis.setTag("F-termC");
                        analisis.setLemma(".");
                        wordAux.setAnalysis(analisis);
                        wordAux.setForm("."); 
                        sentAux.pushBack(wordAux);  
                    }               
                    else{
                        sentAux.pushBack(vec.get(i));       
                    }
                }
            }   
            listAux.pushBack(sentAux);
        }
        return listAux;
    }
    
    
}
