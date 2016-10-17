
public class EvasionAlgorithm implements DrivingAlgorithm {

	public DrivingData calculate(DrivingData data) {
		// Evasion Steering
				
		if( data.dist_cars[0] < 10 ) {
						
			if ( data.dist_cars[1] < 0 && data.toMiddle < 0) {
				if ( Math.abs(data.dist_cars[1]-data.toMiddle) < 4 ) {
					data.steer = 0.3;
				}
			} else if ( data.dist_cars[1] > 0 && data.toMiddle > 0) {
				if ( Math.abs(data.dist_cars[1]-data.toMiddle) < 4 ) {
					data.steer = 0.3;
				}
			} else if( Math.abs(data.dist_cars[1]) + Math.abs(data.toMiddle) < 5 ) {
				data.steer = 0.3;
			}
//			double plusDegree = Math.atan2(Math.abs(cmd.toMiddle - cmd.dist_cars[1]), cmd.dist_cars[0]) - Math.PI/2;
//			
//			//왼쪽으로 피할것인가 오른쪽으로 피할것인가
//			if( plusDegree > 0 && Math.abs(plusDegree * 180/Math.PI) < 60 ) { 
//				cmd.steer -= (plusDegree);
//			}
//			else if(plusDegree < 0 && plusDegree * 180/Math.PI > -60) {
//				cmd.steer += (plusDegree);
//			} 		
		}
		
		System.out.println(data.steer);
		return data;
	}

}
