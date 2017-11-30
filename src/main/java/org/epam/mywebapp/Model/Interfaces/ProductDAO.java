package org.epam.mywebapp.Model.Interfaces;


import org.epam.mywebapp.Model.Implements.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ProductDAO {

 ArrayList<Product> getAll() throws SQLException;
 ArrayList<Product> findByTitle(String name) throws SQLException;
 Product findByUID(long uID) throws SQLException;
 ArrayList<Product> findBySeller(String sellerName) throws SQLException;
 void add(Product product);
 void sellProduct(Product product);


}
