package mq;

import java.util.Observable;
import java.util.Observer;

public class Subscribe implements Observer {

    public Subscribe(Observable o){
        o.addObserver(this);        //将该观察者放入待通知观察者里
    }
    @Override
    public void update(Observable o, Object arg) {
        System.out.println("收到通知:" + ((Publish)o).getData());
    }

}
