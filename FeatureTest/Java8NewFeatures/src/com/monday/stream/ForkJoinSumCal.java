package com.monday.stream;

import java.util.concurrent.RecursiveTask;

import org.junit.Test;

public class ForkJoinSumCal extends RecursiveTask {


	private static final long serialVersionUID = -800086911964691678L;
	
	
	private long start;
	private long end;
	private final long THRESHOLD = 10000;
	
	public ForkJoinSumCal(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		if ((end-start)<=THRESHOLD) {
			long sum = 0;
			for (long x= start; x<=end; x++) {
				sum = sum+x;
			}
			return sum;
		}else {
			long middle=(start+end)/2;
			ForkJoinSumCal left = new ForkJoinSumCal (start,middle);
			ForkJoinSumCal right = new ForkJoinSumCal (middle+1,end);
			
			left.fork();
			right.fork();
			return (long)left.join() + (long)right.join();
		}
		
	}
	
	

}
