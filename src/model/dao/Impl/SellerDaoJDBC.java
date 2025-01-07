package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.Database;
import database.DatabaseException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {
	
	Connection connection;
	
	public SellerDaoJDBC(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Seller findById(Integer id) {
		
		PreparedStatement prepState = null;
		ResultSet resultSet = null;
		
		try {
			prepState = connection.prepareStatement(
					"SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE seller.Id = ? ");
			
			prepState.setInt(1, id);
			
			resultSet = prepState.executeQuery();
		
			if (resultSet.next()) {
				
				Department department = instantiateDepartment(resultSet);
				Seller seller = instantiateSeller(resultSet, department);				
		
				return seller;
				
			} 
			
			return null;
		
		} 
		
		catch (SQLException e) {
			
			throw new DatabaseException(e.getMessage());
		
		} 
		
		finally {
			
			Database.closeStatement(prepState);
			Database.closeResultSet(resultSet);

		}
	}
	
	public List<Seller> findByDepartment(Department departmentParameteer) {
		
		// Inicialização da consulta e da coleta de resultados da mesma (preparedStatement e resultSet)
		PreparedStatement preparedState = null;
		ResultSet resultSet = null;
		
		try {
			/*
			 * Com connection devidamente instanciado, podemos chamar seus métodos estáticos
			 * neste caso, fazemos isso dentro da variável "preparedState" com o connection.prepareStatement
			 * que possui uma String com o script SQL que desejamos para o método em questão.
			 * 
			 * */
			preparedState = connection.prepareStatement(""
					+ "SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			// setInt é um método de PreparedStatement que tem como parâmetros (index, valor a ser atribuído dentro da Query)
			preparedState.setInt(1, departmentParameteer.getId());
			
			/*
			 * Passamos o preparedState com método para finalmente executar query para a variável do tipo 
			 * ResultSet que tem como dever, trazer os resultados da consulta.
			 */
			resultSet = preparedState.executeQuery();
			
			List<Seller> sellerByDepartment = new ArrayList<>();
			Map<Integer, Department> departmentMapper = new HashMap<>();
			
			
			while(resultSet.next()) {
				
				// departmentIdChecker retorna Null quando o retorno não existe, nesse caso, 
				Department department = departmentMapper.get(resultSet.getInt("DepartmentId"));
				
				if (department == null) {					
					department = instantiateDepartment(resultSet);
					departmentMapper.put(resultSet.getInt("DepartmentId"), department);
				}
				
				Seller sellerInstance = instantiateSeller(resultSet, department);
				
				sellerByDepartment.add(sellerInstance);
				
			}
			
			return sellerByDepartment;
			
		} catch (SQLException e) {
			
			throw new DatabaseException(e.getMessage());
			
		} finally {

			Database.closeStatement(preparedState);
			Database.closeResultSet(resultSet);			
			
		}
		
	}

	private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
		
		Seller seller = new Seller();
		
		seller.setId(resultSet.getInt("Id"));
		seller.setName(resultSet.getString("Name"));
		seller.setEmail(resultSet.getString("Email"));
		seller.setBirthDate(resultSet.getDate("BirthDate"));
		seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
		seller.setDepartment(department);
		
		return seller;
		
	}

	private Department instantiateDepartment(ResultSet resultSet) throws SQLException {
		
		Department department = new Department();
		
		department.setId(resultSet.getInt("DepartmentId"));
		department.setName(resultSet.getString("DepName"));
		
		return department;
		
	}

	
	
	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
