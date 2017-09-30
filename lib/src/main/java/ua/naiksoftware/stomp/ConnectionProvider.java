package ua.naiksoftware.stomp;

import io.reactivex.Flowable;

/**
 * Created by naik on 05.05.16.
 *
 * Update by cunchen on 30.09.17
 */
public interface ConnectionProvider {

    /**
     * Subscribe this for receive stomp messages
     */
    Flowable<String> messages();

    /**
     * Sending stomp messages via you ConnectionProvider.
     * onError if not connected or error detected will be called, or onCompleted id sending started
     * TODO: send messages with ACK
     */
    Flowable<Void> send(String stompMessage);

    /**
     * Subscribe this for receive #LifecycleEvent events
     */
    Flowable<LifecycleEvent> getLifecycleReceiver();

    /**
     * Destory this instance
     */
    void destory();
}
