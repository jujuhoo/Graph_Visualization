/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umass.rio.gpm.data;

import edu.umass.rio.gpm.data.proto.GpmCommon.AnswerCoding;
import java.util.Comparator;

/**
 *
 * @author jujuhoo
 */
public class AnswerComparator implements Comparator<AnswerCoding>{
    @Override
    public int compare(AnswerCoding a, AnswerCoding b) {
         if(a.getScore() > b.getScore()){
             return -1;
         }
        if(a.getScore() < b.getScore()){
             return 1;
         }
        return 0;
    }    
}
