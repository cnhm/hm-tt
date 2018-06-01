package com.tt.manage.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tt.manage.pojo.BasePojo;

/**
 * 通用service
 * @author 胡景皓  2018.04.28
 *
 * @param <T> 所操作的实体类
 */
public abstract class BaseService<T extends BasePojo> {//继承BasePojo目的：为了设置创建时间和修改时间
	
	//public abstract Mapper<T> getMapper();
	
	//spring4泛型注入
	@Autowired
	private Mapper<T> mapper;
	
	/**
	 * 根据ID（主键）查询数据
	 * @param id
	 * @return
	 */
	public T queryById(Long id){
		return this.mapper.selectByPrimaryKey(id);
	}
	/**
	 * 查询所有数据
	 * @return
	 */
	public List<T> queryAll(){
		return this.mapper.select(null);
	}
	
	/**
	 * 根据条件查询一条数据，如果该条件所查询的数据为多条则抛异常
	 * @return
	 */
	public T queryOne(T record){
		return this.mapper.selectOne(record);
	}
	
	/**
	 * 根据条件查询多条数据
	 * @param record
	 * @return
	 */
	public List<T> queryListByWhere(T record){
		return this.mapper.select(record);
	}
	
	/**
	 * 根据条件分页查询数据
	 * @param record
	 * @return
	 */
	public PageInfo<T> queryPageListByWhere(T record,Integer page,Integer rows){
		//设置分页数据
		PageHelper.startPage(page,rows);
		List<T> list = this.mapper.select(record);
		return new PageInfo<T>(list);
	}
	
	/**
	 * 新增数据
	 * @param t
	 * @return
	 */
	public Integer save(T t){
		t.setCreated(new Date());//创建时间
		t.setUpdated(t.getCreated());//更新时间
		return this.mapper.insert(t);
	}
	
	/**
	 * 新增数据, 选择不为null的字段作为插入数据的字段 来插入数据
	 * @param t
	 * @return
	 */
	public Integer saveSelective(T t){
		t.setCreated(new Date());
		t.setUpdated(t.getCreated());
		return this.mapper.insertSelective(t);
	}
	
	/**
	 * 更新数据
	 * @param t
	 * @return
	 */
	public Integer update(T t){
		t.setUpdated(new Date());
		return this.mapper.updateByPrimaryKey(t);
	}
	
	/**
	 * 更新数据，选择不为null的字段作为更新数据的字段 来更新数据
	 * @param t
	 * @return
	 */
	public Integer updateSelective(T t){
		t.setUpdated(new Date());
		//强制设置更新时间为null,永远不会被更新  ， 因为跟新的数据是不为null的，这样做更严谨 
		t.setCreated(null);
		return this.mapper.updateByPrimaryKeySelective(t);
	}
	
	/**
	 * 根据主键id删除数据（物理删除：把数据从介质上彻底删除掉。）
	 * 逻辑删除：（可以调用updateSelective就行了）逻辑删除就是对要要删除的数据打上一个删除标记，在逻辑上是数据是被删除的，但数据本身依然存在！
	 * @param id
	 * @return
	 */
	public Integer deleteById(Long id){
		return this.mapper.deleteByPrimaryKey(id);
	}
	
	/**
	 * 批量删除数据
	 * @param ids 所删除数据的id
	 * @param clazz 
	 * @param property 传递主键
	 * @return
	 */
	public Integer deleteByIds(List<Object> ids,Class<T> clazz,String property ){
		//根据自定义条件删除
		Example example = new Example(clazz);//example:例子
		//设置条件  property属性
		example.createCriteria().andIn(property, ids);
		return this.mapper.deleteByExample(example);
	}
	
	/**
	 * 根据条件删除数据
	 * @param record
	 * @return
	 */
	public Integer deleteByWhere(T record){
		return this.mapper.delete(record);
	}
}

















