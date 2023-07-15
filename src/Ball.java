import javafx.scene.shape.Polygon;

import java.util.Random;

public class Ball extends Character {
    Random rnd = new Random();

    double ballXSpeed = rnd.nextInt(2)==0 ? 2.5:-2.5;
    double ballYSpeed = rnd.nextInt(2)==0 ? 1.1:-1.1;

    public Ball(int x, int y) {
        super(new Polygon(2,-2,2,2,-2,2,-2,-2), x, y);
    }


    public void move(Character ball,Character p1,Character  p2) {

        if(ball.getCharacter().getTranslateY()>=PongGame.HEIGHT || ball.getCharacter().getTranslateY()<=0){
            ballYSpeed *= -1;
        }

        if(ball.collide(p1)){

            ballXSpeed *= -1;
            ballXSpeed++;
            if(ballYSpeed>0) {
                ballYSpeed++;
            }else{
                ballYSpeed--;
            }

            ballXSpeed *= 0.85;
            ballYSpeed *= 0.85;

        }

        if(ball.collide(p2)){

            ballXSpeed *= -1;
            ballXSpeed--;
            if(ballYSpeed>0) {
                ballYSpeed++;
            }else{
                ballYSpeed--;
            }

            ballXSpeed *= 0.85;
            ballYSpeed *= 0.85;

        }

        ball.getCharacter().setTranslateX(ball.getCharacter().getTranslateX()+ballXSpeed);
        ball.getCharacter().setTranslateY(ball.getCharacter().getTranslateY()+ballYSpeed);

    }

    public void reset(Ball ball){
        ball.getCharacter().setTranslateX(PongGame.WIDTH/2);
        ball.getCharacter().setTranslateY(PongGame.HEIGHT/2);
        ballXSpeed =0;
        ballYSpeed =0;
    }

    public void ballStart(){
        ballXSpeed =rnd.nextInt(2)==0 ? 2.5:-2.5;
        ballYSpeed =rnd.nextInt(2)==0 ? 1.1:-1.1;
    }

    public int ballOutOfFrame(Ball ball){
        if(ball.getCharacter().getTranslateX()<=0){
            return 1;
        }
        if(ball.getCharacter().getTranslateX()>=PongGame.WIDTH){

            return 0;
        }
        return -1;
    }
}
