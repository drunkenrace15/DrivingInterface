
public class CorneringAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// OUT - IN - OUT
		
		if ( data.track_dist_straight < 30 ) {
//			data.dest_Speed = 100;
			
			if( data.track_curve_type == DrivingInterface.curve_type_right) {
				data.dest_Middle = data.getRightMiddle(1);
			}
			if( data.track_curve_type == DrivingInterface.curve_type_left) {
				data.dest_Middle = data.getLeftMiddle(1);
			}
		} else if ( data.getKMhSpeed() > 100 && data.track_dist_straight < 40 ) {
//			data.dest_Speed = 120;

			if( data.track_curve_type == DrivingInterface.curve_type_right) {
				data.dest_Middle = data.getRightMiddle(1);
			}
			if( data.track_curve_type == DrivingInterface.curve_type_left) {
				data.dest_Middle = data.getLeftMiddle(1);
			}
		} else if ( data.getKMhSpeed() > 200 && data.track_dist_straight < 50 ) {
//			data.dest_Speed = 120;

			if( data.track_curve_type == DrivingInterface.curve_type_right) {
				data.dest_Middle = data.getRightMiddle(1);
			}
			if( data.track_curve_type == DrivingInterface.curve_type_left) {
				data.dest_Middle = data.getLeftMiddle(1);
			}
		} else {
			if(Math.abs(data.toMiddle) < 1) 
				data.dest_Middle = 0;
			else {
				if( data.toMiddle < 0 )
					data.dest_Middle = data.toMiddle + 0.5;
				else
					data.dest_Middle = data.toMiddle - 0.5;
			}
		}
		return true;
	}

}
