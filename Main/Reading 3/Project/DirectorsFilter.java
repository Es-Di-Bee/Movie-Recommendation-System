
public class DirectorsFilter implements Filter{
    String directors;
    
    public DirectorsFilter(String d) {
        directors = d;
    }
    
    public boolean satisfies(String id) {
        String str = MovieDatabase.getDirector(id);
        String[] dir = directors.split(",");
        for (String d : dir) {
            if (str.contains(d)) return true;
        }
        return false;
    }

}
