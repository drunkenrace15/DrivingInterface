
public class DefailtAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {

		data.dest_Middle = data.getMostRightMiddle(); //data.toMiddle;
		data.dest_Speed  = data.DEFAULT_SPEED;	
		
		return true;
	}

}
