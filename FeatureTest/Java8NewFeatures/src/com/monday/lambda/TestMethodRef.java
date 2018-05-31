package com.monday.lambda;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

import org.junit.Test;

public class TestMethodRef { 
	
	@Test
	public void test1 () {
		//use method reference
		//对象：：实例方法名
		PrintStream ps = System.out;
		Consumer<String> con = ps::println;
		con.accept("abc");
		
		//without method reference
		Consumer<String> con1 = (x) -> System.out.println(x);
		con1.accept("def");
	}
	
	@Test
	public void test2 () {
		Comparator<Integer> com1 = (x,y) -> Integer.compare(x,y);
		
		//method reference 
		//类：：静态方法名
		Comparator<Integer> com2 = Integer::compare;
		System.out.println(com1.compare(3, 2));
	}
	
	@Test
	public void test3 () {
		BiPredicate<String, String> predicate = (x,y) -> x.equals(y);
		BiPredicate<String, String> predicate2 = String::equals;
	}
	

}
