package com.TT.model;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

public class CargaOntologia {   /*  Clase encargada de leer los archivos xml que contienen la ontologia */
    private String ontoPath;    /*  Guarda la ruta del archivo XML de la ontología  */
    private String relPath;     /*  Guarda la ruta del archivo XML de las relaciones    */
    private static List<Concepto>[] indexOntoAux=new ArrayList[24];
    private List<Relacion> indexRelAux=new ArrayList();
    private Concepto raiz=new Concepto();
    private Relacion raizRel=new Relacion();
    private Ontologia ontologia;
    
    public CargaOntologia(String ontoPath,String relPath){
        this.ontoPath=ontoPath;
        this.relPath=relPath;
        ontologia=new Ontologia(initIndex(), new ArrayList());
        this.cargaOntologia();
    }

    public void cargaOntologia(){
        SAXBuilder saxB = new SAXBuilder(XMLReaders.DTDVALIDATING);
        raiz.setNombre("Root");  
        initIndex();
        try{
            Document document=(Document)saxB.build(new File(ontoPath));
            recorreXML(document.getRootElement(),raiz);
            ontologia.setIndexOnto(indexOntoAux);
            //Cargamos las relaciones
            document=(Document)saxB.build(new File(relPath));
            raizRel=cargaRelaciones(document.getRootElement());
            ontologia.setIndexRel(indexRelAux);
        }catch(JDOMException | IOException ex){
            System.err.print(ex.getMessage());
            System.exit(0);
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
            nuevo.setAgente(ontologia.buscarEnIndice(raiz.getChild("agente").getValue()));
            nuevo.setPasivo(ontologia.buscarEnIndice(raiz.getChild("pasivo").getValue()));
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
            indexRelAux.add(nuevo);
        }
        return nuevo;
    }
    public void recorreXML(Element raiz,Concepto padre){
        Concepto nuevo=new Concepto(); //Nuevo objeto de tipo Concepto    
        try{
            nuevo.setNombre(raiz.getAttributeValue("nombre"));
            nuevo.setPadre(padre);
            //Nuevo concepto creado
            //Se guarda el valor en el indice
            List<Concepto> index=indexOntoAux[nuevo.getNombre().charAt(0)-97];
            index.add(nuevo);
            indexOntoAux[nuevo.getNombre().charAt(0)-97]=index;
          //  System.out.println(indexOntoAux[nuevo.getNombre().charAt(0)-97].get(0).getNombre());
        }catch(Exception a){
        }
        List<Element> hijos=raiz.getChildren("concept"); //Los hijos son aquellos con la etiqueta concept
        for (Element hijo : hijos) {
            //Recorremos todos los hijos
            recorreXML(hijo, nuevo);
        }
    }
        
    private List<Concepto>[] initIndex(){
        for(int i=0;i<24;i++){
            indexOntoAux[i]=new ArrayList<>();
        } 
        return indexOntoAux;
    }
    
    public Ontologia getOntologia(){
        return ontologia;
    }
}