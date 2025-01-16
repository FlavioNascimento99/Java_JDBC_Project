package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			preparedStatement = connection.prepareStatement(
							"INSERT INTO seller "
							+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
							+ "VALUES "
							+ "( ?, ?, ?, ?, ? ) ",
							Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setString(1, obj.getName());
			preparedStatement.setString(2, obj.getEmail());
			preparedStatement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			preparedStatement.setDouble(4, obj.getBaseSalary());
			preparedStatement.setInt(5, obj.getDepartment().getId());
			
			int rowsAffected = preparedStatement.executeUpdate();
			
			
			if (rowsAffected > 0) {
				
				resultSet = preparedStatement.getGeneratedKeys();
				
				if (resultSet.next()) {
					
					int id = resultSet.getInt(1);
					obj.setId(id);
					
				}
				
				Database.closeResultSet(resultSet);
				
			}
			
			else {
				
				throw new DatabaseException("Nenhuma linha afetada.");
				
			}
			
		} 
		
		catch (SQLException e) {
			
			throw new DatabaseException(e.getMessage());
			
		}
		
		finally {
			
			Database.closeStatement(preparedStatement);
			Database.closeResultSet(resultSet);
			
		}
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


	
	
	@Override
	public List<Seller> findAll() {
		
		PreparedStatement prepState = null;
		ResultSet resultSet = null;
		
		try {
			prepState = connection.prepareStatement(
					  "SELECT seller.*, department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
						
			resultSet = prepState.executeQuery();
			
			List<Seller> sellerResultList = new ArrayList<>();
			Map<Integer, Department> resultMapper = new HashMap<>();
		
			while(resultSet.next()) {
				
				Department departmentIdChecker = resultMapper.get(resultSet.getInt("DepartmentId"));
				
				if (departmentIdChecker == null) {
					departmentIdChecker = instantiateDepartment(resultSet);
					resultMapper.put(resultSet.getInt("DepartmentId"), departmentIdChecker);
					
				}
				
				Seller seller = instantiateSeller(resultSet, departmentIdChecker);
				sellerResultList.add(seller);
				
			}
			
			return sellerResultList;
		
		} 
		
		catch (SQLException e) {
			
			throw new DatabaseException(e.getMessage());
		
		} 
		
		finally {
			
			Database.closeStatement(prepState);
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

	
}
