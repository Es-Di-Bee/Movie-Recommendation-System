import java.util.*;

public class RecommendationRunner implements Recommender {
    ArrayList<String> moviesToRate = new ArrayList<String>();
    
    public ArrayList<String> getItemsToRate() {
        ArrayList<String> ids = new ArrayList<String>();
        ids.add("0111161");
        ids.add("0468569");
        ids.add("1375666");
        ids.add("0109830");
        ids.add("0816692");
        ids.add("4154756");
        ids.add("0482571");
        ids.add("0103064");
        ids.add("0095327");
        ids.add("0110357");
        ids.add("1345836");
        ids.add("1187043");
        ids.add("0910970");
        ids.add("0209144");
        ids.add("0435761");
        for (int i = 0 ; i < ids.size(); ++i) moviesToRate.add(ids.get(i));
        return ids;
    }
    
    public void printRecommendationsFor(String webRaterID) {
        FourthRatings fr = new FourthRatings();
        MovieDatabase.initialize("ratedmoviesfull.csv");
        // RaterDatabase.addRatings("ratings.csv");
        try {
            ArrayList<Rating> recommendations = getSimilarRatings(webRaterID, 50, 5);
            System.out.println("<table>");
            for (int i = 0; i < recommendations.size(); ++i) {
                if (moviesToRate.contains(recommendations.get(i).getItem())) continue;
                System.out.println("<tr><td class=\"movieTitle\">"+ 
                                    MovieDatabase.getTitle(recommendations.get(i).getItem()) + "</td></tr>");
            }
            System.out.println("</table>");
        } catch (Exception e) {
            System.out.println("No recommendations found, Please go back and rate more movies for proper recommendation");
            return;
        }
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
        return al;
    }
    
    public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) throws Exception {
        try {
            ArrayList<Rating> al = new ArrayList<Rating>();
            ArrayList<Rating> smlrtyRating = getSimilarities(id);
            
            for (String mve : MovieDatabase.filterBy(new TrueFilter())) {
                if (moviesToRate.contains(mve)) {
                    continue; 
                }
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
        } catch (Exception e) {
            throw new Exception();
        }
        
    }
    
}
