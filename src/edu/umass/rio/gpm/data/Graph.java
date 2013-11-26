/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umass.rio.gpm.data;

import java.util.ArrayList;

/**
 *
 * @author jujuhoo
 */
public class Graph {
    private ArrayList<GraphNode> graphData=  new  ArrayList<GraphNode>();

    /**
     * @return the graphData
     */
    public ArrayList<GraphNode> getGraphData() {
        return graphData;
    }

    /**
     * @param graphData the graphData to set
     */
    public void setGraphData(ArrayList<GraphNode> graphData) {
        this.graphData = graphData;
    }
}
