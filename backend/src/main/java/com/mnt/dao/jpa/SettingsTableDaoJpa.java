package com.mnt.dao.jpa;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mnt.dao.MonthlyTotalsDataDao;
import com.mnt.dao.MonthlyTotalsReportDao;
import com.mnt.dao.SettingsTableDao;
import com.mnt.domain.MonthlyTotalsData;
import com.mnt.domain.MonthlyTotalsReport;
import com.mnt.domain.SettingsTable;
import com.mnt.utils.Scheduler;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Repository
public class SettingsTableDaoJpa extends BaseDaoJpa<SettingsTable> implements SettingsTableDao {

	SettingsTableDaoJpa() {
		super(SettingsTable.class, "SettingsTable");
	}
	
	@Override
	@Transactional
	public void setMaintenanceMode(String mode) {
		Query query = getEntityManager().createNativeQuery("update settings_table set maintenance_mode="+'\''+mode+'\'');
        query.executeUpdate();
	}
	
	@Override
	@Transactional
	public void setReinsuranceThreshold(Integer reinsuranceThreshold) {
		Query query = getEntityManager().createNativeQuery("update settings_table set reinsurance_threshold="+'\''+reinsuranceThreshold+'\'');
        query.executeUpdate();
	}
	
	@Override
	@Transactional
	public void setReinsuranceCostThreshold(Integer reinsuranceCostThreshold) {
		Query query = getEntityManager().createNativeQuery("update settings_table set reinsurance_cost_threshold="+'\''+reinsuranceCostThreshold+'\'');
        query.executeUpdate();
	}
	
	@Override
	@Transactional
	public List<Object[]> getMaintenanceMode() {
		Query query = getEntityManager().createNativeQuery("select * from settings_table");
        return query.getResultList();
	}
	
	@Override
	@Transactional
	public void deleteAllData() {
		Query query = getEntityManager().createNativeQuery("delete from temp_demographic_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("delete from temp_inst_claim_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("delete from temp_prof_claim_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("delete from temp_specialty_claim_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("delete from temp_premium_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("delete from temp_rx_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("delete from temp_stoploss_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("delete from temp_monthly_totals_data"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("delete from temp_monthly_totals_report"); 
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_demographic_detail SELECT * FROM demographic_detail");
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_inst_claim_detail SELECT * FROM inst_claim_detail");
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_prof_claim_detail SELECT * FROM prof_claim_detail");
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_specialty_claim_detail SELECT * FROM specialty_claim_detail");
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_premium_detail SELECT * FROM premium_detail");
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_rx_detail SELECT * FROM rx_detail");
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_stoploss_detail SELECT * FROM stoploss_detail");
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_monthly_totals_data SELECT * FROM monthly_totals_data");
        query.executeUpdate();
        
        query = getEntityManager().createNativeQuery("INSERT temp_monthly_totals_report SELECT * FROM monthly_totals_report");
        query.executeUpdate();
	}
	
	@Autowired
	MonthlyTotalsReportDao monthlyTotalsReportDao;
	@Autowired
	MonthlyTotalsDataDao monthlyTotalsDataDao;
	
	@Value("${user.config.monthly-totals-dir}")
	public static String monthlyTotalsBasePath;
	
	@Override
	@Transactional
	public void uploadData() {
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.runScheduledTask();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Reader reader = null;
		try {
			reader = Files.newBufferedReader(Paths.get(monthlyTotalsBasePath+"/monthly_totals_report.csv"));
		} catch (IOException e) {e.printStackTrace();}
		
		CsvToBean<MonthlyTotalsReport> csvToBean = new CsvToBeanBuilder(reader)
                .withType(MonthlyTotalsReport.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
		
		List<MonthlyTotalsReport> list = csvToBean.parse();
		
		for(MonthlyTotalsReport obj: list) {
			monthlyTotalsReportDao.save(obj);
		}
		
		reader = null;
		try {
			reader = Files.newBufferedReader(Paths.get(monthlyTotalsBasePath+"/monthly_totals_data.csv"));
		} catch (IOException e) {e.printStackTrace();}
		
		CsvToBean<MonthlyTotalsData> csvToBeanData = new CsvToBeanBuilder(reader)
                .withType(MonthlyTotalsData.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
		
		List<MonthlyTotalsData> dataList = csvToBeanData.parse();
		
		for(MonthlyTotalsData obj: dataList) {
			monthlyTotalsDataDao.save(obj);
		}
		
		Query query = getEntityManager().createNativeQuery("update demographic_detail set constant_val='23.55' where YEAR(eligible_month)='2018'"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("update monthly_totals_data set constant_val='23.55' where month like '2018%'"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("update monthly_totals_report set reinsurance='23.55' where month like '2018%'"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("DROP INDEX `idx_medicare_id` ON demographic_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("DROP INDEX `idx_medicare_id` ON inst_claim_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("DROP INDEX `idx_medicare_id` ON prof_claim_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("DROP INDEX `idx_medicare_id` ON specialty_claim_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("DROP INDEX `idx_medicare_id` ON rx_detail"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("CREATE INDEX idx_medicare_id ON demographic_detail (medicare_id)"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("CREATE INDEX idx_medicare_id ON inst_claim_detail (medicare_id)"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("CREATE INDEX idx_medicare_id ON prof_claim_detail (medicare_id)"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("CREATE INDEX idx_medicare_id ON specialty_claim_detail (medicare_id)"); 
		query.executeUpdate();
		
		query = getEntityManager().createNativeQuery("CREATE INDEX idx_medicare_id ON rx_detail (medicare_id)"); 
		query.executeUpdate();
		
	}
	
}
