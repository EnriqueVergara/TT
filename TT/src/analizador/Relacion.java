package analizador;


import java.util.List;
public class Relacion extends Concepto{
    private Concepto agente;
    private Concepto pasivo;
    private List<Relacion> hijos;
    private String tipoAgente;
    private String tipoPasivo;
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

    public List<Relacion> getHijos() {
        return hijos;
    }
    public void setHijos(List<Relacion> hijos) {
        this.hijos = hijos;
    }
    public String getTipoAgente() {
        return tipoAgente;
    }

    public void setTipoAgente(String tipoAgente) {
        this.tipoAgente = tipoAgente;
    }

    public String getTipoPasivo() {
        return tipoPasivo;
    }

    public void setTipoPasivo(String tipoPasivo) {
        this.tipoPasivo = tipoPasivo;
    }
    
    
}