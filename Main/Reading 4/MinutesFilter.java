
public class MinutesFilter implements Filter {
    int minMinute = 0;
    int maxMinute = 0;
    
    public MinutesFilter(int minMinute, int maxMinute) {
        this.minMinute = minMinute;
        this.maxMinute = maxMinute;
    }
    
    public boolean satisfies(String id) {
        int minute = MovieDatabase.getMinutes(id);
        if (minute >= minMinute && minute <= maxMinute) return true;
        return false;
    }

}
