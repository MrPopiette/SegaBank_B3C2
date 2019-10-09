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
import bo.Compte;
import bo.CompteEpargne;
import bo.ComptePayant;
import bo.CompteSimple;
import exceptions.SoldeNegatifException;
import exceptions.TypeCompteInvalidException;

public class CompteDAO implements IDAO<Long, Compte>{
	
	private static final String INSERT_QUERY = "INSERT INTO Compte (identifiant, solde, taux_interet, type_compte, id_agence, decouvert_max) VALUES(?,?,?,?,?,?)";
	private static final String UPDATE_QUERY = "UPDATE Compte SET solde = ?, taux_interet = ?, type_compte= ?, id_agence = ?, decouvert_max = ?  WHERE identifiant = ?";
	private static final String REMOVE_QUERY = "DELETE FROM Compte WHERE identifiant = ?";
	private static final String FIND_QUERY = "SELECT * FROM Compte WHERE identifiant = ?";
	private static final String FIND_ALL_QUERY = "SELECT * FROM Compte";

	@Override
	public void create(Compte compte) throws SQLException, IOException, ClassNotFoundException {
		
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection
					.prepareStatement( INSERT_QUERY, Statement.RETURN_GENERATED_KEYS ) ) {
				ps.setInt( 1, compte.getIdentifiant() );
				ps.setDouble( 2, compte.getSolde() );
				
				if(compte.getClass().toString().equals("CompteEpargne"))
					ps.setDouble( 3, ((CompteEpargne) compte).getTauxInteret() );
				else
					ps.setDouble( 3, 0 );
				
				ps.setString( 4, compte.getClass().getName() );
				ps.setInt( 5, compte.getAgence() );
				
				if(compte.getClass().toString().equals("CompteSimple"))
					ps.setDouble( 6, ((CompteSimple) compte).getDecouvert() );
				else
					ps.setDouble( 6, 0 );
				
				
				ps.executeUpdate();
				try ( ResultSet rs = ps.getGeneratedKeys() ) {
					if ( rs.next() ) {
						compte.setIndentifiant( rs.getInt( 1 ) );
					}
				}
			}
		}
		
	}

	@Override
	public void update(Compte compte) throws SQLException, IOException, ClassNotFoundException {
		
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( UPDATE_QUERY ) ) {
				ps.setDouble( 1, compte.getSolde() );
				
				if(compte.getClass().toString().equals("CompteEpargne"))
					ps.setDouble( 2, ((CompteEpargne) compte).getTauxInteret() );
				else
					ps.setDouble( 2, 0 );
								
				ps.setString( 3, compte.getClass().getName() );
				ps.setInt( 4, compte.getAgence());
				
				if(compte.getClass().toString().equals("CompteSimple"))
					ps.setDouble( 5, ((CompteSimple) compte).getDecouvert() );
				else
					ps.setDouble( 5, 0 );
				
				ps.setInt( 6, compte.getIdentifiant() );
				ps.executeUpdate();
			}
		}
		
	}

	@Override
	public void remove(Compte compte) throws SQLException, IOException, ClassNotFoundException {
		
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( REMOVE_QUERY ) ) {
				ps.setInt( 1, compte.getIdentifiant() );
				ps.executeUpdate();
			}
		}
		
	}

	@Override
	public Compte findById(Long id) throws SQLException, IOException, ClassNotFoundException, TypeCompteInvalidException {
		
		Compte compte = null;
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( FIND_QUERY ) ) {
				ps.setLong( 1, id );
				try ( ResultSet rs = ps.executeQuery() ) {
					if ( rs.next() ) {
						if(rs.getString( "type_compte" ).equals("ComptePayant"))
							compte=new ComptePayant(rs.getInt( "identifiant" ), rs.getDouble("solde"), rs.getInt("id_agence"));
						else if(rs.getString( "type_compte" ).equals("CompteEpargne"))
							compte=new CompteEpargne(rs.getInt( "identifiant" ), rs.getDouble("solde"), rs.getDouble("taux_interet"), rs.getInt("id_agence"));
						else if(rs.getString( "type_compte" ).equals("CompteSimple"))
							compte=new CompteSimple(rs.getInt( "identifiant" ), rs.getDouble("solde"), rs.getDouble("decouvert"), rs.getInt("id_agence"));
						else
							throw new TypeCompteInvalidException("/!\\ \t --- Warning : Type de compte invalid --- \t /!\\\\");
					}
				}
			}
		}
		return compte;
		
	}

	@Override
	public List<Compte> findAll() throws SQLException, IOException, ClassNotFoundException, TypeCompteInvalidException {
		
		List<Compte> list = new ArrayList<>();
		Connection connection = PersistenceManager.getConnection();
		if ( connection != null ) {
			try ( PreparedStatement ps = connection.prepareStatement( FIND_ALL_QUERY ) ) {
				try ( ResultSet rs = ps.executeQuery() ) {
					while ( rs.next() ) {
						Compte compte = null;
						if(rs.getString( "type_compte" ).equals("ComptePayant"))
							compte=new ComptePayant(rs.getInt( "identifiant" ), rs.getDouble("solde"), rs.getInt("id_agence"));
						else if(rs.getString( "type_compte" ).equals("CompteEpargne"))
							compte=new CompteEpargne(rs.getInt( "identifiant" ), rs.getDouble("solde"), rs.getDouble("taux_interet"), rs.getInt("id_agence"));
						else if(rs.getString( "type_compte" ).equals("CompteSimple"))
							compte=new CompteSimple(rs.getInt( "identifiant" ), rs.getDouble("solde"), rs.getDouble("decouvert"), rs.getInt("id_agence"));
						else
							throw new TypeCompteInvalidException("/!\\ \t --- Warning : Type de compte invalid --- \t /!\\\\");
						
						list.add( compte );
					}
				}
			}
		}
		return list;
		
	}

	

}
