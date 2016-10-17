
public class DefailtAlgorithm implements DrivingAlgorithm {

	public DrivingData calculate(DrivingData data) {
		data.steer = (data.angle - data.toMiddle/data.track_width) * (1/0.541052);
		data.accel = 0.2;
		data.brake = 0.0;
		data.backward = DrivingInterface.gear_type_forward;
		
		return data;
	}

}
