package model.dao;

import database.Database;
import model.dao.Impl.SellerDaoJDBC;

public class DaoFactory {
	
	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(Database.getConnection());
	}
	
}
