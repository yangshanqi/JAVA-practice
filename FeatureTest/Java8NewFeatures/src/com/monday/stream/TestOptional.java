package com.monday.stream;

import java.util.Optional;

import org.junit.Test;

import com.monday.lambda.Employee;
import com.monday.lambda.Employee.Status;

public class TestOptional {
	
	@Test
	public void test3 () {
		Optional<Employee> op = Optional.ofNullable(new Employee());
		
//		if (op.isPresent()) {
//			System.out.println(op.get());
//		}
		
//		Employee emp = op.orElse(new Employee ("jack",27,9000,Status.FREE));
//		System.out.println(emp);
		
		Employee emp = op.orElseGet(( ) -> new Employee( ));
		System.out.println(emp);
	}
	
	@Test
	public void test2 () {
		Optional<Employee> op = Optional.empty();
		System.out.println(op.get());
	}
	
	@Test
	public void test1() {
		Optional<Employee> op = Optional.of(null);
		Employee emp = op.get();
		System.out.println(emp);
	}

}
