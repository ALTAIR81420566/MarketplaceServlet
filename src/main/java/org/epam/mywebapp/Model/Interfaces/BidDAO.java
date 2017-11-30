package org.epam.mywebapp.Model.Interfaces;



import org.epam.mywebapp.Model.Implements.Bid;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BidDAO {

    ArrayList<Bid> findByProduct(long productId) throws SQLException;
    void add(Bid bid);
    Bid getLast(long productId);

    ArrayList<Bid> getAll();
}
