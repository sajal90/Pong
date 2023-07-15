import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public abstract class Character {
    private Polygon character;

    public Character(Polygon polygon,int x,int y){
        this.character = polygon;
        this.character.setFill(Color.WHITE);
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

    }

    public void move(String str){

        if(str.equals("up")){
            this.character.setTranslateY(character.getTranslateY()-4);
        }

        if(str.equals("down")){
            this.character.setTranslateY(character.getTranslateY()+4);
        }
    }

    public boolean collide(Character other){
        Shape collisionArea = Shape.intersect(this.character,other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() !=-1;
    }

    public Polygon getCharacter() {
        return this.character;
    }

    public void setCharacter(Polygon character) {
        this.character = character;
    }
}

