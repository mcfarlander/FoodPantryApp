package org.pantry.food.dao.mapper;

public interface ArrayRowMapper<T> {
	T map(String[] row);

	String[] toCsvRow(T entity);
}
