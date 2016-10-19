
public class CorneringAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// OUT - IN - OUT
		
		if ( data.track_dist_straight < 30 ) {
			data.dest_Speed = 100;
		} 
		
		if ( data.getKMhSpeed() > 100 && data.track_dist_straight < 40 ) {
			data.dest_Speed = 120;
		}		
		
		if ( data.getKMhSpeed() > 200 && data.track_dist_straight < 50 ) {
			data.dest_Speed = 120;
		}		
		
		return true;
	}

}
