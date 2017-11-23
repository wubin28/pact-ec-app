package kata;

public class Rating {
    private int productId;
    private String userName;
    private int rating;

    public Rating() {
    }

    public Rating(int productId, String userName, int rating) {
        this.productId = productId;
        this.userName = userName;
        this.rating = rating;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

        Rating rating1 = (Rating) o;

        if (productId != rating1.productId) return false;
        if (rating != rating1.rating) return false;
        return userName != null ? userName.equals(rating1.userName) : rating1.userName == null;
    }

    @Override
    public int hashCode() {
        int result = productId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + rating;
        return result;
    }
}
