
public class DefailtAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {

		data.dest_Middle = data.toMiddle;
		data.dest_Speed  = 100;// data.DEFAULT_SPEED;			
		return true;
	}

}
