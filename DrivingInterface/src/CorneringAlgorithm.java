
public class CorneringAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// OUT - IN - OUT
		double angle_now =  ((data.angle - data.track_Front_angles[0]) * 180/Math.PI);
		double angle_base = ((data.track_current_angle - data.track_Front_angles[0]) * 180/Math.PI);
		double angle_c[] = new double[20];
		angle_c[0] = ((data.track_Front_angles[0] - data.track_Front_angles[1]) * 180/Math.PI);
		angle_c[1] = ((data.track_Front_angles[1] - data.track_Front_angles[2]) * 180/Math.PI);
		angle_c[2] = ((data.track_Front_angles[2] - data.track_Front_angles[3]) * 180/Math.PI);
		angle_c[3] = ((data.track_Front_angles[3] - data.track_Front_angles[4]) * 180/Math.PI);
		angle_c[4] = ((data.track_Front_angles[4] - data.track_Front_angles[5]) * 180/Math.PI);
		angle_c[5] = ((data.track_Front_angles[5] - data.track_Front_angles[6]) * 180/Math.PI);
		
		double mm = 0.4;
		if(data.track_width == 12 ){//레벨 5짜리 맵
			mm = 0.2;
		}else if(data.track_width == 13 ){//레벨 5짜리 맵
			mm = 0.3;
		}
		double m_angle = 0.1;
		
//		System.out.println("angle_base "+angle_base + ", data.track_dist_straight"+data.track_dist_straight+" data.speed" + data.getKMhSpeed());

		
		if (data.track_dist_straight > 100  && data.track_dist_straight <200) {	
			if(data.dest_Speed >= 120){
				data.dest_Speed = data.dest_Speed -3;
			};
//			System.out.println("OIO 시도 : "+ angle_now+ "///// angle_base"+ angle_base+ "//////" +"data.speed" + data.getKMhSpeed());
//			
//			
//			if( data.track_curve_type == DrivingInterface.curve_type_right) { 
//				data.dest_Middle = data.getLeftMiddle(0.3); 
//			} 
//			if( data.track_curve_type == DrivingInterface.curve_type_left) { 
//				data.dest_Middle = data.getRightMiddle(0.3); 
//			} 
//		data.dest_Speed = data.dest_Speed -3;

		
		}else if(data.track_dist_straight > 0 && data.track_dist_straight < 100 ){
//			System.out.println("55555555555555555555555555555555555555");
			if( data.toMiddle < 0 )
				data.dest_Middle = data.getLeftMiddle(0.1); 
			else
				data.dest_Middle = data.getRightMiddle(0.1); 
		}else if(Math.abs(angle_base) > 1){// 현재 회전중이라면
			
			System.out.println(angle_c[0]+", "+angle_c[1] + " " + angle_c[2] + " " + angle_c[3] +  " " + angle_c[4]
					+ " " + angle_c[5] +  " " + angle_c[6]);

			//앞 트랙과 그 앞앞 트랙의 각도에 따른 속도 조절 부여
			if(Math.abs(angle_base) >9){
				data.dest_Speed = data.dest_Speed -4;
				m_angle = 0.8;
			}else if(Math.abs(angle_base) >7 || Math.abs(angle_c[3]) >7 ){
				data.dest_Speed = data.dest_Speed -3;
				m_angle = 0.7;
			}else if(Math.abs(angle_base) >5){
				data.dest_Speed = data.dest_Speed -2;
				m_angle = 0.5;
			}else if(Math.abs(angle_base) >3){
				data.dest_Speed = data.dest_Speed -1;
				m_angle = 0.3;
			}else if(Math.abs(angle_base) >0 && data.getKMhSpeed() >120){
				data.dest_Speed = data.dest_Speed -1;
				m_angle = 0.4;
			}		
			
			
			if(angle_base > 3 && angle_base <angle_c[0]){
				//각도가 더 꺽여지는 구간이라면?
				System.out.println("급 회전 시작!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				data.dest_Speed = data.dest_Speed -3;
			}
			
			if(data.track_curve_type == DrivingInterface.curve_type_right){				
				System.out.println("우우회전중 anlge_now : "+ angle_now+ "///// angle_base"+ angle_base);
				data.dest_Middle = -m_angle * Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);

				System.out.println("우회전 ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+"//// m_angle : "+m_angle);
				
				double steer22 = data.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);		
				System.out.print(steer22);
				System.out.print("= " + data.angle);
				System.out.println("+"+(data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052));	
				
				if(angle_c[0]== 0 && angle_c[1]== 0 && angle_c[2]  == 0&&angle_c[3] == 0){
					System.out.println("급 회전 시작!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
					data.dest_Speed = data.dest_Speed -3;
				}
//				if(angle_now < -88){
//					//System.out.println("급 우회전 88도"+angle_now);
//					data.dest_Speed = 60;
//				}
//				else if(angle_now < -60){
//					//System.out.println("급 우회전 60도"+angle_now);
//					data.dest_Speed = 70;
//				}
				
			}else if( data.track_curve_type == DrivingInterface.curve_type_left) {
				
//				System.out.println("★좌좌회전중 anlge_now : "+ angle_now+ "///// angle_base"+ angle_base+ "//////" +"data.speed" + data.getKMhSpeed());
				data.dest_Middle = m_angle * Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
				System.out.println("★좌회전ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+ "//// m_angle : "+m_angle);
				
				double steer22 = data.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);		
				System.out.print(steer22+"////"+ data.steer);
				System.out.print("= " + data.angle);
				System.out.println("+"+(data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052));	
//				if(angle_now < -88){
//					//System.out.println("급 좌회전 80도"+angle_now);
//					data.dest_Speed = 60;
//				}
//				else if(angle_now < -60){
//					//System.out.println("급 좌회전 60도"+angle_now);
//					data.dest_Speed = 70;
//				}
			}
		}else if(data.track_current_angle == data.angle){
//			System.out.println("현재 트랙의 각도와 나의 각도가 같으면");
			if(Math.abs(data.toMiddle) < 3) 
				data.dest_Middle = 0;
			else {
				if( data.toMiddle < 0 )
					data.dest_Middle = data.toMiddle + 0.5;
				else
					data.dest_Middle = data.toMiddle - 0.5;
			}
		}else {//코너링 나왔을 때 가운데로 가게하는 소스
//			System.out.println("코너링 나왔을 때 가운데로 가게하는 소스");
			if(Math.abs(data.toMiddle) < 1) 
				data.dest_Middle = 0;
			else {
				if( data.toMiddle < 0 )
					data.dest_Middle = data.toMiddle + 0.5;
				else
					data.dest_Middle = data.toMiddle - 0.5;
			}
			if ( data.track_dist_straight >100) {
//				System.out.println("150미터 이상 남았을 때 angle_c[0] : "+angle_c[0]);
				if(Math.abs(angle_c[0]) >9){
					data.dest_Speed = data.dest_Speed -3;
				}else if(Math.abs(angle_c[0]) >7){
					data.dest_Speed = data.dest_Speed -2;
				}else if(Math.abs(angle_c[0]) >5){
					data.dest_Speed = data.dest_Speed -1;
				}else if(Math.abs(angle_c[0]) >3){
					data.dest_Speed = data.dest_Speed -0.5;
				}
				
//				if( data.track_curve_type == DrivingInterface.curve_type_right) {
//					data.dest_Middle = data.getRightMiddle(mm);
//				}
//				if( data.track_curve_type == DrivingInterface.curve_type_left) {
//					data.dest_Middle = data.getLeftMiddle(mm);
//				}
			}
		}
		return true;
	}
}
