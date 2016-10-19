
public class CorneringAlgorithm implements DrivingAlgorithm {

	public boolean calculate(DrivingData data) {
		// OUT - IN - OUT
		boolean isFront = false;
		for(int i=0; i<10&&!isFront; i++)
			if(data.isCarOnTheFront(i))
				isFront = true;
		
		if ( !isFront && data.track_dist_straight < 30 ) {
			data.dest_Speed = 80;
		}		
		return true;
	}

}
