package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the t_dhh_kpi_mst database table.
 * 
 */
@Entity
@Table(name = "t_dhh_kpi_mst")
@NamedQuery(name = "TDhhKpiMst.findAll", query = "SELECT t FROM TDhhKpiMst t")
public class TDhhKpiMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "kpi_id")
	private Long kpiId;

	@Column(name = "logic_type")
	private String logicType;

	@Column(name = "metric_defn")
	private String metricDefn;

	@Column(name = "metric_group")
	private String metricGroup;

	@Column(name = "metric_name")
	private String metricName;

	@Column(name = "optimal_target")
	private Double optimalTarget;

	@Column(name = "active_flag")
	private String activeFlag;

	// bi-directional many-to-one association to TDhhTuplaMst
	@ManyToOne
	@JoinColumn(name = "tupla_id")
	private TDhhTuplaMst TDhhTuplaMst;

	// bi-directional many-to-one association to TDhhTuplaMst
	@ManyToOne
	@JoinColumn(name = "lever_id")
	private TDhhLeverMst tDhhLeverMst;

	public TDhhLeverMst gettDhhLeverMst() {
		return tDhhLeverMst;
	}

	public void settDhhLeverMst(TDhhLeverMst tDhhLeverMst) {
		this.tDhhLeverMst = tDhhLeverMst;
	}

	public TDhhKpiMst() {
	}

	public Long getKpiId() {
		return this.kpiId;
	}

	public void setKpiId(Long kpiId) {
		this.kpiId = kpiId;
	}

	public String getLogicType() {
		return this.logicType;
	}

	public void setLogicType(String logicType) {
		this.logicType = logicType;
	}

	public String getMetricDefn() {
		return this.metricDefn;
	}

	public void setMetricDefn(String metricDefn) {
		this.metricDefn = metricDefn;
	}

	public String getMetricGroup() {
		return this.metricGroup;
	}

	public void setMetricGroup(String metricGroup) {
		this.metricGroup = metricGroup;
	}

	public String getMetricName() {
		return this.metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public Double getOptimalTarget() {
		return this.optimalTarget;
	}

	public void setOptimalTarget(Double optimalTarget) {
		this.optimalTarget = optimalTarget;
	}

	public TDhhTuplaMst getTDhhTuplaMst() {
		return this.TDhhTuplaMst;
	}

	public void setTDhhTuplaMst(TDhhTuplaMst TDhhTuplaMst) {
		this.TDhhTuplaMst = TDhhTuplaMst;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
}