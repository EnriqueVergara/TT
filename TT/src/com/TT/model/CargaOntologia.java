package com.TT.model;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class CargaOntologia {
    private String ontoPath="prueba.xml";
    private String relPath="relacion.xml";
    private static List<Concepto>[] indexOnto=new ArrayList[24];
    private List<Relacion> indexRel=new ArrayList();
    BuscadorConceptos buscar=new BuscadorConceptos();
    private Concepto raiz=new Concepto();
    private Relacion raizRel=new Relacion();
 //   Relacion con=new Relacion();
    public void cargaOntologia(){
        SAXBuilder saxB=new SAXBuilder();
        raiz.setNombre("Root");  
        initIndex();
        try{
            Document document=(Document)saxB.build(new File(ontoPath));
            recorreXML(document.getRootElement(),raiz);
            //Cargamos las relaciones
            document=(Document)saxB.build(new File(relPath));
            raizRel=cargaRelaciones(document.getRootElement());
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public Relacion cargaRelaciones(Element raiz){
        Relacion nuevo=new Relacion();
        List<Relacion> hijosRel=new ArrayList(); //Lista para guardar los hijos de larelacion
        List<Element> hijos=raiz.getChildren("concept"); //Los hijos son aquellos con la etiqueta concept
        for(int i=0;i<hijos.size();i++){
            hijosRel.add(cargaRelaciones(hijos.get(i)));
        }
        nuevo.setHijos(hijosRel);
        try{
            nuevo.setNombre(raiz.getAttributeValue("nombre"));
            //System.out.println(nuevo.getNombre());
            nuevo.setAgente(buscar.buscarEnIndice(indexOnto, raiz.getChild("agente").getValue()));
            nuevo.setPasivo(buscar.buscarEnIndice(indexOnto, raiz.getChild("pasivo").getValue()));
            try{
                nuevo.setTipoAgente(raiz.getChild("tipoAgente").getValue());
                nuevo.setTipoPasivo(raiz.getChild("tipoPasivo").getValue());
            }    
            catch(Exception ex){
                nuevo.setTipoAgente("N");
                nuevo.setTipoPasivo("N");
                        
            }
        }catch(Exception ex){
            //ex.printStackTrace();
            indexRel.add(nuevo);
        }
        return nuevo;
    }
    public void recorreXML(Element raiz,Concepto padre){
        Concepto nuevo=new Concepto(); //Nuevo objeto de tipo Concepto    
        try{
            nuevo.setNombre(raiz.getAttributeValue("nombre"));
//            nuevo.setLenguaje(raiz.getChild("languaje").getValue());
  //          nuevo.setWord(raiz.getChild("word").getValue());
            nuevo.setPadre(padre);
           // System.out.println(nuevo.getNombre());
            //Nuevo concepto creado
            
            //Se guarda el valor en el indice
           // System.out.println(nuevo.getNombre());
            List<Concepto> index=indexOnto[nuevo.getNombre().charAt(0)-97];
            index.add(nuevo);
             
            indexOnto[nuevo.getNombre().charAt(0)-97]=index;
          //  System.out.println(indexOnto[nuevo.getNombre().charAt(0)-97].get(0).getNombre());
       
        }catch(Exception a){
            a.printStackTrace();
        }
         
        List<Element> hijos=raiz.getChildren("concept"); //Los hijos son aquellos con la etiqueta concept
     
        for(int i=0;i<hijos.size();i++){ //Recorremos todos los hijos
            recorreXML(hijos.get(i), nuevo);
        }
    }
        
    private void initIndex(){
        int i=0;
        for(i=0;i<24;i++){
            indexOnto[i]=new ArrayList<>();
        } 
    }
    public static List<Concepto>[] getIndexOnto() {
        return indexOnto;
    }
    public List<Relacion> getIndexRel() {
        return indexRel;
    }

}