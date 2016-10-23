import java.text.DecimalFormat;


public class DrivingData {

	//global data
	public static double DEFAULT_SPEED = 150.0;
	public static boolean IS_EMERGENCY = false;
	
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
	
	//track information
	public TrackData[] tracks = new TrackData[5];
	public int track_index = -1;
	
	
	public void initData(double[] driveArray, double[] aicarArray, double[] trackArray, double[] damageArray, int[] rankArray, int trackCurveType, double[] trackAngleArray, double[] trackDistArray, double trackCurrentAngle){
		toMiddle    		= driveArray[DrivingInterface.drvie_toMiddle    ];
		angle        		= driveArray[DrivingInterface.drvie_angle       ];
		speed        		= driveArray[DrivingInterface.drvie_speed       ];

		toStart				 = trackArray[DrivingInterface.track_toStart		];
		dist_track			 = trackArray[DrivingInterface.track_dist_track		];
		track_width			 = trackArray[DrivingInterface.track_width			];
		track_dist_straight	 = trackArray[DrivingInterface.track_dist_straight	];
		track_curve_type	 = trackCurveType;

		track_forward_angles	= trackAngleArray;
		track_forward_dists	= trackDistArray;
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
		
		
		setInitTracks();
	}
	
	public void setInitTracks(){
		
		tracks[0] = new TrackData(3243.64, 15.00);
		
		tracks[1] = new TrackData(5380.50, 12.00);
		
		tracks[2] = new TrackData(4441.29, 13.00);
		
		tracks[3] = new TrackData(3260.43, 16.00);
		
		tracks[4] = new TrackData(3185.83, 15.00);
		
	}
	
	public int getTrackIdx(){
		
		double length = this.dist_track;
		double width = this.track_width;
		
		String pattern = ".##";
		DecimalFormat dformat = new DecimalFormat(pattern);
		
		length = Double.parseDouble(dformat.format(length));
		width = Double.parseDouble(dformat.format(width));
		
		if(this.track_index == -1 && length != 0.0 && width != 0.0){
			
			for(int i=0;i<tracks.length;i++){
				
				if(tracks[i].getTrack_length() == length && tracks[i].getTrack_width() == width){
					//return index;
					this.track_index = i;
					break;
				}
			}
			
		}
		
		return this.track_index;
		
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
	
	
	
	
	
//--- 미사용 매소드	
	
	/**
	 * @param carIndex 차량 번호 전방(1~5) 후방(6~9)
	 * @return 근접 차량 여부
	 */
	public boolean isNearCar(int carIndex) {
		if( carIndex < 0 || carIndex > 9 ) 
			return false;
		
		return Math.abs(dist_cars[2*carIndex] - safeExtraGap) < car_length + safeGap && Math.abs(toMiddle - dist_cars[2*carIndex+1]) < car_width + safeGap;
	}

	/**
	 * @param carIndex 차량 번호 전방(1~5) 후방(6~9)
	 * @return 좌측 전방에 존재하는지
	 */
	public boolean isCarOnLeftFrontSide(int carIndex) {
		if( carIndex < 0 || carIndex > 9 ) 
			return false;
		
		return 		(dist_cars[2*carIndex]   > -1*car_length/2) 
				&&	(dist_cars[2*carIndex+1] > toMiddle + car_width/2);	
	}

	/**
	 * @param carIndex 차량 번호 전방(1~5) 후방(6~9)
	 * @return 좌측 후방에 존재하는지
	 */
	public boolean isCarOnLeftRearSide(int carIndex) {
		if( carIndex < 0 || carIndex > 9 ) 
			return false;
		
		return 		(dist_cars[2*carIndex]   < car_length/2) 
				&&	(dist_cars[2*carIndex+1] > toMiddle + car_width/2);	
	}

	/**
	 * @param carIndex 차량 번호 전방(1~5) 후방(6~9)
	 * @return 우측 전방에 존재하는지
	 */
	public boolean isCarOnRightFrontSide(int carIndex) {
		if( carIndex < 0 || carIndex > 9 ) 
			return false;
		
		return 		(dist_cars[2*carIndex]   > -1*car_length/2) 
				&&	(dist_cars[2*carIndex+1] < toMiddle - car_width/2);	
	}

	/**
	 * @param carIndex 차량 번호 전방(1~5) 후방(6~9)
	 * @return 좌측 흐방에 존재하는지
	 */
	public boolean isCarOnRightRearSide(int carIndex) {
		if( carIndex < 0 || carIndex > 9 ) 
			return false;
		
		return 		(dist_cars[2*carIndex]   < car_length/2) 
				&&	(dist_cars[2*carIndex+1] < toMiddle - car_width/2);
	}

	/**
	 * @param carIndex 차량 번호 전방(1~5) 후방(6~9)
	 * @return 전방에 존재하는지
	 */
	public boolean isCarOnTheFront(int carIndex) {
		if( carIndex < 0 || carIndex > 9 ) 
			return false;
		
		return 		(dist_cars[2*carIndex]   > 0) 
				&&	(Math.abs(toMiddle - dist_cars[2*carIndex+1]) < car_width/2);	
	}

	/**
	 * @param carIndex 차량 번호 전방(1~5) 후방(6~9)
	 * @return 후방에 존재하는지
	 */
	public boolean isCarOnTheRear(int carIndex) {
		if( carIndex < 0 || carIndex > 9 ) 
			return false;
		
		return 		(dist_cars[2*carIndex]   > 0) 
				&&	(Math.abs(toMiddle - dist_cars[2*carIndex+1]) < car_width/2);	
	}
}
