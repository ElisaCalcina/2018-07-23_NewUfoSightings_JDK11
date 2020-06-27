package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Year;
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
				idMap.put(state.getId(), state);
				result.add(state);
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
	
	public List<String> getForma(Integer anno){
		String sql="SELECT distinct shape as s " + 
				"FROM sighting " + 
				"WHERE YEAR(DATETIME)=? " + 
				"ORDER BY shape asc ";
		
		List<String> result= new ArrayList<>();
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1,anno);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("s"));
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
	public List<State> getVertici(Map<String, State> idMap){
		String sql="SELECT id, s.Name as name, capital, lat, lng, s.Area as area, population, neighbors " + 
				"FROM state AS s ";
		List<State> result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State s= new State(rs.getString("id"),
									rs.getString("name"),
									rs.getString("capital"),
									rs.getDouble("lat"),
									rs.getDouble("lng"),
									rs.getInt("area"),
									rs.getInt("population"),
									rs.getString("neighbors"));
				result.add(s);
									
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
	public List<Adiacenze> getAdiacenze(Map<String, State> idMap, Integer anno, String forma){
		String sql="SELECT si1.state AS st1, si2.state AS st2, COUNT(si1.id) AS peso " + 
				"FROM neighbor AS n, sighting AS si1, sighting AS si2 " + 
				"WHERE si1.state>si2.state " + 
				" 		AND si1.shape=si2.shape " + 
				"		AND si1.shape=? " + 
				" 		AND si1.state=n.state1 " + 
				" 		AND si2.state=n.state2 " + 
				" 		AND YEAR(si1.DATETIME)=YEAR(si2.datetime) " + 
				"		AND YEAR(si1.datetime)=? " + 
				"GROUP BY st1, st2 " + 
				"HAVING peso!=0 ";
		
		List<Adiacenze> result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, forma);
			st.setInt(2, anno);
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				
				State s1=idMap.get(rs.getString("st1").toUpperCase());
				State s2= idMap.get(rs.getString("st2").toUpperCase());
				
				if(s1!=null && s2!=null) {
					Adiacenze a= new Adiacenze(s1, s2, rs.getInt("peso"));
					result.add(a);
					
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
}

