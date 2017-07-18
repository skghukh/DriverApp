package com.rodafleets.app.dataaccess;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.rodafleets.app.model.Bid;

public interface BidRepository extends CrudRepository<Bid, Long> {

	List<Bid> findAll();
}
