package starter.service;

import akka.actor.AbstractActor;
import akka.actor.Props;
import pl.zankowski.iextrading4j.api.marketdata.TOPS;

public class OrdinaryActor extends AbstractActor {

    public static Props props() {
        return Props.create(OrdinaryActor.class);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(TOPS.class, s -> System.out.println(s))
                .matchAny(o -> {
                    System.out.println("Unknown Type");
                }).build();
    }
}
