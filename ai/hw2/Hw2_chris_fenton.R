library(MASS)
library(lattice)

n <- 100 #Number of samples
mu <- rep(0, 2) #Set means to 0
sig <- cbind(c(1.0,0.5), c(0.5,2.0)) #Covariance matrix
samp <- mvrnorm(n, mu, sig) #Get samples

plot(samp) #Plot the samples
abline(lm(samp[,2] ~ samp[,1])) #Add a regression line