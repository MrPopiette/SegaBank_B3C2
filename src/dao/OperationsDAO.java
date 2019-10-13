package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bo.Adresse;
import bo.Agence;
import exceptions.TypeCompteInvalidException;

public class OperationsDAO implements IDAO<Long, List<String>>{
	
	private static final String INSERT_QUERY = "INSERT INTO Operation (type_operation, date, id_agence, id_compte, montant,compte_status) VALUES(?,?,?,?,?,?)";
	private static final String FIND_QUERY = "SELECT * FROM Operation WHERE id = ?";
	private static final String FIND_ALL_QUERY = "SELECT * FROM Operation";

	@Override
	public void create(List<String> operations) throws SQLException, IOException, ClassNotFoundException {
		
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection
					.prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS ) ) {
				ps.setString( 1, operations.get(0) );
				ps.setString( 2, operations.get(1) );
				ps.setString( 3, operations.get(2) );
				ps.setString( 4, operations.get(3) );
				ps.setString( 5, operations.get(4) );
				ps.setString( 6, operations.get(5) );
				
				ps.executeUpdate();
			}
		}
		
	}

	@Override
	public void update(List<String> object) throws SQLException, IOException, ClassNotFoundException {}

	@Override
	public void remove(List<String> object) throws SQLException, IOException, ClassNotFoundException {}

	@Override
	public List<String> findById(Long id)
			throws SQLException, IOException, ClassNotFoundException, TypeCompteInvalidException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<List<String>> findAll()
			throws SQLException, IOException, ClassNotFoundException, TypeCompteInvalidException {
		List<List<String>> list = new ArrayList<>();
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( FIND_ALL_QUERY ) ) {
				try ( ResultSet rs = ps.executeQuery() ) {
					while ( rs.next() ) {
						List<String> operation = Arrays.asList(
								rs.getString( "type_operation" ),
								rs.getString( "date" ),
								rs.getString( "id_agence" ),
								rs.getString( "id_compte" ),
								rs.getString( "montant" ),
								rs.getString( "compte_status" )
								);
								
						list.add( operation );
					}
				}
			}
		}
		return list;
		
	}

}
