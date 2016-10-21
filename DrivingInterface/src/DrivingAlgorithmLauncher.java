import java.util.Vector;
 
public class DrivingAlgorithmLauncher {

	static int tic = 0;
	
	private Vector algorithms;
		
	public DrivingAlgorithmLauncher() {
		algorithms = new Vector();
	}
	
	public void addAlgorithm(DrivingAlgorithm algorithm) {
		algorithms.add(algorithm);
	}
		
	public DrivingData doDrive(DrivingData data) {
		
		for (int i = 0; i < algorithms.size(); i++) {			
			if( !((DrivingAlgorithm)algorithms.get(i)).calculate(data) )
				break;
		}

//		// Collision Check
//		boolean isToForward     = data.dest_Speed > 0;
//		boolean isToBackward    = data.dest_Speed < 0;
//		boolean isToLeft 		= (data.dest_Middle - data.toMiddle) > 0;
//		boolean isToRight		= (data.dest_Middle - data.toMiddle) < 0;
//
//		// total cars
//		for (int i=0; i<10 ;i++ ){
//			if( data.isNearCar(i) ) {
//				if( isToForward ) {
//					if( isToLeft && data.isCarOnLeftFrontSide(i)) {
//						data.dest_Middle = data.toMiddle;
////						data.dest_Speed  = data.speed;
//					} else if( isToRight && data.isCarOnRightFrontSide(i) ) {
//						data.dest_Middle = data.toMiddle;
////						data.dest_Speed  = data.speed;						
//					} else if( data.isCarOnTheFront(i) ) {	
////						data.dest_Middle = data.toMiddle;
//						data.dest_Speed  = data.getKMhSpeed();							
//					}
//				} else if ( isToBackward ) {
//					if( isToLeft && data.isCarOnLeftRearSide(i)) {
//						data.dest_Middle = data.toMiddle;
////						data.dest_Speed  = data.speed;
//					} else if( isToRight && data.isCarOnRightRearSide(i) ) {
//						data.dest_Middle = data.toMiddle;
////						data.dest_Speed  = data.speed;						
//					} else if( data.isCarOnTheRear(i) ) {	
////						data.dest_Middle = data.toMiddle;
//						data.dest_Speed  = data.getKMhSpeed();					
//					}					
//				}
//			}	
//		}
		
//		printCourseRader(data);
//		printMapRader(data);
				
		//<-- data -> cmd
		// Set Geer and Steer
		if( data.dest_Speed < 0 ) {
			data.backward = DrivingInterface.gear_type_backward;			
			data.steer = -1*data.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);
		}
		else {
			data.backward = DrivingInterface.gear_type_forward;
			data.steer = data.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);			
		}

		// Set Accel and Brake
		if ( Math.abs(data.getKMhSpeed()) <  Math.abs(data.dest_Speed) )
		{ 
			data.accel = Math.abs(data.dest_Speed)/300; 
			data.brake = 0;
		} else if ( Math.abs(data.getKMhSpeed()) >  Math.abs(data.dest_Speed) ) {
			data.accel = 0;
			data.brake = (data.getKMhSpeed() - Math.abs(data.dest_Speed))/200;
		} else {
			data.accel = 0;
			data.brake = 0;
		}
//		System.out.println(data.toMiddle + ", " + data.dest_Middle + ", " + data.steer);
//		System.out.println(data.getKMhSpeed() + "(" + data.speed + "), " + data.dest_Speed + ", " + data.accel + ", " + data.brake + ", ");		
		
		return data;
		//-->
	}
	
	public void printCourseRader(DrivingData data){
//		System.out.println();
		System.out.println(data.track_current_angle);
		for(int i=0 ;i<20 ;i++ ){
			System.out.println(", " + data.track_Front_angles[i] + ", " + data.track_Front_dists[i]);
		}
		System.out.println();
	}
	
	public void printMapRader(DrivingData data){
		int [][] map;
		map = new int[10][];
		map[0] = new int[10];
		map[1] = new int[10];
		map[2] = new int[10];
		map[3] = new int[10];
		map[4] = new int[10];
		map[5] = new int[10];
		map[6] = new int[10];
		map[7] = new int[10];
		map[8] = new int[10];
		map[9] = new int[10];
				
		// total cars
		for (int i=0; i<10 ;i++ ){
			
//			System.out.print(i + " " + data.dist_cars[2*i] + " " + data.dist_cars[2*i+1] + " " + data.toMiddle + " ");			
			if( Math.floor(data.dist_cars[2*i])/6 + 5 > 0 && Math.floor(data.dist_cars[2*i])/6 + 5 < 10)
			{
				if( Math.floor(data.toMiddle - data.dist_cars[2*i+1])/2 + 5 > 0 && Math.floor(data.toMiddle - data.dist_cars[2*i+1])/2 + 5 < 10)
				{
					map[(int) (Math.floor(data.toMiddle - data.dist_cars[2*i+1])/2 + 5)][(int) (Math.floor(data.dist_cars[2*i])/6 + 5)] = 1;
				}
			}
		}

		for (int i=0; i<10 ;i++ ){
			
			for (int j=0; j<10; j++ ){
				
				if( map[j][9-i] == 1 )
					System.out.print("★");
				else
				    System.out.print("□");
				
			}
			System.out.print(" " + i + " " + data.dist_cars[2*i] + " " + data.dist_cars[2*i+1] + " " + data.toMiddle + " " + data.angle + " " + data.isNearCar(i));
			System.out.println();	
		}
		System.out.println();	
	}
}
