package ca.ece.ubc.cpen221.mp5.database;

public class User
{
    private String review_count;

    private String name;

    private Votes votes;

    private String user_id;

    private String type;

    private String url;

    private String average_stars;

    public String getReview_count ()
    {
        return review_count;
    }

    public void setReview_count (String review_count)
    {
        this.review_count = review_count;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public Votes getVotes ()
    {
        return votes;
    }

    public void setVotes (Votes votes)
    {
        this.votes = votes;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    public String getAverage_stars ()
    {
        return average_stars;
    }

    public void setAverage_stars (String average_stars)
    {
        this.average_stars = average_stars;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [review_count = "+review_count+", name = "+name+", votes = "+votes+", user_id = "+user_id+", type = "+type+", url = "+url+", average_stars = "+average_stars+"]";
    }
}
