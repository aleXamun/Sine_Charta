package manager;
import java.util.Collection;
import java.util.Iterator;

import com.sun.org.apache.bcel.internal.generic.NEW;

import beans.Personaggio;
import beans.User;

import java.sql.*;
import interfaces.UserModelI;

public class UsersManager implements UserModelI<User>{

	private static final String TABLE_NAME = "utenteRegistrato";

	
	@Override
	public User doRetrieveByKey(String user) throws SQLException {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		User bean = new User();
		String selectSql = "SELECT * FROM " + UsersManager.TABLE_NAME + " WHERE Username = ?;";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();
			preparedStatement = connection.prepareStatement(selectSql);
			preparedStatement.setString(1, user);
			System.out.println("doRetrieveByKey: " + preparedStatement.toString());
			
			ResultSet rs = preparedStatement.executeQuery();
			System.out.println("doRetrieveByKey: " + preparedStatement.toString());

			while(rs.next()) {
				bean.setUsername(rs.getString("Username"));
				bean.setPassword(rs.getString("Password"));
				bean.setEmail(rs.getString("EMail"));
				bean.setName(rs.getString("Nome"));
				bean.setSurname(rs.getString("Cognome"));
				bean.setRuolo(rs.getString("Ruolo"));
				
			}
			
		}
		finally {
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			}
		 finally {
			DriverManagerConnectionPool.releaseConnection(connection);
		 }
		}
		
		StoryManager storyM = new StoryManager();
		Collection<Storia> listaStoria = storyM.listaStorie(bean.getUsername());
		if(listaStoria.size()>0) {
			Iterator<Storia> it = listaStoria.iterator();
			while(it.hasNext()) {
				Storia storia = (Storia) it.next();
		//		this.aggiungiStoriaUser(storia.getId(), bean.getUsername());
			}
		}
		return bean;

	}

	public void aggiungiPGUser(String username, int idStoria)throws SQLException {
			User utentePG = new User();	
			UsersManager user = new UsersManager();
			PersonaggioManager pgM = new PersonaggioManager();
			Personaggio pgUtente = pgM.getPersonaggioByUtente(idStoria, username);

			
			utentePG = user.doRetrieveByKey(username);
			utentePG.aggiungiPG(pgUtente);
		
	}
	

	/**
	 * Metodo che comunica con StoryManager per aggiunger una storia ad un utente
	 * @param idStoria= identificativo della storia da aggiungere
	 * @param username= utente a cui viene inserita una storia.
	 */
/*	public void aggiungiStoriaUser(int idStoria, String username) throws SQLException{
		User utente = this.doRetrieveByKey(username);
		StoryManager storyM = new StoryManager();
		Storia storia = storyM.getStoria(username);
		utente.aggiungiStoria(storia);
	}*/
	
	
	

	@Override
	public Collection<User> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doSave(User user) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO " + UsersManager.TABLE_NAME
				+ " (USERNAME, PASSWORD, EMAIL, NOME, COGNOME, RUOLO) VALUES (?, md5(?), ?, ?, ?, 'utenteModeratore')";
		
		try {
			connection = DriverManagerConnectionPool.getConnection();

			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getEmail());
			preparedStatement.setString(4, user.getName());
			preparedStatement.setString(5, user.getSurname());
			
			preparedStatement.executeUpdate();

			connection.commit();
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				DriverManagerConnectionPool.releaseConnection(connection);
			}
		}
	}

	@Override
	public void doUpdate(User product) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean doDelete(String code) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doUpName(String name) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
	
	
}