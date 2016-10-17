
public class DrivingController {	
//	insert inner class here 	
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
		 * steer 	: 직진
		 * speed 	: 0.2 (100km/h) 고정
		 * brake 	: 0 고정
		 * backward : DrivingInterface.gear_type_forward;
		 */
		dal.addAlgorithm(new DefailtAlgorithm());

		/*
		 * 코너링 알고리즘 앞 20개 트랙 계산하여 가속/감속 및 OUT-IN-OUT 코너링 전략 수립 
		 */
		dal.addAlgorithm(new CorneringAlgorithm());
		
		/*
		 * 부스터 알고리즘
		 * 전방 직선길이 일정 이상인 경우 최고속도 사용 및 직선길이 일정 이하인경우 감속수행
		 */
		dal.addAlgorithm(new BoosterOnAlgorithm());
		
		/*
		 * 회피기동 알고리즘(전방 장애물 회피)
		 * 앞차와의 간격이 특정 길이 이하 인 경우 앞차와의 거리를 비교하여 회전각이 더 적은 방향으로
		 */
		dal.addAlgorithm(new EvasionAlgorithm());
				
		/*
		 * 응급기동 알고리즘
		 * 스핀, 코너아웃등의 상황으로 차가 도로에 거의 수직으로 속도가 안나는경우 후진으로
		 * 탈출하는 알고리즘 필요
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
}
