import java.util.*;

public class MovieRunnerWithFilters {
    
    public void printAverageRatings() {
        ThirdRatings tr = new ThirdRatings(); 
        System.out.println("\nNumber of Raters: " + tr.getRaterSize()); 
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
    
    public void printAverageRatingsByYear() {
        YearAfterFilter yf = new YearAfterFilter(2000); // the year you want to filter with
        ThirdRatings tr = new ThirdRatings(); 
        System.out.println("\nNumber of Raters: " + tr.getRaterSize()); 
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("\nNumer of Movies: " + MovieDatabase.size());
        ArrayList<Rating> filtered = tr.getAverageRatingsByFilter(20 , yf); // minimal number of raters should be in the argument
        filtered = sortByRating(filtered);
        System.out.println("\nNumber of Movies with minimal Raters are: " + filtered.size());
        System.out.println();
        for (Rating r : filtered) {
            System.out.printf("%.4f %d ", r.getValue(), MovieDatabase.getYear(r.getItem()));
            System.out.println(MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public void printAverageRatingsByGenre() {
        GenreFilter gf = new GenreFilter("Comedy"); // the year you want to filter with
        ThirdRatings tr = new ThirdRatings(); 
        System.out.println("\nNumber of Raters: " + tr.getRaterSize()); 
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("\nNumer of Movies: " + MovieDatabase.size());
        ArrayList<Rating> filtered = tr.getAverageRatingsByFilter(20, gf); // minimal number of raters should be in the argument
        filtered = sortByRating(filtered);
        System.out.println("\nNumber of Movies with minimal Raters are: " + filtered.size());
        System.out.println();
        for (Rating r : filtered) {
            System.out.printf("%.4f ", r.getValue());
            System.out.print(MovieDatabase.getTitle(r.getItem()) + " [");
            System.out.println(MovieDatabase.getGenres(r.getItem()) + "]");
        }
    }
    
    public void printAverageRatingsByMinutes() {
        MinutesFilter mf = new MinutesFilter(105, 135); // the year you want to filter with
        ThirdRatings tr = new ThirdRatings(); 
        System.out.println("\nNumber of Raters: " + tr.getRaterSize()); 
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("\nNumer of Movies: " + MovieDatabase.size());
        ArrayList<Rating> filtered = tr.getAverageRatingsByFilter(5, mf); // minimal number of raters should be in the argument
        filtered = sortByRating(filtered);
        System.out.println("\nNumber of Movies with minimal Raters are: " + filtered.size());
        System.out.println();
        for (Rating r : filtered) {
            System.out.printf("%.4f Time: %d ", r.getValue(), MovieDatabase.getMinutes(r.getItem()));
            System.out.println(MovieDatabase.getTitle(r.getItem()));
        }
    }
    
    public void printAverageRatingsByDirectors() {
        DirectorsFilter df = new DirectorsFilter("Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack"); // the year you want to filter with
        ThirdRatings tr = new ThirdRatings(); 
        System.out.println("\nNumber of Raters: " + tr.getRaterSize()); 
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("\nNumer of Movies: " + MovieDatabase.size());
        ArrayList<Rating> filtered = tr.getAverageRatingsByFilter(4, df); // minimal number of raters should be in the argument
        filtered = sortByRating(filtered);
        System.out.println("\nNumber of Movies with minimal Raters are: " + filtered.size());
        System.out.println();
        for (Rating r : filtered) {
            System.out.printf("%.4f ", r.getValue());
            System.out.print(MovieDatabase.getTitle(r.getItem()));
            System.out.println(" [" + MovieDatabase.getDirector(r.getItem()) + "]");
        }
    }
    
    public void printAverageRatingsByYearAfterAndGenre() {
        AllFilters af = new AllFilters();
        YearAfterFilter yf = new YearAfterFilter(1990);
        GenreFilter gf = new GenreFilter("Drama");
        af.addFilter(yf); af.addFilter(gf);
        ThirdRatings tr = new ThirdRatings(); 
        System.out.println("\nNumber of Raters: " + tr.getRaterSize()); 
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
    
    public void printAverageRatingsByDirectorsAndMinutes() {
        AllFilters af = new AllFilters();
        DirectorsFilter df = new DirectorsFilter("Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack");
        MinutesFilter mf = new MinutesFilter(90, 180);
        af.addFilter(df); af.addFilter(mf);
        ThirdRatings tr = new ThirdRatings(); 
        System.out.println("\nNumber of Raters: " + tr.getRaterSize()); 
        MovieDatabase.initialize("ratedmoviesfull.csv");
        System.out.println("\nNumer of Movies: " + MovieDatabase.size());
        ArrayList<Rating> filtered = tr.getAverageRatingsByFilter(3, af); // minimal number of raters should be in the argument
        filtered = sortByRating(filtered);
        System.out.println("\nNumber of Movies with minimal Raters are: " + filtered.size());
        System.out.println();
        for (Rating r : filtered) {
            System.out.printf("%.4f Time: %d ", r.getValue(), MovieDatabase.getMinutes(r.getItem()));
            System.out.print(MovieDatabase.getTitle(r.getItem()));
            System.out.println(" [" + MovieDatabase.getDirector(r.getItem()) + "]");
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

}
