/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.urmiauniversity.it.mst.DBSCAN;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;
import org.gephi.data.attributes.api.AttributeModel;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.utils.longtask.spi.LongTask;
import org.gephi.utils.progress.Progress;
import org.gephi.utils.progress.ProgressTicket;

/**
 *
 * @author Mir Saman Tajbakhsh
 */
public class DBSCAN implements org.gephi.statistics.spi.Statistics, LongTask {

    //DBSCAN
    private double eps = 0.0d;
    private int mu = 0;
    private int ClusterName = -1;
    private int ProgressIndex = 0;
    private static int ColIndx;
    HashMap<Node, Boolean> visited = new HashMap<Node, Boolean>();
    HashMap<String, Integer> Members = new HashMap<String, Integer>();

    //LongTask
    private boolean cancel = false;
    private ProgressTicket progressTicket;

    @Override
    public void execute(GraphModel gm, AttributeModel am) {
        Graph g = gm.getGraphVisible();

        //Initializing
        Progress.start(progressTicket, 100);
        Progress.setDisplayName(progressTicket, "Initializing ...");
        for (Node tmp : g.getNodes()) {
            visited.put(tmp, false); //No node visited.
        }

        //Add Columns to data output
        if (!am.getNodeTable().hasColumn("DBSCAN")) {
            am.getNodeTable().addColumn("DBSCAN", AttributeType.STRING);
        } else {
            //reset all values
            for (Node tmp : g.getNodes()) {
                tmp.getNodeData().getAttributes().setValue(ColIndx, "");
            }
        }
        ColIndx = am.getNodeTable().getColumn("DBSCAN").getIndex();

        Progress.setDisplayName(progressTicket, "Calculating ...");
        //DBSCAN
        for (Node tmp : g.getNodes()) {
            if (!isVisited(tmp)) {
                setVisited(tmp);

                Vector<Node> neighbours = regionQuery(g, tmp, eps);

                if (neighbours.size() < mu) {
                    //Mark tmp as Noise.
                    addToCluster(tmp, "NOISE");
                } else {
                    ClusterName++; //Next Cluster
                    expandCluster(g, tmp, neighbours);
                }
            } else {
                //Do nothing!
                //continue;
            }
            ProgressIndex++;
            Progress.progress(progressTicket, (int)((((double)ProgressIndex) / ((double)g.getNodeCount())) * 100));
        }
        
        Progress.finish(progressTicket);
    }

    private boolean isVisited(Node n) {
        return visited.get(n);
    }

    private void setVisited(Node n) {
        visited.remove(n);
        visited.put(n, true);
    }

    private Vector<Node> regionQuery(Graph g, Node tmp, double eps) {
        Vector<Node> neighbours = new Vector<Node>();

        //Add the node to the set.
        neighbours.add(tmp);

        for (Node candidate : g.getNeighbors(tmp)) {
            if (Similarity(g, tmp, candidate) >= eps) {
                neighbours.add(candidate);
            }
        }

        return neighbours;
    }

    private double Similarity(Graph g, Node main, Node neighbour) {
        //Similarity based on
        //Novel heuristic density-based method for community detection in networks
        //Maoguo Gong , Jie Liu, Lijia Ma, Qing Cai, Licheng Jiao
        //http://dx.doi.org/10.1016/j.physa.2014.01.043

        int intersect = 0;

        Set<Node> neighboursHash = new HashSet<Node>();
        Iterator<Node> i = g.getNeighbors(main).iterator();
        while (i.hasNext()) {
            neighboursHash.add(i.next());
        }

        //Find if the the other node's neoghbours are in the first node's hashed neighbours?
        for (Node possible : g.getNeighbors(neighbour)) {
            if (neighboursHash.contains(possible)) {
                intersect++;
            }
        }

        //return result
        double result = ((((double) intersect)) / Math.pow(((double) (g.getNeighbors(main).toArray().length * g.getNeighbors(neighbour).toArray().length)), 0.5d));
        return result;
    }

    private void expandCluster(Graph g, Node p, Vector<Node> neighbours) {
        //Add P to current cluster.
        addToCluster(p, ClusterName);

        //for (Node n : neighbours) {
        for (int i = 0; i < neighbours.size(); i++) {
            Node n = neighbours.get(i);
            if (!isVisited(n)) {
                setVisited(n);

                Vector<Node> newNodes = regionQuery(g, n, eps);
                if (newNodes.size() >= mu) { //Neighbour is a core node, So add members to neighbours.
                    //neighbours.addAll(newNodes);
                    for (Node newNeighbours : newNodes) {
                        if (!neighbours.contains(newNeighbours)) {
                            neighbours.add(newNeighbours);
                        }
                    }
                }
            }

            if (getCluster(n) == null || getCluster(n) == "") {
                addToCluster(n, ClusterName);
            }
        }
    }

    @Override
    public String getReport() {
        String report = "<h1>DBSCAN</h1><br />"
                + "The result of clustering with μ: <font size='3' color='red'>" + mu + "</font> and ε: <font size='3' color='red'>" + eps + "</font><br />"
                + "Total clusters (including NOISE): " + (ClusterName + 2) + "<br />"
                + "<table><tr><td>Cluster</td><td>Members Count</td></tr>";
        for (String s : Members.keySet()) {
            //TODO Members are not returned correctly.
            report += "<tr><td>" + s + "</td><td>" + (Members.get(s) == null ? "NaN" : Members.get(s)) + "</td></tr>";
        }
        report += "</table><br /><h1>Reference</h1><br /><ul><li>Novel heuristic density-based method for community detection in networks; Maoguo Gong , Jie Liu, Lijia Ma, Qing Cai, Licheng Jiao; "
                + "<a href='http://dx.doi.org/10.1016/j.physa.2014.01.043'>http://dx.doi.org/10.1016/j.physa.2014.01.043</a></li><li><a href='https://en.wikipedia.org/wiki/DBSCAN'>Wikipedia entry for DBSCAN</a></li></ul>";

        report += "<br /><h1>Developer</h1><br />Mir Saman Tajbakhsh<br />IT PhD. Candidate<br />Urmia University<br />Urmia, Iran<br /><a href='https://mstajbakhsh.ir'>https://mstajbakhsh.ir</a><br />Contact me at: <a href='mailto:ms.tajbakhsh@urmia.ac.ir?Subject=DBSCAN Gephi Plugin'>ms.tajbakhsh@urmia.ac.ir</a>";

        return report;
    }

    @Override
    public boolean cancel() {
        cancel = true;
        return true;
    }

    @Override
    public void setProgressTicket(ProgressTicket pt) {
        this.progressTicket = pt;
    }

    public double getEps() {
        return 0.0d;
    }

    public void setEps(double eps) {
        this.eps = eps;
    }

    public double getMu() {
        return 0.0;
    }

    public void setMu(int mu) {
        this.mu = mu;
    }

    private void addToCluster(Node n, Object cluster) {
        n.getNodeData().getAttributes().setValue(ColIndx, cluster.toString());
        Integer formerMembers = Members.get(cluster.toString());

        if (formerMembers != null) {
            Members.remove(cluster);
            Members.put(cluster.toString(), ++formerMembers);
        } else {
            Members.remove(cluster);
            Members.put(cluster.toString(), 1);
        }
    }

    private Object getCluster(Node n) {
        return n.getNodeData().getAttributes().getValue(ColIndx);
    }
}
