import java.util.Vector;
 
public class DrivingAlgorithmLauncher {

	static int tic = 0;
	
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
		// Set Geer and Steer
		if( data.dest_Speed < 0 ) {
			data.backward = DrivingInterface.gear_type_backward;			
			data.steer = -1*data.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);
		}
		else {
			data.backward = DrivingInterface.gear_type_forward;
			data.steer = data.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);			
		}

		// Set Accel and Brake
		if ( Math.abs(data.getKMhSpeed()) <  Math.abs(data.dest_Speed) )
		{ 
//			data.accel = Math.abs(data.dest_Speed)/300;
//			악셀		최종기어	최종속도
//			0.1	/	1	/	47
//			0.2	/	1	/	77
//			--------------------------------------
//			0.3	/	2	/	105
//			0.4	/	3	/	125
//			0.5	/	4	/	145
//			0.6	/	4	/	165
//			--------------------------------------
//			0.7	/	5	/	180
//			0.8	/	5	/	195
//			0.9	/	5	/	195
//			1	/	6	/	215
			if( data.getKMhSpeed() < 100) {
				data.accel = 0.4;
			} else if( data.getKMhSpeed() < 120) {
				data.accel = 0.5;
			} else if( data.getKMhSpeed() < 140) {
				data.accel = 0.6;
			} else if( data.getKMhSpeed() < 160) {
				data.accel = 0.7;
			} else if( data.getKMhSpeed() < 180) {
				data.accel = 0.8;
			} else if( data.getKMhSpeed() < 200) {
				data.accel = 0.9;
			} else {
				data.accel = 1;
			}
			data.brake = 0;
		} else if ( Math.abs(data.getKMhSpeed()) >  Math.abs(data.dest_Speed) ) {
			data.accel = 0;
			data.brake = (data.getKMhSpeed() - Math.abs(data.dest_Speed))/200;
		} else {
			data.accel = 0;
			data.brake = 0;
		}
		return data;
		//-->
	}
}
