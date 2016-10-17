import java.util.Vector;

public class DrivingAlgorithmLauncher {

	private Vector algorithms;
	
	public DrivingAlgorithmLauncher() {
		algorithms = new Vector();
	}
	
	public void addAlgorithm(DrivingAlgorithm algorithm) {
		algorithms.add(algorithm);
	}
	
	public DrivingCmd doDrive(DrivingCmd cmd) {
		
		for (int i = 0; i < algorithms.size(); i++) {
			((DrivingAlgorithm)algorithms.get(i)).calculate(cmd);
		}
		
		return cmd;
	}
}
