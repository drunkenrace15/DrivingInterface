
public class CorneringAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// OUT - IN - OUT
		
		data.dest_Speed = data.speed - 10;
		
		return true;
	}

}
