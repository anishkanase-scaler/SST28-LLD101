package models;

public class Movie {
    private final String movieId;
    private String title;
    private String genre;
    private int durationMinutes;
    private String language;
    private double rating; // e.g. IMDb rating

    public Movie(String movieId, String title, String genre, int durationMinutes,
                 String language, double rating) {
        this.movieId         = movieId;
        this.title           = title;
        this.genre           = genre;
        this.durationMinutes = durationMinutes;
        this.language        = language;
        this.rating          = rating;
    }

    public String getMovieId()        { return movieId; }
    public String getTitle()          { return title; }
    public String getGenre()          { return genre; }
    public int getDurationMinutes()   { return durationMinutes; }
    public String getLanguage()       { return language; }
    public double getRating()         { return rating; }

    public void setTitle(String title)               { this.title = title; }
    public void setGenre(String genre)               { this.genre = genre; }
    public void setDurationMinutes(int d)            { this.durationMinutes = d; }
    public void setLanguage(String language)         { this.language = language; }
    public void setRating(double rating)             { this.rating = rating; }
}
