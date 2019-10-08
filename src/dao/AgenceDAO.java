package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bo.Adresse;
import bo.Agence;

public class AgenceDAO implements IDAO<Long, Agence>{
	
	private static final String INSERT_QUERY = "INSERT INTO Agence (identifiant, code, numero_adresse, adresse, code_postal, ville) VALUES(?,?,?,?,?,?)";
	private static final String UPDATE_QUERY = "UPDATE Agence SET code = ?, numero_adresse = ?, adresse= ?, code_postal = ?, ville = ?  WHERE identifiant = ?";
	private static final String REMOVE_QUERY = "DELETE FROM Agence WHERE identifiant = ?";
	private static final String FIND_QUERY = "SELECT * FROM Agence WHERE identifiant = ?";
	private static final String FIND_ALL_QUERY = "SELECT * FROM Agence";

	@Override
	public void create(Agence agence) throws SQLException, IOException, ClassNotFoundException {
		
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection
					.prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS ) ) {
				ps.setInt( 1, agence.getId() );
				ps.setString( 2, agence.getCode() );
				ps.setString( 3, agence.getAdresse().getNumero() );
				ps.setString( 4, agence.getAdresse().getIntutile() );
				ps.setString( 5, agence.getAdresse().getCodepostal() );
				ps.setString( 6, agence.getAdresse().getNameVille() );
				
				ps.executeUpdate();
				try ( ResultSet rs = ps.getGeneratedKeys() ) {
					if ( rs.next() ) {
						agence.setId( rs.getInt( 1 ) );
					}
				}
			}
		}
		
	}

	@Override
	public void update(Agence agence) throws SQLException, IOException, ClassNotFoundException {
		
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( UPDATE_QUERY ) ) {
				ps.setString( 1, agence.getCode() );
				ps.setString( 2, agence.getAdresse().getNumero() );
				ps.setString( 3, agence.getAdresse().getIntutile() );
				ps.setString( 4, agence.getAdresse().getCodepostal() );
				ps.setString( 5, agence.getAdresse().getNameVille() );
				ps.setInt( 6, agence.getId() );
				ps.executeUpdate();
			}
		}
		
	}

	@Override
	public void remove(Agence agence) throws SQLException, IOException, ClassNotFoundException {
		
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( REMOVE_QUERY ) ) {
				ps.setInt( 1, agence.getId() );
				ps.executeUpdate();
			}
		}
		
	}

	@Override
	public Agence findById(Long id) throws SQLException, IOException, ClassNotFoundException {
		
		Agence agence = null;
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( FIND_QUERY ) ) {
				ps.setLong( 1, id );
				try ( ResultSet rs = ps.executeQuery() ) {
					if ( rs.next() ) {
						agence = new Agence(
								rs.getString( "code" ), 
								rs.getInt( "identifiant" ), 
								new Adresse(
										rs.getString( "numero_adresse" ),
										rs.getString( "adresse" ),
										rs.getString( "ville" ),
										rs.getString( "code_postal" )
										)
								);
						
					}
				}
			}
		}
		return agence;
		
	}

	@Override
	public List<Agence> findAll() throws SQLException, IOException, ClassNotFoundException {
		List<Agence> list = new ArrayList<>();
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( FIND_ALL_QUERY ) ) {
				try ( ResultSet rs = ps.executeQuery() ) {
					while ( rs.next() ) {
						Agence agence = new Agence(
								rs.getString( "code" ), 
								rs.getInt( "identifiant" ), 
								new Adresse(
										rs.getString( "numero_adresse" ),
										rs.getString( "adresse" ),
										rs.getString( "ville" ),
										rs.getString( "code_postal" )
										)
								);
						
						list.add( agence );
					}
				}
			}
		}
		return list;
	}

}
