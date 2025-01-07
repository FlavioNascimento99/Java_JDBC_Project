package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		SellerDao sellD = DaoFactory.createSellerDao();
		
		
		System.out.print("==== Teste I - FindById ====");
		Seller seller = sellD.findById(7);
		System.out.println(seller);
	}

}
