package com.mnt.dao.jpa;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.SpecialtyClaimDetailDao;
import com.mnt.domain.SpecialtyClaimDetail;

@Repository
public class SpecialtyClaimDetailDaoJpa extends BaseDaoJpa<SpecialtyClaimDetail> implements SpecialtyClaimDetailDao {

	public SpecialtyClaimDetailDaoJpa() {
		super(SpecialtyClaimDetail.class, "SpecialtyClaimDetail");
	}
	
	@Override
	@Transactional
	public void deleteOldRecords(String year, String month, String provider) {
		Query query = getEntityManager().createQuery("DELETE FROM SpecialtyClaimDetail scd where scd.year=:year and scd.month=:month and scd.provider=:provider");
		query.setParameter("year", year);
        query.setParameter("month", month);
        query.setParameter("provider", provider);
        query.executeUpdate();
	}
	
	@Override
	@Transactional
	public Double getSum(String year, List<String> providerArr) {
		String str = "";
			if(providerArr.contains("all")) {
				str = "select round(sum(paid_amount),2) from specialty_claim_detail";
				if(!year.equals("all") && year != null && !year.equals("")) {
					str = str + " where year="+year;
				}
			} else {
				str = "select round(sum(paid_amount),2) from specialty_claim_detail where ";
				for(int i=0; i<providerArr.size();i++) {
					if(i == providerArr.size()-1) {
						str = str + "provider="+'\''+providerArr.get(i)+'\'';
					} else {
						str = str + "provider="+'\''+providerArr.get(i)+'\''+" or ";
					}
				}	
				if(!year.equals("all") && year != null && !year.equals("")) {
					str = str + " and year="+year;
				}
			}
			
			System.out.println(str);
		Query query = getEntityManager().createNativeQuery(str);
		return query.getSingleResult() == null ? 0: (Double)query.getSingleResult();
	}
}
