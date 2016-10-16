public class DrivingController {	
	public class DrivingCmd{
		public double steer;
		public double accel;
		public double brake;
		public int backward;
	};

	public DrivingCmd controlDriving(double[] driveArray, double[] aicarArray, double[] trackArray, double[] damageArray, int[] rankArray, int trackCurveType, double[] trackAngleArray, double[] trackDistArray, double trackCurrentAngle){
		DrivingCmd cmd = new DrivingCmd();
		
		////////////////////// input parameters
		double toMiddle     = driveArray[DrivingInterface.drvie_toMiddle    ];
		double angle        = driveArray[DrivingInterface.drvie_angle       ];
		double speed        = driveArray[DrivingInterface.drvie_speed       ]*3;

		double toStart				 = trackArray[DrivingInterface.track_toStart		];
		double dist_track			 = trackArray[DrivingInterface.track_dist_track		];
		double track_width			 = trackArray[DrivingInterface.track_width			];
		double track_dist_straight	 = trackArray[DrivingInterface.track_dist_straight	];
		int track_curve_type		 = trackCurveType;

		double[] track_forward_angles	= trackAngleArray;
		double[] track_forward_dists	= trackDistArray;
		double track_current_angle		= trackCurrentAngle;
		
		double[] dist_cars = aicarArray;
		
		double damage		 = damageArray[DrivingInterface.damage];
		double damage_max	 = damageArray[DrivingInterface.damage_max];

		int total_car_num	 = rankArray[DrivingInterface.rank_total_car_num	];
		int my_rank			 = rankArray[DrivingInterface.rank_my_rank			];
		int opponent_rank	 = rankArray[DrivingInterface.rank_opponent_rank	];		
		////////////////////// END input parameters
		
		// To-Do : Make your driving algorithm
		

		double plusDegree=0;
		double plusAcc=0;
		double plusBrake=0;
		double coe_steer = 0;
		int frontCar = getIndexNearFrontCar(toMiddle,dist_cars); //������ index�� �޾ƿ�
		
		if(frontCar>=0){
			plusDegree = getDegreeNearFrontCar(toMiddle,frontCar,frontCar+1);
			if(plusDegree * 180/Math.PI >= 60) {
				System.out.println("PlusDegree over 60"+" " + plusDegree * 180/Math.PI);
				plusDegree = 0;
			}
		}
		//index�� 0�̻��̸� 50m�̳��� ������ �ִٴ� �ǹ� 60�� �̻��϶� �ڵ��� ��������
		System.out.println("PlusDegree "+plusDegree * 180/Math.PI);
//		System.out.println("toMiddle/track_width "+toMiddle/track_width);
//		System.out.println("c "+(1+coe_steer)/0.541052);
		
		
		if(plusDegree >0) { 
			angle -= (plusDegree)/2;
		}
		//�������� ���Ұ��ΰ� ���������� ���Ұ��ΰ�
		else if(plusDegree <0) {
			angle +=  (plusDegree)/2;
		} else {
			// 60���̻� ������������ �����ʿ䰡 ���ٰ� �Ǵ�, ���ӵ� ����
			if(toDegree(angle)<=40 && track_dist_straight >=30){
				plusAcc += 0.2;
			}			
		}
		
		//���� Ʈ�� �Ÿ��� �̿��Ͽ� �߰� ���ӵ� �ο�
		
		System.out.println("track_dist_straight "+track_dist_straight+ "m");
		if(track_dist_straight >=100){
			plusAcc +=0.3;
		}else if(track_dist_straight >=150){
			plusAcc +=0.4;
		}
		System.out.println("Ʈ�� �� "+(track_current_angle - track_forward_angles[0]) * 180/Math.PI);
//		System.out.println("Ʈ�� �� "+(track_current_angle - track_forward_angles[0]));

		if((track_dist_straight >=0) && (track_dist_straight <=40)){
			if(track_curve_type ==1){//��ȸ�� �ڽ�
				angle +=  (track_forward_angles[1] - track_forward_angles[0]); //�ϴ� ������ 0���� �ǹ̰� ����
//				coe_steer =  4*(track_current_angle - track_forward_angles[0]);
				if(plusAcc >=0.3){
					plusAcc -= 0.3;
				}
				if(plusBrake >= 1){
					plusBrake +=0.05;
				}
				System.out.println("��ȸ�� angle "+(track_current_angle - track_forward_angles[0]) * 180/Math.PI);
			}else if(track_curve_type == 2){//��ȸ�� �ڽ�
				angle -=  (track_forward_angles[1] - track_forward_angles[0]);
//				coe_steer =  -4*(track_current_angle - track_forward_angles[0]);
				if(plusAcc >=0.3){
					plusAcc -= 0.3;
				}
				if(plusBrake >= 1){
					plusBrake +=0.05;
				}
				System.out.println("��ȸ�� angle "+(track_current_angle - track_forward_angles[0]) * 180/Math.PI);
			}			
		}else{
			plusBrake = 0;
		}
		
//		if((track_current_angle - track_forward_angles[0])*(180/Math.PI)>=4.5){
//			plusBrake = 0.05;
//			angle +=  (track_current_angle - track_forward_angles[0])/2;
//			System.out.println("�������� �� Ŀ�� angle  "+coe_steer * 180/Math.PI);
//		}else if((track_current_angle - track_forward_angles[0])*(180/Math.PI)<= -4.5){
//			plusBrake = 0.05;
//			angle -=  (track_current_angle - track_forward_angles[0])/2;
//			System.out.println("��ȸ������ �� Ŀ�� angle  "+coe_steer * 180/Math.PI);
//		}
		

//		System.out.println("angle "+angle*(180/Math.PI));
//		int front_curve = getTypeFrontCurve(); 
		
		////////////////////// output values		
//		cmd.steer = 3.5*angle+(-1*toMiddle/100);
		cmd.steer = (angle - toMiddle/track_width) *((1+coe_steer)/0.541052);
		cmd.accel = 0.2+plusAcc;
		cmd.brake = 0.0+plusBrake;
		cmd.backward = DrivingInterface.gear_type_forward;
		////////////////////// END output values
		
		
		return cmd;
	}
	
	public static int getTypeFrontCurve() {
		
		return 0;
	}

	//���� ������ �پ��ִ� ���� index
	
	public static int getIndexNearFrontCar(double myMiddle,double cars[]){
		int c=-1;
		
		double minDist = 50; 
		double minMiddle = 1;
		//���� ������ ���� �˻��� 1m ~ 50m
		
		for(int i =0;i<cars.length;i+=2){
			if(cars[i]>=0 && Math.abs(cars[i+1]-myMiddle)>=2){
				//>=0 �� ��� ���ǹ̾��� �Ÿ��� 0m�̻� �������ֳĴ°�
				double dist = Math.sqrt(cars[i]*cars[i]+Math.abs(cars[i+1]-myMiddle)*Math.abs(cars[i+1]-myMiddle));
				if(minDist >= dist) {
					minDist = dist;
					c = i;
				}
				
			}
		}
		if(c>=0){	System.out.println("Near Dist : " + minDist);}
		return c;
	}
	
	//���� ������ �پ��ִ� ������ ���� 
	public static double getDegreeNearFrontCar(double myMiddle,double car,double carMiddle){
		double c=0;
				
//		c = Math.atan2(Math.abs(myMiddle-carMiddle), car) - Math.PI/2 ;
		c = Math.atan2(car, Math.abs(myMiddle-carMiddle)) - Math.PI/2 ;
		
		
		System.out.println("Near Degree : " + c + " "+c*(180/Math.PI));
		return c;
	}
	//������� ���� ���� ��ȯ
	public static double toDegree(double rad){
		return rad * 180/Math.PI;
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
}
