package com.rodafleets.app.response;

import java.util.List;

import org.springframework.data.util.Pair;

public class NearyByDriversResponse {

	private List<Pair<Double, Double>> nearBys;
	private int count;

	public List<Pair<Double, Double>> getNearBys() {
		return nearBys;
	}

	public void setNearBys(List<Pair<Double, Double>> nearBys) {
		this.nearBys = nearBys;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
