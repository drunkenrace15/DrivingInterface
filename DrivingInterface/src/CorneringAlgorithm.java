
public class CorneringAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// OUT - IN - OUT
		double angle_now =  ((data.angle - data.track_Front_angles[0]) * 180/Math.PI);
		double angle_base = ((data.track_Front_angles[0] - data.track_Front_angles[1]) * 180/Math.PI);
		double angle_c[] = new double[20];
		double mm = 0.5;
		for(int i=0; i<19; i++){
			angle_c[i] = ((data.track_Front_angles[i] - data.track_Front_angles[i+1]) * 180/Math.PI);
		}
//		double angle_c1 = ((data.track_Front_angles[1] - data.track_Front_angles[2]) * 180/Math.PI);
//		double angle_c2 = ((data.track_Front_angles[2] - data.track_Front_angles[3]) * 180/Math.PI);
//		double angle_c3 = ((data.track_Front_angles[3] - data.track_Front_angles[4]) * 180/Math.PI);
//		double angle_c4 = ((data.track_Front_angles[4] - data.track_Front_angles[5]) * 180/Math.PI);
		
		
		//System.out.println(angle_base + ", "+data.track_dist_straight);
		//코너링 전에 우회전일경우 우측으로 붙고, 좌회전일 경우 좌측으로 붙고
		
		
		if ( data.track_dist_straight >15 && data.track_dist_straight < 100  ) {		
//			//System.out.println("11111111111111111111111111111111111111111111");
			if(data.track_width == 12 || data.speed >=180 ){//레벨 5짜리 맵
				mm = 0.2;
			}else if(data.track_width == 13 || data.speed >=150){//레벨 5짜리 맵
				mm = 0.3;
			}
			if( data.track_curve_type == DrivingInterface.curve_type_right) {
				data.dest_Middle = data.getLeftMiddle(mm);
			}
			if( data.track_curve_type == DrivingInterface.curve_type_left) {
				data.dest_Middle = data.getRightMiddle(mm);
			}
			//유턴일때를 정해야 될듯????????????
			
			//앞 트랙과 그 앞앞 트랙의 각도에 따른 속도 조절 부여
			if(Math.abs(angle_base) >3){
				data.dest_Speed = 110;
			}else if(Math.abs(angle_base) >5){
				data.dest_Speed = 90;
			}else if(Math.abs(angle_base) >7){
				data.dest_Speed = 70;
			}else if(Math.abs(angle_base) >9){
				data.dest_Speed = 50;
			}		
//			//System.out.println("data.dest_Speed" + data.dest_Speed);
		
		}
		else if(data.track_dist_straight ==0){// 현재 회전중이라면
			//앞 트랙과 그 앞앞 트랙의 각도에 따른 속도 조절 부여
			if(Math.abs(angle_base) >3){
				data.dest_Speed = 110;
			}else if(Math.abs(angle_base) >5){
				data.dest_Speed = 90;
			}else if(Math.abs(angle_base) >7){
				data.dest_Speed = 70;
			}else if(Math.abs(angle_base) >9){
				data.dest_Speed = 50;
			}	
			
			if(data.track_curve_type == DrivingInterface.curve_type_right){
				//System.out.println("우우회전중 anlge_now : "+ angle_now+ "///// angle_base"+ angle_base+ "//////" +"data.steer" + data.steer);
				data.dest_Middle = -1 * Math.abs(Math.sin(Math.PI/2 - (data.track_Front_angles[0] - data.track_Front_angles[1])) * data.toMiddle);
//				//System.out.println("우회전dest_Middle : "+ data.dest_Middle);
				
				if(angle_now < -88){
					//System.out.println("급 우회전 88도"+angle_now);
					data.dest_Speed = 60;
				}
//				else if(angle_now < -60){
//					//System.out.println("급 우회전 60도"+angle_now);
//					data.dest_Speed = 70;
//				}
				
			}else if( data.track_curve_type == DrivingInterface.curve_type_left) {
				//System.out.println("좌좌회전중 anlge_now : "+ angle_now+ "///// angle_base"+ angle_base+ "//////" +"data.steer" + data.steer);
				data.dest_Middle = 1 * Math.abs(Math.sin(Math.PI/2 - (data.track_Front_angles[0] - data.track_Front_angles[1])) * data.toMiddle);
//				//System.out.println("좌회전dest_Middle : "+ data.dest_Middle);
				if(angle_now < -88){
					//System.out.println("급 좌회전 80도"+angle_now);
					data.dest_Speed = 60;
				}
//				else if(angle_now < -60){
//					//System.out.println("급 좌회전 60도"+angle_now);
//					data.dest_Speed = 70;
//				}
			}
		} 
//		else if(Math.abs(angle_base) == 0 && Math.abs(data.angle) > 0.05){
//			//System.out.println("우회전으로 90도 이상 꺽일 떄 anlge_now : "+ angle_now+ "//////" +"data.steer" + data.angle);
//			//앞 트랙과 그 앞앞 트랙의 각도에 따른 속도 조절 부여
//			if(Math.abs(angle_now) >3){
//				data.dest_Speed = 110;
//			}else if(Math.abs(angle_now) >5){
//				data.dest_Speed = 90;
//			}else if(Math.abs(angle_now) >7){
//				data.dest_Speed = 70;
//			}else if(Math.abs(angle_now) >9){
//				data.dest_Speed = 50;
//			}	
//			if( data.toMiddle < 0 )
//				data.dest_Middle = data.toMiddle + 0.5;
//			else
//				data.dest_Middle = data.toMiddle - 0.5;
//			
//			//System.out.println("우회전으로 90도 이상 꺽일 떄 destMiddle : "+ data.dest_Middle);
//		}
//		
		else {//코너링 나왔을 때 가운데로 가게하는 소스
			//System.out.println("444444444444444444444444444444444444444444444444444444");
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
		
		
//		if ( data.track_dist_straight < 100 ) {			
//			if( data.track_curve_type == DrivingInterface.curve_type_right) {
//				data.dest_Middle = data.getRightMiddle(0.5);
//			}
//			if( data.track_curve_type == DrivingInterface.curve_type_left) {
//				data.dest_Middle = data.getLeftMiddle(0.5);
//			}
//		} else if(Math.abs(data.track_current_angle - data.track_Front_angles[0]) > 0.001 ) {
//			if( data.track_current_angle - data.track_Front_angles[0] > 0 ) {
//				data.dest_Middle = data.getRightMiddle(0.5);
//			}
//			else {
//				data.dest_Middle = data.getLeftMiddle(0.5);
//			}
//		} else {
//			if(Math.abs(data.toMiddle) < 1) 
//				data.dest_Middle = 0;
//			else {
//				if( data.toMiddle < 0 )
//					data.dest_Middle = data.toMiddle + 0.5;
//				else
//					data.dest_Middle = data.toMiddle - 0.5;
//			}
//		}
//		return true;
	}

}
