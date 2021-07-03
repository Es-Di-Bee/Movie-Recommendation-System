import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;
import java.io.*;

public class MovieRunnerAverage {
    
    public void printAverageRatings() {
        SecondRatings sr = new SecondRatings();
        System.out.println("\nNumber of Movies: " + sr.getMovieSize());
        System.out.println("\nNumber of Raters: " + sr.getRaterSize()); 
        // System.out.println(sr.getAverageRatings(4));
        ArrayList<Rating> filtered = sr.getAverageRatings(12);
        filtered = sortByRating(filtered);
        System.out.println();
        for (Rating r : filtered) {
            System.out.println(r.getValue() + " " + sr.getTitle(r.getItem()));
        }
    }
    
    public void getAverageRatingOneMovie() {
        Scanner sc = new Scanner(System.in);
        String movieName = sc.nextLine();
        SecondRatings sr = new SecondRatings();
        double rating = sr.getAverageByID(sr.getID(movieName), 0);
        System.out.println("\nThe Rating for the Movie " + movieName + 
                           " is: " + rating);
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
