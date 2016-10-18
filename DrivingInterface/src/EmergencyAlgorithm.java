
public class EmergencyAlgorithm implements DrivingAlgorithm {

	static int cnt = 0;
	
	public boolean calculate(DrivingData data) {
		
		
		//리타이어 임박 여부
		boolean isWarningRetire = false;
		
		//중앙에서 많이 이탈한 경우
		boolean isOutCourse = false;  
		
		//코너에서 STEER가 1보다 큰데 속도는 안높은경우
		boolean isEmergency = false;
		
		
//				System.out.println("toStraight : " + data.track_dist_straight);
		System.out.println("angle : " +data.angle);
		
		
		if(data.damage_max - data.damage < 10){
			isWarningRetire = true;
		}
		
		
		// 기본 코스이탈여부 확인 중앙에서 떨어짐 여부 + 속도 여부 + 회전각 여부
		if(Math.abs(data.toMiddle) > 8.5 && Math.abs(data.speed) < 8.15 && Math.abs(data.angle) > 2){
			isOutCourse = true;
		}
		
		
		// 비상 or 정상 구분하여 체크속도 분기처리 하였으나 기어가 계속 전진후진됨
//				if(Math.abs(data.toMiddle) > 8.5 && Math.abs(data.angle) > 2){
//					
//					if(DrivingData.IS_EMERGENCY){
//						if(Math.abs(data.speed) < 20.00){
//							isOutCourse = true;
//						}
//					}else{
//						if(Math.abs(data.speed) < 8.15){
//							isOutCourse = true;
//						}
//					}
//				}
		
		//후진할때 속도 마이너스 아래는 후진해서 계속 갈때 값
		//steer : 5.838021438190756
		//speed : -7.041156768798828
		
		//steer : 5.794016712426436
		//speed : 0.08803201466798782
		
		
		// 회전각은 큰데 + 전진 스피드가 매우 작을경우
		// 비상 상황으로 셋팅
		if(data.angle < 5 && data.angle > 1 && data.speed < 0.15 && data.speed > 0.0){
			isEmergency = true;
		}
		
		
		if(isEmergency){
			System.out.println("emergency!!");
			data.dest_Speed = -100.0;
			System.out.println("angle : " +data.angle);
			System.out.println("speed : " + data.speed);
//					data.steer = data.steer * -20;
		}else if(isOutCourse){
			
			//course out!!
//					middle : 9.00872802734375
//					speed : -4.814175605773926
			
			System.out.println("course out!!");
			System.out.println("middle : " + data.toMiddle);
			data.dest_Speed = -100.0;
			System.out.println("speed : " + data.speed);
//					data.angle = data.angle*10;
			
		}else{
			data.backward = DrivingInterface.gear_type_forward;
		}
		
		return true;
		
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
	}

}
