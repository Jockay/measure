package com.measure.repositories;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.measure.entities.Measure;

public interface RepoMeasure extends JpaRepository<Measure, Integer> {

	List<Measure> findAll();
	
	List<Measure> findByMeasureDate(Date measureDate);
	
	@Query("select m from Measure m where m.measureDate >= :dateFrom and m.measureDate <= :dateTo")
	List<Measure> find(@Param("dateFrom") Date dateFrom, @Param("dateTo") Date dateTo);
	
}
