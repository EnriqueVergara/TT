package com.TT.controller;

import edu.upc.freeling.ChartParser;
import edu.upc.freeling.DepTxala;
import edu.upc.freeling.HmmTagger;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListWord;
import edu.upc.freeling.Maco;
import edu.upc.freeling.MacoOptions;
import edu.upc.freeling.Nec;
import edu.upc.freeling.Senses;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;
import edu.upc.freeling.Ukb;
import edu.upc.freeling.Util;

public class Freeling { //Esta clase carga todos los parametros para poder usar Freeling
       /*      Variables usadas por el API de Freeling   */    
    private static final String FREELINGDIR = "/usr/local";
    private static final String DATA = FREELINGDIR + "/share/freeling/";
    private static final String LANG = "es";
    private final MacoOptions op;  
    public final Splitter sp;
    public final Maco mf;   
    public final Tokenizer tk;
    public final ChartParser parser;       
    public final DepTxala dep;
    public final HmmTagger tg;
    private final Nec neclass;
    private final Senses sen;
    private final Ukb dis;
        /*      Variables usadas por el API de Freeling   */
    
    public Freeling(){      
            /*  Inicializando variables para usar Freeling   */
        System.loadLibrary( "freeling_javaAPI" );
        Util.initLocale( "default" );
        op=new MacoOptions(LANG);
        op.setActiveModules(false, true, true, true,true, true, true,true, true, true);
        op.setDataFiles("",DATA + LANG + "/locucions.dat",DATA + LANG + "/quantities.dat",
                DATA + LANG + "/afixos.dat",DATA + LANG + "/probabilitats.dat",DATA + LANG + "/dicc.src",
                DATA + LANG + "/np.dat",DATA + "common/punct.dat");
        mf = new Maco(op); 
        tk = new Tokenizer(DATA + LANG + "/tokenizer.dat");
        parser = new ChartParser(DATA + LANG + "/chunker/grammar-chunk.dat"); 
        dep = new DepTxala(DATA + LANG + "/dep/dependences.dat",parser.getStartSymbol());
        tg = new HmmTagger(DATA + LANG + "/tagger.dat", true, 2);  
        neclass = new Nec(DATA + LANG + "/nerc/nec/nec-ab-poor1.dat");
        sen = new Senses(DATA + LANG + "/senses.dat"); // sense dictionary
        dis = new Ukb(DATA + LANG + "/ukb.dat"); // sense disambiguator
        sp = new Splitter( DATA + LANG + "/splitter.dat" );
            /*  Inicializando variables para usar Freeling   */
        
    }
    
}
    
