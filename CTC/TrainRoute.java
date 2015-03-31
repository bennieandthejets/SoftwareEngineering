package CTC;

public class TrainRoute {
	public int block;
	public TrainRoute next;
	
	// 1 constructor, use NULL for destination block
	public TrainRoute(int b, TrainRoute n){
		this.block = b;
		this.next = n;
	}
	

}
