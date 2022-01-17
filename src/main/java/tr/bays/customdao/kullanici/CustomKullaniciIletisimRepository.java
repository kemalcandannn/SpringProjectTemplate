package tr.bays.customdao.kullanici;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import tr.bays.entity.kullanici.KullaniciIletisim;
import tr.bays.entity.kullanici.QKullaniciIletisim;
import util.Util;

@Repository
public class CustomKullaniciIletisimRepository {

	@PersistenceContext
	private EntityManager em;

	public BooleanExpression kriter(KullaniciIletisim kullaniciIletisim) {
		BooleanExpression booleanExpression = null;

		if(Util.notEmpty(kullaniciIletisim.getKullanici_id()) && Util.notEmpty(kullaniciIletisim.getKullanici_id().getId())) {
			booleanExpression = Util.and(booleanExpression, QKullaniciIletisim.kullaniciIletisim.kullanici_id.id.eq(kullaniciIletisim.getKullanici_id().getId()));
		}
		if(kullaniciIletisim.getAktif() != 0) {
			booleanExpression = Util.and(booleanExpression, QKullaniciIletisim.kullaniciIletisim.aktif.eq(kullaniciIletisim.getAktif()));
		}
		if(Util.notEmpty(kullaniciIletisim.getEposta())) {
			booleanExpression = Util.and(booleanExpression, QKullaniciIletisim.kullaniciIletisim.eposta.eq(kullaniciIletisim.getEposta()));
		}
		if(Util.notEmpty(kullaniciIletisim.getCep_telefonu())) {
			booleanExpression = Util.and(booleanExpression, QKullaniciIletisim.kullaniciIletisim.cep_telefonu.eq(kullaniciIletisim.getCep_telefonu()));
		}

		return booleanExpression;
	}

	public Page<KullaniciIletisim> sorgula(int pageSize, int pageNumber, KullaniciIletisim kullaniciIletisim, Map<String, SortMeta> sort) {
		BooleanExpression booleanExpression = kriter(kullaniciIletisim);

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		JPAQuery<KullaniciIletisim> query = queryFactory.
				select(QKullaniciIletisim.kullaniciIletisim).
				from(QKullaniciIletisim.kullaniciIletisim);

		if (booleanExpression != null)
			query = query.where(booleanExpression);

		long total = query.fetchCount();

		if (sort != null) {
			for (Map.Entry<String, SortMeta> entry : sort.entrySet()) {
				PathBuilder<KullaniciIletisim> builder = new PathBuilder<KullaniciIletisim>(QKullaniciIletisim.kullaniciIletisim.getType(), QKullaniciIletisim.kullaniciIletisim.getMetadata());

				if (entry.getValue().getOrder().isAscending()) {
					query.orderBy(builder.getString(entry.getKey()).asc());
				} else {
					query.orderBy(builder.getString(entry.getKey()).desc());
				}
			}
		}

		query = query.offset(pageNumber * pageSize).limit(pageSize);

		List<KullaniciIletisim> list = (List<KullaniciIletisim>) query.fetch();

		return new PageImpl<>(list, PageRequest.of(pageNumber, pageSize), total);
	}

	public List<KullaniciIletisim> combo(String sorgu) {
		BooleanExpression booleanExpression = null;
		if(Util.notEmpty(sorgu)) {
			booleanExpression = Util.or(booleanExpression, QKullaniciIletisim.kullaniciIletisim.eposta.likeIgnoreCase(sorgu + "%"));
			booleanExpression = Util.or(booleanExpression, QKullaniciIletisim.kullaniciIletisim.cep_telefonu.likeIgnoreCase(sorgu + "%"));
		}

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		JPAQuery<KullaniciIletisim> query = queryFactory.
				select(QKullaniciIletisim.kullaniciIletisim).
				from(QKullaniciIletisim.kullaniciIletisim).
				limit(Util.LIMIT);

		if (booleanExpression != null)
			query = query.where(booleanExpression);

		return (List<KullaniciIletisim>) query.fetch();
	}

	public List<KullaniciIletisim> kullanicininTumIletisimBilgileriniGetir(Long kullanici_id) {
		if(Util.empty(kullanici_id)) {
			return new ArrayList<KullaniciIletisim>();
		}
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		JPAQuery<KullaniciIletisim> query = queryFactory.
				select(QKullaniciIletisim.kullaniciIletisim).
				from(QKullaniciIletisim.kullaniciIletisim).
				where(QKullaniciIletisim.kullaniciIletisim.kullanici_id.id.eq(kullanici_id));

		return (List<KullaniciIletisim>) query.fetch();
	}

}
