import javafx.scene.shape.Polygon;

public class Paddle extends Character{

    private int xValue;
    private int yValue;

    public Paddle(int x, int y) {
        super(new Polygon(2,-30,2,30,-2,30,-2,-30), x, y);
        this.xValue = x;
        this.yValue = y;
    }

    @Override
    public void move(String str) {
        if(str.equals("up") && super.getCharacter().getTranslateY()-30>0){
            super.getCharacter().setTranslateY(super.getCharacter().getTranslateY()-4);
        }

        if(str.equals("down")&& super.getCharacter().getTranslateY()+30<PongGame.HEIGHT){
            super.getCharacter().setTranslateY(super.getCharacter().getTranslateY()+4);
        }
    }

    public void reset(){
        super.getCharacter().setTranslateX(xValue);
        super.getCharacter().setTranslateY(yValue);
    }
}
