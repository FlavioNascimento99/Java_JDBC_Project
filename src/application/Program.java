package application;

import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellerDaoInstance = DaoFactory.createSellerDao();
		
		
		System.out.print("======= Teste I - FindById =======");
		Seller seller = sellerDaoInstance.findById(7);
		System.out.println("\n" + seller);
		
		
		System.out.print("\n======= Teste II - FindByDepartment =======");
		Department department = new Department(2, null);
		List<Seller> resultSellerByDepartment = sellerDaoInstance.findByDepartment(department);
		
		for (Seller obj : resultSellerByDepartment) {
			System.out.println("\n" + obj);
		}
		
		
		System.out.print("\n===== Teste III - FindAll ======");
		List<Seller> resultSellerAll = sellerDaoInstance.findAll();
		for (Seller obj : resultSellerAll) {
			System.out.println("\n" + obj);
		}
		
		System.out.print("\n===== Teste III - FindAll ======");
		Seller newSeller = new Seller(null, "Islam Makhachev", "makhachevmma@daguestan.com", new Date(), 3500.0, department);
		sellerDaoInstance.insert(newSeller);
		System.out.println("Inserido com sucesso: " + newSeller.getId());
	}

}
