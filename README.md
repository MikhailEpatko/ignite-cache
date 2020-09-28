# ignite-cache
The application for downloading dataset, parsing it and setting to the Apache Ignite Data Grid cache.

The application is a service working in the Apache Ignite Service Grid environment.

## Dataset
The dataset which application working for is "Small subset for experimentation" from Amazon named Electronics:
 http://snap.stanford.edu/data/amazon/productGraph/categoryFiles/reviews_Electronics_5.json.gz

These data have been reduced to extract the k-core, such that each of the remaining users and items have k reviews each.

Example:

{

  "reviewerID": "A2SUAM1J3GNN3B",
  
  "asin": "0000013714",
  
  "reviewerName": "J. McDonald",
  
  "helpful": [2, 3],
  
  "reviewText": "I bought this for my husband who plays the piano.  He is having a wonderful time playing these old hymns.  
  The music  is at times hard to read because we think the book was published for singing from more than playing from.  
  Great purchase though!",
  
  "overall": 5.0,
  
  "summary": "Heavenly Highway Hymns",
  
  "unixReviewTime": 1252800000,
  
  "reviewTime": "09 13, 2009"
  
}
