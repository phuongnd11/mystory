package com.inspireon.mystory.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author phuongnd11
 * 
 */
public abstract class BaseRepoImpl<T, I extends Serializable> implements
		BaseRepo<T, I> {
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	private Class<T> clazz;

	protected BaseRepoImpl() {
		this.clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * @return the class type of entity
	 */
	public Class<T> getClazz() {
		return clazz;
	}
	
	public T find(I id) {
		return mongoTemplate.findById(id, clazz);
	}
	
	public List<T> findAll() {
		return mongoTemplate.findAll(clazz);
	}

	public void remove(T t) {
		mongoTemplate.remove(t);
	}

	public void add(T t) {
		mongoTemplate.insert(t);
	}
	
	public void store(T t) {
		mongoTemplate.save(t);
	}

	public MongoTemplate getTemplate(){
		return mongoTemplate;
	}

}
