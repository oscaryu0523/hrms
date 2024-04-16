package com.example.hrms.repository;

import com.example.hrms.dto.EmpDeptDto;
import com.example.hrms.entity.Emp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepository extends JpaRepository<Emp, Integer> {
    List<Emp> findByDept_DeptNo(Integer deptNo);


    List<Emp> findByEnameContaining(String keyword);

    List<Emp> findByDept_DeptNoAndEnameContaining(Integer deptNo, String nameKeyword);

    boolean existsByEmail(String email);
    boolean existsByEmailAndEmpNoNot(String email, Integer empNo);

    // JPQL 查詢來找出部門名稱包含特定關鍵字的員工
    @Query("SELECT e FROM Emp e WHERE e.dept.dname LIKE %:keyword%")
    List<Emp> findEmpsByDeptNameContaining(@Param("keyword") String keyword);


//    @Query(value = "SELECT e.empNo, e.ename, d.deptNo, e.job, e.email, e.phoneNumber, e.address, e.hiredate, e.sal, d.dname, d.location FROM emp e JOIN dept d ON e.dept_no = d.dept_no WHERE e.empNo= :empNo", nativeQuery = true)//沒使用@ManyToOne的情況
    @Query("SELECT new com.example.hrms.dto.EmpDeptDto(e.empNo, e.ename, d.deptNo, e.job, e.email, e.phoneNumber, e.address, e.hiredate, e.sal, d.dname, d.location) FROM Emp e JOIN e.dept d WHERE e.empNo = :empNo")
    EmpDeptDto findEmpWithDeptInfo(@Param("empNo") Integer empNo);

}
