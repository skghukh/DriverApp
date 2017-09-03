package com.rodafleets.app.response;

import com.rodafleets.app.model.Bid;
import com.rodafleets.app.model.Driver;

public class BidResponse extends CustomResponse {

	private Bid bid;
	
	public Bid getBid() {
		return bid;
	}
	
	public void setBid(Bid bid) {
		this.bid = bid;
	}
}
