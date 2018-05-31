package com.monday.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;

public class TestLambda {
	
	
	//Consumer<T> FunctionInterface
	@Test
	public void test1() {
		buy (1000, (x)  -> System.out.println("spend "+x+ " on the tickets"));
		buy (2000, (x) -> System.out.println("spend" +x+" on the cloes"));
	}

	public void buy (double money, Consumer<Double> con){
		con.accept(money);
	}
	
	//Supplier<T> FunctionINterface
	@Test 
	public void test2() {
		List<Integer> newList = getIntList (10, () -> (int)(Math.random()*100));
		
		for (int x : newList) {
			System.out.println(x);
		}	
	}
		
	//Parameter: the size of list, and how to create it
	//Return: a list
	public List<Integer> getIntList (int intNum, Supplier<Integer> supp){
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i<intNum; i++) {
			list.add(supp.get());
		}
		return list;
	}
	
	//Function<T,R>
	@Test
	public void Test3 () {
		System.out.println(getUpdate (3, (x) -> x+1));
		System.out.println(getUpdate (3, (x) -> x*5));
	}
	
	//update the value according to self-defined way
	public int getUpdate (int num, Function<Integer, Integer> fun) {
		return (fun.apply(num));
	}
	
	//Predicate<T> function interface
	@Test
	public void Test4() {
		List<String> list = new ArrayList<>();
		list = Arrays.asList("stuttgart","berlin","infotech","hi","how");
		ArrayList<String> newList = filterList (list, (x) -> x.length() >3 );
		
		for (String str: newList) {
			System.out.println(str);
		}
	}
	
	public ArrayList<String> filterList (List<String> list, Predicate<String> predicate){
		ArrayList<String> newList = new ArrayList<String>();
		for (String str: list) {
			if (predicate.test(str))
				newList.add(str);
		}
		return newList;
	}
	
}
