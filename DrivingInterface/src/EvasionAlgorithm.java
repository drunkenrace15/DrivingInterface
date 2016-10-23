 
public class EvasionAlgorithm implements DrivingAlgorithm {

	
	public boolean calculate(DrivingData data) {
		// Evasion Steering	
		
		////////System.out.printlnln("Track width = " + data.track_width);
		double currentSpeed = data.getKMhSpeed();
		////////System.out.printlnln("Speed = " + currentSpeed);
		double sight = 0;
		double angle = 0;
		
		if(currentSpeed > 200){
			angle = data.track_width/5 * 0.6;
			sight = 100;
		} else if(currentSpeed > 150){
			angle = data.track_width/5 * 0.7 ;
			sight = 90;
		} else if(currentSpeed > 80){
			sight = 80;
			angle = data.track_width/5 * 0.8;
		} else {
			sight = 70;
			angle = data.track_width/5 * 0.9;
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
			 
			 
			if(secondDist - firstDist <= 12 && secondDist < 100){
				
				
				if(minMiddle > ((firstMiddle<secondMiddle)?firstMiddle:secondMiddle)){
					minMiddle = ((firstMiddle<secondMiddle)?firstMiddle:secondMiddle);
				}
				if(maxMiddle <  ((firstMiddle>secondMiddle)?firstMiddle:secondMiddle)){
					maxMiddle = ((firstMiddle>secondMiddle)?firstMiddle:secondMiddle);
				}
				
				//if( data.dist_cars[1] > data.toMiddle - 2 && data.dist_cars[1] < data.toMiddle + 2)
				
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
			if( data.dist_cars[i] < sight ) {
				
				if( data.dist_cars[i+1] > data.toMiddle - 2.3 &&
				    data.dist_cars[i+1] < data.toMiddle + 2.3){
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
					 //////System.out.printlnln("dist_cars = ["+i+"] = "+data.dist_cars[i] +" ["+(i+1)+"] = "+data.dist_cars[i+1] );
						
					break;
				}		
			}
		}
		*/
		
		
		//double rightGab = data.track_width/2 - Math.abs(maxMiddle);
		  double rightGab = Math.abs(-1 * data.track_width - (minMiddle) + (data.track_width/2));
		//double leftGab = data.track_width/2 - Math.abs(minMiddle);
		  double leftGab = Math.abs(data.track_width - (maxMiddle) - (data.track_width/2));
		
		  
		
		
		if(chkInFront){
			System.out.println("leftgab = " + leftGab +" rightgab = " + rightGab);		
			double minMiddleDist = data.track_width - (minMiddle) - (data.track_width/2);
			double maxMiddleDist = data.track_width - (maxMiddle) - (data.track_width/2);
			double toMiddleDist = data.track_width - (data.toMiddle) - (data.track_width/2);
			if(leftGab>rightGab) {  
				System.out.print("Left Zone check");
				System.out.print(" speed = " + currentSpeed);
							
				if( minMiddleDist-toMiddleDist	> maxMiddleDist-toMiddleDist)
				{	
					data.dest_Middle = data.toMiddle - angle; 	
					if(data.dest_Middle > minMiddle) data.dest_Middle = minMiddle - 1.5;
					if(data.dest_Middle < data.getMostRightMiddle()) data.dest_Middle = data.getMostRightMiddle();
					System.out.println(" but go right.");
				} else {
						
						
						data.dest_Middle = data.toMiddle + angle; 
						if(data.dest_Middle < maxMiddle) data.dest_Middle = maxMiddle + 1.5;
						if(data.dest_Middle > data.getMostLeftMiddle()) data.dest_Middle = data.getMostLeftMiddle();
						
						System.out.println(" and go left.");
						
				}
				
				
				
			} else {
				System.out.print("Right Zone check");
				System.out.print(" speed = " + currentSpeed);
				if( minMiddleDist-toMiddleDist	>= maxMiddleDist-toMiddleDist)
					{	
					data.dest_Middle = data.toMiddle  - angle;
					if(data.dest_Middle > minMiddle) data.dest_Middle = minMiddle - 1.5;
					if(data.dest_Middle < data.getMostRightMiddle()) data.dest_Middle = data.getMostRightMiddle();
					System.out.println(" and go right.");
					} else {
						
						
						data.dest_Middle = data.toMiddle  + angle;
						if(data.dest_Middle < maxMiddle) data.dest_Middle = maxMiddle + 1.5;
						if(data.dest_Middle > data.getMostLeftMiddle()) data.dest_Middle = data.getMostLeftMiddle();
						System.out.println(" but go left.");
					}
				
				
			}
		
			
			
			//if( data.dist_cars[0] < 5 && currentSpeed >=50 ) {data.dest_Speed = 20;}
			//if( data.dist_cars[0] < 30 && data.dest_Speed > 80) {data.dest_Speed =100;}
			
			System.out.print("minMiddle = " + minMiddle + " maxMiddle = " + maxMiddle);   
			System.out.println(" "+chkInFront+" toMiddle = " + data.toMiddle +" Dest_Middle = " + data.dest_Middle);
			
			for(int i=0;i<data.dist_cars.length/2-1;i+=2){
				if(data.dist_cars[i] <100){
				System.out.println("dist_cars = ["+i+"] = "+data.dist_cars[i] +" ["+(i+1)+"] = "+data.dist_cars[i+1] );
				}
			}			
		} else if(cntCar > 0){
			data.dest_Middle = data.toMiddle;
		}
				
		return true;
	}
	
	public boolean calculate_sun(DrivingData data) {
		// Evasion Steering
		
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
