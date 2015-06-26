package com.TT.view;

public class Analizador extends javax.swing.JFrame {
        
    public static int oraciones;
    public Thread hiloAnalizar;
    public Thread hiloProgressBar;
    
    public Analizador() {

        initComponents();
        this.setLocationRelativeTo(null);
        //progressBar.setStringPainted(true);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        botonAnalizar = new javax.swing.JButton();
        tagged = new javax.swing.JLabel();
        labelEstado = new javax.swing.JLabel();
        buttonNuevo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        line = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        panelImagenes = new javax.swing.JPanel();
        panelImgFondo = new javax.swing.JPanel();
        checkBoxAnalisisOriginal = new javax.swing.JCheckBox();
        menuBar = new javax.swing.JMenuBar();
        menuImagen = new javax.swing.JMenu();
        menuZoom = new javax.swing.JMenu();
        zoom150 = new javax.swing.JMenuItem();
        zoom125 = new javax.swing.JMenuItem();
        zoom75 = new javax.swing.JMenuItem();
        zoom50 = new javax.swing.JMenuItem();
        zoom25 = new javax.swing.JMenuItem();
        menuRestaurar = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        menuAcercaDe = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Módulo de análisis semántico");
        setResizable(false);

        jLabel1.setText("Ingresa la oracion:");

        botonAnalizar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        botonAnalizar.setText("Analizar");

        buttonNuevo.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        buttonNuevo.setText("Nueva oración");

        line.setColumns(20);
        line.setRows(5);
        line.setToolTipText("Ingresar texto");
        line.setAutoscrolls(false);
        jScrollPane1.setViewportView(line);
        line.getAccessibleContext().setAccessibleParent(this);

        panelImagenes.setPreferredSize(new java.awt.Dimension(900, 1000));

        javax.swing.GroupLayout panelImagenesLayout = new javax.swing.GroupLayout(panelImagenes);
        panelImagenes.setLayout(panelImagenesLayout);
        panelImagenesLayout.setHorizontalGroup(
            panelImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1039, Short.MAX_VALUE)
        );
        panelImagenesLayout.setVerticalGroup(
            panelImagenesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );

        jScrollPane2.setViewportView(panelImagenes);

        javax.swing.GroupLayout panelImgFondoLayout = new javax.swing.GroupLayout(panelImgFondo);
        panelImgFondo.setLayout(panelImgFondoLayout);
        panelImgFondoLayout.setHorizontalGroup(
            panelImgFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1081, Short.MAX_VALUE)
        );
        panelImgFondoLayout.setVerticalGroup(
            panelImgFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 687, Short.MAX_VALUE)
        );

        checkBoxAnalisisOriginal.setBackground(new java.awt.Color(226, 213, 179));
        checkBoxAnalisisOriginal.setText("Realizar análisis original de Freeling");

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

        jMenu1.setText("Ayuda");

        menuAcercaDe.setText("Acerca de...");
        jMenu1.add(menuAcercaDe);

        menuBar.add(jMenu1);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(botonAnalizar)
                        .addGap(28, 28, 28)
                        .addComponent(buttonNuevo)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1057, Short.MAX_VALUE)
                            .addComponent(labelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(49, 49, 49)
                                        .addComponent(checkBoxAnalisisOriginal)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(tagged, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(166, 166, 166)))
                        .addContainerGap())))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelImgFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(checkBoxAnalisisOriginal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAnalizar)
                    .addComponent(buttonNuevo))
                .addGap(15, 15, 15)
                .addComponent(labelEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tagged, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelImgFondo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getAccessibleContext().setAccessibleName("desktopPane");

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton botonAnalizar;
    public javax.swing.JButton buttonNuevo;
    public javax.swing.JCheckBox checkBoxAnalisisOriginal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public static javax.swing.JLabel labelEstado;
    public javax.swing.JTextArea line;
    public javax.swing.JMenuItem menuAcercaDe;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu menuImagen;
    public javax.swing.JMenuItem menuRestaurar;
    private javax.swing.JMenu menuZoom;
    public javax.swing.JPanel panelImagenes;
    public javax.swing.JPanel panelImgFondo;
    public static javax.swing.JLabel tagged;
    public javax.swing.JMenuItem zoom125;
    public javax.swing.JMenuItem zoom150;
    public javax.swing.JMenuItem zoom25;
    public javax.swing.JMenuItem zoom50;
    public javax.swing.JMenuItem zoom75;
    // End of variables declaration//GEN-END:variables
}
