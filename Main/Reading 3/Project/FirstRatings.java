import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class FirstRatings {
    
    public ArrayList<Movie> loadMovies(String filename) { 
        ArrayList<Movie> al = new ArrayList<Movie>();
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            Movie mv = new Movie(record.get("id"), record.get("title"),
                       record.get("year"), record.get("genre"), record.get("director"),
                       record.get("country"), record.get("poster"), Integer.parseInt(record.get("minutes")));
            al.add(mv);
        }
        return al;
    }
    
    public ArrayList<Rater> loadRaters(String filename) {
        ArrayList<Rater> al = new ArrayList<Rater>();
        FileResource fr = new FileResource(filename);
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            String raterId = record.get("rater_id");
            boolean flag = false;
            int foundAt = 0;
            for (int i = 0; i < al.size(); ++i) {
                if (al.get(i).getID().equals(raterId)) {
                    foundAt = i;
                    flag = true;
                    break;
                }
            }
            if (! flag) {
                Rater rtr = new EffecientRater(raterId);
                rtr.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
                al.add(rtr);
            } else {
                Rater rtr = al.get(foundAt);
                rtr.addRating(record.get("movie_id"), Double.parseDouble(record.get("rating")));
            }
        }
        return al;
    }
    
    public void testLoadMovies() {
        ArrayList<Movie> mveRecords = loadMovies("ratedmoviesfull.csv");
        HashMap<String, Integer> drctrRecords = new HashMap<String, Integer>();
        System.out.println(mveRecords.size());
        // System.out.println(mveRecords);
        int cmdy = 0, lngth = 0, mx = 0;
        for (Movie mv : mveRecords) {
            if (mv.getGenres().contains("Comedy")) ++cmdy;
            if (mv.getMinutes() > 150) ++lngth;
            String str = mv.getDirector();
            String[] directors = str.split(", ");
            for (String x : directors) {
                if (drctrRecords.containsKey(x)) {
                    drctrRecords.put(x, drctrRecords.get(x)+1);
                } else {
                    drctrRecords.put(x, 1);                    
                }
                mx = Math.max(mx, drctrRecords.get(x));
            }
        }
        System.out.println("There are " + cmdy + " comedy genre movies\n" +
                            "There are " + lngth + " movies having length greater than 150 minutes");
        System.out.println("The maximum number of movies directed by any director is: " + 
                            mx);
        ArrayList<String> maxMovieDirectors = new ArrayList<String>();
        for (String x : drctrRecords.keySet()) {
            if (drctrRecords.get(x) == mx) maxMovieDirectors.add(x);
        }
        System.out.println("Number of directors who directed the maximum number of movies is: " +
                           maxMovieDirectors.size() + "\nThe name of that director is: " + 
                           maxMovieDirectors.get(0));
    }
    
    public void testLoadRaters() {
        ArrayList<Rater> rtrRecords = loadRaters("ratings.csv");
        for (Rater r : rtrRecords) {
            System.out.println("\nRater's ID is: " + r.getID() + 
                               "\tRatings Given: " + r.numRatings());
            ArrayList<String> temp = r.getItemsRated();
            for (String str : temp) {
                System.out.println("Movie ID is: " + str + 
                                   "\tRating given: " + r.getRating(str));
            }
        }
        System.out.println("\nNumber of raters are: " + rtrRecords.size());
        
        maxRatingByARater(rtrRecords);
        ratingOnAMovie(rtrRecords);
        differentRatedMovies(rtrRecords);
    }
    
    private void maxRatingByARater(ArrayList<Rater> rtrRecords) {
        int mx = 0;
        for (Rater r : rtrRecords) {
            mx = Math.max(mx, r.numRatings());
        }
        for (Rater r : rtrRecords) {
            if (r.numRatings() == mx) {
                System.out.println("\nRater " + r.getID() + " has Maximum " + mx + " number of Ratings");
            }
        }
    }
    
    private void ratingOnAMovie(ArrayList<Rater> rtrRecords) {
        System.out.println("\nPlease enter the MOVIE ID");
        Scanner sc = new Scanner(System.in);
        String id = sc.next();
        int ratedBy = 0;
        for (Rater r : rtrRecords) {
            if (r.hasRating(id)) ++ratedBy;
        }
        System.out.println("\nThis movie is Rated by " + ratedBy + " raters");
    } 
    
    private void differentRatedMovies(ArrayList<Rater> rtrRecords) {
        ArrayList<String> difMovies = new ArrayList<String>();
        for (Rater r : rtrRecords) {
            ArrayList<String> temp = r.getItemsRated();
            for (String str : temp) {
                if (! difMovies.contains(str)) difMovies.add(str);
            }
        }
        System.out.println("\nThere are " + difMovies.size() + " different movies rated");
    } 

}
