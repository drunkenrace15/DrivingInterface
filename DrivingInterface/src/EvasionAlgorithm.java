 
public class EvasionAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// Evasion Steering	
		
		//////System.out.printlnln("Track width = " + data.track_width);
		double currentSpeed = data.speed * 3.6;
		//////System.out.printlnln("Speed = " + currentSpeed);
		double angle = 0;
		if(currentSpeed > 140){
			angle = 2;
		} else if(currentSpeed > 120){
			angle = 3;
		} else if(currentSpeed > 70){
			angle = 3.5;
		} else {
			angle = 4;
		}
		
		double minMiddle =9;
		double maxMiddle =-9;
		double minTrack = -1 * data.track_width/2;
      	double maxTrack =  data.track_width/2;
      	int cntCar =0;
      	boolean chkInFront = false;
		boolean chkInBack = false;
		for(int i=0; i < data.dist_cars.length - 3 ;i+=2){
			 double firstDist= data.dist_cars[i];
			 double firstMiddle = data.dist_cars[i+1];
			 double secondDist = data.dist_cars[i+2];
			 double secondMiddle = data.dist_cars[i+3];
			 
			 
			if(secondDist - firstDist <= 7){
				
				
				if(minMiddle > ((firstMiddle<secondMiddle)?firstMiddle:secondMiddle)){
					minMiddle = ((firstMiddle<secondMiddle)?firstMiddle:secondMiddle);
				}
				if(maxMiddle <  ((firstMiddle>secondMiddle)?firstMiddle:secondMiddle)){
					maxMiddle = ((firstMiddle>secondMiddle)?firstMiddle:secondMiddle);
				}
				
				if( data.dist_cars[1] > data.toMiddle - 2 && data.dist_cars[1] < data.toMiddle + 2)
				
				cntCar ++;
				
			} else {
				break;
			}
		}
		if(minMiddle > maxMiddle && data.dist_cars[0] < 100){
			minMiddle = data.dist_cars[1];
			maxMiddle = data.dist_cars[1];
		}
		
		
		//if(between == 0) between = 1;
		
		for(int i=0;i<data.dist_cars.length/2-1;i+=2){
			if( data.dist_cars[i] <= 60 ) {
				
				if( data.dist_cars[i+1] > data.toMiddle - 2.2 &&
				    data.dist_cars[i+1] < data.toMiddle + 2.2){
					chkInFront = true;
					break;
				}		
			}
		}
		/*
		for(int i=data.dist_cars.length/2;i<data.dist_cars.length-1;i+=2){
			if( data.dist_cars[i] >= -10 ) {
				
				if( data.dist_cars[i+1] > data.toMiddle - 2 &&
				    data.dist_cars[i+1] < data.toMiddle + 2){
					 chkInBack= true;
					 ////System.out.printlnln("dist_cars = ["+i+"] = "+data.dist_cars[i] +" ["+(i+1)+"] = "+data.dist_cars[i+1] );
						
					break;
				}		
			}
		}
		*/
		
		
		double rightGab = data.track_width/2 - maxMiddle; // 대부분양수
		double leftGab = data.track_width/2 + minMiddle; // 대부분 음수
		
		
		
		
		
		if(chkInFront){
					
			if(leftGab>rightGab) {  //왼쪽이 더 공간이 클경우
				//System.out.println("Left Zone check");
				if( minMiddle-data.toMiddle	> maxMiddle-data.toMiddle)
				{	
						data.dest_Middle = data.toMiddle + angle; //왼쪽으로
						if(data.dest_Middle < maxMiddle) data.dest_Middle = maxMiddle + angle;
						////System.out.printlnln(" and go left.");
				} else {
						data.dest_Middle = data.toMiddle - angle; //오른쪽으로	
						if(data.dest_Middle > minMiddle) data.dest_Middle = minMiddle - angle;
						////System.out.printlnln(" but go right.");
				}
				
				
				
			} else {
				//System.out.println("Right Zone check");
				if( minMiddle-data.toMiddle	> maxMiddle-data.toMiddle)
					{	
						data.dest_Middle = data.toMiddle  + angle;
						if(data.dest_Middle < maxMiddle) data.dest_Middle = maxMiddle + angle;
						////System.out.printlnln(" but go left.");
					} else {
						data.dest_Middle = data.toMiddle  - angle;
						if(data.dest_Middle > minMiddle) data.dest_Middle = minMiddle - angle;
						////System.out.printlnln(" and go right.");
					}
				
				
			}
		
			
			
			//if( data.dist_cars[0] < 5 && currentSpeed >=50 ) {data.dest_Speed = 20;}
			if( data.dist_cars[0] < 50 && data.dest_Speed > 100) {data.dest_Speed = 80;}
			
			//System.out.println("minMiddle = " + minMiddle + " maxMiddle = " + maxMiddle);   
			////System.out.printlnln(" "+chkInFront+" toMiddle = " + data.toMiddle +" Dest_Middle = " + data.dest_Middle);
			
			for(int i=0;i<data.dist_cars.length/2-1;i+=2){
				if(data.dist_cars[i] <100){
				////System.out.printlnln("dist_cars = ["+i+"] = "+data.dist_cars[i] +" ["+(i+1)+"] = "+data.dist_cars[i+1] );
				}
			}			
		} 
		
		
//		if( data.dist_cars[0] < 3 ) {
//			
//			if( data.dist_cars[1] > data.toMiddle - 2 &&
//			    data.dist_cars[1] < data.toMiddle + 2)
//			{
//				if( data.dist_cars[1] > data.toMiddle ) 			
//					data.dest_Middle = data.dist_cars[1] - 4;
//				else 
//					data.dest_Middle = data.dist_cars[1] + 4;
//				
//				data.dest_Speed = 20;
//			}			
//		} else if( data.dist_cars[0] < 10 ) {
//			
//			if( data.dist_cars[1] > data.toMiddle - 2 &&
//			    data.dist_cars[1] < data.toMiddle + 2)
//			{
//				if( data.dist_cars[1] > data.toMiddle ) 			
//					data.dest_Middle = data.dist_cars[1] - 4;
//				else 
//					data.dest_Middle = data.dist_cars[1] + 4;
//
//				data.dest_Speed = 80;
//			}			
//		} else if( data.speed > 25 && data.dist_cars[0] < 40 ) {
//			
//			if( data.dist_cars[1] > data.toMiddle - 2 &&
//			    data.dist_cars[1] < data.toMiddle + 2)
//			{
//				if( data.dist_cars[1] > data.toMiddle ) 			
//					data.dest_Middle = data.dist_cars[1] - 4;
//				else 
//					data.dest_Middle = data.dist_cars[1] + 4;
//			}			
//		} 
				
		return true;
	}

}
