# regression
Exercises to learn about linear and logistic regression
Description:
In March and April 2019, we are running a learning group called _Make predictions with machine learning (for beginners)_

This is where the code exercises live.

Description for the learning group:

We will cover a couple fundamental machine learning algorithms that you can use to make predictions. If you’re 
curious about machine learning but you’ve not learned about it formally, or you’ve never learned via playing with data 
yourself, these sessions are for you. 

We will cover linear and logistic regression. Linear regression can be used to make real valued predictions like: 
how much a house will sell for? Logistic regression can be used to make classifiers, for example: is this email spam or 
not? We also need an optimisation algorithm for both linear and logistic regression, so we will cover a classic: gradient 
descent.  

We will round things off by applying what we’ve learned to make a binary classifier based on the Titanic dataset: 
https://www.kaggle.com/c/titanic. 

Notes will be available after each session, and each session will have exercises. Since each topic depends on the one 
previous, it’s probably best to still go through the notes and exercises if you need to miss a session. The exercises will be prepared in Scala, so if you’re not terribly familiar with Scala you can pair up with someone who is. 

Ongoing news at the Learning Group(s) channel. 

Rough outline:

### Week 1: Introduction and linear regression

exercises: https://github.com/guardian/regression/blob/master/notes/week1-written.pdf

notes: https://github.com/guardian/regression/blob/master/tex/week1.pdf
The notes reference the Stanford Machine Learning course on Coursera and Machine Learning for humans but I just noticed the links merely Work On My Machine and not in the PDF once it's landed here on github so I've added those links below. 

Machine learning course on coursera: https://www.coursera.org/learn/machine-learning

Machine learning for humans: https://medium.com/machine-learning-for-humans/why-machine-learning-matters-6164faf1df12

### Week 2: Gradient Descent and linear regression wrap up

notes: https://github.com/guardian/regression/blob/master/tex/week2.pdf

exercises:

- Implement `linearMeanSquaredError` and `linearMeanAbsoluteError` in `LinearErrorCalculator` (note: there is already a `LinearErrorCalculatorTest`)
- Implement `theta0Updated` and `theta1Updated` in `GradientDescent` (note: there is already a `GradientDescentTest`)
- With the help of `GradientDescentApp`, implement the `gradientDescent` function in the `GradientDescent` object. `GradientDescentApp` loads the house prices training data, calls the `gradientDescent` function, and then launches of plot of cost vs number of iterations. You'll know you're on the right track when cost decreases with every iteration (see: the cost vs iterations plot in the week 2 notes for a graph that shows gradient descent has converged). 
- `GradientDescentAndCheckTestSetApp` will do the same as `GradientDescentApp` and additionally load in the test set, and calulate the Mean Absolute Error

bonus points:

- Start thinking about how you could improve your model. I started with the Ames dataset: https://www.kaggle.com/c/house-prices-advanced-regression-techniques and just extracted lot size and selling price. Does Mean Absolute Error improve if you pick a feature that might be a better predictor of selling price? Then you can also feel some data munging pain that is a very authentic experience. :D


### Week 3: Linear algebra review + vectorising your gradient descent implementation

### Week 4: Logistic regression + applying it to the Titanic dataset

### Week 5: If we get behind, then finishing up the previous topics
