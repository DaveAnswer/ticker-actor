package starter.service;

import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Keep;
import akka.stream.javadsl.RunnableGraph;
import akka.stream.javadsl.Sink;
import akka.stream.javadsl.Source;
import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.api.marketdata.TOPS;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.socket.manager.SocketRequest;
import pl.zankowski.iextrading4j.client.socket.request.marketdata.TopsAsyncRequestBuilder;

import java.time.Duration;

/**
 * Java Client API to get the Market Data details
 * todo remove dependency on API and query the source directly
 */
@Service
public class TrackerApiService {

    public void getTicker() {
        final IEXTradingClient iexTradingClient = IEXTradingClient.create();
        final SocketRequest<TOPS> request = new TopsAsyncRequestBuilder()
                .withAllSymbols()
                .build();

        iexTradingClient.subscribe(request, this::stringList);

    }

    private void stringList(TOPS tops) {
        Source source = Source.fromFuture(Futures.successful(tops)).throttle(5, Duration.ofSeconds(1));
        ActorSystem system = ActorSystem.create("system");
        Sink sink = Sink.actorRef(system.actorOf(OrdinaryActor.props()), "Done");

        RunnableGraph graph = source.toMat(sink, Keep.right());

        ActorMaterializer mat = ActorMaterializer.create(system);
        graph.run(mat);
    }
}
