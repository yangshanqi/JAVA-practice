package com.monday.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.monday.lambda.Employee;
import com.monday.lambda.Employee.Status;

public class TestStreamAPI3 {

	List<Employee> list = Arrays.asList(
			new Employee("Jackson",25,7777,Status.FREE),
			new Employee("Jerry",27,6666,Status.BUSY),
			new Employee("Tom",35,100000,Status.BUSY),
			new Employee("Wang",44,19999,Status.FREE),
			new Employee("Mark",24,9999,Status.VOCATION));
	
	//allMatch, anyMathch, noneMatch, findFirst, findAny
	@Test
	public void test1 () {
		boolean b1 = list.stream()
				.allMatch((x) ->x.getAge() >30);
		System.out.println(b1);
		
		boolean b2 = list.stream()
				.anyMatch((x) ->x.getAge() >30);
		System.out.println(b2);
	}
	
	//count, max, min
	@Test
	public void test2 () {
		long count = list.stream().count();
		System.out.println(count);
		
		 Optional<Employee> op=list.stream()
			.max((x,y)-> Double.compare(x.getSalary(), y.getSalary()));
		 System.out.println(op.toString());
		 
	}
	
}
