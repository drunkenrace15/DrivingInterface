import java.util.Vector;

public class DrivingController {	

	public class DrivingCmd{
		public double steer;
		public double accel;
		public double brake;
		public int backward;
	};

	public DrivingCmd controlDriving(double[] driveArray, double[] aicarArray, double[] trackArray, double[] damageArray, int[] rankArray, int trackCurveType, double[] trackAngleArray, double[] trackDistArray, double trackCurrentAngle){
		DrivingCmd cmd = new DrivingCmd();
		DrivingData data = new DrivingData();
		DrivingAlgorithmLauncher dal = new DrivingAlgorithmLauncher();
		
		// initialize input parameters
		data.initData(driveArray, aicarArray, trackArray, damageArray, rankArray, trackCurveType, trackAngleArray, trackDistArray, trackCurrentAngle);
		
		// To-Do : Make your driving algorithm	
		/*
		 * 기본주행 알고리즘 
		 * dest_middle  : 차선유지.
		 * dest_speed 	: 100km/h
		 */
		dal.addAlgorithm(new DefailtAlgorithm());

		/*
		 * 부스터-온 알고리즘
		 * dest_middle 	: 변경없음. 
		 * dest_speed 	: 연속 직진거리에 따른 최대속력 적용.
		 */
		dal.addAlgorithm(new BoosterOnAlgorithm());
		
		/*
		 * 코너링 알고리즘
		 * dest_middle 	: 트랙분석을 통한 OUT-IN-OUT. 
		 * dest_speed 	: ?
		 */
		dal.addAlgorithm(new CorneringAlgorithm());
		
		/*
		 * 회피기동 알고리즘(전방 장애물 회피)
		 * dest_middle 	: 전방 근접 차량 정보를 통한 최적의 차선 선택. 
		 * dest_speed 	: 전방 근접 차량 정보에 따른 감속.
		 */
		dal.addAlgorithm(new EvasionAlgorithm());
				
		/*
		 * 응급기동 알고리즘(후진 기능)
		 * dest_middle 	: ? 
		 * dest_speed 	: 음수 값 리턴을 통한 후진 요청.
		 */
		dal.addAlgorithm(new EmergencyAlgorithm());
		
		// set output values		
		data = dal.doDrive(data);
			
		//-- remove(start)
		cmd.steer = data.steer;
		cmd.accel = data.accel;
		cmd.brake = data.brake;
		cmd.backward = data.backward;
		//-- remove(end)
				
		return cmd;
	}
	
	public static void main(String[] args) {
		DrivingInterface driving = new DrivingInterface();
		DrivingController controller = new DrivingController();
		
		double[] driveArray = new double[DrivingInterface.INPUT_DRIVE_SIZE];
		double[] aicarArray = new double[DrivingInterface.INPUT_AICAR_SIZE];
		double[] trackArray = new double[DrivingInterface.INPUT_TRACK_SIZE];
		double[] damageArray = new double[DrivingInterface.INPUT_DAMAGE_SIZE];
		int[] rankArray = new int[DrivingInterface.INPUT_RANK_SIZE];
		int[] trackCurveType = new int[1];
		double[] trackAngleArray = new double[DrivingInterface.INPUT_FORWARD_TRACK_SIZE];
		double[] trackDistArray = new double[DrivingInterface.INPUT_FORWARD_TRACK_SIZE];
		double[] trackCurrentAngle = new double[1];
				
		// To-Do : Initialize with your team name.
		int result = driving.OpenSharedMemory();
		
		if(result == 0){
			boolean doLoop = true;
			while(doLoop){
				result = driving.ReadSharedMemory(driveArray, aicarArray, trackArray, damageArray, rankArray, trackCurveType, trackAngleArray, trackDistArray, trackCurrentAngle);
				switch(result){
				case 0:
					DrivingCmd cmd = controller.controlDriving(driveArray, aicarArray, trackArray, damageArray, rankArray, trackCurveType[0], trackAngleArray, trackDistArray, trackCurrentAngle[0]);
					driving.WriteSharedMemory(cmd.steer, cmd.accel, cmd.brake, cmd.backward);
					break;
				case 1:
					break;
				case 2:
					// disconnected
				default:
					// error occurred
					doLoop = false;
					break;
				}
			}
		}
	}
	
	//	insert inner class here 	
	
	// global data
	public static double DEFAULT_SPEED = 150.0;
	public static boolean IS_EMERGENCY = false;
	public static int tic = 0;
	public static int cnt = 0;
	public static int block_cnt = 0;
	
	public class DrivingData {
		
		public static final int car_width  = 2;
		public static final int car_length = 5; // (4.5 or 4.8)
		private static final double safeGap = 5;
		private static final double safeExtraGap = 2;
		
		//input parameters
		public double toMiddle;
		public double angle;
		public double speed; 
		
		public double toStart;	
		public double dist_track;
		public double track_width;
		public double track_dist_straight;
		public int track_curve_type;
		
		public double[] track_forward_angles;	
		public double[] track_forward_dists;
		public double track_current_angle;
		
		public double[] dist_cars;
		
		public double damage;
		public double damage_max;
		
		public int total_car_num;
		public int my_rank;
		public int opponent_rank;
		
		//output parameters
		public double dest_Middle;
		public double dest_Speed;
		
		//-- remove
		public double steer;
		public double accel;
		public double brake;
		public int backward;
		//-- remove
		
		public void initData(double[] driveArray, double[] aicarArray, double[] trackArray, double[] damageArray, int[] rankArray, int trackCurveType, double[] trackAngleArray, double[] trackDistArray, double trackCurrentAngle){
			toMiddle    		= driveArray[DrivingInterface.drvie_toMiddle    ];
			angle        		= driveArray[DrivingInterface.drvie_angle       ];
			speed        		= driveArray[DrivingInterface.drvie_speed       ];
	
			toStart				 = trackArray[DrivingInterface.track_toStart		];
			dist_track			 = trackArray[DrivingInterface.track_dist_track		];
			track_width			 = trackArray[DrivingInterface.track_width			];
			track_dist_straight	 = trackArray[DrivingInterface.track_dist_straight	];
			track_curve_type	 = trackCurveType;
	
			track_forward_angles = trackAngleArray;
			track_forward_dists	 = trackDistArray;
			track_current_angle	 = trackCurrentAngle;
			
			dist_cars 			 = aicarArray;
			
			damage		 		 = damageArray[DrivingInterface.damage];
			damage_max	 		 = damageArray[DrivingInterface.damage_max];
	
			total_car_num	 	 = rankArray[DrivingInterface.rank_total_car_num	];
			my_rank			 	 = rankArray[DrivingInterface.rank_my_rank			];
			opponent_rank		 = rankArray[DrivingInterface.rank_opponent_rank	];	
			
			dest_Middle			 = 0.0;
			dest_Speed			 = 0.0;
			backward 			 = 0;
		}
		
		/**
		 * @return 트랙 가장 우측 위치 값
		 */
		public double getMostRightMiddle(){
			return car_width/2 - track_width/2 + 1; 
		}
		
		public double getRightMiddle(double m) {
			return Math.max(toMiddle-m, getMostRightMiddle());
		}
		
		public double getRightMiddle(double m, boolean lineCheck) {
			return lineCheck ? getRightMiddle(m) : toMiddle-m;
		}
		
		/**
		 * @return 트랙 가장 좌측 위치 값
		 */
		public double getMostLeftMiddle(){
			return track_width/2 - car_width/2 - 1;
		}
	
		public double getLeftMiddle(double m) {
			return Math.min(toMiddle+m, getMostLeftMiddle());
		}
		
		public double getLeftMiddle(double m, boolean lineCheck) {
			return lineCheck ? getLeftMiddle(m) : toMiddle+m;
		}
			
		/**
		 * @return 코스 아웃 여부
		 */
		public boolean isOutOfTrack(){
			
			if( toMiddle < getMostRightMiddle() - 0.5 ) 
				return true;
			
			if( toMiddle > getMostLeftMiddle() + 0.5 )
				return true;
			
			return false;
		}
		
		/**
		 * @return 현재속도를 km/h기준으로 변환한 값
		 */
		public double getKMhSpeed(){
			return speed * 3.6;
		}
	}
	 
	public interface DrivingAlgorithm {

		public boolean calculate(DrivingData data);
	}

	public class DefailtAlgorithm implements DrivingAlgorithm {
	
		public boolean calculate(DrivingData data) {
	
			data.dest_Middle = data.toMiddle;
			data.dest_Speed  = DEFAULT_SPEED;			
			return true;
		}	
	}

	public class BoosterOnAlgorithm implements DrivingAlgorithm {
			
		public boolean calculate(DrivingData data) {
			// Go!				
			boolean isFrontEmpty = true;
			for(int i=0; i<10&&isFrontEmpty; i++)
				isFrontEmpty = 		(data.dist_cars[2*i]   > 0)
								&&	(data.dist_cars[2*i]   < 40)
								&&	(Math.abs(data.toMiddle - data.dist_cars[2*i+1]) < data.car_width/2);	
			
			if ( isFrontEmpty && data.track_dist_straight >=40 ) {
				data.dest_Speed = 300;
			}
			
			++tic;
			if(tic < 50 && data.track_dist_straight >=40 ) {
				if( data.toMiddle > 0 )
					data.dest_Middle = data.getMostLeftMiddle();
				else
					data.dest_Middle = data.getMostRightMiddle();
				data.dest_Speed = 300;
	
				IS_EMERGENCY = false;
				return false;
			}
			
			return true;
		}
	
	}

	public class CorneringAlgorithm implements DrivingAlgorithm {
	
		public boolean calculate(DrivingData data) {
			// OUT - IN - OUT
			double angle =  ((data.angle - data.track_forward_angles[0]) * 180/Math.PI);
			double angle_current = ((data.angle - data.track_current_angle) * 180/Math.PI);
			double angle_base = ((data.track_current_angle - data.track_forward_angles[0]) * 180/Math.PI);
			double angle_base1 = ((data.track_current_angle - data.track_forward_angles[1]) * 180/Math.PI);
			double angle_base2 = ((data.track_current_angle - data.track_forward_angles[2]) * 180/Math.PI);
			
			double angle_c[] = new double[20];
			angle_c[0] = ((data.track_forward_angles[0] - data.track_forward_angles[1]) * 180/Math.PI);
			angle_c[1] = ((data.track_forward_angles[1] - data.track_forward_angles[2]) * 180/Math.PI);
			angle_c[2] = ((data.track_forward_angles[2] - data.track_forward_angles[3]) * 180/Math.PI);
			angle_c[3] = ((data.track_forward_angles[3] - data.track_forward_angles[4]) * 180/Math.PI);
			angle_c[4] = ((data.track_forward_angles[4] - data.track_forward_angles[5]) * 180/Math.PI);
			angle_c[5] = ((data.track_forward_angles[5] - data.track_forward_angles[6]) * 180/Math.PI);
			angle_c[6] = ((data.track_forward_angles[6] - data.track_forward_angles[7]) * 180/Math.PI);
			angle_c[7] = ((data.track_forward_angles[7] - data.track_forward_angles[8]) * 180/Math.PI);
			angle_c[8] = ((data.track_forward_angles[8] - data.track_forward_angles[9]) * 180/Math.PI);
			
			double mm = 0.35;
			if(data.track_width == 12 ){//레벨 5짜리 맵
				mm = 0.13;
			}else if(data.track_width == 13 ){//레벨 4짜리 맵
				mm = 0.17;
			}
			double m_angle = 0.1;
			
	//		System.out.println("angle_base "+angle_base + ", data.track_dist_straight"+data.track_dist_straight+" data.speed" + data.getKMhSpeed());
	
			if (data.track_dist_straight > 50  && data.track_dist_straight <120) {	
				System.out.println("OIO 시도 : "+  ">A///// angle_base"+ angle_base+ "//////" +"data.speed" + data.getKMhSpeed());	
				if( data.track_curve_type == DrivingInterface.curve_type_right) { 
					data.dest_Middle = data.getLeftMiddle(mm); 
				} 
				if( data.track_curve_type == DrivingInterface.curve_type_left) { 
					data.dest_Middle = data.getRightMiddle(mm); 
				} 
	//			if(Math.abs(data.toMiddle) < 1) 
	//				data.dest_Middle = 0;
	//			else {
	//				if( data.toMiddle < 0 )
	//					data.dest_Middle = data.toMiddle + 0.5;//왼쪽으로
	//				else
	//					data.dest_Middle = data.toMiddle - 0.5;// 오른쪽
	//			}
				if(data.getKMhSpeed() >180){
					data.dest_Speed = data.getKMhSpeed() -2;
				}
			
			}else if(data.track_dist_straight >0 && data.track_dist_straight < 50 ){
				System.out.println("코너링 근접!!!!!!!!!!!!!!!!!!!1");
				if(data.getKMhSpeed() >150){
					data.dest_Speed = data.getKMhSpeed() -1;
				}
				
				if(data.track_curve_type == DrivingInterface.curve_type_right){			
					data.dest_Middle = data.getRightMiddle(1);// 오른쪽으로
				}else if(data.track_curve_type == DrivingInterface.curve_type_left){			
					data.dest_Middle = data.getLeftMiddle(1); //왼쪽으로
				}
			}
			else if(data.track_dist_straight >0 && data.track_dist_straight < 30){// 회전 하기 전에
				
				if(data.track_curve_type == DrivingInterface.curve_type_right){				
	//				System.out.println("회전 하기 전에 우회전중 angle_current : "+  angle_current+"data.speed" + data.getKMhSpeed());
					data.dest_Middle = data.getRightMiddle(1);
	//				System.out.println("회전 하기 전에 우회전 ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+"//// m_angle : "+m_angle);
				
				}else if( data.track_curve_type == DrivingInterface.curve_type_left) {				
	//				System.out.println("★회전 하기 전에 좌회전중 angle_current : "+ angle_current+"data.speed" + data.getKMhSpeed());
					data.dest_Middle = data.getLeftMiddle(1);
	//				System.out.println("★회전 하기 전에 좌회전ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+ "//// m_angle : "+m_angle);
					
				}
			}
			else if(Math.abs(angle_base) > 1 || Math.abs(angle_c[0]) > 1 || Math.abs(angle_c[1]) > 1 || data.track_dist_straight ==0 ){// 현재 회전중이라면
				System.out.println("track_dist_straight"+ data.track_dist_straight);
				System.out.println("angle_base"+ angle_base + "맵 : "+ angle_c[0]+", "+angle_c[1] + " " + angle_c[2] + " " + angle_c[3] +  " " + angle_c[4]
						+ " " + angle_c[5] +  " " + angle_c[6]);
	
				//앞 트랙과 그 앞앞 트랙의 각도에 따른 속도 조절 부여
				if(Math.abs(angle_base) >9  ){
					if(data.getKMhSpeed() >60){
						data.dest_Speed = data.getKMhSpeed() -2;
					}
					m_angle = 3;
				}else if(Math.abs(angle_base) >7  ){
					if(data.getKMhSpeed() >80){
						data.dest_Speed = data.getKMhSpeed() -2;
					}
					m_angle = 2.8;
				}else if(Math.abs(angle_base) >6 ){
					if(data.getKMhSpeed() >110){
						data.dest_Speed = data.getKMhSpeed() -1;
					}
					m_angle = 2.5;
				}else if(Math.abs(angle_base) >5 ){
					if(data.getKMhSpeed() >120){
						data.dest_Speed = data.getKMhSpeed() -1;
					}
					m_angle = 2.3;
				}else if(Math.abs(angle_base) >4   ){
					if(data.getKMhSpeed() >130){
						data.dest_Speed = data.getKMhSpeed() -1;
					}
					m_angle = 2;
				}else if(Math.abs(angle_base) >3 ){
					if(data.getKMhSpeed() >140){
						data.dest_Speed = data.getKMhSpeed() -1;
					}
					m_angle = 1.5;
				}else if(Math.abs(angle_base) >=0 ){
					if(data.getKMhSpeed() >150){
						data.dest_Speed = data.getKMhSpeed() -1;
					}
					m_angle = 1;
				}		
					if(data.track_curve_type == DrivingInterface.curve_type_right){				
					System.out.println("우우회전중 angle_current : "+  angle_current+"data.speed" + data.getKMhSpeed());
	//				data.dest_Middle = -m_angle * Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
					//data.dest_Middle = data.getRightMiddle(m_angle); 
					
	//				if(data.toMiddle >0)//왼쪽 차선에 있을때
	//				{				
	////					data.dest_Middle =  data.toMiddle -m_angle;//m_angle * Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
	//					data.dest_Middle = -m_angle*Math.abs( data.toMiddle/ Math.cos(Math.abs(data.track_current_angle - data.track_Front_angles[0])));
	//				
	//				}else{//오른쪽 차선에 있을때
	////					data.dest_Middle = data.toMiddle -m_angle;//m_angle * Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
	//					data.dest_Middle = -m_angle*Math.abs( data.toMiddle/ Math.cos(Math.abs(data.track_current_angle - data.track_Front_angles[0])));
	//				}
					
					if( data.toMiddle - m_angle > data.getMostRightMiddle()+1) {
						data.dest_Middle = data.toMiddle - m_angle;
					} else if (data.toMiddle < data.getMostRightMiddle()+1) {
						data.dest_Middle = data.getMostRightMiddle()+0.5;	
					} else {
						data.dest_Middle = data.getMostRightMiddle()+1;
					}
					
	//				data.dest_Middle = data.getRightMiddle(m_angle);
					System.out.println("우회전 ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+"//// m_angle : "+m_angle);
					
	//				double steer22 = ata.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);		
	//				System.out.print(steer22);
					System.out.print(data.angle);
					System.out.println("+"+(data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052));	
					
	//				if(angle_c[0]== 0 && angle_c[1]== 0 && angle_c[2]  == 0&&angle_c[3] == 0){
	//					System.out.println("급 회전 시작!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	//					data.dest_Speed = data.dest_Speed -3;
	//				}
	//				if(angle_now < -88){
	//					//System.out.println("급 우회전 88도"+angle_now);
	//					data.dest_Speed = 60;
	//				}
	//				else if(angle_now < -60){
	//					//System.out.println("급 우회전 60도"+angle_now);
	//					data.dest_Speed = 70;
	//				}
					
				}else if( data.track_curve_type == DrivingInterface.curve_type_left) {
					
					System.out.println("★좌좌회전중 angle_current : "+ angle_current+"data.speed" + data.getKMhSpeed());
	//				if(data.toMiddle >0)
	//				{				
	////					data.dest_Middle = data.toMiddle +Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
	//					data.dest_Middle =  Math.abs( data.toMiddle/ Math.cos(Math.abs(data.track_current_angle - data.track_Front_angles[0])));
	//					
	//				}else{
	////					data.dest_Middle =  Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
	//					data.dest_Middle = Math.abs( data.toMiddle/ Math.cos(Math.abs(data.track_current_angle - data.track_Front_angles[0])));
	//				}
	//				data.dest_Middle = data.getLeftMiddle(data.toMiddle/angle_base); 
					
					if( data.toMiddle + m_angle < data.getMostLeftMiddle()-1) {
						data.dest_Middle = data.toMiddle + m_angle;
					} else if (data.toMiddle > data.getMostLeftMiddle()-1) {
						data.dest_Middle = data.getMostLeftMiddle();		
					} else {
						data.dest_Middle = data.getMostLeftMiddle()-1;
					}
					
	//				data.dest_Middle = data.getLeftMiddle(m_angle);
					System.out.println("★좌회전ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+ "//// m_angle : "+m_angle);
					
					double steer22 = data.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);		
					System.out.print(steer22+"////"+ data.steer);
					System.out.print("= " + data.angle);
					System.out.println(" "+(data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052));	
	//				if(angle_now < -88){
	//					//System.out.println("급 좌회전 80도"+angle_now);
	//					data.dest_Speed = 60;
	//				}
	//				else if(angle_now < -60){
	//					//System.out.println("급 좌회전 60도"+angle_now);
	//					data.dest_Speed = 70;
	//				}
				}
			}else {//코너링 나왔을 때 가운데로 가게하는 소스
				System.out.println("코너링 나왔을 때 가운데로 가게하는 소스");
				if(Math.abs(data.toMiddle) < 1) 
					data.dest_Middle = 0;
				else {
					if( data.toMiddle < 0 )
						data.dest_Middle = data.toMiddle + 0.5;//왼쪽으로
					else
						data.dest_Middle = data.toMiddle - 0.5;// 오른쪽
				}
			}
			return true;
		}
	}
	 
	public class EvasionAlgorithm implements DrivingAlgorithm {
		
		public boolean calculate(DrivingData data) {
			// Evasion Steering	
			double currentSpeed = data.getKMhSpeed();
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
					
					cntCar ++;				
				} else {
					
					break;
				}
			}
			
			if(minMiddle > maxMiddle && data.dist_cars[0] < 100){
				minMiddle = data.dist_cars[1];
				maxMiddle = data.dist_cars[1];
			}
			
			for(int i=0;i<data.dist_cars.length/2-1;i+=2){
				if( data.dist_cars[i] < sight ) {				
					if( data.dist_cars[i+1] > data.toMiddle - 2.3 &&
					    data.dist_cars[i+1] < data.toMiddle + 2.3){
						chkInFront = true;
						break;
					}		
				}
			}

			double rightGab = Math.abs(-1 * data.track_width - (minMiddle) + (data.track_width/2));
			double leftGab = Math.abs(data.track_width - (maxMiddle) - (data.track_width/2));
					
			if(chkInFront){
			
				double minMiddleDist = data.track_width - (minMiddle) - (data.track_width/2);
				double maxMiddleDist = data.track_width - (maxMiddle) - (data.track_width/2);
				double toMiddleDist = data.track_width - (data.toMiddle) - (data.track_width/2);
				
				if(leftGab>rightGab) {  
								
					if( minMiddleDist-toMiddleDist	> maxMiddleDist-toMiddleDist) {	
						
						data.dest_Middle = data.toMiddle - angle; 	
						if(data.dest_Middle > minMiddle) 
							data.dest_Middle = minMiddle - 1.5;
						if(data.dest_Middle < data.getMostRightMiddle()) 
							data.dest_Middle = data.getMostRightMiddle();

					} else {
						
						data.dest_Middle = data.toMiddle + angle; 
						if(data.dest_Middle < maxMiddle) 
							data.dest_Middle = maxMiddle + 1.5;
						if(data.dest_Middle > data.getMostLeftMiddle()) 
							data.dest_Middle = data.getMostLeftMiddle();						
					}
				} else {

					if( minMiddleDist-toMiddleDist	>= maxMiddleDist-toMiddleDist) {	
						
						data.dest_Middle = data.toMiddle  - angle;
						if(data.dest_Middle > minMiddle) 
							data.dest_Middle = minMiddle - 1.5;
						if(data.dest_Middle < data.getMostRightMiddle()) 
							data.dest_Middle = data.getMostRightMiddle();
						
					} else {

						data.dest_Middle = data.toMiddle  + angle;
						if(data.dest_Middle < maxMiddle) 
							data.dest_Middle = maxMiddle + 1.5;
						if(data.dest_Middle > data.getMostLeftMiddle()) 
							data.dest_Middle = data.getMostLeftMiddle();
					}			
				}	
			} else if(cntCar > 0){
				data.dest_Middle = data.toMiddle;
			}
					
			return true;
		}
	}
	 
	public class EmergencyAlgorithm implements DrivingAlgorithm {
		
		public boolean calculate(DrivingData data) {
				
			if( IS_EMERGENCY) {
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
							IS_EMERGENCY = false;
						else {
							data.dest_Speed = 0;
							if( data.toMiddle < 0 ) 
								data.dest_Middle = data.getMostRightMiddle();
							else
								data.dest_Middle = data.getMostLeftMiddle();
						}
						
					}
						
				}
			} else {		
				if( data.isOutOfTrack() ) {
					++cnt;
					
					if( cnt > 5 ) {
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
						
						IS_EMERGENCY = true;
						
					}
					
				} else if(data.getKMhSpeed() < 10){
					
					blockEscape(data);
					
				}else{
					
					cnt = 0;
				}
			}
			
			if(data.getKMhSpeed() < 10){
				blockEscape(data);
			}
			
			return ! IS_EMERGENCY;
		}
		
		
		void blockEscape(DrivingData data){
						
			if(block_cnt > 15){
				
				data.dest_Speed = -300;
				if( data.toMiddle < 0 ) 
					data.dest_Middle = data.getMostRightMiddle();
				else
					data.dest_Middle = data.getMostLeftMiddle();
				
				IS_EMERGENCY = true;
				block_cnt = 0;
				
			}else{
				++block_cnt;
			}
			
		}

	}
	 
	public class DrivingAlgorithmLauncher {
		
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
//				data.accel = Math.abs(data.dest_Speed)/300;
//				악셀		최종기어	최종속도
//				0.1	/	1	/	47
//				0.2	/	1	/	77
//				--------------------------------------
//				0.3	/	2	/	105
//				0.4	/	3	/	125
//				0.5	/	4	/	145
//				0.6	/	4	/	165
//				--------------------------------------
//				0.7	/	5	/	180
//				0.8	/	5	/	195
//				0.9	/	5	/	195
//				1	/	6	/	215
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
			return data;
			//-->		
		}	
	}
}
