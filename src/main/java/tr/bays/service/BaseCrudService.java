package tr.bays.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortMeta;

import tr.bays.common.QueryResult;

public interface BaseCrudService<T> extends BaseListingService<T> {

	T kaydet(T t);

	void sil(T t);

	QueryResult<T> listele(int firstRecord, int pageSize, T sorguKriteri, Map<String, SortMeta> sort);

	List<T> combo(String query);

	T getir(long id);

}
