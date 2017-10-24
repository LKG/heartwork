package im.heart.core.service;

public interface CrudService<T, ID> {
	public T save(T entity);

	public void delete(ID id);

	public void delete(Iterable<? extends T> entities);
}
