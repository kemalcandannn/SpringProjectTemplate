package tr.bays.common.base;

import java.util.List;
import java.util.Map;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;

import tr.bays.common.QueryResult;
import tr.bays.service.BaseCrudService;

@SuppressWarnings("serial")
public class BaseLazyDataModel<DTO extends BaseDto> extends LazyDataModel<DTO> {

	private BaseCrudService<DTO> crudService;
	private List<DTO> datasource;
	private DTO sorguKriteri;

	public BaseLazyDataModel(BaseCrudService<DTO> crudService, DTO sorguKriteri) {
		this.sorguKriteri = sorguKriteri;
		this.crudService = crudService;
		this.crudService.listele();
	}

	@Override
	public DTO getRowData(String rowKey) {

		if (datasource != null) {
			for (DTO dto : datasource) {
				if (dto.getId().toString().equals(rowKey))
					return dto;
			}
		}

		return null;
	}

	@Override
	public String getRowKey(DTO dto) {
		return dto.getId() + "";
	}

	@Override
	public int getRowCount() {
		return super.getRowCount();
	}

	@Override
	public int count(Map<String, FilterMeta> filterBy) {
		return datasource != null ? datasource.size() : 0;
	}

	@Override
	public List<DTO> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
		QueryResult<DTO> page = crudService.listele(first, pageSize, sorguKriteri, sortBy);
		datasource = page.getList();

		setPageSize(pageSize);
		setRowCount(page.getCount());

		return datasource;
	}

	public DTO getSorguKriteri() {
		return sorguKriteri;
	}

	public void setSorguKriteri(DTO sorguKriteri) {
		this.sorguKriteri = sorguKriteri;
	}

}
