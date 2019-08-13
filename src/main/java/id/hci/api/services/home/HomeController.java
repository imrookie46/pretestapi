package id.hci.api.services.home;

import id.hci.api.dao.BaseDaoTemplate;
import id.hci.api.dao.model.Customer;
import id.hci.api.dao.model.Movie;
import id.hci.api.helper.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class HomeController {

    @Autowired
    BaseDaoTemplate baseDaoTemplate;

    @GetMapping("home")
    public ResponseEntity home(
            Principal principal) {

        Customer customer = baseDaoTemplate.getCustomerDetail(principal.getName());
        List<Movie> hot = baseDaoTemplate.getMovieCategory(1);
        List<Movie> trending = baseDaoTemplate.getMovieCategory(2);
        List<Movie> history = baseDaoTemplate.getHistoryMovie(customer.getId_customer());

        Map<String, Object> datas = new HashMap<>();
        datas.put("hot", hot);
        datas.put("trending", trending);
        datas.put("history", history);

        ResponseApi responseApi = ResponseApi.builder().status(0)
                .message("ok")
                .data(datas)
                .build();

        return ResponseEntity.ok(responseApi);
    }

    @GetMapping("movie-serial-{id_movie}")
    public ResponseEntity Movie(@PathVariable("id_movie")int movieId) {

        List<Movie> movies = baseDaoTemplate.getMovieSerial(movieId);
        Map<String, Object> datas = new HashMap<>();
        datas.put("movie", movies);

        ResponseApi responseApi = ResponseApi.builder().status(0)
                .message("ok")
                .data(datas)
                .build();

        return ResponseEntity.ok(responseApi);
    }

    @GetMapping("movie-play-{id_serial}-{id_movie}")
    public ResponseEntity Movie( Principal principal,@PathVariable("id_serial")int idSerial,
                        @PathVariable("id_movie")int movieId) {

        List<Movie> movies = baseDaoTemplate.getMovieSerialExclude(movieId,idSerial);
        String pathVideo = baseDaoTemplate.getPathVideo(idSerial);
        Map<String, Object> datas = new HashMap<>();
        datas.put("movie", movies);
        datas.put("path_video",pathVideo);
        Customer customer = baseDaoTemplate.getCustomerDetail(principal.getName());
        try {
            baseDaoTemplate.saveHistory(customer.getId_customer(), idSerial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseApi responseApi = ResponseApi.builder().status(0)
                .message("ok")
                .data(datas)
                .build();

        return ResponseEntity.ok(responseApi);
    }
}
