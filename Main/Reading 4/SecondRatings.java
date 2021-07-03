import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<Rater> myRaters;
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        myMovies = fr.loadMovies(moviefile);
        myRaters = fr.loadRaters(ratingsfile);
    }
    
    public int getMovieSize() {
        return myMovies.size();
    }
    
    public int getRaterSize() {
        return myRaters.size();
    }
    
    public double getAverageByID(String id, int minimalRaters) {
        int totalRaters = 0;
        double totalRating = 0.0;
        for (Rater r : myRaters) {
            double rating = r.getRating(id);
            if (rating != -1) {
                ++totalRaters;
                totalRating += rating;
            }
        }
        if (totalRaters < minimalRaters) return 0.0;
        return (totalRating / totalRaters);
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
        ArrayList<Rating> al = new ArrayList<Rating>();
        for (Movie m : myMovies) {
            String id = m.getID();
            double rating = getAverageByID(id, minimalRaters);
            if (rating != 0.0) al.add(new Rating(id, rating));
        }
        return al;
    }
    
    public String getTitle(String id) {
        for (Movie m : myMovies) {
            if (m.getID().equals(id)) return m.getTitle();
        }
        return "\nID was Not Found";
    }
    
    public String getID(String title) {
        for (Movie m : myMovies) {
            if (m.getTitle().equals(title)) return m.getID();
        }
        return "NO SUCH TITLE.";
    }
    
}