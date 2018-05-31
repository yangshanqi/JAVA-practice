package com.monday.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import com.monday.lambda.Employee;

public class TestStreamAPI {
	
	
	//create stream
	@Test
	public void test1 () {
		
		// Collection has the method stream () parallelStream()
		List<String> list = new ArrayList<String> ();
		Stream stream1 = list.stream();
		
		//Arrays.stream(...)
		Integer[] arr = new Integer[10];
		Stream stream2 = Arrays.stream(arr);
		
		Employee[] emps = new Employee[10];
		Stream stream3 = Arrays.stream(emps);
		
		//Stream.of
		Stream stream4 = Stream.of("aaa","bbb","ccc");
		
		//Stream.iterate(seed, f)
		Stream stream5 = Stream.iterate(0, (x) -> x+2);
		stream5.limit(20)
		       .forEach(System.out::println);
		
		//Stream.generator
		Stream stream6 = Stream.generate(Math::random);
		stream6.limit(5)
		       .forEach(System.out::println);
		
		
		
	}

}
