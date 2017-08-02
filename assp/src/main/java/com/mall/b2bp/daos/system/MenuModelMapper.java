package com.mall.b2bp.daos.system;

import java.util.List;

import com.mall.b2bp.models.system.MenuModel;

public interface MenuModelMapper {
	List<MenuModel> search(MenuModel model);
	List<MenuModel> searchMenu(MenuModel model);
	int insert(MenuModel model);
	int update(MenuModel model);
	int delete(MenuModel model);
	List<MenuModel> selectmenu(MenuModel model);
	List<MenuModel> getMenuname(MenuModel model);
}