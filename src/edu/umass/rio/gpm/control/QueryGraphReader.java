/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umass.rio.gpm.control;

import edu.umass.rio.gpm.data.Graph;
import edu.umass.rio.gpm.data.GraphNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author jujuhoo
 */
public class QueryGraphReader {
    public Graph readGraph(String path){
        Graph g = new Graph();
        HashMap<String, String> nameMap = new HashMap<String, String>();
        try {
            FileReader reader = new FileReader(path);
            BufferedReader br = new BufferedReader(reader);
            String s1 = null;
            while ((s1 = br.readLine()) != null) {
                if(s1.startsWith("#")){
                    String[] map = s1.substring(1).split(" ");
                    nameMap.put(map[0], map[1]);
                    continue;
                }
                String id = s1.split("\t")[0];
                String type = s1.split("\t")[1].split(";")[0].trim();
                String[] nei = s1.split("\t")[1].split(";")[1].split(" ");
                GraphNode gn = new GraphNode();
                gn.setName(nameMap.get(id));
                gn.setId(new Integer(id));
                gn.setType(new Integer(type));
                ArrayList<Integer> arr = new ArrayList<Integer>();
                for(int i=0; i<nei.length; i++){
                    arr.add(new Integer(nei[i]));
                }
                gn.setNeighors(arr);
                g.getGraphData().add(gn);
                System.out.println(gn.toString());
            }
            br.close();
            reader.close();
        } catch (Exception e) {
            System.out.print(e);
        }
        return g;
    }
}
