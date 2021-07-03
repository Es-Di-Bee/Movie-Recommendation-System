import java.util.*;

public class ThirdRatings {
    private ArrayList<Rater> myRaters;
    
    public ThirdRatings() {
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
        FirstRatings fr = new FirstRatings();
        myRaters = fr.loadRaters(ratingsfile);
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
        ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
        ArrayList<Rating> al = new ArrayList<Rating>();
        for (String id : movies) {
            double rating = getAverageByID(id, minimalRaters);
            if (rating != 0.0) al.add(new Rating(id, rating));
        }
        return al;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> al = new ArrayList<Rating>();
        ArrayList<String> movies = MovieDatabase.filterBy(filterCriteria);
        for (String id : movies) {
            double rating = getAverageByID(id, minimalRaters);
            if (rating != 0.0) al.add(new Rating(id, rating));
        }
        return al;
    }
    
}
