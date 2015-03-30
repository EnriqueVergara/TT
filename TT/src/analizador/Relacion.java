package analizador;


import java.util.List;
public class Relacion extends Concepto{
    private Concepto agente;
    private Concepto pasivo;
    private List<Relacion> hijos;
    public Concepto getAgente() {
        return agente;
    }
    public void setAgente(Concepto agente) {
        this.agente = agente;
    }
    public Concepto getPasivo() {
        return pasivo;
    }
    public void setPasivo(Concepto pasivo) {
        this.pasivo = pasivo;
    }

    /**
     * @return the hijos
     */
    public List<Relacion> getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(List<Relacion> hijos) {
        this.hijos = hijos;
    }
    
    
}
