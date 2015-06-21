package com.TT.model;

import java.util.ArrayList;
import java.util.List;

public class Ontologia {
        
    private List<Concepto>[] indexOnto=new ArrayList[24];
    private List<Relacion> indexRel=new ArrayList();
    
    public Ontologia(List<Concepto>[] indexOnto,List<Relacion> indexRel){
        this.indexOnto=indexOnto;
        this.indexRel=indexRel;
    }
    
    public Concepto buscarEnIndice(String concepto){
        Concepto result=new Concepto();
        concepto=concepto.toLowerCase();
        int i=0,j=0;
        for(i=0;i<24;i++){
            for(j=0;j<indexOnto[i].size();j++){
                if(indexOnto[i].get(j).getNombre().equals(concepto)){
                    return indexOnto[i].get(j);
                }
            }
        }
        return result;
    }
    
    public Relacion buscarEnIndiceRel(String concepto){
        Relacion result=null;
        int i;
        for(i=0;i<indexRel.size();i++){
            if(indexRel.get(i).getNombre().equals(concepto))
                return indexRel.get(i);
        }
        return result;
    }

    public List<Concepto>[] getIndexOnto() {
        return indexOnto;
    }

    public void setIndexOnto(List<Concepto>[] indexOnto) {
        this.indexOnto = indexOnto;
    }

    public List<Relacion> getIndexRel() {
        return indexRel;
    }

    public void setIndexRel(List<Relacion> indexRel) {
        this.indexRel = indexRel;
    }
    
}
