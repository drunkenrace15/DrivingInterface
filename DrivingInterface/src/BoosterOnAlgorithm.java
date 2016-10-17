
public class BoosterOnAlgorithm implements DrivingAlgorithm {

	public DrivingData calculate(DrivingData data) {
		// Go!		
		if ( data.track_dist_straight >=100 ) {
			data.dest_Speed = 150;
		} else if ( data.track_dist_straight >=150 ) {
			data.dest_Speed = 230;
		}
		
		return data;
	}

}
