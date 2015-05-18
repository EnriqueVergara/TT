package analizador;


import java.util.List;

public class BuscadorConceptos {
    public Concepto buscarEnIndice(List<Concepto>[] index,String concepto){
        Concepto result=new Concepto();
        concepto=concepto.toLowerCase();
        int i=0,j=0;
        for(i=0;i<24;i++){
            for(j=0;j<index[i].size();j++){
                if(index[i].get(j).getNombre().equals(concepto)){
                    return index[i].get(j);
                }
            }
        }
        return result;
    }
    
    public Relacion buscarEnIndiceRel(List<Relacion> indexRel,String concepto){
        Relacion result=new Relacion();
        int i;
        for(i=0;i<indexRel.size();i++){
            if(indexRel.get(i).getNombre().equals(concepto))
                return indexRel.get(i);
        }
        return result;
    }
}
