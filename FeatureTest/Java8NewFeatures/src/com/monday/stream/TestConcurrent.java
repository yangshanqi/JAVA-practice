package com.monday.stream;

import java.time.Duration;
import java.time.Instant;
//import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.*;

import org.junit.Test;

public class TestConcurrent {
	
	@Test
	public void test1 () {
		Instant start = Instant.now();
		
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinSumCal task = new ForkJoinSumCal (1,1000000000L);
		Long res = (Long) pool.invoke(task);
		System.out.println(res);
		Instant end = Instant.now();
		System.out.println("duration: "+Duration.between(start, end).toMillis());	
	}
	
	@Test
	public void test2 () {
		Instant start = Instant.now();
		
		OptionalLong res = LongStream.rangeClosed(1, 10000000000L)
				  .parallel()
				  .reduce(Long::sum);
		System.out.println(res.getAsLong());
		Instant end = Instant.now();
		System.out.println("duration: "+Duration.between(start, end).toMillis());		
	}

}
