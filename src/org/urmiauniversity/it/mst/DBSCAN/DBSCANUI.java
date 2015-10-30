/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.urmiauniversity.it.mst.DBSCAN;

import javax.swing.JPanel;
import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsUI;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mir Saman Tajbakhsh
 */
@ServiceProvider(service = StatisticsUI.class)
public class DBSCANUI implements StatisticsUI {

    private DBSCANPanel panel;
    private DBSCAN myDBSCAN;

    @Override
    public JPanel getSettingsPanel() {
        panel = new DBSCANPanel();
        return panel;
    }

    @Override
    public void setup(Statistics ststcs) {
        this.myDBSCAN = (DBSCAN) ststcs;
        if (panel != null) {
            panel.setEps(myDBSCAN.getEps());
            panel.setMu(myDBSCAN.getMu());
        }
    }

    @Override
    public void unsetup() {
        if (panel != null) {
            myDBSCAN.setEps(panel.getEps());
            myDBSCAN.setMu(panel.getMu());
        }
        panel = null;
    }

    @Override
    public Class<? extends Statistics> getStatisticsClass() {
        return DBSCAN.class;
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "DBSCAN";
    }

    @Override
    public String getShortDescription() {
        return "DBSCAN Community Detection";
    }

    @Override
    public String getCategory() {
        return CATEGORY_NETWORK_OVERVIEW;
    }

    @Override
    public int getPosition() {
        return 1000;
    }

}
