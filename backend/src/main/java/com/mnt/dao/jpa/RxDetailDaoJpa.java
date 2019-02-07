package com.mnt.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.RxDetailDao;
//import com.mnt.domain.QRxDetail;
import com.mnt.domain.RxDetail;

@Repository
public class RxDetailDaoJpa extends BaseDaoJpa<RxDetail> implements RxDetailDao {

	public RxDetailDaoJpa() {
		super(RxDetail.class, "RxDetail");
	}
	
	@Override
	@Transactional
	public void deleteOldRecords(String year, String month, String provider) {
		Query query = getEntityManager().createQuery("DELETE FROM RxDetail rd where rd.year=:year and rd.month=:month and rd.provider=:provider");
		query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("provider", provider);
        query.executeUpdate();
	}
	
	@Override
	@Transactional
	public List<Object[]> getTop20PrescriptionDrugs(String type) {
		String queryStr = "";
		if(type.equals("cost")) {
			queryStr = "select round(sum(ingredient_cost),2) as cost,drug_name from rx_detail group by drug_name order by cost desc limit 20";
		}
		if(type.equals("volume cost")) {
			queryStr = "select round(sum(metric_quantity*ingredient_cost),2) as cost,drug_name from rx_detail group by drug_name order by cost desc limit 20";
		}
		if(type.equals("volume")) {
			queryStr = "select sum(metric_quantity) as cost,drug_name from rx_detail group by drug_name order by cost desc limit 20";
		}
		
		Query query = getEntityManager().createNativeQuery(queryStr);
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<Object[]> getPrescriptionDrugs(String qry) {
		Query query = getEntityManager().createNativeQuery(qry);
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<String> getAllPlans() {
		/*JPAQueryFactory jpaQuery = new JPAQueryFactory(getEntityManager());
		QRxDetail rxDetail = QRxDetail.rxDetail;
		RxDetail obj = jpaQuery.selectFrom(rxDetail).where(rxDetail.id.eq(451486L)).fetchOne();
		System.out.println(obj.getAhfsId()+"   .....................................");*/
		Query query = getEntityManager().createNativeQuery("select distinct provider from specialty_claim_detail");
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<Object[]> getAllPCP(String provider) {
		String str = "";
		if(provider.equals("all")) {
			str = "select distinct pcp_name,'' from monthly_totals_data order by pcp_name";
		} else {
			str = "select distinct pcp_name,pcp_id from monthly_totals_data where provider="+'\''+provider+'\''+" order by pcp_name";
		}
		Query query = getEntityManager().createNativeQuery(str);
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<String> getAllPCPLocationCode(String year) {
		String str  = "";
		
		if(year.equals("all")) {
			str = "select distinct pcp_location from monthly_totals_data order by pcp_location";
		} else {
			str = "select distinct pcp_location from monthly_totals_data where year=(select distinct year from monthly_totals_data order by year desc limit 1) order by pcp_location";
		}
		Query query = getEntityManager().createNativeQuery(str);
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public List<String> getAllPCPLocationByYear(String year) {
		String str  = "";
		
		if(year.equals("all")) {
			str = "select distinct pcp_location from monthly_totals_data order by pcp_location";
		} else {
			str = "select distinct pcp_location from monthly_totals_data where year="+'\''+year+'\''+" order by pcp_location";
		}
		Query query = getEntityManager().createNativeQuery(str);
		return query.getResultList();
	}
	
}
