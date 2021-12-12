package co.edu.icesi.model.hr;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

import co.edu.icesi.model.sales.Salesperson;

/**
 * The persistent class for the employee database table.
 *
 */
@Entity
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "EMPLOYEE_BUSINESSENTITYID_GENERATOR", allocationSize = 1, sequenceName = "EMPLOYEE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEE_BUSINESSENTITYID_GENERATOR")
	private Integer businessentityid;

	//@Temporal(TemporalType.DATE)
	@NotNull(message = "El campo no debe estar vacío")
	@Past(message = "La fecha de nacimiento del empleado debe ser anterior a la fecha actual")
	private LocalDate birthdate;
	
	@NotNull(message = "El campo no debe estar vacío")
	private String currentflag;
	
	@NotNull(message = "El campo no debe estar vacío")
	private EmployeeGender gender;
	
	//@Temporal(TemporalType.DATE)
	@NotNull(message = "El campo no debe estar vacío")
	@PastOrPresent(message = "La fecha de contratación no puede ser posterior a la fecha actual")
	private LocalDate hiredate;
	
	@NotNull(message = "El campo no debe estar vacío")
	private String jobtitle;

	private String loginid;
	
	@NotNull(message = "El campo no debe estar vacío")
	private String maritalstatus;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@FutureOrPresent(message = "La fecha de modificación debe ser la actual")
	private LocalDate modifieddate;

	private String nationalidnumber;

	private String organizationnode;

	private Integer rowguid;

	private String salariedflag;

	private Integer sickleavehours;

	private Integer vacationhours;
	
	// bi-directional many-to-one association to Salesperson
	@OneToOne
	@JoinColumn(name = "salespersonid")
	//@NotNull(message = "Se debe escoger un vendedor")
	private Salesperson salesperson;
	
	// bi-directional many-to-one association to Employeedepartmenthistory
	@OneToMany(mappedBy = "employee")
	private List<Employeedepartmenthistory> employeedepartmenthistories;

	// bi-directional many-to-one association to Employeepayhistory
	@OneToMany(mappedBy = "employee")
	private List<Employeepayhistory> employeepayhistories;

	// bi-directional many-to-one association to Jobcandidate
	@OneToMany(mappedBy = "employee")
	private List<Jobcandidate> jobcandidates;

	public Employee() {
		setModifieddate(LocalDate.now());
	}

	public Employeedepartmenthistory addEmployeedepartmenthistory(Employeedepartmenthistory employeedepartmenthistory) {
		getEmployeedepartmenthistories().add(employeedepartmenthistory);
		employeedepartmenthistory.setEmployee(this);

		return employeedepartmenthistory;
	}

	public Employeepayhistory addEmployeepayhistory(Employeepayhistory employeepayhistory) {
		getEmployeepayhistories().add(employeepayhistory);
		employeepayhistory.setEmployee(this);

		return employeepayhistory;
	}

	public Jobcandidate addJobcandidate(Jobcandidate jobcandidate) {
		getJobcandidates().add(jobcandidate);
		jobcandidate.setEmployee(this);

		return jobcandidate;
	}

	public LocalDate getBirthdate() {
		return this.birthdate;
	}

	public Integer getBusinessentityid() {
		return this.businessentityid;
	}

	public String getCurrentflag() {
		return this.currentflag;
	}
	
	public Salesperson getSalesperson() {
		return this.salesperson;
	}

	public List<Employeedepartmenthistory> getEmployeedepartmenthistories() {
		return this.employeedepartmenthistories;
	}

	public List<Employeepayhistory> getEmployeepayhistories() {
		return this.employeepayhistories;
	}

	public EmployeeGender getGender() {
		return this.gender;
	}

	public LocalDate getHiredate() {
		return this.hiredate;
	}

	public List<Jobcandidate> getJobcandidates() {
		return this.jobcandidates;
	}

	public String getJobtitle() {
		return this.jobtitle;
	}

	public String getLoginid() {
		return this.loginid;
	}

	public String getMaritalstatus() {
		return this.maritalstatus;
	}

	public LocalDate getModifieddate() {
		return this.modifieddate;
	}

	public String getNationalidnumber() {
		return this.nationalidnumber;
	}

	public String getOrganizationnode() {
		return this.organizationnode;
	}

	public Integer getRowguid() {
		return this.rowguid;
	}

	public String getSalariedflag() {
		return this.salariedflag;
	}

	public Integer getSickleavehours() {
		return this.sickleavehours;
	}

	public Integer getVacationhours() {
		return this.vacationhours;
	}

	public Employeedepartmenthistory removeEmployeedepartmenthistory(
			Employeedepartmenthistory employeedepartmenthistory) {
		getEmployeedepartmenthistories().remove(employeedepartmenthistory);
		employeedepartmenthistory.setEmployee(null);

		return employeedepartmenthistory;
	}

	public Employeepayhistory removeEmployeepayhistory(Employeepayhistory employeepayhistory) {
		getEmployeepayhistories().remove(employeepayhistory);
		employeepayhistory.setEmployee(null);

		return employeepayhistory;
	}

	public Jobcandidate removeJobcandidate(Jobcandidate jobcandidate) {
		getJobcandidates().remove(jobcandidate);
		jobcandidate.setEmployee(null);

		return jobcandidate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public void setBusinessentityid(Integer businessentityid) {
		this.businessentityid = businessentityid;
	}

	public void setCurrentflag(String currentflag) {
		this.currentflag = currentflag;
	}
	
	public void setSalesperson(Salesperson salesperson) {
		this.salesperson = salesperson;
	}
	
	public void setEmployeedepartmenthistories(List<Employeedepartmenthistory> employeedepartmenthistories) {
		this.employeedepartmenthistories = employeedepartmenthistories;
	}

	public void setEmployeepayhistories(List<Employeepayhistory> employeepayhistories) {
		this.employeepayhistories = employeepayhistories;
	}

	public void setGender(EmployeeGender gender) {
		this.gender = gender;
	}

	public void setHiredate(LocalDate hiredate) {
		this.hiredate = hiredate;
	}

	public void setJobcandidates(List<Jobcandidate> jobcandidates) {
		this.jobcandidates = jobcandidates;
	}

	public void setJobtitle(String jobtitle) {
		this.jobtitle = jobtitle;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public void setModifieddate(LocalDate modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setNationalidnumber(String nationalidnumber) {
		this.nationalidnumber = nationalidnumber;
	}

	public void setOrganizationnode(String organizationnode) {
		this.organizationnode = organizationnode;
	}

	public void setRowguid(Integer rowguid) {
		this.rowguid = rowguid;
	}

	public void setSalariedflag(String salariedflag) {
		this.salariedflag = salariedflag;
	}

	public void setSickleavehours(Integer sickleavehours) {
		this.sickleavehours = sickleavehours;
	}

	public void setVacationhours(Integer vacationhours) {
		this.vacationhours = vacationhours;
	}

}