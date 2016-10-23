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
//			data.accel = Math.abs(data.dest_Speed)/300;
//			악셀		최종기어	최종속도
//			0.1	/	1	/	47
//			0.2	/	1	/	77
//			--------------------------------------
//			0.3	/	2	/	105
//			0.4	/	3	/	125
//			0.5	/	4	/	145
//			0.6	/	4	/	165
//			--------------------------------------
//			0.7	/	5	/	180
//			0.8	/	5	/	195
//			0.9	/	5	/	195
//			1	/	6	/	215
			if( data.getKMhSpeed() < 50) {
				data.accel = 0.4;
			} else if( data.getKMhSpeed() < 100) {
				data.accel = 0.5;
			} else if( data.getKMhSpeed() < 120) {
				data.accel = 0.6;
			} else if( data.getKMhSpeed() < 140) {
				data.accel = 0.7;
			} else if( data.getKMhSpeed() < 160) {
				data.accel = 0.8;
			} else if( data.getKMhSpeed() < 180) {
				data.accel = 0.9;
			} else if( data.getKMhSpeed() < 200) {
				data.accel = 1;
			} else {
				data.accel = 1;
			}
			data.brake = 0;
		} else if ( Math.abs(data.getKMhSpeed()) >  Math.abs(data.dest_Speed) ) {
			data.accel = 0;
			data.brake = (data.getKMhSpeed() - Math.abs(data.dest_Speed))/200;
		} else {
			data.accel = 0;
			data.brake = 0;
		}
//		printCmd(data);
		return data;
		//-->
		
//		if(++tic < 250) {
//
//			data.backward = DrivingInterface.gear_type_backward;
//			data.steer = 0;
//			data.accel = 0.1;
//			data.brake = 0;	
//		} else {
//		
//			data.backward = DrivingInterface.gear_type_forward;
//			data.steer = 0;
//			data.accel = 1;
//			data.brake = 0;		
//		}
//		return data;
	}
	
	public void printCmd(DrivingData data){

		System.out.println(data.toMiddle + ", " + data.dest_Middle + ", " + data.steer);
		System.out.println(data.getKMhSpeed() + "(" + data.speed + "), " + data.dest_Speed + ", " + data.accel + ", " + data.brake + ", ");	
		
	}
	
	public void printCourseRader(DrivingData data){
//		System.out.println();
		System.out.println(data.track_current_angle);
		for(int i=0 ;i<20 ;i++ ){
			System.out.println(", " + data.track_forward_angles[i] + ", " + data.track_forward_dists[i]);
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
