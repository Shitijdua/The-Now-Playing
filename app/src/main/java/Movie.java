import android.widget.Button;

public class Movie {
    private String movieTitle;
    private int movieImageId;
   private Button like;

    public Movie(String movieTitle, int movieImageId, Button like) {
        this.movieTitle = movieTitle;
        this.movieImageId = movieImageId;
        this.like = like;

    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public int getMovieImageId() {
        return movieImageId;
    }

    public Button getLike() {
        return like;
    }

}
