package com.monday.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.monday.lambda.Employee.Status;

public class TestLambda1 {
	List<Employee> list = Arrays.asList(
			new Employee("Jackson",25,7777,Status.FREE),
			new Employee("Jerry",27,6666,Status.BUSY),
			new Employee("Tom",35,100000,Status.BUSY),
			new Employee("Wang",44,19999,Status.FREE),
			new Employee("Mark",24,9999,Status.VOCATION));

	
	public List <Employee> filterCollection (List<Employee> list, MyPredicate pre){
		List <Employee> newList = new ArrayList<Employee>();
		for (Employee e:list) {
			if (pre.test(e))
				newList.add(e);
		}
		return newList;
	}	
	
	//Anonymous inner class
	@Test 
	public void test1() {
		List<Employee> newList = filterCollection(list, new MyPredicate() {

			@Override
			public boolean test(Employee e) {
				if (e.getAge()>30) return true;
				return false;
			}
			
		});
		
		for (Employee e : newList) {
			System.out.println(e);
		}
		
	}
	
	
	//lambda expression
	@Test 
	public void test2() {
		List<Employee> newList = filterCollection (list, (x) -> x.getAge()>30);
		for (Employee e : newList) {
			System.out.println(e);
		}
	}
	
	
	//stream API
	@Test
	public void test3() {
		list.stream()
		    .filter((x) -> x.getAge()>30)
		    .forEach(System.out::println);
	}
	
	//sort the employ according to the age. If the employees are in the same age, sort according to the name.
	@Test 	
	public void test4() {
		Collections.sort(list, (x,y) -> {
			if (x.getAge()==y.getAge()) {
				return x.getName().compareTo(y.getName());
			}else {
				return Integer.compare(x.getAge(), y.getAge());
			}
		});
		
		for (Employee e : list) {
			System.out.println(e);
		}
	}
	
//	@Test
//	public void test5() {
//		Collections.sort(list, Integer::compare);
//		
//	}
	
	@Test
	public void test6 () {
		String str = "abd";
//		String newStr = processStr (str, (x)->x.toUpperCase());		
		//use method reference
		String newStr = processStr (str, String::toUpperCase);
		System.out.println(newStr);
	}
	
	public String processStr (String str, updateValue u) {
		return u.update(str);
	}


}
