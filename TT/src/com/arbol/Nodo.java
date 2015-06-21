/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.arbol;

/**
 *
 * @author bruno
 */
public class Nodo {
    String name;
    String namePadre;
    String tag;
    String tagPadre;
    String idPadre;
    String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(String idPadre) {
        this.idPadre = idPadre;
    }

    public String getNamePadre() {
        return namePadre;
    }

    public void setNamePadre(String namePadre) {
        this.namePadre = namePadre;
    }

    public String getTagPadre() {
        return tagPadre;
    }

    public void setTagPadre(String tagPadre) {
        this.tagPadre = tagPadre;
    }

}
