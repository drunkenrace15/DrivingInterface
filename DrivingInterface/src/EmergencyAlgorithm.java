 
public class EmergencyAlgorithm implements DrivingAlgorithm {

	static int cnt = 0;
	static int block_cnt = 0;
	
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
				
				if(data.getKMhSpeed() < 10){
					blockEscape(data);
				}else{
					
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
					
			}
			
//			System.out.println("1踰�");
		} else {		
			if( data.isOutOfTrack() ) {
				++cnt;
				
				if( cnt > 5 || data.toMiddle != 0.0) {
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
					
					//
					data.IS_EMERGENCY = true;
					
				}
				
			} else if(data.getKMhSpeed() < 10){
				
				blockEscape(data);
				
			}else{
				
				cnt = 0;
//				System.out.println("2踰�");
			}
//			System.out.println("3踰�");
		}
		
		if(data.getKMhSpeed() < 10){
			blockEscape(data);
		}
		
		return ! data.IS_EMERGENCY;
//		return true;
	}
	
	
	void blockEscape(DrivingData data){
		
		System.out.println("block!!! : " + block_cnt);
		
		if(block_cnt > 25){
			
			data.dest_Speed = -300;
			if( data.toMiddle < 0 ) 
				data.dest_Middle = data.getMostRightMiddle();
			else
				data.dest_Middle = data.getMostLeftMiddle();
			
			data.IS_EMERGENCY = true;
			block_cnt = 0;
			
		}else{
			++block_cnt;
		}
		
	}

}

