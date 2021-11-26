package pl.kskowronski.freemapsmonitoringboats.services;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import pl.kskowronski.freemapsmonitoringboats.models.Datum;
import pl.kskowronski.freemapsmonitoringboats.models.Point;
import pl.kskowronski.freemapsmonitoringboats.models.Token;
import pl.kskowronski.freemapsmonitoringboats.models.Track;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TrackService {

    Token token = new Token();
    RestTemplate restTemplate = new RestTemplate();

    public void getToken() {
        HttpHeaders httpHeaders = new HttpHeaders();
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("client_id", "tetojiv972@nefacility.com:klaudiusz");
        body.add("client_secret", "polibuda????");
        body.add("grant_type", "client_credentials");
        body.add("scope", "api");

        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, httpHeaders);

        ResponseEntity<Token> exchange = restTemplate.exchange("https://id.barentswatch.no/connect/token",
                HttpMethod.POST,
                httpEntity,
                Token.class);
        token = exchange.getBody();
    }

    public List<Point> getTracks() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + token.getAccessToken());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        restTemplate.setErrorHandler(new MyErrorHandler(this));

        ResponseEntity<Track[]> exchange = restTemplate.exchange("https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?Xmin=10.09094&Xmax=10.67047&Ymin=63.3989&Ymax=63.58645",
                HttpMethod.GET,
                httpEntity,
                Track[].class);

        List<Point> collect = Stream.of(exchange.getBody()).map(track -> {

            Datum datum = getDestination(track.getDestination(), track.getGeometry().getCoordinates());

             return new Point(track.getGeometry().getCoordinates().get(1),
                    track.getGeometry().getCoordinates().get(0),
                    track.getName(),
                    track.getDestination(),
                    datum.getLatitude(),
                    datum.getLongitude());
        }).collect(Collectors.toList());

        return collect;
    }

    public Datum getDestination(String destinationName, List<Double> coordinates) {
        try {
            String url = "http://api.positionstack.com/v1/forward?access_key=f9aae45e031a1e66eac64db90ffda427&query=" + destinationName;
            JsonNode data = restTemplate.getForObject(url, JsonNode.class).get("data").get(0);
            double latitude = data.get("latitude").asDouble();
            double longitude = data.get("longitude").asDouble();
            return new Datum(latitude, longitude);

        } catch (Exception ex) {
            return new Datum(coordinates.get(1), coordinates.get(0));
        }
    }
}
