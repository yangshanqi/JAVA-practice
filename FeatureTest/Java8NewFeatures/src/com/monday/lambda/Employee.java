package com.monday.lambda;

public class Employee {
	
	private String Name;
	private int age;
	private int Salary;
	private Status WorkerStatus;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Employee(String name, int age, int salary, Status workerStatus) {
		super();
		Name = name;
		this.age = age;
		Salary = salary;
		WorkerStatus = workerStatus;
	}

	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getSalary() {
		return Salary;
	}
	public void setSalary(int salary) {
		Salary = salary;
	}	
	public Status getWorkerStatus() {
		return WorkerStatus;
	}
	public void setWorkerStatus(Status workerStatus) {
		WorkerStatus = workerStatus;
	}
	
	@Override
	public String toString() {
		return "Employee [Name=" + Name + ", age=" + age + ", Salary=" + Salary + ", WorkerStatus=" + WorkerStatus
				+ "]";
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + Salary;
		result = prime * result + ((WorkerStatus == null) ? 0 : WorkerStatus.hashCode());
		result = prime * result + age;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (Salary != other.Salary)
			return false;
		if (WorkerStatus != other.WorkerStatus)
			return false;
		if (age != other.age)
			return false;
		return true;
	}

	public enum Status{
		BUSY,FREE,VOCATION;
	}

}
