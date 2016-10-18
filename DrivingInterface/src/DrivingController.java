
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
		 * �⺻���� �˰��� 
		 * steer 	: ����
		 * speed 	: 0.2 (100km/h) ����
		 * brake 	: 0 ����
		 * backward : DrivingInterface.gear_type_forward;
		 */
		dal.addAlgorithm(new DefailtAlgorithm());

		/*
		 * �ڳʸ� �˰��� �� 20�� Ʈ�� ����Ͽ� ����/���� �� OUT-IN-OUT �ڳʸ� ���� ���� 
		 */
		dal.addAlgorithm(new CorneringAlgorithm());
		
		/*
		 * �ν��� �˰���
		 * ���� �������� ���� �̻��� ��� �ְ�ӵ� ��� �� �������� ���� �����ΰ�� ���Ӽ���
		 */
		dal.addAlgorithm(new BoosterOnAlgorithm());
		
		/*
		 * ȸ�Ǳ⵿ �˰���(���� ��ֹ� ȸ��)
		 * �������� ������ Ư�� ���� ���� �� ��� �������� �Ÿ��� ���Ͽ� ȸ������ �� ���� ��������
		 */
		dal.addAlgorithm(new EvasionAlgorithm());
				
		/*
		 * ���ޱ⵿ �˰���
		 * ����, �ڳʾƿ����� ��Ȳ���� ���� ���ο� ���� �������� �ӵ��� �ȳ��°�� ��������
		 * Ż���ϴ� �˰��� �ʿ�
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
