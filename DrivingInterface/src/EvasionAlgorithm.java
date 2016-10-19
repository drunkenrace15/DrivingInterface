 
public class EvasionAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// Evasion Steering				
		if( data.dist_cars[0] < 3 ) {
			
			if( data.dist_cars[1] > data.toMiddle - 2 &&
			    data.dist_cars[1] < data.toMiddle + 2)
			{
				if( data.dist_cars[1] > data.toMiddle ) 			
					data.dest_Middle = data.dist_cars[1] - 4;
				else 
					data.dest_Middle = data.dist_cars[1] + 4;
				
				data.dest_Speed = 20;
			}			
		} else if( data.dist_cars[0] < 10 ) {
			
			if( data.dist_cars[1] > data.toMiddle - 4 &&
			    data.dist_cars[1] < data.toMiddle + 4)
			{
				if( data.dist_cars[1] > data.toMiddle ) 			
					data.dest_Middle = data.dist_cars[1] - 4;
				else 
					data.dest_Middle = data.dist_cars[1] + 4;

				data.dest_Speed = 80;
			}			
		} else if( data.speed > 25 && data.dist_cars[0] < 40 ) {
			
			if( data.dist_cars[1] > data.toMiddle - 4 &&
			    data.dist_cars[1] < data.toMiddle + 4)
			{
				if( data.dist_cars[1] > data.toMiddle ) 			
					data.dest_Middle = data.dist_cars[1] - 4;
				else 
					data.dest_Middle = data.dist_cars[1] + 4;
			}			
		} else {
			if(Math.abs(data.toMiddle) < 1) 
				data.dest_Middle = 0;
			else {
				if( data.toMiddle < 0 )
					data.dest_Middle = data.toMiddle + 1;
				else
					data.dest_Middle = data.toMiddle - 1;
			}
		}
		
		return true;
	}

}
