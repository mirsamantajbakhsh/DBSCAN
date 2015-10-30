/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.urmiauniversity.it.mst.DBSCAN;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Mir Saman Tajbakhsh
 */
public class DBSCANPanel extends JPanel {

    JTextField eps;
    JTextField mu;

    @SuppressWarnings("unchecked")
    public DBSCANPanel() {
        org.jdesktop.swingx.JXHeader jXHeader1 = new org.jdesktop.swingx.JXHeader();

        jXHeader1.setDescription("Set the initialization parameters for ε (Epsilon) and μ (Mu). Different values results different non-overlapping communities.");
        jXHeader1.setTitle("DBSCAN");

        JLabel epsLabel = new JLabel("Enter value of eps (ε, Neighbout Thresold) here (floating number between 0 and 1):");
        epsLabel.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 13));
        this.add(epsLabel);

        eps = new JTextField();
        this.add(eps);

        JLabel muLabel = new JLabel("Enter value of Mu (μ, Core Thresold) here (Positive non-zero integer):");
        muLabel.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 13));
        this.add(muLabel);

        mu = new JTextField();
        this.add(mu);

        Insets insets = this.getInsets();

        Dimension epsLabelSize = epsLabel.getPreferredSize();
        epsLabel.setBounds(20 + insets.left, 30 + insets.top, epsLabelSize.width, epsLabelSize.height);

        Dimension epsSize = eps.getPreferredSize();
        eps.setBounds(20 + insets.left, 80 + insets.top, epsSize.width + 20, epsSize.height);

        Dimension muLabelSize = epsLabel.getPreferredSize();
        epsLabel.setBounds(20 + insets.left, 180 + insets.top, muLabelSize.width, muLabelSize.height);

        Dimension muSize = eps.getPreferredSize();
        eps.setBounds(20 + insets.left, 230 + insets.top, muSize.width + 20, muSize.height);
        javax.swing.GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jXHeader1, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(epsLabel)
                        .addContainerGap(354, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(eps)
                        .addContainerGap(382, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(muLabel)
                        .addContainerGap(382, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(mu)
                        .addContainerGap(382, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jXHeader1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(epsLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(eps)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(muLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(mu)
                        .addContainerGap(187, Short.MAX_VALUE))
        );
    }

    public int getMu() {
        int i = 0;
        try {
            i = Integer.valueOf(mu.getText());
        } catch (Exception ex) {
            return 0;
        }
        return i;
    }

    public void setMu(double mu) {
        this.mu.setText(String.valueOf(mu));
    }

    public double getEps() {
        double i = 0.0d;
        try {
            i = Double.valueOf(eps.getText());
        } catch (Exception ex) {
            return 0;
        }
        return i;
    }
    
    public void setEps(double eps) {
        this.eps.setText(String.valueOf(eps));
    }
}
