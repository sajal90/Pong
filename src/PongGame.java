import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;



public class PongGame extends Application{

    public static int WIDTH = 600;
    public static int HEIGHT = 400;

    public boolean GAME_START = false;


    public void start(Stage stage){
        Pane mainPane = new Pane();
        mainPane.setPrefSize(WIDTH,HEIGHT);
        mainPane.setStyle("-fx-background-color:BLACK");


        Pane startingPane = new Pane();
        startingPane.setPrefSize(WIDTH,HEIGHT);
        startingPane.setStyle("-fx-background-color:BLACK");

        Label gameName = new Label("PONG!");
        gameName.setStyle("-fx-font-style:ITALIC");
        gameName.setFont(Font.font(100));
        gameName.setTextFill(Color.WHITE);
        gameName.setTranslateX(140);
        gameName.setTranslateY(15);


        VBox vBox = new VBox();
        Button startButton = new Button(" > Start");
        startButton.setTextFill(Color.WHITE);
        startButton.setBackground(Background.EMPTY);
        startButton.setScaleX(2);
        startButton.setScaleY(2);

        Button exitButton = new Button("> Exit");
        exitButton.setScaleX(2);
        exitButton.setScaleY(2);
        exitButton.setTextFill(Color.WHITE);
        exitButton.setBackground(Background.EMPTY);


        vBox.getChildren().addAll(startButton,exitButton);
        vBox.setSpacing(30);
        vBox.setTranslateX(250);
        vBox.setTranslateY(190);


        startingPane.getChildren().add(vBox);
        startingPane.getChildren().add(gameName);


        Line line = new Line(300,0,300,400);
        line.setStroke(Color.WHITE);
        line.getStrokeDashArray().addAll(2d);

        AtomicInteger points1 = new AtomicInteger();
        AtomicInteger points2 = new AtomicInteger();
        Text poi1 = new Text("0");
        Text poi2 = new Text("0");
        poi1.setFill(Color.WHITE);
        poi2.setFill(Color.WHITE);
        poi1.setScaleX(5);
        poi2.setScaleX(5);
        poi1.setScaleY(5);
        poi2.setScaleY(5);
        poi1.setTranslateX(150);
        poi2.setTranslateX(450);
        poi1.setTranslateY(50);
        poi2.setTranslateY(50);

        Paddle p1 = new Paddle(40,200);
        Paddle p2 = new Paddle(560,200);
        Ball ball = new Ball(WIDTH/2,HEIGHT/2);


        Label replay = new Label("TAP TO MOVE");
        replay.setFont(Font.font(30));
        replay.setTextFill(Color.WHITE);
        replay.setTranslateX(WIDTH / 2 - 80);
        replay.setTranslateY(HEIGHT / 2 - 80);
        replay.setPrefSize(200, 150);



        mainPane.getChildren().add(poi1);
        mainPane.getChildren().add(poi2);
        mainPane.getChildren().add(line);
        mainPane.getChildren().add(p1.getCharacter());
        mainPane.getChildren().add(p2.getCharacter());
        mainPane.getChildren().add(ball.getCharacter());



        Scene startingScene = new Scene(startingPane);
        stage.setScene(startingScene);
        Scene mainScene= new Scene(mainPane);


        startButton.setOnAction(e ->{
            mainPane.getChildren().add(replay);
            stage.setScene(mainScene);
        });


        Map<KeyCode, Boolean> map = new HashMap<>();
        mainScene.setOnKeyPressed(event -> {
            map.put(event.getCode(), true);
        });
        mainScene.setOnKeyReleased(keyEvent -> {
            map.put(keyEvent.getCode(), false);
        });



        AnimationTimer timer = new AnimationTimer() {

            @Override
            public void handle(long l) {

                if (!GAME_START) {
                    replay.setOnMouseClicked(e -> {
                        mainPane.getChildren().remove(replay);
                        GAME_START = true;
                        start();
                    });
                }

                if(GAME_START){
                    if(ball.ballXSpeed==0 || ball.ballYSpeed ==0) {
                        ball.ballStart();
                    }
                    ball.move(ball, p1, p2);
                }


                if (map.getOrDefault(KeyCode.UP, false)) {
                    p2.move("up");
                }

                if (map.getOrDefault(KeyCode.DOWN, false)) {
                    p2.move("down");
                }
                if (map.getOrDefault(KeyCode.W, false)) {
                    p1.move("up");
                }

                if (map.getOrDefault(KeyCode.S, false)) {
                    p1.move("down");
                }




                if (ball.ballOutOfFrame(ball) != -1) {

                    if (ball.ballOutOfFrame(ball) == 1) {
                        poi2.setText("" + points2.addAndGet(1));
                        GAME_START = false;
                    }
                    if (ball.ballOutOfFrame(ball) == 0) {
                        poi1.setText("" + points1.addAndGet(1));
                        GAME_START = false;
                    }
                    if (points2.get() == 5) {
                        Label p2Won = new Label("Player 2 Won");
                        p2Won.setFont(Font.font(30));
                        p2Won.setTextFill(Color.WHITE);
                        p2Won.setTranslateX(WIDTH / 2 - 80);
                        p2Won.setTranslateY(HEIGHT / 2 - 80);
                        p2Won.setPrefSize(200, 150);


                        GAME_START = false;
                        p2Won.setOnMouseClicked(e->{
                            points2.getAndSet(0);
                            poi2.setText(""+points2.get());

                            points1.getAndSet(0);
                            poi1.setText(""+points1.get());

                            ball.reset(ball);
                            p1.reset();
                            p2.reset();

                            mainPane.getChildren().remove(p2Won);
                            stage.setScene(startingScene);
                        });


                        mainPane.getChildren().add(p2Won);

                        stop();
                    }

                    if (points1.get() == 5) {
                        Label p1Won = new Label("Player 1 Won");
                        p1Won.setFont(Font.font(30));
                        p1Won.setTextFill(Color.WHITE);
                        p1Won.setTranslateX(WIDTH / 2 - 80);
                        p1Won.setTranslateY(HEIGHT / 2 - 80);
                        p1Won.setPrefSize(200, 150);


                        GAME_START = false;
                        p1Won.setOnMouseClicked(e->{
                            points2.getAndSet(0);
                            poi2.setText(""+points2.get());

                            points1.getAndSet(0);
                            poi1.setText(""+points1.get());

                            ball.reset(ball);
                            p1.reset();
                            p2.reset();

                            mainPane.getChildren().remove(p1Won);
                            stage.setScene(startingScene);
                        });


                        mainPane.getChildren().add(p1Won);
                        stop();
                    }

                    if(points1.get() != 5 && points2.get() != 5) {
                        ball.reset(ball);
                        mainPane.getChildren().add(replay);
                        replay.setOnMouseClicked(e -> {
                            ball.ballStart();
                            mainPane.getChildren().remove(replay);
                        });
                    }

                }
            }
        };timer.start();

        exitButton.setOnAction(e->{
            stage.close();
        });

        stage.setTitle("Pong!");
        stage.show();
    }

    public static void main(String[] args) {
        launch(PongGame.class);
    }
}