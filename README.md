
## Task ##

We would like you to write code that will cover the functionality explained below and provide us with the source, instructions to build and run the application, as well as a sample output of an execution:

+ Connect to [Twitter Streaming API 1.1](https://developer.twitter.com/en/docs/twitter-api/v1/tweets/filter-realtime/overview)
    * Use the following values:
        + Consumer Key: `RLSrphihyR4G2UxvA0XBkLAdl`
        + Consumer Secret: `FTz2KcP1y3pcLw0XXMX5Jy3GTobqUweITIFy4QefullmpPnKm4`
    * The app name will be `java-exercise`
    * You will need to login with Twitter
+ Filter messages that track on "bieber"
+ Retrieve the incoming messages for 30 seconds or up to 100 messages, whichever comes first
+ Your application should return the messages grouped by user (users sorted chronologically, ascending)
+ The messages per user should also be sorted chronologically, ascending
+ For each message, we will need the following:
    * The message ID
    * The creation date of the message as epoch value
    * The text of the message
    * The author of the message
+ For each author, we will need the following:
    * The user ID
    * The creation date of the user as epoch value
    * The name of the user
    * The screen name of the user
+ All the above information is provided in either Standard output, or a log file
+ You are free to choose the output format, provided that it makes it easy to parse and process by a machine

### __Bonus points for:__ ###

+ Keep track of messages per second statistics across multiple runs of the application
+ The application can run as a Docker container

## Provided functionality ##

The present project in itself is a [Maven project](http://maven.apache.org/) that contains one class that provides you with a `com.google.api.client.http.HttpRequestFactory` that is authorised to execute calls to the Twitter API in the scope of a specific user.
You will need to provide your _Consumer Key_ and _Consumer Secret_ and follow through the OAuth process (get temporary token, retrieve access URL, authorise application, enter PIN for authenticated token).
With the resulting factory you are able to generate and execute all necessary requests.
If you want to, you can also disregard the provided classes or Maven configuration and create your own application from scratch.

## Delivery ##

You are assigned to you own private repository. Please use your own branch and do not commit on master.
When the assignment is finished, please create a pull request on the master of this repository, and your contact person will be notified automatically. 

## Configuration ##
You can modify the properties in the application.yml file in order to set:
+ Number of producer threads (producer_threads)
+ Number of consumer threads (consumer_threads)
+ Max number of messages to receive (max_tweets)
+ Max time in milliseconds to be receiving messages (max_time_in_milliseconds)
+ Key word for searching tweets (search)
+ Consumer Twitter key (consumer_key)
+ Consumer Twitter Secret (consumer_secret)

## Build ##
+ To compile and package the jar without docker run this command:
  * mvn compile package (-DskipTests)

+ To compile and create a docker image, you may run this command:
  * mvn compile jib:dockerBuild 

## Run ##
+ Run docker-compose to startup kafka server
  * docker-compose up
  
+ In order to run just jar file run (need to set variable environments or modify application.yml): 
  * java -jar target/java-exercise-1.0.0-SNAPSHOT.jar

+ In order to run the docker image, you may run this command:
  * docker run  -it  secdevoops/java-exercise:1.0.0-SNAPSHOT
