package backend;

import name.panitz.game.framework.*;

import java.util.ArrayList;
import java.util.List;

public class SpaceTank<I,S> extends AbstractGame<I,S> {
  List<GameObject<I>> backgorund = new ArrayList<>();
  List<GameObject<I>> enemy = new ArrayList<>();
  List<ImageObject<I>> shield = new ArrayList<>();

  int health = 3;
  int highscore;
  int alienX = 5;
  int alienY = 50;

  boolean lost;

  TextObject<I> healthText = new TextObject<>(new Vertex(720,20)
          ,"Health: " + health,"Berlin Sans FB",20);

  TextObject<I> highscoreText = new TextObject<>(new Vertex(5,20)
          ,"Highscore: " + highscore,"Berlin Sans FB",20);

  public SpaceTank(double widthScreen, double heightScreen) {
    super(new ImageObject<>("tank.png", new Vertex(widthScreen/2,heightScreen*0.85)),widthScreen,heightScreen);

    backgorund.add(new ImageObject<>("space.jpg"));
    backgorund.add(healthText);
    backgorund.add(highscoreText);

    for (int aliens = 0; aliens < 30; aliens++) {
      String alienType = "alien" + (int) (Math.random() * 3) + ".png";
      if (aliens % 10 == 0) {
        alienY += 45;
        alienX = 5;
      }
      enemy.add(new ImageObject<>(alienType,new Vertex(alienX,alienY), new Vertex(0.25,0.005)));
      alienX += 45;
    }

    getGOss().add(backgorund);
    goss.add(enemy);

    getButtons().add(new Button("Pause", ()-> pause()));
    getButtons().add(new Button("Start", ()-> start()));
    getButtons().add(new Button("Exit", ()-> System.exit(0)));
  }

  @Override
  public void doChecks() {
    if (health<= 0){
      loose();
    }

    if (enemy.get(9).getPos().x >= 770 || enemy.get(0).getPos().x <= 0) {
      for (GameObject<I> en:enemy) {
        en.setVelocity(new Vertex(-en.getVelocity().x,en.getVelocity().y));
      }
    }

    if (enemy.get(20).getPos().y >= 450) {
      loose();
    }
  }

  public void loose() {
    enemy.add(new TextObject<>(new Vertex(100,300)
            ,"GAME OVER","Berlin Sans FB",56));
    lost = true;
  }

  @Override
  public boolean isStopped() {
    return super.isStopped() || health<=0 || lost;
  }

  @Override
  public void keyPressedReaction(KeyCode keycode) {
    if (keycode!=null) {
      switch (keycode){
        case RIGHT_ARROW:
          getPlayer().getVelocity().moveTo(new Vertex(2,0));
          break;
        case LEFT_ARROW:
          getPlayer().getVelocity().moveTo(new Vertex(-2,0));
          break;
        default: ;
      }
    }
  }

  @Override
  public void keyReleasedReaction(KeyCode keycode){
    if (keycode!=null) {
      switch (keycode){
        case RIGHT_ARROW:
        case LEFT_ARROW:
          getPlayer().getVelocity().moveTo(new Vertex(0,0));
          break;
        default: ;
      }
    }
  }
}