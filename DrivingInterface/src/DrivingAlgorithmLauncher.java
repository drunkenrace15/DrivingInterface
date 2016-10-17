import java.util.Vector;

public class DrivingAlgorithmLauncher {

	private Vector algorithms;
	
	public DrivingAlgorithmLauncher() {
		algorithms = new Vector();
	}
	
	public void addAlgorithm(DrivingAlgorithm algorithm) {
		algorithms.add(algorithm);
	}
	
	public DrivingData doDrive(DrivingData data) {
		
		for (int i = 0; i < algorithms.size(); i++) {			
			if( !((DrivingAlgorithm)algorithms.get(i)).calculate(data) )
				break;
		}

		//<-- data -> cmd  
		data.steer = (data.angle + (data.dest_Middle - data.toMiddle)/data.track_width) * (1/0.541052);		
		if (data.speed*3 < data.dest_Speed)
		{ 
			data.accel = data.dest_Speed/300;//data.accel;
			data.brake = 0;
		} else {
			data.accel = 0;
			data.brake = (data.speed*3)/100;
		}
		data.backward = DrivingInterface.gear_type_forward;
		
		return data;
		//-->
	}
}
