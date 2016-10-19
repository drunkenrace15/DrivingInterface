 
public class BoosterOnAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// Go!				
		if ( data.track_dist_straight >=40 ) {
			data.dest_Speed = 300;
		}
		
		return true;
	}

}
