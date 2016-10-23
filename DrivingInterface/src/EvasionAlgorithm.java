 
public class EvasionAlgorithm implements DrivingAlgorithm {

	
	public boolean calculate(DrivingData data) {
		// Evasion Steering	
		
		////////System.out.printlnln("Track width = " + data.track_width);
		double currentSpeed = data.getKMhSpeed();
		////////System.out.printlnln("Speed = " + currentSpeed);
		double sight = 0;
		double angle = 0;
		
		if(currentSpeed > 200){
			//angle = data.track_width/5 * 0.6;
			angle = 2 * 0.3;
			sight = 100;
		} else if(currentSpeed > 150){
			//angle = data.track_width/5 * 0.7 ;
			angle = 2 * 0.8;
			sight = 90;
		} else if(currentSpeed > 80){
			sight = 80;
			//angle = data.track_width/5 * 0.8;
			angle = 2 * 1;
		} else {
			sight = 70;
			//angle = data.track_width/5 * 0.9;
			angle = 2*1.3;
		}
		
		if(data.track_dist_straight==0){
			angle = angle * 0.7;
		}
		
		
		double minMiddle =9;
		double maxMiddle =-9;
		double minTrack = -1 * data.track_width/2;
      	double maxTrack =  data.track_width/2;
      	int cntCar =0;
      	boolean chkInFront = false;
		boolean chkInBack = false;
		for(int i=0; i < data.dist_cars.length/2 - 3 ;i+=2){
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
				
				if( data.dist_cars[i+1] > data.toMiddle - 2 &&
				    data.dist_cars[i+1] < data.toMiddle + 2){
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
			double minMiddleDist = data.track_width - (minMiddle) - (data.track_width/2);
			double maxMiddleDist = data.track_width - (maxMiddle) - (data.track_width/2);
			double toMiddleDist = data.track_width - (data.toMiddle) - (data.track_width/2);
			if(leftGab>rightGab) {  
							
				if( minMiddleDist-toMiddleDist	> maxMiddleDist-toMiddleDist)
				{	
					data.dest_Middle = data.toMiddle - angle; 	
					if(data.dest_Middle > minMiddle) data.dest_Middle = minMiddle - 1.5;
					if(data.dest_Middle < data.getMostRightMiddle()) data.dest_Middle = data.getMostRightMiddle();
					if(data.dest_Middle - minMiddle >= -2){ 
						
						data.dest_Middle = 0;
					}
					
				} else {
						
						
						data.dest_Middle = data.toMiddle + angle; 
						if(data.dest_Middle < maxMiddle) data.dest_Middle = maxMiddle + 1.5;
						if(data.dest_Middle > data.getMostLeftMiddle()) data.dest_Middle = data.getMostLeftMiddle();
						if(data.dest_Middle - maxMiddle <= 2){
							
							data.dest_Middle = 0;
						}
						
						
				}
				
				
				
			} else {
				if( minMiddleDist-toMiddleDist	>= maxMiddleDist-toMiddleDist) {
					
					data.dest_Middle = data.toMiddle  - angle;
					if(data.dest_Middle > minMiddle) data.dest_Middle = minMiddle - 1.5;
					if(data.dest_Middle < data.getMostRightMiddle()) data.dest_Middle = data.getMostRightMiddle();
					if(data.dest_Middle - minMiddle >= -2){ 
						data.dest_Middle = 0;
					} 
					
				} else {
					
					data.dest_Middle = data.toMiddle  + angle;
					if(data.dest_Middle < maxMiddle) data.dest_Middle = maxMiddle + 1.5;
					if(data.dest_Middle > data.getMostLeftMiddle()) data.dest_Middle = data.getMostLeftMiddle();
					if(data.dest_Middle - maxMiddle <= 2){ 
						
						data.dest_Middle = 0;
					} 
					
				}
				
				
			}
			
			//if( data.dist_cars[0] < 5 && currentSpeed >=50 ) {data.dest_Speed = 20;}
			if( data.dist_cars[0] < 50 && currentSpeed > 100) {data.dest_Speed *= 0.8;}
			
						
		} else if(cntCar > 0){
			data.dest_Middle = data.toMiddle;
		}
		
		return true;
	}
}

