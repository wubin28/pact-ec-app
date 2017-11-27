package kata;

public class Rating {
    private String productId;
    private String userName;
    private String rating;

    public Rating() {
    }

    public Rating(String productId, String userName, String rating) {
        this.productId = productId;
        this.userName = userName;
        this.rating = rating;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating1 = (Rating) o;

        if (productId != null ? !productId.equals(rating1.productId) : rating1.productId != null) return false;
        if (userName != null ? !userName.equals(rating1.userName) : rating1.userName != null) return false;
        return rating != null ? rating.equals(rating1.rating) : rating1.rating == null;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        return result;
    }
}
