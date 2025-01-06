package model.dao.Impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
					+ "WHERE seller.Id = ?");
			
			prepState.setInt(1, id);
			resultSet = prepState.executeQuery();
		
			if (resultSet.next()) {
			
				Department dep = new Department();
				dep.setId(resultSet.getInt("DepartmentId"));
				dep.setName(resultSet.getString("DepName"));
				
				Seller sel = new Seller();
				sel.setId(resultSet.getInt("Id"));
				sel.setName(resultSet.getString("Name"));
				sel.setEmail(resultSet.getString("Email"));
				sel.setBirthDate(resultSet.getDate("BirthDate"));
				sel.setBaseSalary(resultSet.getDouble("BaseSalary"));
				sel.setDepartment(dep);
		
				return sel;
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

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
