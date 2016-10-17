
public class DefailtAlgorithm implements DrivingAlgorithm {

	public DrivingData calculate(DrivingData data) {
		
		data.dest_Middle = data.toMiddle;
		data.dest_Speed  = 100;	
		
		return data;
	}

}
