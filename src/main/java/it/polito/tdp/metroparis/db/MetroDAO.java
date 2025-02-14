package it.polito.tdp.metroparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metroparis.model.Connessione;
import it.polito.tdp.metroparis.model.Fermata;
import it.polito.tdp.metroparis.model.Linea;
import it.polito.tdp.metroparis.model.coppiaId;

/**
 * inserisco partenza e arrivo
 *
 */
public class MetroDAO {

	public boolean isfermateconnesse(Fermata partenza,Fermata arrivo) {
		String sql="SELECT COUNT(*) AS cnt "
				+ "FROM connessione "
				+ "WHERE id_stazP=? and id_stazA=?";
				try {
					Connection conn = DBConnect.getConnection();
					PreparedStatement st = conn.prepareStatement(sql);
					st.setInt(1, partenza.getIdFermata());
					st.setInt(2, arrivo.getIdFermata());
					ResultSet rs = st.executeQuery();
					rs.first();
					int count=rs.getInt("cnt");
					st.close();
					conn.close();
					return count>0;

				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("Errore di connessione al Database.");
				}
	}
	
	public List<Integer> getIdFermateConnesse(Fermata partenza) {
		String sql="SELECT id_stazA FROM connessione WHERE id_stazP=? GROUP BY id_stazA";
				try {
					Connection conn = DBConnect.getConnection();
					PreparedStatement st = conn.prepareStatement(sql);
					st.setInt(1, partenza.getIdFermata());
					ResultSet rs = st.executeQuery();
					List<Integer> result=new ArrayList<Integer>();
					while (rs.next()) {
						result.add(rs.getInt("id_stazA"));
					}
					st.close();
					conn.close();
					return result;

				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException("Errore di connessione al Database.");
				}
	}

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				
				// LIBRERIA CHE APPROSSIMA E GESTISCE LE COORDINATE GEOGRAFICHE E CONVERTITO IN DOUBLE
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<Linea> getAllLinee() {
		final String sql = "SELECT id_linea, nome, velocita, intervallo FROM linea ORDER BY nome ASC";

		List<Linea> linee = new ArrayList<Linea>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Linea f = new Linea(rs.getInt("id_linea"), rs.getString("nome"), rs.getDouble("velocita"),
						rs.getDouble("intervallo"));
				linee.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return linee;
	}

	public List<Fermata> getIdFermateConnesse2(Fermata partenza) {
		final String sql = "SELECT id_fermata,nome,coordx,coordy "
				+ "from fermata "
				+ "WHERE id_fermata IN ("
				+ "SELECT id_stazA "
				+ "FROM connessione "
				+ "WHERE id_stazP=? "
				+ "GROUP BY id_stazA) "
				+ "ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, partenza.getIdFermata());
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"),
						new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				
				// LIBRERIA CHE APPROSSIMA E GESTISCE LE COORDINATE GEOGRAFICHE E CONVERTITO IN DOUBLE
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}
	public List<coppiaId> getAllFermateConnesse() {
		String sql = "SELECT DISTINCT id_stazP, id_stazA "
				+ "FROM connessione" ;
		
		Connection conn = DBConnect.getConnection() ;
		try {
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			List<coppiaId> result = new ArrayList<coppiaId>() ;
			while(res.next()) {
				result.add(new coppiaId(res.getInt("id_stazP"), res.getInt("id_stazA"))) ;
			}
			conn.close();
			return result ;
		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}

}
