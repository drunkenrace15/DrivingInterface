 
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
		 * 湲곕낯二쇳뻾 �븣怨좊━利� 
		 * dest_middle  : 李⑥꽑�쑀吏�.
		 * dest_speed 	: 100km/h
		 */
		dal.addAlgorithm(new DefailtAlgorithm());

		/*
		 * 遺��뒪�꽣-�삩 �븣怨좊━利�
		 * dest_middle 	: 蹂�寃쎌뾾�쓬. 
		 * dest_speed 	: �뿰�냽 吏곸쭊嫄곕━�뿉 �뵲瑜� 理쒕��냽�젰 �쟻�슜.
		 */
		dal.addAlgorithm(new BoosterOnAlgorithm());
		
		/*
		 * 肄붾꼫留� �븣怨좊━利�
		 * dest_middle 	: �듃�옓遺꾩꽍�쓣 �넻�븳 OUT-IN-OUT. 
		 * dest_speed 	: ?
		 */
		dal.addAlgorithm(new CorneringAlgorithm());
		
		/*
		 * �쉶�뵾湲곕룞 �븣怨좊━利�(�쟾諛� �옣�븷臾� �쉶�뵾)
		 * dest_middle 	: �쟾諛� 洹쇱젒 李⑤웾 �젙蹂대�� �넻�븳 理쒖쟻�쓽 李⑥꽑 �꽑�깮. 
		 * dest_speed 	: �쟾諛� 洹쇱젒 李⑤웾 �젙蹂댁뿉 �뵲瑜� 媛먯냽.
		 */
//		dal.addAlgorithm(new EvasionAlgorithm());
				
		/*
		 * �쓳湲됯린�룞 �븣怨좊━利�(�썑吏� 湲곕뒫)
		 * dest_middle 	: ? 
		 * dest_speed 	: �쓬�닔 媛� 由ы꽩�쓣 �넻�븳 �썑吏� �슂泥�.
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
