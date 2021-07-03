
public class GenreFilter implements Filter{
    String genre;
    
    public GenreFilter(String genre) {
        this.genre = genre;
    }
    
    public boolean satisfies(String id) {
        String allGenres = MovieDatabase.getGenres(id);
        if (allGenres.contains(genre)) return true;
        return false;
    }
    
}
