package id.hci.api.dao.model;

import java.sql.Timestamp;

public class Movie {

    public int id_movie;
    public String movie_title;
    public String release_year;
    public String description;
    public String image_video;
    public int status;
    public Timestamp created_date;
    public int id_user;
    public int serial;
    public int id_serial;

    public int getId_movie() {
        return id_movie;
    }

    public void setId_movie(int id_movie) {
        this.id_movie = id_movie;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getRelease_year() {
        return release_year;
    }

    public void setRelease_year(String release_year) {
        this.release_year = release_year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_video() {
        return image_video;
    }

    public void setImage_video(String image_video) {
        this.image_video = image_video;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Timestamp getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Timestamp created_date) {
        this.created_date = created_date;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getId_serial() {
        return id_serial;
    }

    public void setId_serial(int id_serial) {
        this.id_serial = id_serial;
    }
}
