package pl.kskowronski.freemapsmonitoringboats.services;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

public class MyErrorHandler extends DefaultResponseErrorHandler {

    private TrackService trackService;

    public MyErrorHandler(TrackService trackService) {
        this.trackService = trackService;
    }


    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().value() == 401) {
            trackService.getToken();
        }
    }


}
