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
public class GraphNode {
    private int id;
    private int type;
    private String name;
    private ArrayList<Integer> neighors;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the neighors
     */
    public ArrayList<Integer> getNeighors() {
        return neighors;
    }

    /**
     * @param neighors the neighors to set
     */
    public void setNeighors(ArrayList<Integer> neighors) {
        this.neighors = neighors;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
        if(this.name==null || this.name.trim().equals("")){
            this.name = new Integer(this.type).toString();
        }
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("ID "+ this.id+" Type "+this.type+" Name "+this.name+ " Nei Size "+this.neighors.size());
        return sb.toString();
    }
}
