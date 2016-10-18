 
public class EmergencyAlgorithm implements DrivingAlgorithm {

	static int cnt = 0;
	
	public boolean calculate(DrivingData data) {
			
//		if( data.IS_EMERGENCY) {
//			if( data.isOutOfTrack() ) {
//				data.dest_Speed = -100;
//				if( data.toMiddle < 0 ) 
//					data.dest_Middle = data.getMostRightMiddle();
//				else
//					data.dest_Middle = data.getMostLeftMiddle();					
//			} else {
//				if( data.speed < 5 )
//					data.IS_EMERGENCY = false;
//				else {
//					data.dest_Speed = 0;
//					if( data.toMiddle < 0 ) 
//						data.dest_Middle = data.getMostRightMiddle();
//					else
//						data.dest_Middle = data.getMostLeftMiddle();
//				}
//					
//			}
//		} else {		
//			if( data.isOutOfTrack() ) {
//				++cnt;
//				
//				if( cnt > 5 ) {
//					data.dest_Speed = -100;
//					if( data.toMiddle < 0 ) 
//						data.dest_Middle = data.getMostRightMiddle();
//					else
//						data.dest_Middle = data.getMostLeftMiddle();
//					data.IS_EMERGENCY = true;
//				}
//			} else 				
//				cnt = 0;
//		}
//		return ! data.IS_EMERGENCY;
		return true;
	}

}
