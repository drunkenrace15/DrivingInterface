
public class DrivingData {
	
	//input parameters
	double toMiddle;
	double angle;
	double speed; 
	
	double toStart;	
	double dist_track;
	double track_width;
	double track_dist_straight;
	int track_curve_type;
	
	double[] track_forward_angles;
	double[] track_forward_dists;
	double track_current_angle;
	
	double[] dist_cars;
	
	double damage;
	double damage_max;
	
	int total_car_num;
	int my_rank;
	int opponent_rank;
	
	//output parameters
	public double steer;
	public double accel;
	public double brake;
	public int backward;
	
	
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
		
		steer 				 = 0.0;
		accel 				 = 0.0;
		brake 				 = 0.0;
		backward 			 = 0;
	}
}
