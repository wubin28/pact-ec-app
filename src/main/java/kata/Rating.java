package kata;

/**
 * Created by twer on 7/15/17.
 */
public class Rating {
    private int fiveStarRating;

    public Rating() {
    }

    public Rating(int fiveStarRating) {
        this.fiveStarRating = fiveStarRating;
    }
    public int getFiveStarRating() {
        return fiveStarRating;
    }

    public void setFiveStarRating(int fiveStarRating) {
        this.fiveStarRating = fiveStarRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        return fiveStarRating == rating.fiveStarRating;
    }

    @Override
    public int hashCode() {
        return fiveStarRating;
    }
}
