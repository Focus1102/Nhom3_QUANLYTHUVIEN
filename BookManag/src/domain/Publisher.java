package domain;
import java.util.ArrayList;
import java.util.List;

public class Publisher {
    protected List<Subscriber> subscribers = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    protected void notifySubscribers() {
        for (Subscriber subscriber : subscribers) {
            subscriber.update();
        }
    }
    protected void notifySubscriber(){
        for (Subscriber subscriber : subscribers) {
            subscriber.updates();
        }
    }

    protected void notifySubscribe(){
        for (Subscriber subscriber : subscribers) {
            subscriber.updateS();
        }
    }
}