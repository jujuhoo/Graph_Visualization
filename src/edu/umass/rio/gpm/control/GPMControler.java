/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.umass.rio.gpm.control;

import edu.umass.rio.gpm.data.Graph;
import edu.umass.rio.gpm.data.proto.GpmCommon;
import edu.umass.rio.gpm.data.proto.GpmCommon.AnswerCoding;
import edu.umass.rio.gpm.data.proto.GpmCommon.EdgeScore;
import edu.umass.rio.gpm.data.proto.GpmCommon.MatchingPair;
import java.io.FileWriter;

/**
 *
 * @author jujuhoo
 */
public class GPMControler {
    static public String TestAnswerGraph(){
        AnswerCoding.Builder answer = AnswerCoding.newBuilder();
        
        MatchingPair.Builder mp1 = MatchingPair.newBuilder();
        MatchingPair.Builder mp2 = MatchingPair.newBuilder();
        MatchingPair.Builder mp3 = MatchingPair.newBuilder();
        MatchingPair.Builder mp4 = MatchingPair.newBuilder();
        

        
        
        mp1.setQid(-1);
        mp1.setMatch(11);
        mp1.setName("aaa");
        
        
        mp2.setQid(-2);
        mp2.setMatch(22);
        mp2.setName("bbb");
        
        mp3.setQid(-3);
        mp3.setMatch(33);
        mp3.setName("ccc");
        
        mp4.setQid(-4);
        mp4.setMatch(44);
        mp4.setName("ddd");
        
        
        EdgeScore.Builder e1 = EdgeScore.newBuilder();
        EdgeScore.Builder e2 = EdgeScore.newBuilder();
        EdgeScore.Builder e3 = EdgeScore.newBuilder();
        EdgeScore.Builder e4 = EdgeScore.newBuilder();
        EdgeScore.Builder e5 = EdgeScore.newBuilder();
        

        
        e1.setSrc(11);
        e1.setDest(22);
        e1.setPathLen(2);
        e1.setPathNum(5);
        e1.setScore(0.52);
        
        
        e2.setSrc(22);
        e2.setDest(44);
        e2.setPathLen(1);
        e2.setPathNum(5);
        e2.setScore(0.78);
        
        e3.setSrc(22);
        e3.setDest(33);
        e3.setPathLen(2);
        e3.setPathNum(1);
        e3.setScore(0.51);
        
        e4.setSrc(33);
        e4.setDest(44);
        e4.setPathLen(3);
        e4.setPathNum(7);
        e4.setScore(0.30);    
        
        
        e5.setSrc(11);
        e5.setDest(44);
        e5.setPathLen(2);
        e5.setPathNum(1);
        e5.setScore(0.5); 
        
        
        answer.addEdges(e1.build());
        answer.addEdges(e2.build());
        answer.addEdges(e3.build());
        answer.addEdges(e4.build());
        answer.addEdges(e5.build());
        answer.addPairs(mp1.build());
        answer.addPairs(mp2.build());
        answer.addPairs(mp3.build());
        answer.addPairs(mp4.build());
        answer.setMaxscore(0.99);
        answer.setScore(5.5);
        
        return GPMControler.getAnswerGraphDot(answer.build());
           
    } 
    static public String getGraphDot(String fileName, String dot_text){
            String imagePath = "";
            try {
            
            FileWriter fw = new FileWriter(fileName+".gv");
            fw.write(dot_text, 0, dot_text.length());
            fw.flush();
            fw.close();
            String cmd="circo -Tpng "+fileName+".gv -o "+fileName+".png"; 
            Process ps = Runtime.getRuntime().exec(cmd);  
            ps.waitFor();
            imagePath = System.getProperty("user.dir")+"/"+fileName+".png";
        } catch (Exception e) {
        }
        return imagePath;
    }
   static public String getAnswerGraphDot(AnswerCoding answer){
        String imagePath = "";
        String dot_script = GPMControler.getAnswerDotText(answer);
        return getGraphDot("answer_graph", dot_script);
    }
   static public String getQueryGraphDot(Graph g){
        String imagePath = "";
        String dot_script = GPMControler.getQueryGraphDotText(g);
        return getGraphDot("query_graph", dot_script);
    }
   static private String getAnswerDotText(AnswerCoding answer){
        int dummy_id=0;
        double max_score= answer.getMaxscore();
        StringBuilder sb = new StringBuilder();
        final String ENDL = "\r\n";
        sb.append("graph{"+ENDL);
        sb.append("edge[fontsize=\"10px\"];"+ENDL);
        for(int i=0; i<answer.getEdgesCount(); i++){
            EdgeScore e = answer.getEdges(i);
            double norm_score = e.getScore()/max_score;
            StringBuilder ss_link_score = new StringBuilder();
            ss_link_score.append(" hop:"+e.getPathLen()+"\\l num:"+e.getPathNum()
                    +"\\l score:"+e.getScore());
            if (e.getPathLen() > 1) {
                sb.append(e.getSrc()+"--"+"d"+dummy_id
                        +"[penwidth="+norm_score*5+",label=\""
                        +ss_link_score.toString()+ "\"];"+ENDL);
                for (int j = 1; j < e.getPathLen() - 1; j++) {
                    int id1 = dummy_id;
                    int id2 = ++dummy_id;
                    sb.append("d"+id1+"--"
                            +"d"+id2+"[penwidth="+norm_score*5+"];"+ENDL);
                }
                 sb.append("d"+dummy_id+"--"
                            +e.getDest()+"[penwidth="+norm_score*5+"];"+ENDL);
                dummy_id++;
            } else {
                sb.append(e.getSrc()+"--"+e.getDest()
                        +"[penwidth="+norm_score*5+",label=\""
                        +ss_link_score.toString()+ "\"];"+ENDL);
                }
        }
        for(int i=0; i<answer.getPairsCount(); i++){
            MatchingPair pair = answer.getPairs(i);
            sb.append(pair.getMatch()+"[label=\""+pair.getName()+"\",fillcolor=\"/paired12/"
                    +(-1*pair.getQid())+"\",style=\"filled\"]"+ENDL);
	}
	for(int i=0; i<dummy_id;i++){
            sb.append("d"+i+"[label=\"\",width=.25, height=.25, fixedsize=true]"+ENDL);

	}
	 sb.append("}");       
         
        return sb.toString();
    }
    
   static private String getQueryGraphDotText(Graph g){
        StringBuilder sb = new StringBuilder();
        final String ENDL = "\r\n";
        sb.append("graph{"+ENDL);
        sb.append("edge[fontsize=\"10px\"];"+ENDL);
        for(int i=0; i < g.getGraphData().size(); i++){
            for(int j=i+1; j < g.getGraphData().size(); j++){
                if(g.getGraphData().get(j).getNeighors().contains( g.getGraphData().get(i).getId())){
                    sb.append(g.getGraphData().get(i).getType()+"--"+g.getGraphData().get(j).getType()+ENDL);
                }
            }
        }
        
        for(int i=0; i < g.getGraphData().size(); i++){
            sb.append(g.getGraphData().get(i).getType()
                    +"[label=\""+g.getGraphData().get(i).getName()+"\",fillcolor=\"/paired12/"
                    +(-1*g.getGraphData().get(i).getId())+"\",style=\"filled\"]"+ENDL);        
        }
        sb.append("}");
        return sb.toString();
    }
}
/*
	vector<list<Matching>::iterator> sort_vec;
		MathingIterCompareHeapMin comp;
		for(list<Matching>::iterator iter=local_topk_.begin(); iter != local_topk_.end();
				iter++){
			sort_vec.push_back(iter);
		}
		std::sort(sort_vec.begin(),sort_vec.end(), comp);
		stringstream csv;
		MemCachedClient mc;
		for(int i=0; i<sort_vec.size(); i++){
				//VLOG(0)<<sort_vec[i]->score<<"\t"<<sort_vec[i]->toString(query_plan_);
				//VLOG(0)<<GS_EDGE_NUM<<" "<<(GS_EDGE_NUM*FLAGS_katz_beta*DistanceUnit::beta_factor);
				csv<<i+1<<",";
				for(int k=0; k<query_plan_.size();k++){
					stringstream sss;
					int node1 = sort_vec[i]->mapping_[k];
					sss<<node1;
					string str_node1 = sss.str();//mc.Get(sss.str().c_str());
					csv<<str_node1;
					csv<<",";
					for(int j=k+1; j<query_plan_.size(); j++){
						if(!GS_QUERY[query_plan_[k]].links_.contains(query_plan_[j]))
							continue;
						int node2 = sort_vec[i]->mapping_[j];
						DistanceUnit du(cost_.getLowerDistance(node1, node2));

						sss.str("");
						sss<<node2;
						string str_node2 = sss.str();//mc.Get(sss.str().c_str());
						//VLOG(0)<<query_plan_[k]<<":"<<str_node1<<" ~ "<<query_plan_[j]<<":"<<str_node2<<"\t"<<du.shortest_hop_<<"--"<<du.shortest_num_<<" "<<du.value_<<" "<<du.value_/(FLAGS_katz_beta*DistanceUnit::beta_factor);
					}
				}
				csv<<sort_vec[i]->score/(GS_EDGE_NUM*FLAGS_katz_beta*DistanceUnit::beta_factor)<<endl;
			}
	for (int i = 0; i < sort_vec.size(); i++) {
	        fstream File;
	        string file = StringPrintf("%s/../../result/visualized/visual-%d",FLAGS_graph_dir.c_str(), i);
	        File.open(file.c_str(), ios::out);
	        File<<sort_vec[i]->outputGraph(query_plan_,mc,cost_);
	        File.close();

	        stringstream ss;
	        ss<<"dot -Tps "<<file<<" -o "<<file<<".eps";
	        system(ss.str().c_str());
		}
	fstream File;
	string file = StringPrintf("%s/../../result/visualized/overall.csv",FLAGS_graph_dir.c_str());
	File.open(file.c_str(), ios::out);
	File<<csv.str();
	File.close();
 */

/*
 string Matching::outputGraph(vector<int>& query_plan_, MemCachedClient& mc, CostFunction& cost){
		int dummy_id=0;
		stringstream ss_graphviz;
		stringstream ss;
		double max_score = DistanceUnit(1,9999).value_;
		VLOG(0)<<max_score;
		ss_graphviz<<"graph{"<<endl;
		ss_graphviz<<"edge[fontsize=\"10px\"];"<<endl;

		for(int k=0; k<query_plan_.size();k++){
			for(int j=k+1; j<query_plan_.size(); j++){
				if(!GS_QUERY[query_plan_[k]].links_.contains(query_plan_[j]))
						continue;
					int node1 = mapping_[k];
					int node2 = mapping_[j];
					DistanceUnit du(cost.getLowerDistance(node1, node2));
					double score = du.value_/max_score;
					stringstream ss_link_score;
					ss_link_score<<std::setprecision(2)<<" hop:"<<du.shortest_hop_<<"\\l num:"<<du.shortest_num_<<"\\l score:"<<score;
					if(du.shortest_hop_ > 1){
						ss_graphviz<<node1<<"--"<<"d"<<dummy_id<<"[penwidth="<<score*5<<",label=\""+ ss_link_score.str()+"\"];"<<endl;
						for(int i=1; i<du.shortest_hop_-1; i++){
							int id1 = dummy_id;
							int id2 = ++dummy_id;
							ss_graphviz<<"d"<<id1<<"--"<<"d"<<id2<<"[penwidth="<<score*5<<"];"<<endl;
						}
						ss_graphviz<<"d"<<dummy_id<<"--"<<node2<<"[penwidth="<<score*5<<"];"<<endl;
						dummy_id++;
					}else{
						ss_graphviz<<node1<<"--"<<node2<<"[penwidth="<<score*5<<",label=\""+ ss_link_score.str()+"\"];"<<endl;
					}
					VLOG(0)<<query_plan_[k]<<":"<<node1<<" ~ "<<query_plan_[j]<<":"<<node2<<"\t"<<du.shortest_hop_<<"--"<<du.shortest_num_;
			}
		}




		for(int k=0; k<query_plan_.size();k++){
			stringstream sss;
			sss<<mapping_[k];
			string name = mc.Get(sss.str().c_str());
			ss_graphviz<<mapping_[k]<<"[label=\""<<name<<"\",fillcolor=\"/accent7/"<<-1*query_plan_[k]<<"\",style=\"filled\"]"<<endl;
		}
		for(int k=0; k<dummy_id;k++){
			ss_graphviz<<"d"<<k<<"[label=\"\",width=.25, height=.25, fixedsize=true]"<<endl;
		}
		ss_graphviz<<"}";


		return ss_graphviz.str();
	}
 */