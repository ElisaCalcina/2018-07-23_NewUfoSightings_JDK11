package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.Adiacenze;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public List<Sighting> loadAllSightings() {
		String sql = "SELECT * FROM sighting";
		List<Sighting> list = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);	
			ResultSet res = st.executeQuery();

			while (res.next()) {
				list.add(new Sighting(res.getInt("id"), res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), res.getString("state"), res.getString("country"), res.getString("shape"),
						res.getInt("duration"), res.getString("duration_hm"), res.getString("comments"),
						res.getDate("date_posted").toLocalDate(), res.getDouble("latitude"),
						res.getDouble("longitude")));
			}

			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}

		return list;
	}

	public List<State> loadAllStates(Map<String, State> idMap) {
		String sql = "SELECT * FROM state";
		List<State> result = new ArrayList<State>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				if(!idMap.containsKey(rs.getString("id"))) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(state);
				idMap.put(state.getId(), state);
				}
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	//vertici
	public List<State> getVertici(){
		String sql="SELECT * " + 
				"FROM state ";
		
		List<State> result= new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State stato= new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				result.add(stato);
			}
		
			conn.close();
			return result;
	} catch (SQLException e) {
		e.printStackTrace();
		System.out.println("Errore connessione al database");
		throw new RuntimeException("Error Connection Database");
	}
	}
	
	//archi
	public List<Adiacenze> getArchi(Map<String, State>idMap, Integer anno, Integer giorni){
		String sql="SELECT s1.state as st1, s2.state as st2, COUNT(*) AS peso " + 
				"FROM neighbor AS n, sighting AS s1, sighting AS s2 " + 
				"WHERE YEAR(s1.datetime)=YEAR(s2.datetime) " + 
				"		AND YEAR(s1.datetime)=? " + 
				"		AND n.state1=UPPER(s1.state) " + 
				"		AND n.state2=UPPER(s2.state) " + 
				"		AND s1.state>s2.state " + 
				"		AND DATEDIFF(s1.datetime, s2.datetime)<=? " + 
				"GROUP BY s1.state, s2.state ";
		
		List<Adiacenze> result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, giorni);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				State s1=idMap.get(rs.getString("st1").toUpperCase());
				State s2= idMap.get(rs.getString("st2").toUpperCase());
				
				if(s1!=null && s2!=null) {
					Adiacenze a= new Adiacenze(s1, s2, rs.getDouble("peso"));
					result.add(a);
				}
			}
			conn.close();
			return result;
				
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Errore connessione al database");
				throw new RuntimeException("Error Connection Database");
			}
	}
}

