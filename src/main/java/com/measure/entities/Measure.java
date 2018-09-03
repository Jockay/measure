package com.measure.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "measures", schema="measure")
public class Measure implements Serializable {

	private static final long serialVersionUID = 5539110572172382146L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="measure_date")
	private Date measureDate;
	
	@Column(name="measure_value")
	private Integer measureValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getMeasureDate() {
		return measureDate;
	}

	public void setMeasureDate(Date measureDate) {
		this.measureDate = measureDate;
	}

	public Integer getMeasureValue() {
		return measureValue;
	}

	public void setMeasureValue(Integer measureValue) {
		this.measureValue = measureValue;
	}

}
