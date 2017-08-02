package com.mall.b2bp.daos.system;

import java.util.List;

import com.mall.b2bp.models.system.BusUnitModel;

public interface BusUnitModelMapper {
	List<BusUnitModel> search(BusUnitModel model);
	List<BusUnitModel> select(BusUnitModel model);
	int insert(BusUnitModel model);
	int update(BusUnitModel model);
	int delete(BusUnitModel model);
	String insertRun(BusUnitModel model);
}