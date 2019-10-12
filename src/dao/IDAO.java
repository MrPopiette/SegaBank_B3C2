package dao;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import exceptions.TypeCompteInvalidException;

public interface IDAO<ID, E> {
	/**
	 * Permet d'inserer un objet dans la base de donnees SegaBank
	 * @param object
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	void create(E object) throws SQLException, IOException, ClassNotFoundException;
	/**
	 * Permet de mettre a jou un objet dans la base de donnees SegaBank
	 * @param object
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	void update(E object) throws SQLException, IOException, ClassNotFoundException;
	/**
	 * Permet de supprimer un objet dans la base de donnees SegaBank
	 * @param object
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	void remove(E object) throws SQLException, IOException, ClassNotFoundException;
	/**
	 * 
	 * @param id
	 * @return l'objet qui a le meme id que celui passe en parametre
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws TypeCompteInvalidException
	 */
	E findById(ID id) throws SQLException, IOException, ClassNotFoundException, TypeCompteInvalidException;
	/**
	 * 
	 * @return l'ensemble des objets de la table
	 * @throws SQLException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws TypeCompteInvalidException
	 */
	List<E> findAll() throws SQLException, IOException, ClassNotFoundException, TypeCompteInvalidException;
}
