package starter.service;

import org.springframework.stereotype.Service;
import pl.zankowski.iextrading4j.api.marketdata.TOPS;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.socket.manager.SocketRequest;
import pl.zankowski.iextrading4j.client.socket.request.marketdata.TopsAsyncRequestBuilder;

import java.util.function.Consumer;

@Service
public class TrackerService {

    public void getTicker() {
        final IEXTradingClient iexTradingClient = IEXTradingClient.create();
        final Consumer<TOPS> TOPS_CONSUMER = System.out::println;
        final SocketRequest<TOPS> request = new TopsAsyncRequestBuilder()
                .withAllSymbols()
                .build();

        iexTradingClient.subscribe(request, TOPS_CONSUMER);
    }
}
