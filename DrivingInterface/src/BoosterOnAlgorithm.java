
public class BoosterOnAlgorithm implements DrivingAlgorithm {

	public DrivingData calculate(DrivingData data) {
		// Go!		
		if ( data.track_dist_straight >=100 ) {
			data.accel += 0.3;
		} else if ( data.track_dist_straight >=150 ) {
			data.accel = 1;
		}
		else {
			if (data.speed > 45) {
				data.brake = 0.2;
			} else if ( data.speed > 35 ) {
				data.brake = 0.1;
			}			
		}	
		
		return data;
	}

}
