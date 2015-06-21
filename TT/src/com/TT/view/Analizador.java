package com.TT.view;

public class Analizador extends javax.swing.JFrame {
        
    public static int oraciones;
    public Thread hiloAnalizar;
    public Thread hiloProgressBar;
    
    public Analizador() {

        initComponents();
        this.setLocationRelativeTo(null);
        progressBar.setStringPainted(true);
        buttonNuevo.setEnabled(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        line = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        botonAnalizar = new javax.swing.JButton();
        tagged = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        labelEstado = new javax.swing.JLabel();
        buttonNuevo = new javax.swing.JButton();
        panelImagenes = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        menuImagen = new javax.swing.JMenu();
        menuZoom = new javax.swing.JMenu();
        zoom150 = new javax.swing.JMenuItem();
        zoom125 = new javax.swing.JMenuItem();
        zoom75 = new javax.swing.JMenuItem();
        zoom50 = new javax.swing.JMenuItem();
        zoom25 = new javax.swing.JMenuItem();
        menuRestaurar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Módulo de análisis semántico");

        jLabel1.setText("Ingresa la oracion:");

        botonAnalizar.setText("Analizar");

        buttonNuevo.setText("Nueva oración");

        javax.swing.GroupLayout panelImagenesLayout = new javax.swing.GroupLayout(panelImagenes);
        panelImagenes.setLayout(panelImagenesLayout);
        panelImagenesLayout.setHorizontalGroup(
            panelImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelImagenesLayout.setVerticalGroup(
            panelImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        menuImagen.setText("Imagen");

        menuZoom.setText("Zoom");

        zoom150.setText("150%");
        menuZoom.add(zoom150);

        zoom125.setText("125%");
        menuZoom.add(zoom125);

        zoom75.setText("75%");
        menuZoom.add(zoom75);

        zoom50.setText("50%");
        menuZoom.add(zoom50);

        zoom25.setText("25%");
        menuZoom.add(zoom25);

        menuImagen.add(menuZoom);

        menuRestaurar.setText("Restaurar");
        menuImagen.add(menuRestaurar);

        menuBar.add(menuImagen);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(panelImagenes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 143, Short.MAX_VALUE)
                        .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(121, 121, 121))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(labelEstado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(line, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(botonAnalizar)
                                        .addGap(18, 18, 18)
                                        .addComponent(buttonNuevo)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addComponent(tagged, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(line, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAnalizar)
                    .addComponent(buttonNuevo))
                .addGap(16, 16, 16)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tagged, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(panelImagenes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("desktopPane");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonAnalizar;
    public javax.swing.JButton buttonNuevo;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel labelEstado;
    public javax.swing.JTextField line;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuImagen;
    public javax.swing.JMenuItem menuRestaurar;
    private javax.swing.JMenu menuZoom;
    public javax.swing.JPanel panelImagenes;
    public static javax.swing.JProgressBar progressBar;
    public static javax.swing.JLabel tagged;
    public javax.swing.JMenuItem zoom125;
    public javax.swing.JMenuItem zoom150;
    public javax.swing.JMenuItem zoom25;
    public javax.swing.JMenuItem zoom50;
    public javax.swing.JMenuItem zoom75;
    // End of variables declaration//GEN-END:variables
}
