package it.polito.tdp.newufosightings.model;

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
	private Map<String, State> idMap;
	private List<State> vertici;
	
	public Model() {
		dao= new NewUfoSightingsDAO();
		idMap= new HashMap<>();
	}
	
	public void creaGrafo(Integer anno, Integer giorni) {
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		dao.loadAllStates(idMap);
		vertici= this.dao.getVertici();
		
		Graphs.addAllVertices(grafo, vertici);
		
		for(Adiacenze a: this.dao.getArchi(idMap, anno, giorni)) {
			if(a.getS1()!=a.getS2() && this.grafo.containsVertex(a.getS1()) && this.grafo.containsVertex(a.getS2())) {
				Graphs.addEdgeWithVertices(grafo, a.getS1(), a.getS2(), a.getPeso());
			}
		}
		
		System.out.println("Grafo creato con "+ this.grafo.vertexSet().size()+" vertici e con "+ this.grafo.edgeSet().size()+" archi\n");
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
}
