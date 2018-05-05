package com.tt.manage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tt.manage.mapper.ItemCatMapper;
import com.tt.manage.pojo.ItemCat;

@Service
public class ItemCatService {

	@Autowired
	private ItemCatMapper itemCatMapper;

	public List<ItemCat> queryItemCatListByParentId(Long pid) {
		ItemCat record = new ItemCat();
		record.setParentId(pid);//查询参数
		return this.itemCatMapper.select(record);
	}
	
	
}
