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

		// Collision Check
		boolean isToForward     = data.dest_Speed > 0;
		boolean isToBackward    = data.dest_Speed < 0;
		boolean isToLeft 		= (data.dest_Middle - data.toMiddle) > 0;
		boolean isToRight		= (data.dest_Middle - data.toMiddle) < 0;

		// total cars
		for (int i=0; i<10 ;i++ ){
			
			if( data.isNearCar(i) ) {
				if( isToForward ) {
					if( isToLeft && data.isCarOnLeftFrontSide(i)) {
						data.dest_Middle = data.toMiddle;
						data.dest_Speed  = data.speed;
					} else if( isToRight && data.isCarOnRightFrontSide(i) ) {
						data.dest_Middle = data.toMiddle;
						data.dest_Speed  = data.speed;						
					} else if( data.isCarOnTheFront(i) ) {	
						data.dest_Middle = data.toMiddle;
						data.dest_Speed  = data.speed;					
					}
				} else if ( isToBackward ) {
					if( isToLeft && data.isCarOnLeftRearSide(i)) {
						data.dest_Middle = data.toMiddle;
						data.dest_Speed  = data.speed;
					} else if( isToRight && data.isCarOnRightRearSide(i) ) {
						data.dest_Middle = data.toMiddle;
						data.dest_Speed  = data.speed;						
					} else if( data.isCarOnTheRear(i) ) {	
						data.dest_Middle = data.toMiddle;
						data.dest_Speed  = data.speed;					
					}					
				}
			}
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
			data.accel = 0.3;//Math.abs(data.dest_Speed);
			data.brake = 0;
		} else {
			data.accel = 0;
			data.brake = 0.3;//data.getKMhSpeed();
		}
		return data;
		//-->
	}
}
