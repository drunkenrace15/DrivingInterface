
public class BoosterOnAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// Go!				
		if ( data.track_dist_straight >=50 ) {
			data.dest_Speed = 120;
		} else if ( data.track_dist_straight >=100 ) {
			data.dest_Speed = 160;
		} else if ( data.track_dist_straight >=100 ) {
			data.dest_Speed = 200;
		} else if ( data.track_dist_straight >=150 ) {
			data.dest_Speed = 230;
		}
		
		return true;
	}

}
