package it.polito.tdp.newufosightings.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.newufosightings.db.NewUfoSightingsDAO;

public class Model {

	private NewUfoSightingsDAO dao;
	private Graph<State, DefaultWeightedEdge> grafo;
	private List<State> vertici;
	private Map<String, State> idMap;
	private List<Adiacenze> adiacenze;
	
	public Model() {
		dao= new NewUfoSightingsDAO();
		idMap = new HashMap<>();
	}
	
	public List<String> getForme(Integer anno){
		return dao.getForma(anno);
	}
	
	public void creaGrafo(Integer anno, String forma) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.loadAllStates(idMap);
		vertici= dao.getVertici(idMap);
		adiacenze= dao.getAdiacenze(idMap, anno, forma);
		
		Graphs.addAllVertices(grafo, vertici);
		
		for(Adiacenze a: adiacenze) {
			if(a.getS1()!=a.getS2() && this.grafo.containsVertex(a.getS1()) && this.grafo.containsVertex(a.getS2())) {
				Graphs.addEdgeWithVertices(grafo, a.getS1(), a.getS2(), a.getPeso());
			}
		}
		
		System.out.println("Grafo creato con "+ this.grafo.vertexSet().size()+" vertici e con "+this.grafo.edgeSet().size()+"archi\n");
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}

	public List<Adiacenze> pesoVicini(){
		List<Adiacenze> result= new ArrayList<>();
		
		for(State s: idMap.values()) {
			int peso=0;
		List<State> vicini= Graphs.neighborListOf(grafo, s);
			for(State v: vicini) {
				peso+=this.grafo.getEdgeWeight(this.grafo.getEdge(s,v));
			}
			result.add(new Adiacenze(s,null,peso));
		}
		return result;
	}
}
