
public class DefailtAlgorithm implements DrivingAlgorithm {

	public DrivingCmd calculate(DrivingCmd cmd) {
		cmd.steer = (cmd.angle - cmd.toMiddle/cmd.track_width) * (1/0.541052);
		cmd.accel = 0.2;
		cmd.brake = 0.0;
		cmd.backward = DrivingInterface.gear_type_forward;
		
		return cmd;
	}

}
