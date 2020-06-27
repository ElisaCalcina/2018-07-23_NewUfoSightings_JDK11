package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.newufosightings.model.State;

public class TestDAO {

	public static void main(String[] args) {
		Map<String, State> idMap= new HashMap<>();
		
		
		try {
			Connection connection = DBConnect.getConnection();
			connection.close();
			System.out.println("Test PASSED");

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

		NewUfoSightingsDAO dao = new NewUfoSightingsDAO();

		System.out.println(dao.loadAllStates(idMap));
		
		System.out.println(dao.getAdiacenze(idMap, 2010, "circle"));
	}

}
