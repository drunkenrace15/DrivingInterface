
public class CorneringAlgorithm implements DrivingAlgorithm {

   public boolean calculate(DrivingData data) {
      
      // OUT - IN - OUT
      double angle =  ((data.angle - data.track_forward_angles[0]) * 180/Math.PI);
      double angle_current = ((data.angle - data.track_current_angle) * 180/Math.PI);
      double angle_base = ((data.track_current_angle - data.track_forward_angles[0]) * 180/Math.PI);
      double angle_base1 = ((data.track_current_angle - data.track_forward_angles[1]) * 180/Math.PI);
      double angle_base2 = ((data.track_current_angle - data.track_forward_angles[2]) * 180/Math.PI);
      
      int tracknum = data.getTrackIdx();
      double c_max = 180;
      double c_near = 150;
      double c_corner = 1;
      double mm = 0.32;
      double mm_near = 0.5;
      if(tracknum == 0){
         c_max =180;
         c_near =150;
      }else if(tracknum == 1){
         c_max =120;
         c_near =90;
      }else if(tracknum == 2){
         c_max =110;
         c_near =100;
         mm = 0.2;
         mm_near = 0.7;
      }else if(tracknum == 3){
         c_max =130;
         c_near =120;
         mm = 0.25;
         mm_near = 0.3;
      }else if(tracknum == 4){
         c_max =150;
         c_near =130;
         mm = 0.3;
         mm_near = 0.4;
      }
      
      System.out.println("tracknum" + tracknum);
      
      int bef_curve_type = data.track_curve_type;
      
      double angle_c[] = new double[20];
      angle_c[0] = ((data.track_forward_angles[0] - data.track_forward_angles[1]) * 180/Math.PI);
      angle_c[1] = ((data.track_forward_angles[1] - data.track_forward_angles[2]) * 180/Math.PI);
      angle_c[2] = ((data.track_forward_angles[2] - data.track_forward_angles[3]) * 180/Math.PI);
      angle_c[3] = ((data.track_forward_angles[3] - data.track_forward_angles[4]) * 180/Math.PI);
      angle_c[4] = ((data.track_forward_angles[4] - data.track_forward_angles[5]) * 180/Math.PI);
      angle_c[5] = ((data.track_forward_angles[5] - data.track_forward_angles[6]) * 180/Math.PI);
      angle_c[6] = ((data.track_forward_angles[6] - data.track_forward_angles[7]) * 180/Math.PI);
      angle_c[7] = ((data.track_forward_angles[7] - data.track_forward_angles[8]) * 180/Math.PI);
      angle_c[8] = ((data.track_forward_angles[8] - data.track_forward_angles[9]) * 180/Math.PI);
      angle_c[9] = ((data.track_forward_angles[9] - data.track_forward_angles[10]) * 180/Math.PI);
      angle_c[10] = ((data.track_forward_angles[10] - data.track_forward_angles[11]) * 180/Math.PI);
      angle_c[11] = ((data.track_forward_angles[11] - data.track_forward_angles[12]) * 180/Math.PI);
      angle_c[12] = ((data.track_forward_angles[12] - data.track_forward_angles[13]) * 180/Math.PI);
      angle_c[13] = ((data.track_forward_angles[13] - data.track_forward_angles[14]) * 180/Math.PI);
      angle_c[14] = ((data.track_forward_angles[14] - data.track_forward_angles[15]) * 180/Math.PI);
      
      
//      if(data.track_width == 12 ){//레벨 5짜리 맵
//         mm = 0.13;
//      }else if(data.track_width == 13 ){//레벨 4짜리 맵
//         mm = 0.17;
//      }
      double m_angle = 0.1;
      
      //짧은 s라인 체크
      boolean plus = false;
      boolean minus = false;
      for(int i=0; i<5; i++){
         if (angle_c[i] > 0.1 ) {
            plus = true;
         }
         
         if (angle_c[i] < -0.1 ) {
            minus = true;
         } 
         
         if (plus && minus)
            break;
      }
      
      if (plus && minus){
         System.out.println("짧은 s라인 체크");
         
         if(Math.abs(angle_base) >9  ){
            if(data.getKMhSpeed() >60){
               data.dest_Speed = data.dest_Speed * 0.3;
            }
            m_angle = 1.4;
         }else if(Math.abs(angle_base) >8  ){
            if(data.getKMhSpeed() >60){
               data.dest_Speed =60;
            }
            m_angle = 1.2;
         }else if(Math.abs(angle_base) >7  ){
            if(data.getKMhSpeed() >70){
               data.dest_Speed = 70;
            }
            m_angle = 1;
         }else if(Math.abs(angle_base) >6 ){
            if(data.getKMhSpeed() >80){
               data.dest_Speed = 80;
            }
            m_angle = 1;
         }else if(Math.abs(angle_base) >5 ){
            if(data.getKMhSpeed() >c_near -40){
               data.dest_Speed = c_near -40;
            }
            m_angle = 0.9;
         }else if(Math.abs(angle_base) >4   ){
            if(data.getKMhSpeed() >c_near -25){
               data.dest_Speed = c_near -25;
            }
            m_angle = 0.8;
         }else if(Math.abs(angle_base) >3 ){
            if(data.getKMhSpeed() >c_near-15){
               data.dest_Speed = c_near-15;
            }
            m_angle = 0.7;
         }else if(Math.abs(angle_base) >1.5){
            if(data.getKMhSpeed() >c_near-10){
               data.dest_Speed =c_near-10;
            }
            m_angle = 0.7;
         }else if(Math.abs(angle_base) >0.5){
            if(data.getKMhSpeed() >120){
               data.dest_Speed =120;
            }
            m_angle = 0.7;
         }
         else if(Math.abs(angle_base) >=0 ){
            if(data.getKMhSpeed() >130){
               data.dest_Speed = 130;
            }
            m_angle =0.7;
         }else{
            data.dest_Speed = c_near;
         }
         
//         if(Math.abs(data.toMiddle) < 1) 
//            data.dest_Middle = 0;
//         else {
//            if( data.toMiddle < 0 )
               data.dest_Middle = data.toMiddle;//왼쪽으로
//            else
//               data.dest_Middle = data.toMiddle;// 오른쪽
//         }
      }
         //S
//      System.out.println("angle_base "+angle_base + ", data.track_dist_straight"+data.track_dist_straight+" data.speed" + data.getKMhSpeed());

      else if (data.track_dist_straight > 45  && data.track_dist_straight <130) {   
         System.out.println("OIO 시도 : "+  ">A///// angle_base"+ angle_base+ "//////" +"data.speed" + data.getKMhSpeed());   
         if( data.track_curve_type == DrivingInterface.curve_type_right) { 
            data.dest_Middle = data.getLeftMiddle(mm); 
         } 
         if( data.track_curve_type == DrivingInterface.curve_type_left) { 
            data.dest_Middle = data.getRightMiddle(mm); 
         } 
//         if(Math.abs(data.toMiddle) < 1) 
//            data.dest_Middle = 0;
//         else {
//            if( data.toMiddle < 0 )
//               data.dest_Middle = data.toMiddle + 0.5;//왼쪽으로
//            else
//               data.dest_Middle = data.toMiddle - 0.5;// 오른쪽
//         }
         if(data.getKMhSpeed() >c_max){
            data.dest_Speed = data.getKMhSpeed() -2;
         }
         data.dest_Speed =c_max;
      
      }else if(data.track_dist_straight >0 && data.track_dist_straight < 45 ){
         System.out.println("코너링 근접!!!!!!!!!!!!!!!!!!!1"+data.speed);
//         if(data.getKMhSpeed() >150){
            data.dest_Speed = c_near;
//         }
         
         if(data.track_curve_type == DrivingInterface.curve_type_right){         
            data.dest_Middle = data.getRightMiddle(mm_near);// 오른쪽으로
         }else if(data.track_curve_type == DrivingInterface.curve_type_left){         
            data.dest_Middle = data.getLeftMiddle(mm_near); //왼쪽으로
         }
      }
//      else if(data.track_dist_straight >0 && data.track_dist_straight < 30){// 회전 하기 전에
//         
//         if(data.track_curve_type == DrivingInterface.curve_type_right){            
////            System.out.println("회전 하기 전에 우회전중 angle_current : "+  angle_current+"data.speed" + data.getKMhSpeed());
//            data.dest_Middle = data.getRightMiddle(1);
////            System.out.println("회전 하기 전에 우회전 ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+"//// m_angle : "+m_angle);
//         
//         }else if( data.track_curve_type == DrivingInterface.curve_type_left) {            
////            System.out.println("★회전 하기 전에 좌회전중 angle_current : "+ angle_current+"data.speed" + data.getKMhSpeed());
//            data.dest_Middle = data.getLeftMiddle(1);
////            System.out.println("★회전 하기 전에 좌회전ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+ "//// m_angle : "+m_angle);
//            
//         }
//      }
      else if(Math.abs(angle_base) > 1 || Math.abs(angle_c[0]) > 1 || data.track_dist_straight ==0 ){// 현재 회전중이라면
         System.out.println("track_dist_straight"+ data.track_dist_straight);
         System.out.println("angle_base"+ angle_base + "맵 : "+ angle_c[0]+", "+angle_c[1] + " " + angle_c[2] + " " + angle_c[3] +  " " + angle_c[4]
               + " " + angle_c[5] +  " " + angle_c[6]);

         //앞 트랙과 그 앞앞 트랙의 각도에 따른 속도 조절 부여
         
//         angle_base = (angle_c[0]+angle_c[1]+angle_c[2])/3;
         
         if(Math.abs(angle_base) >9  ){
            if(data.getKMhSpeed() >60){
               data.dest_Speed = data.dest_Speed * 0.3;
            }
            m_angle = 1.4;
         }else if(Math.abs(angle_base) >8  ){
            if(data.getKMhSpeed() >60){
               data.dest_Speed =60;
            }
            m_angle = 1.2;
         }else if(Math.abs(angle_base) >7  ){
            if(data.getKMhSpeed() >70){
               data.dest_Speed = 70;
            }
            m_angle = 1;
         }else if(Math.abs(angle_base) >6 ){
            if(data.getKMhSpeed() >80){
               data.dest_Speed = 80;
            }
            m_angle = 1;
         }else if(Math.abs(angle_base) >5 ){
            if(data.getKMhSpeed() >c_near -40){
               data.dest_Speed = c_near -40;
            }
            m_angle = 0.9;
         }else if(Math.abs(angle_base) >4   ){
            if(data.getKMhSpeed() >c_near -25){
               data.dest_Speed = c_near -25;
            }
            m_angle = 0.8;
         }else if(Math.abs(angle_base) >3 ){
            if(data.getKMhSpeed() >c_near-15){
               data.dest_Speed = c_near-15;
            }
            m_angle = 0.7;
         }else if(Math.abs(angle_base) >1.5){
            if(data.getKMhSpeed() >c_near-10){
               data.dest_Speed =c_near-10;
            }
            m_angle = 0.7;
         }else if(Math.abs(angle_base) >0.5){
            if(data.getKMhSpeed() >120){
               data.dest_Speed =120;
            }
            m_angle = 0.7;
         }
         else if(Math.abs(angle_base) >=0 ){
            if(data.getKMhSpeed() >130){
               data.dest_Speed = 130;
            }
            m_angle =0.7;
         }   
         data.dest_Speed  = data.dest_Speed  -1;
            if(data.track_curve_type == DrivingInterface.curve_type_right){            
            System.out.println("우우회전중 angle_current : "+  angle_current+"data.speed" + data.getKMhSpeed());
//            data.dest_Middle = -m_angle * Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
            //data.dest_Middle = data.getRightMiddle(m_angle); 
            
//            if(data.toMiddle >0)//왼쪽 차선에 있을때
//            {            
////               data.dest_Middle =  data.toMiddle -m_angle;//m_angle * Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
//               data.dest_Middle = -m_angle*Math.abs( data.toMiddle/ Math.cos(Math.abs(data.track_current_angle - data.track_Front_angles[0])));
//            
//            }else{//오른쪽 차선에 있을때
////               data.dest_Middle = data.toMiddle -m_angle;//m_angle * Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
//               data.dest_Middle = -m_angle*Math.abs( data.toMiddle/ Math.cos(Math.abs(data.track_current_angle - data.track_Front_angles[0])));
//            }
            
            if( data.toMiddle - m_angle > data.getMostRightMiddle()+1) {//중앙에서 왹각으로 서서히
               data.dest_Middle = data.toMiddle - m_angle;
            } else if (data.toMiddle < data.getMostRightMiddle()+1) {
               data.dest_Middle = data.getMostRightMiddle()+0.6;   
            } else {
               data.dest_Middle = data.getMostRightMiddle()+1;
            }
            
//            data.dest_Middle = data.getRightMiddle(m_angle);
            System.out.println("우회전 ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+"//// m_angle : "+m_angle);
            
//            double steer22 = ata.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);      
//            System.out.print(steer22);
            System.out.print(data.angle);
            System.out.println("+"+(data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052));   
            
//            if(angle_c[0]== 0 && angle_c[1]== 0 && angle_c[2]  == 0&&angle_c[3] == 0){
//               System.out.println("급 회전 시작!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//               data.dest_Speed = data.dest_Speed -3;
//            }
//            if(angle_now < -88){
//               //System.out.println("급 우회전 88도"+angle_now);
//               data.dest_Speed = 60;
//            }
//            else if(angle_now < -60){
//               //System.out.println("급 우회전 60도"+angle_now);
//               data.dest_Speed = 70;
//            }
            
         }else if( data.track_curve_type == DrivingInterface.curve_type_left) {
            
            System.out.println("★좌좌회전중 angle_current : "+ angle_current+"data.speed" + data.getKMhSpeed());
//            if(data.toMiddle >0)
//            {            
////               data.dest_Middle = data.toMiddle +Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
//               data.dest_Middle =  Math.abs( data.toMiddle/ Math.cos(Math.abs(data.track_current_angle - data.track_Front_angles[0])));
//               
//            }else{
////               data.dest_Middle =  Math.abs(Math.sin(Math.PI/2 - (data.track_current_angle - data.track_Front_angles[0])) * data.toMiddle);
//               data.dest_Middle = Math.abs( data.toMiddle/ Math.cos(Math.abs(data.track_current_angle - data.track_Front_angles[0])));
//            }
//            data.dest_Middle = data.getLeftMiddle(data.toMiddle/angle_base); 
            
            if( data.toMiddle + m_angle < data.getMostLeftMiddle()-1) {
               System.out.println("1111111111111111111111111111111111111111111111");
               data.dest_Middle = data.toMiddle + m_angle;
            } else if (data.toMiddle > data.getMostLeftMiddle()-1) {
               System.out.println("22222222222222222222222222222222222222222222222");
               data.dest_Middle = data.getMostLeftMiddle()-0.6;      
            } else {
               System.out.println("33333333333333333333333333333333333333333333333");
               data.dest_Middle = data.getMostLeftMiddle()-1;
            }
            
//            data.dest_Middle = data.getLeftMiddle(m_angle);
            System.out.println("★좌회전ToMIddle "+ data.toMiddle + " // dest_Middle : "+ data.dest_Middle+ "//// m_angle : "+m_angle);
            
            double steer22 = data.angle + (data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052);      
            System.out.print(steer22+"////"+ data.steer);
            System.out.print("= " + data.angle);
            System.out.println(" "+(data.dest_Middle - data.toMiddle)/data.track_width * (1/0.541052));   
//            if(angle_now < -88){
//               //System.out.println("급 좌회전 80도"+angle_now);
//               data.dest_Speed = 60;
//            }
//            else if(angle_now < -60){
//               //System.out.println("급 좌회전 60도"+angle_now);
//               data.dest_Speed = 70;
//            }
         }
      }else {//코너링 나왔을 때 가운데로 가게하는 소스
         System.out.println("코너링 나왔을 때 가운데로 가게하는 소스");
         if(Math.abs(data.toMiddle) < 1) 
            data.dest_Middle = 0;
         else {
            if( data.toMiddle < 0 )
               data.dest_Middle = data.toMiddle + 0.5;//왼쪽으로
            else
               data.dest_Middle = data.toMiddle - 0.5;// 오른쪽
         }
      }
      return true;
   }
}