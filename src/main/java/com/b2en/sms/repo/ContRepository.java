package com.b2en.sms.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.b2en.sms.dto.autocompleteinfo.ContACInterface;
import com.b2en.sms.model.Cont;

@Repository
public interface ContRepository extends JpaRepository<Cont, Integer>{
	
	List<Cont> findByDelYn(String yn);
	
	List<Cont> findByHeadContIdAndDelYnOrderByContIdDesc(int headContId, String yn);
	
	List<ContACInterface> findAllBy();
	
	@Query(value="SELECT count(*) FROM cont WHERE cust_id = :custId", nativeQuery = true)
	Integer countByCustId(@Param("custId") int custId);
	
	@Query(value="SELECT count(*) FROM cont WHERE emp_id = :empId", nativeQuery = true)
	Integer countByEmpId(@Param("empId") int empId);
	
	@Query(value="SELECT count(*) FROM cont WHERE org_id = :orgId", nativeQuery = true)
	Integer countByOrgId(@Param("orgId") int orgId);
	
	@Query(value="SELECT * FROM cont ORDER BY binary(cont_nm)", nativeQuery = true)
	List<Cont> findAllOrderByContNm();
	
	@Query(value="SELECT * FROM cont WHERE head_cont_id <> 0 AND del_yn = 'N' ORDER BY head_cont_id, cont_dt", nativeQuery = true)
	List<Cont> findMtncCont();
	
	// cust가 null일 경우의 insert
	@Transactional
	@Modifying
	@Query(value="INSERT INTO cont(cust_id, org_id, emp_id, head_cont_id, cont_nm, cont_dt, cont_tot_amt, del_yn, cont_report_no, cont_tp_cd, install_dt, check_dt, mtnc_start_dt, mtnc_end_dt, created_date, modified_date) VALUES(null, :#{#paramCont.org.orgId}, :#{#paramCont.b2en.empId}, :#{#paramCont.headContId}, :#{#paramCont.contNm}, :#{#paramCont.contDt}, :#{#paramCont.contTotAmt}, 'N', :#{#paramCont.contReportNo}, :#{#paramCont.contTpCd}, :#{#paramCont.installDt}, :#{#paramCont.checkDt}, :#{#paramCont.mtncStartDt}, :#{#paramCont.mtncEndDt}, :#{#paramCont.createdDate}, :#{#paramCont.modifiedDate})", nativeQuery = true)
	int forceInsert(@Param("paramCont") Cont cont);
	
	// cust가 null일 경우의 update
	@Transactional
	@Modifying
	@Query(value="UPDATE cont SET cust_id = null, org_id = :#{#paramCont.org.orgId}, emp_id = :#{#paramCont.b2en.empId}, head_cont_id = :#{#paramCont.headContId}, cont_nm = :#{#paramCont.contNm}, cont_dt = :#{#paramCont.contDt}, cont_tot_amt = :#{#paramCont.contTotAmt}, cont_report_no = :#{#paramCont.contReportNo}, cont_tp_cd = :#{#paramCont.contTpCd}, install_dt = :#{#paramCont.installDt}, check_dt = :#{#paramCont.checkDt}, mtnc_start_dt = :#{#paramCont.mtncStartDt}, mtnc_end_dt = :#{#paramCont.mtncEndDt} WHERE cont_id = :#{#paramCont.contId}", nativeQuery = true)
	int forceUpdate(@Param("paramCont") Cont cont);
	
	// cust가 null일 경우의 과정에 필요함
	@Query(value="SELECT * FROM cont WHERE cont_id = (SELECT max(cont_id) FROM cont)", nativeQuery = true)
	Cont findRecentCont();
}
