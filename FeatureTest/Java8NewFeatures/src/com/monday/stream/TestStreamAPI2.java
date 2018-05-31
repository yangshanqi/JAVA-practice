package com.monday.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import com.monday.lambda.Employee;
import com.monday.lambda.Employee.Status;

public class TestStreamAPI2 {
	
	List<Employee> list = Arrays.asList(
			new Employee("Jackson",25,7777,Status.FREE),
			new Employee("Jerry",27,6666,Status.BUSY),
			new Employee("Tom",35,100000,Status.BUSY),
			new Employee("Wang",44,19999,Status.FREE),
			new Employee("Mark",24,9999,Status.VOCATION));
	
	//4 methods: filter, skip, limit, distinct
	
	//filter
	@Test
	public void test1 () {
		Stream<Employee> stream = list.stream()
									 .filter((x)->{
										 System.out.println("filter operator");
										 return x.getAge()>30;});
		stream.forEach(System.out::println);
	}
	
	//limit
	@Test
	public void test2() {
		Stream<Employee> stream = list.stream()
										.filter((x) -> {
											System.out.println("filter operator");
											return x.getSalary() >7000;
										})
										.limit(2);
		stream.forEach(System.out::println);
	}
	
	//distinct     depends on hashCode() equals
	
	
	//map
	@Test
	public void test3() {
		
		String[] strs= {"aaa","bbb","ccc"};
		Stream<String> stream = Arrays.stream(strs)
				                      .map(String::toUpperCase);
		stream.forEach(System.out::println);
		
		Stream<String> stream1 = list.stream()
			   .map((x) -> x.getName());
		stream1.forEach(System.out::println);
		
	}
	
	
	//map flatMap
	@Test
	public void test4() {
		String[] strs= {"aaa","bbb","ccc"};
		Stream<Stream<Character>> stream1 = Arrays.stream(strs)
				   .map(TestStreamAPI2::createCharStream);
			
		stream1.forEach((x)->{
				x.forEach(System.out::println);
			});
		
		Stream<Character> stream2 =Arrays.stream(strs)
				.flatMap(TestStreamAPI2::createCharStream);
		
		stream2.forEach(System.out::println);
	}
	
	public static Stream<Character> createCharStream (String str){
		List<Character> list = new ArrayList<Character> ();
				for(Character ch : str.toCharArray()) {
			list.add(ch);
		}		
		return list.stream();
		
	}
	
	//sorted()
	//sorted(Comparator)
	@Test
	public void test5() {		
		String[] strs = {"ddd","aaa","ccc","bbb"};
		Arrays.stream(strs)
			.sorted()
			.forEach(System.out::println);
		System.out.println("---------------------");
		list.stream()
			.sorted((x,y) -> {
				if (x.getAge()==y.getAge())
					return x.getName().compareTo(y.getName());
				return Integer.compare(x.getAge(), y.getAge());
			})
			.forEach(System.out::println);
	}
	
	//reduce
	@Test
	public void test6() {
		Integer[] arr = {1,2,3,4,5,6,7,8,9,10};
		Integer res = Arrays.stream(arr)
							.reduce(0,(x,y) -> x+y);
		System.out.println(res);
		
		Optional<Integer> res2 = list.stream()
			.map(Employee::getSalary)
			.reduce((x,y) -> x+y);
		System.out.println(res2);		
	}
	
	//collect
	@Test
	public void test7() {
		List<String> newList = list.stream()
								   .map(Employee::getName)
								   .collect(Collectors.toList());
		for (String str : newList) {
			System.out.println(str);
		}
		System.out.println("-------------------------------");
		Set<String> newSet = list.stream()
				   .map(Employee::getName)
				   .collect(Collectors.toSet());
		for (String str : newSet) {
			System.out.println(str);
		}
		System.out.println("-------------------------------");
		HashSet<String> hs = list.stream()
				   .map(Employee::getName)
				   .collect(Collectors.toCollection(HashSet::new));
		for (String str : hs) {
			System.out.println(str);
		}
		
	}

}
