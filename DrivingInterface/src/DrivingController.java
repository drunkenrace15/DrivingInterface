
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
		 * dest_middle  : 차선유지.
		 * dest_speed 	: 100km/h
		 */
		dal.addAlgorithm(new DefailtAlgorithm());

//		/*
//		 * 코너링 알고리즘
//		 * dest_middle 	: 트랙분석을 통한 OUT-IN-OUT. 
//		 * dest_speed 	: ?
//		 */
//		dal.addAlgorithm(new CorneringAlgorithm());
//		
//		/*
//		 * 부스터-온 알고리즘
//		 * dest_middle 	: 변경없음. 
//		 * dest_speed 	: 연속 직진거리에 따른 최대속력 적용.
//		 */
//		dal.addAlgorithm(new BoosterOnAlgorithm());
//		
//		/*
//		 * 회피기동 알고리즘(전방 장애물 회피)
//		 * dest_middle 	: 전방 근접 차량 정보를 통한 최적의 차선 선택. 
//		 * dest_speed 	: 전방 근접 차량 정보에 따른 감속.
//		 */
//		dal.addAlgorithm(new EvasionAlgorithm());
//				
//		/*
//		 * 응급기동 알고리즘(후진 기능)
//		 * dest_middle 	: ? 
//		 * dest_speed 	: 음수 값 리턴을 통한 후진 요청.
//		 */
//		dal.addAlgorithm(new EmergencyAlgorithm());
//		
		
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
