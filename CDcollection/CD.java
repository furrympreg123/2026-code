/**
 * This is the blueprint for CD objects
 *
 * @author Kanya Farley
 * @version 05/08/26
 */
public class CD
{
    // instance variables
    private String artistName;
    private String albumName;
    int releaseYear;
    int runTime; // in mins
    double rating; // 0.0 to 5.0
    double value; // $

    /**
     * Constructor for objects of class CD
     */
    public CD(String artistName, String albumName, int releaseYear, int runTime)
    {
        this.artistName = artistName;
        this.albumName = albumName;
        this.releaseYear = releaseYear;
        this.runTime = runTime;
    }
    
    public String getArtistName() {
        return(this.artistName);
    }
    
    public String getAlbumName() {
        return(this.albumName);
    }
    
    public int getReleaseYear() {
        return(this.releaseYear);
    }
    
    public int getRunTime() {
        return(this.releaseYear);
    }
}
