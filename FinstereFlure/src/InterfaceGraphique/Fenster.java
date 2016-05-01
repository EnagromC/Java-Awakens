/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceGraphique;

import Model.Coordonnees;
import Model.Direction;
import com.sun.glass.events.KeyEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Corentin
 */
public class Fenster extends javax.swing.JFrame {

    public static final String VERSION = "0.2";

    /**
     * Creates new form Fenste
     */
    public Fenster() {
        initComponents();
        this.getContentPane().setBackground(new Color(220, 205, 245));

        /*
          Création des jetons joueurs avec ajout de leurs images
         */
        String[] adresses = {"pionred_1_6_clair.gif", "pionpurple_1_6_fonce.gif"};
        pionsPurple[0] = new JPion(adresses);
        String[] adresses2 = {"pionred_3_4_clair.gif", "pionpurple_3_4_fonce.gif"};
        pionsPurple[1] = new JPion(adresses2);
        String[] adresses3 = {"pionred_4_3_clair.gif", "pionpurple_4_3_fonce.gif"};
        pionsPurple[2] = new JPion(adresses3);
        String[] adresses4 = {"pionred_5_2_clair.gif", "pionpurple_5_2_fonce.gif"};
        pionsPurple[3] = new JPion(adresses4);

        String[] adresses5 = {"piongreen_1_6_clair.gif", "piongreen_1_6_fonce.gif"};
        pionsGreen[0] = new JPion(adresses5);
        String[] adresses6 = {"piongreen_3_4_clair.gif", "piongreen_3_4_fonce.gif"};
        pionsGreen[1] = new JPion(adresses6);
        String[] adresses7 = {"piongreen_4_3_clair.gif", "piongreen_4_3_fonce.gif"};
        pionsGreen[2] = new JPion(adresses7);
        String[] adresses8 = {"piongreen_5_2_clair.gif", "piongreen_5_2_fonce.gif"};
        pionsGreen[3] = new JPion(adresses8);

        /*
            A chaque jeton on ajoute un MouseListener afin de détecter un clic dessus
         */
        for (JPion p : pionsPurple) {
            p.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    pionClicked(evt);
                }
            });
        }

        for (JPion p : pionsGreen) {
            p.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    pionClicked(evt);
                }
            });
        }

        /*
            Création et initialisation du plateau
         */
        plateau = new JPlateau();
        plateau.setBounds(100, 50, 694, 479);//permet de définir la position et la taille en même temps        
        this.add(plateau);

        /*
            Là c'est des essais
         */
        plateau.add(pionsPurple[0], new Integer(1));
        pionsPurple[0].setLocation(106, 220);
        pionsPurple[0].setOpaque(false);

        int larg = 4;
        int haut = 220;
        for (int i = 1; i < 4; i++) {
            plateau.add(pionsPurple[i], new Integer(1));
            larg++;
            pionsPurple[i].setLocation(plateau.position(new Coordonnees(6, larg)));
            pionsPurple[i].setOpaque(false);
        }

        JFlaque flaque = new JFlaque(JFlaque.Forme.HORIZ);
        flaque.setLocation(plateau.position(new Coordonnees(5, 6)));
        flaque.setOpaque(false);
        plateau.add(flaque, new Integer(0));
        sortis.add(pionsGreen[1]);
        sortis.add(pionsGreen[2]);
        //pionsGreen[1].setLocation(plateau.position(new Coordonnees(5, 1)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sortis = new JGroupePions(false);
        salleAttenteGreen = new JGroupePions(true);
        salleAttenteRed = new JGroupePions(true);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Finstere Flure v" +this.VERSION);
        setPreferredSize(new java.awt.Dimension(1000, 800));
        setResizable(false);
        setSize(new java.awt.Dimension(1000, 800));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        sortis.setBackground(null);
        sortis.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        sortis.setPreferredSize(new java.awt.Dimension(40, 374));

        javax.swing.GroupLayout sortisLayout = new javax.swing.GroupLayout(sortis);
        sortis.setLayout(sortisLayout);
        sortisLayout.setHorizontalGroup(
            sortisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );
        sortisLayout.setVerticalGroup(
            sortisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 429, Short.MAX_VALUE)
        );

        salleAttenteGreen.setBackground(null);
        salleAttenteGreen.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        salleAttenteGreen.setPreferredSize(new java.awt.Dimension(301, 40));

        javax.swing.GroupLayout salleAttenteGreenLayout = new javax.swing.GroupLayout(salleAttenteGreen);
        salleAttenteGreen.setLayout(salleAttenteGreenLayout);
        salleAttenteGreenLayout.setHorizontalGroup(
            salleAttenteGreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        salleAttenteGreenLayout.setVerticalGroup(
            salleAttenteGreenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        salleAttenteRed.setBackground(null);
        salleAttenteRed.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        salleAttenteRed.setPreferredSize(new java.awt.Dimension(301, 40));

        javax.swing.GroupLayout salleAttenteRedLayout = new javax.swing.GroupLayout(salleAttenteRed);
        salleAttenteRed.setLayout(salleAttenteRedLayout);
        salleAttenteRedLayout.setHorizontalGroup(
            salleAttenteRedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 299, Short.MAX_VALUE)
        );
        salleAttenteRedLayout.setVerticalGroup(
            salleAttenteRedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 38, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(sortis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(124, 124, 124)
                        .addComponent(salleAttenteRed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(74, 74, 74)
                        .addComponent(salleAttenteGreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(200, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(sortis, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(salleAttenteRed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salleAttenteGreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(193, 193, 193))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Detection de la pression d'une touche du clavier. Si c'est une des
     * flèches et qu'un pion est sélectionné, on essaye de déplacer ce pion dans
     * la direction de la flèche.
     *
     * @param evt
     */
    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        Direction d = null;
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                d = Direction.BAS;
                break;
            case KeyEvent.VK_UP:
                d = Direction.HAUT;
                break;
            case KeyEvent.VK_LEFT:
                d = Direction.GAUCHE;
                break;
            case KeyEvent.VK_RIGHT:
                d = Direction.DROITE;
                break;
        }

        if (selected != null && d != null) {

            /*
            METTRE ICI LES INSTRUCTIONS DE DEPLACEMENT
             */
            selected.setLocation(plateau.position(selected.getCoordonnees().plus(d.getVector())));
        }
    }//GEN-LAST:event_formKeyPressed

    /**
     * Instructions à éxécuter quand on clique sur un pion : on le sélectionne
     *
     * @param evt
     */
    private void pionClicked(MouseEvent evt) {
        //vérifier que on a bien cliqué sur un de ses pions, et pas de l'adversaire
        JPion clicked = (JPion) evt.getSource();
        if (clicked.isEnabled()) {
            //this.selected.unselect();
            this.selected.setBorder(null);
            this.selected = clicked;
            //this.selected.select();
            this.selected.setBorder(BorderFactory.createLineBorder(Color.yellow, 5)); //Queque chose pour montrer que le pion est sélectionné
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Fenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Fenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Fenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Fenster.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Fenster().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel salleAttenteGreen;
    private javax.swing.JPanel salleAttenteRed;
    private javax.swing.JPanel sortis;
    // End of variables declaration//GEN-END:variables
    JPion[] pionsPurple = new JPion[4];
    JPion[] pionsGreen = new JPion[4];
    JPlateau plateau;
    JPion selected = new JPion(new String[1]);

}
