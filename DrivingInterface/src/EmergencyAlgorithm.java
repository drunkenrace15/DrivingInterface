 
public class EmergencyAlgorithm implements DrivingAlgorithm {

	static int cnt = 0;
	static int block_cnt = 0;
	static int emergency_speed = 0;
	
	public boolean calculate(DrivingData data) {
			
		if( data.IS_EMERGENCY) {
			if( data.angle > 1 && data.isOutOfTrack() ) {
								
				if( data.toMiddle < 0 ) {

					data.dest_Speed = emergency_speed;	
					data.dest_Middle = data.getMostRightMiddle()/2;
				}
				else{

					data.dest_Speed = emergency_speed;
					data.dest_Middle = data.getMostLeftMiddle()/2;
				}
			} else {
				data.IS_EMERGENCY = false;					
			}
		} else {		
			if( data.isOutOfTrack() ) {
				++cnt;
				
				if( cnt > 5 || data.toMiddle != 0.0) {
					if( data.toMiddle < 0 ) {

						if( data.angle > Math.PI*1/4 && data.angle < Math.PI*3/4 )
							emergency_speed = -50;
						else
							emergency_speed = 50;	
						data.dest_Middle = data.getMostRightMiddle()/2;
					}
					else{

						if( data.angle > Math.PI*-3/4 && data.angle < Math.PI*-1/4 )
							emergency_speed = -50;
						else
							emergency_speed = 50;	
						data.dest_Middle = data.getMostLeftMiddle()/2;
					}
					
					data.IS_EMERGENCY = true;					
				}
			} else{				
				cnt = 0;
			}
		}
		
		if(data.getKMhSpeed() < 10){
			blockEscape(data);
		}
		
		return ! data.IS_EMERGENCY;
	}
	
	
	void blockEscape(DrivingData data){
		
		System.out.println("block!!! : " + block_cnt);
		
		if(block_cnt > 25){			

			data.dest_Speed = -300;
			if( data.toMiddle < 0 ) 
				data.dest_Middle = data.getMostRightMiddle()/2;
			else
				data.dest_Middle = data.getMostLeftMiddle()/2;
			
			data.IS_EMERGENCY = true;
			block_cnt = 0;
			
		}else{
			++block_cnt;
		}
		
	}

}

