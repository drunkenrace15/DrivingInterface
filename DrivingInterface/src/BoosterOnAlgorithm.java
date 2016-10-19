
public class BoosterOnAlgorithm implements DrivingAlgorithm {

	static int tic = 0;
	
	public boolean calculate(DrivingData data) {
		// Go!				
		boolean isFrontEmpty = true;
		for(int i=0; i<10&&isFrontEmpty; i++)
			if(data.isCarOnTheFront(i))
				isFrontEmpty = false;
		
		if ( isFrontEmpty && data.track_dist_straight >=40 ) {
			data.dest_Speed = 300;
		}
		
		++tic;
		if(tic < 50 &&  data.track_dist_straight >=40 ) {
			if( data.toMiddle > 0 )
				data.dest_Middle = data.getMostLeftMiddle();
			else
				data.dest_Middle = data.getMostRightMiddle();
			data.dest_Speed = 300;

			data.IS_EMERGENCY = false;
			return false;
		}
		
		return true;
	}

}
