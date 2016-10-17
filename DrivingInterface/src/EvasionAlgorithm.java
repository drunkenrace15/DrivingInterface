
public class EvasionAlgorithm implements DrivingAlgorithm {

	public DrivingData calculate(DrivingData data) {
		// Evasion Steering
				
		if( data.dist_cars[0] < 10 ) {
			
			if( data.dist_cars[1] > data.toMiddle - 4 &&
			    data.dist_cars[1] < data.toMiddle + 4)
			{
				if( data.dist_cars[1] > data.toMiddle ) 			
					data.dest_Middle = data.dist_cars[1] - 4;
				else 
					data.dest_Middle = data.dist_cars[1] + 4;
			}
		} else {
			data.dest_Middle = 0;
		}
		
		return data;
	}

}
