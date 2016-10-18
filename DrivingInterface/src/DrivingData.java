
public class DrivingData {

	//global data
	public static double DEFAULT_SPEED = 100.0;
	public boolean IS_EMERGENCY = false;
	
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
}
