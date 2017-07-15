package kata;

/**
 * Created by twer on 7/15/17.
 */
public class Rating {
    private int rating;

    public Rating() {
    }

    public Rating(int rating) {
        this.rating = rating;
    }
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        return this.rating == rating.rating;
    }

    @Override
    public int hashCode() {
        return rating;
    }
}
