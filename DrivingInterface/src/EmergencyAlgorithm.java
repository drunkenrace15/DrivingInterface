 
public class EmergencyAlgorithm implements DrivingAlgorithm {

	static int cnt = 0;
	
	public boolean calculate(DrivingData data) {
			
		if( data.IS_EMERGENCY) {
			if( data.isOutOfTrack() ) {
								
				if( data.toMiddle < 0 ) {

					if( data.angle > Math.PI*1/4 && data.angle < Math.PI*3/4 )
						data.dest_Speed = -50;
					else
						data.dest_Speed = 50;	
					data.dest_Middle = data.getMostRightMiddle();
				}
				else{

					if( data.angle > Math.PI*-3/4 && data.angle < Math.PI*-1/4 )
						data.dest_Speed = -50;
					else
						data.dest_Speed = 50;	
					data.dest_Middle = data.getMostLeftMiddle();					
				}
			} else {
				if( data.getKMhSpeed() < 30 )
					data.IS_EMERGENCY = false;
				else {
					data.dest_Speed = 0;
					if( data.toMiddle < 0 ) 
						data.dest_Middle = data.getMostRightMiddle();
					else
						data.dest_Middle = data.getMostLeftMiddle();
				}
					
			}
		} else {		
			if( data.isOutOfTrack() ) {
				++cnt;
				
				if( cnt > 5 ) {
					data.dest_Speed = -100;
					if( data.toMiddle < 0 ) 
						data.dest_Middle = data.getMostRightMiddle();
					else
						data.dest_Middle = data.getMostLeftMiddle();
					data.IS_EMERGENCY = true;
				}
			} else 				
				cnt = 0;
		}
		return ! data.IS_EMERGENCY;
//		return true;
	}

}
