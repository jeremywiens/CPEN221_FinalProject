What are the methods?
What is the representation?
What are the rep invariants and abstraction functions?
Think about extensibility.
Can your design be used beyond the Yelp dataset and the algorithms needed for MP5?
Are you using subtypes to allow for reuse/extensibility?

When do we parse?

5 different datatypes : Product, Review, User, YelpD5, Restaurants

Product will represent any product which has user reviews
Restaurant will extend Product and be the specific Product used by YelpD5
Review will be a Review for any product given by a user
User will represent an account which has given review(s) for a product
YelpD5 will implement all of these interfaces as well as implement the MPD5 interface

Methods for User
User(String s)
getUsername()
getAvgRating()
getProducts()
get...
equals()
hashCode()

Methods for Review
Review(String s)
getRating()
getReview()
get....
equals()
hashCode()

Methods for Product
Product(String s)
getLocation()
getName()
getRatings()
get....
equals()
hashcode()

Methods for Restaurant
Restaurant(String s)
Product interface methods
get(Any information which isn't contained in product and should be included)

YelpD5
YelpD5(String s)
Interface methods

The Review, User, and Product classes are all usable by any other dataset which has a product that is reviewed by users.
