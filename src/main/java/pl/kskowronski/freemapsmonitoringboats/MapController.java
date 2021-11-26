package pl.kskowronski.freemapsmonitoringboats;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kskowronski.freemapsmonitoringboats.services.TrackService;

@Controller
@RequestMapping("/map")
public class MapController {

    private TrackService trackService;

    public MapController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public String getMap(Model model) {
        model.addAttribute("tracks", trackService.getTracks());
        return "map";
    }


}
