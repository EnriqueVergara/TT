package analizador;

public class Desambiguador {
    public String desambiguaTripleta(Concepto agente,Concepto pasivo,Relacion rel){
//        System.out.println(agente.getNombre());
//        System.out.println(pasivo.getNombre());
        for(int i=0;i<rel.getHijos().size();i++){
//                        System.out.println("Intentando con: "+rel.getHijos().get(i).getNombre());
//            System.out.println(desambiguaRelacion(agente, pasivo, rel.getHijos().get(i)));
            if(desambiguaRelacion(agente, pasivo, rel.getHijos().get(i)))
                return rel.getHijos().get(i).getNombre();
        }
            
        return "";
    }
    public boolean desambiguaRelacion(Concepto agente,Concepto pasivo,Relacion rel){
        if(empatar(agente, rel.getAgente()) && empatar(pasivo, rel.getPasivo()))
            return true;
//        System.out.println("lado izquierdo: "+empatar(agente, rel.getAgente()));
//        System.out.println("lado derecho: "+empatar(pasivo, rel.getPasivo()));
//        System.out.println();
        return false;
                
                
    }
    public boolean empatar(Concepto concepto1,Concepto concepto2){
//        System.out.println("Concepto a empatar:"+concepto2.getNombre());
//        System.out.println("Concepto a intentar: "+concepto1.getNombre());
//        System.out.println();
        if(concepto1.getNombre().equals("entidad"))
            return false;
        if(concepto1.equals(concepto2))
            return true;
        else
            return empatar(concepto1.getPadre(),concepto2);
    }
}
