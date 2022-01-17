package tr.bays.customdao.kullanici;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.primefaces.model.SortMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import tr.bays.entity.kullanici.Kullanici;
import tr.bays.entity.kullanici.QKullanici;
import util.Util;

@Repository
public class CustomKullaniciRepository {

	@PersistenceContext
	private EntityManager em;

	public BooleanExpression kriter(Kullanici kullanici) {
		BooleanExpression booleanExpression = null;
		if (Util.notEmpty(kullanici.getKullanici_adi())) {
			booleanExpression = Util.and(booleanExpression, QKullanici.kullanici.kullanici_adi.likeIgnoreCase("%" + kullanici.getKullanici_adi() + "%"));
		}
		if (kullanici.getParola_degistir() != 0) {
			booleanExpression = Util.and(booleanExpression, QKullanici.kullanici.parola_degistir.eq(kullanici.getParola_degistir()));
		}
		if (Util.notEmpty(kullanici.getParola_degistirme_tarihi())) {
			booleanExpression = Util.and(booleanExpression, QKullanici.kullanici.parola_degistirme_tarihi.eq(kullanici.getParola_degistirme_tarihi()));
		}
		if (Util.notEmpty(kullanici.getTc_kimlik_no())) {
			booleanExpression = Util.and(booleanExpression, QKullanici.kullanici.tc_kimlik_no.likeIgnoreCase(kullanici.getTc_kimlik_no() + "%"));
		}
		if (Util.notEmpty(kullanici.getAd())) {
			booleanExpression = Util.and(booleanExpression, QKullanici.kullanici.ad.likeIgnoreCase("%" + kullanici.getAd() + "%"));
		}
		if (Util.notEmpty(kullanici.getSoyad())) {
			booleanExpression = Util.and(booleanExpression, QKullanici.kullanici.soyad.likeIgnoreCase("%" + kullanici.getSoyad() + "%"));
		}
		if (kullanici.getCinsiyet() != 0) {
			booleanExpression = Util.and(booleanExpression, QKullanici.kullanici.cinsiyet.eq(kullanici.getCinsiyet()));
		}
		if (Util.notEmpty(kullanici.getDogum_tarihi())) {
			booleanExpression = Util.and(booleanExpression, QKullanici.kullanici.dogum_tarihi.eq(kullanici.getDogum_tarihi()));
		}

		return booleanExpression;
	}

	public Page<Kullanici> sorgula(int pageSize, int pageNumber, Kullanici kullanici, Map<String, SortMeta> sort) {
		BooleanExpression booleanExpression = kriter(kullanici);

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		JPAQuery<Kullanici> query = queryFactory.
				select(QKullanici.kullanici).
				from(QKullanici.kullanici);

		if (booleanExpression != null)
			query = query.where(booleanExpression);

		long total = query.fetchCount();

		if (sort != null) {
			for (Map.Entry<String, SortMeta> entry : sort.entrySet()) {
				PathBuilder<Kullanici> builder = new PathBuilder<Kullanici>(QKullanici.kullanici.getType(), QKullanici.kullanici.getMetadata());

				if (entry.getValue().getOrder().isAscending()) {
					query.orderBy(builder.getString(entry.getKey()).asc());
				} else {
					query.orderBy(builder.getString(entry.getKey()).desc());
				}
			}
		}

		query = query.offset(pageNumber * pageSize).limit(pageSize);

		List<Kullanici> list = (List<Kullanici>) query.fetch();

		return new PageImpl<>(list, PageRequest.of(pageNumber, pageSize), total);
	}

	public List<Kullanici> combo(String sorgu) {
		BooleanExpression booleanExpression = null;

		if (Util.notEmpty(sorgu)) {
			booleanExpression = Util.or(booleanExpression, QKullanici.kullanici.tc_kimlik_no.likeIgnoreCase(sorgu + "%"));
			booleanExpression = Util.or(booleanExpression, QKullanici.kullanici.soyad.likeIgnoreCase("%" + sorgu + "%"));
			booleanExpression = Util.or(booleanExpression, QKullanici.kullanici.soyad.likeIgnoreCase("%" + sorgu + "%"));
		}

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		JPAQuery<Kullanici> query = queryFactory.
				select(QKullanici.kullanici).
				from(QKullanici.kullanici).
				orderBy(new OrderSpecifier<>(Order.ASC, QKullanici.kullanici.ad)).
				orderBy(new OrderSpecifier<>(Order.ASC, QKullanici.kullanici.soyad)).
				limit(Util.LIMIT);

		if (booleanExpression != null)
			query = query.where(booleanExpression);

		return (List<Kullanici>) query.fetch();
	}

	public Kullanici kullaniciAdiIleGetir(String kullaniciAdi) {
		if(Util.empty(kullaniciAdi)) {
			return null;
		}
		BooleanExpression booleanExpression = QKullanici.kullanici.kullanici_adi.eq(kullaniciAdi);
		
		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		JPAQuery<Kullanici> query = queryFactory.
				select(QKullanici.kullanici).
				from(QKullanici.kullanici).
				where(booleanExpression).
				limit(1);

		List<Kullanici> kullaniciListesi = (List<Kullanici>) query.fetch();
		return kullaniciListesi.size() > 0 ? kullaniciListesi.get(0) : null;
	}

}
