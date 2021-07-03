import java.util.*;

public class MovieRunnerSimilarRatings {
    
    public void printAverageRatings() {
        FourthRatings tr = new FourthRatings(); 
        RaterDatabase.addRatings("ratings.csv");
        System.out.println("\nNumber of Raters: " + RaterDatabase.size()); 
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("\nNumer of Movies: " + MovieDatabase.size());
        ArrayList<Rating> filtered = tr.getAverageRatings(35); // minimal number of raters should be in the argument
        filtered = sortByRating(filtered);
        System.out.println("\nNumber of Movies with minimal Raters are: " + filtered.size());
        System.out.println();
        for (Rating r : filtered) {
            System.out.printf("%.4f ", r.getValue());
            System.out.println(MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        AllFilters af = new AllFilters();
        YearAfterFilter yf = new YearAfterFilter(1990);
        GenreFilter gf = new GenreFilter("Drama");
        af.addFilter(yf); af.addFilter(gf);
        FourthRatings tr = new FourthRatings(); 
        RaterDatabase.initialize("ratings.csv");
        System.out.println("\nNumber of Raters: " + RaterDatabase.size()); 
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("\nNumer of Movies: " + MovieDatabase.size());
        ArrayList<Rating> filtered = tr.getAverageRatingsByFilter(8, af); // minimal number of raters should be in the argument
        filtered = sortByRating(filtered);
        System.out.println("\nNumber of Movies with minimal Raters are: " + filtered.size());
        System.out.println();
        for (Rating r : filtered) {
            System.out.printf("%.4f %d ", r.getValue(), MovieDatabase.getYear(r.getItem()));
            System.out.print(MovieDatabase.getTitle(r.getItem()));
            System.out.println(" [" + MovieDatabase.getGenres(r.getItem()) + "]");
        }
    }
    
    public ArrayList<Rating> sortByRating(ArrayList<Rating> al) {
        for (int i = 0; i < al.size(); ++i) {
            for (int j = i+1; j < al.size(); ++j) {
                if (al.get(i).getValue() > al.get(j).getValue()) {
                    Rating temp = al.get(i);
                    al.set(i, al.get(j));
                    al.set(j, temp);
                }
            }
        }
        return al;
    }
    
    private double dotProduct(Rater me, Rater r) {
        ArrayList<String> myRatings = me.getItemsRated();
        double dotRating = 0.0;
        for (String str : r.getItemsRated()) {
            if (myRatings.contains(str)) {
                double x = me.getRating(str) - 5.0;
                double y = r.getRating(str) - 5.0;
                dotRating += (x*y);
            }
        }
        return dotRating;
    }
    
    private ArrayList<Rating> getSimilarities(String id) {
        ArrayList<Rating> al = new ArrayList<Rating>();
        Rater me = RaterDatabase.getRater(id);
        
        for (Rater r : RaterDatabase.getRaters()) {
            double product = 0.0;
            if (! r.getID().equals(id)) {
                product = dotProduct(me, r);
            }
            if (product > 0.0) {
                al.add(new Rating(r.getID(), product));
            }
        }
        Collections.sort(al, Collections.reverseOrder());
        // System.out.println(al);
        return al;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
        ArrayList<Rating> al = new ArrayList<Rating>();
        ArrayList<Rating> smlrtyRating = getSimilarities(id);
        
        for (String mve : MovieDatabase.filterBy(new TrueFilter())) {
            // if (RaterDatabase.getRater(id).getItemsRated().contains(mve)) {
                // continue; 
            // }
            double sum = 0.0;
            int count = 0;
            
            for (int i = 0; i < numSimilarRaters; ++i) {
                Rating r = smlrtyRating.get(i);
                Rater rtr = RaterDatabase.getRater(r.getItem());
                if (rtr.hasRating(mve)) {
                    ++count;
                    double prod = r.getValue() * rtr.getRating(mve);
                    sum += prod;
                }
            }
            
            if (count >= minimalRaters) {
                al.add(new Rating(mve, (sum/count)));
            }
        }
        Collections.sort(al, Collections.reverseOrder());
        return al;
    }
    
    public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, int minimalRaters, Filter filterCriteria) {
        ArrayList<Rating> al = new ArrayList<Rating>();
        ArrayList<Rating> smlrtyRating = getSimilarities(id);
        
        for (String mve : MovieDatabase.filterBy(filterCriteria)) {
            double sum = 0.0;
            int count = 0;
            
            for (int i = 0; i < numSimilarRaters; ++i) {
                Rating r = smlrtyRating.get(i);
                Rater rtr = RaterDatabase.getRater(r.getItem());
                if (rtr.hasRating(mve)) {
                    ++count;
                    double prod = r.getValue() * rtr.getRating(mve);
                    sum += prod;
                }
            }
            if (count >= minimalRaters) {
                al.add(new Rating(mve, (sum/count)));
            }
        }
        Collections.sort(al, Collections.reverseOrder());
        return al;
    }
    
    public void printSimilarRatings() {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.addRatings("ratings.csv");
        ArrayList<Rating> recommendations = getSimilarRatings("71", 20, 5);
        System.out.println(MovieDatabase.getTitle(recommendations.get(0).getItem()));
    }
    
    public void printSimilarRatingsByGenre() {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.addRatings("ratings.csv");
        GenreFilter gf = new GenreFilter("Mystery");
        ArrayList<Rating> recommendations = getSimilarRatingsByFilter("964", 20, 5, gf);
        System.out.println(MovieDatabase.getTitle(recommendations.get(0).getItem()));  
    }
    
    public void printSimilarRatingsByDirector() {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.addRatings("ratings.csv");
        DirectorsFilter df = new DirectorsFilter("Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh");
        ArrayList<Rating> recommendations = getSimilarRatingsByFilter("120", 10, 2, df);
        System.out.println(MovieDatabase.getTitle(recommendations.get(0).getItem()));
    }
    
    public void printSimilarRatingsByGenreAndMinutes() {
        AllFilters af = new AllFilters();
        GenreFilter gf = new GenreFilter("Drama");
        MinutesFilter mf = new MinutesFilter(80, 160);
        af.addFilter(gf); af.addFilter(mf);
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.addRatings("ratings.csv");
        ArrayList<Rating> recommendations = getSimilarRatingsByFilter("168", 10, 3, af);
        System.out.println(MovieDatabase.getTitle(recommendations.get(0).getItem()));
    }
    
    public void printSimilarRatingsByYearAfterAndMinutes() {
        AllFilters af = new AllFilters();
        YearAfterFilter yf = new YearAfterFilter(1975);
        MinutesFilter mf = new MinutesFilter(70, 200);
        af.addFilter(yf); af.addFilter(mf);
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        RaterDatabase.addRatings("ratings.csv");
        ArrayList<Rating> recommendations = getSimilarRatingsByFilter("314", 10, 5, af);
        System.out.println(MovieDatabase.getTitle(recommendations.get(0).getItem()));
    }
    
}
