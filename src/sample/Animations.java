package sample;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animations {
    private TranslateTransition component;

    public Animations(Node node){
        component = new TranslateTransition(Duration.millis(70), node);
        component.setFromX(0f);
        component.setCycleCount(3); // Сколько раз проиграется анимка
        component.setAutoReverse(true); // После сдвига возвращать обратно
    }

    public void playAnim(){
        component.playFromStart(); // Проигрывание анимации
    }

}
