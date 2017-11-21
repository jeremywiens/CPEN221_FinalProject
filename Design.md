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
Abstraction function: Will exist as a Hashmap of types (ex. user_id) which point to the values of these types (ex. 581894134)
Rep Invariant: All individual users can only review a product once, and have unique user_id within each dataset
//Constructor
User(String s)
getUsername()
getAvgRating()
getProducts()
get...
equals()
hashCode()

Methods for Review
Abstraction function: Will exist as a Hashmap of types (ex. rating) which point to the values of these types (ex. 4/5)
Rep Invariant: All individual product reviews must be made by a different user
//Constructor
Review(String s)
getRating()
getReview()
get....
equals()
hashCode()

Methods for Product
Abstraction function: Will exist as a Hashmap of types (ex. Product_name) which point to the values of these types (ex. Fidget Spinner)
Rep Invariant: Must be distinct from other products within a datatype
//Constructor
Product(String s)
getName()
getRatings()
get....
equals()
hashcode()

Methods for Restaurant
//Abstraction function: Restaraunts will extend products
//Rep invariant: Each restaraunt has one distinct location. 
//Constructor
Restaurant(String s)
getLocation()
Product parent methods
get(Any information which isn't contained in product and should be included)

YelpDB
//Abstraction function: YelpDB will exist as three lists. Each will be a list of Restaurants, Users, or reviews.
//Rep Invariant: Each list must contain a form of distinct restaruants, users and reviews.

//Constructor
YelpD5(String filename1, String filename2, String filename3)
//the parser will use JSON and call the constructors for Restaurant, User, and review.
parse()
Interface methods


The Review, User, and Product classes are all usable by any other dataset which has a product that is reviewed by users.
