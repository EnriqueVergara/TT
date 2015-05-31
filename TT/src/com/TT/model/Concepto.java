package com.TT.model;

public class Concepto {
    private String nombre;
    private Concepto padre;
    private String word;
    private String lenguaje;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Concepto getPadre() {
        return padre;
    }
    public void setPadre(Concepto padre) {
        this.padre = padre;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }
    
}
