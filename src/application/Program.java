package application;

import java.util.Date;

import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {

		Department dep = new Department(1, "Livros");
		
		Seller sel = new Seller(1, "Jon Jones", "bones@mail.com", new Date(), 8000.0, dep);
		
		System.out.println(sel);
	}

}
