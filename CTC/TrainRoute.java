<<<<<<< HEAD
package CTC;
=======
package com.BennieAndTheJets.CTC;
>>>>>>> 6d2b9428abe1c66a9fea66ffc5d3acf5e93ea78c

public class TrainRoute {
	public int block;
	public TrainRoute next;
	
	// 1 constructor, use NULL for destination block
	public TrainRoute(int b, TrainRoute n){
		this.block = b;
		this.next = n;
	}
	

}
