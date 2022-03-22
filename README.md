In this assignment we’ll be asking you to build a small app that talks to a public api of HackerNews. The app can be setup in any language or framework, but keep the
requirements in mind. On the following page you’ll find the documentation: https://github.com/HackerNews/API.
We want you to create three endpoints that return data:
1. Recent top words: return a set of 25 words that are the most occuring words in the last 250 posted stories. The response should include the number of times
each word is occuring.
2. Hisorical top words: again, return a set of the 25 top words with the number of times they occur. This time use all the comments (not stories!) in the last 1000
items that are available in the hackernews API. It is important that the endpoint returns in a reasonable timeframe, so you'll need to do parralel calls to the API.
However, for this assignment you're not allowed to send more than 100 parralel requests at the same time.
3. Karma words: hackernews assigns karma to users that post nice things. We want to use that karma to determine important words. Find the last 100
comments and retrieve the karma for all the authors. For each comment, assign the karma points to the top 10 most occuring words. Do that for each of the
100 posts, and add together all the karma points for all the top 10s. Finally return a top 10 of words from all those top 10s.
The associated karma is calculated as follows.
For each comment take the top 10 most occuring words. These words are assigned the karma held by their respective authors.
The total top 10 is determined by aggregating the karma points for each word.
In your analysis, ignore stop words (e.g. see this list on Github). Also, make sure you're application is (mostly) stateless and does not use any (database) storage.
It's up to you what the endpoints look like and how exactly the response is returned (we do expect the latter to be JSON). Of course we expect you to make sensible
choices and will ask you to explain them!
