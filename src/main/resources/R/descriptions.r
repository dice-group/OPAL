workingDirectory <- "/home/adrian/eclipse-workspace/marleen-mcloud-json/src/main/resources/json/out"
descriptionCsv <- "mCloud-statistics-description-length-2018-03-28.txt"

labelX <- "Wortanzahl in Beschreibungstexten"
labelY <- "Anzahl Datensätze"



#### Packages ####

#install.packages("ggplot2")
library(ggplot2)

#install.packages('ggrepel')
library(ggrepel)

#library(plyr)
#library(psych)



#### Load data ####

setwd(workingDirectory)
descriptions <- read.csv(descriptionCsv, header = F, sep = ",")
##numberOfWords <- descriptions[1]
##count <- descriptions[2]
colnames(descriptions) <- c("numberOfWords", "count")
head(descriptions, 50)
tail(descriptions)



#### Simple plot ####

plot <- qplot(data=descriptions, x=numberOfWords, y=count)
plot <- plot + geom_point() 
plot <- plot + geom_line(color="#999999")
plot <- plot + labs(x = labelX, y = labelY)

# Smoothed conditional means
#plot <- plot + geom_smooth(span=.1, method="loess", color="#666666")

plot


#### Summarized plot ####

# Compute range column for each entry
# Will be used for aggregation
rangeStep <- 10
ranges <- (floor(descriptions$numberOfWords / rangeStep)+1) * rangeStep

# Smaller steps
# WARNING: Assumes, if index == numberOfWords -1
#rangeStep <- 5
#for(i in 0:39)
#  ranges[i] <- (floor(descriptions$numberOfWords[i] / rangeStep)+1) * rangeStep

ranges[1] = 0

head(descriptions$numberOfWords, 51)
head(ranges, 51)

# Aggregate
# ?by data, INDICES, FUN
countByRanges <- by(descriptions$count, ranges, sum)
countByRangesKeys <- as.numeric(names(countByRanges))
rangesDF <- as.data.frame(as.numeric(countByRanges))
rangesDF$numberOfWords <- countByRangesKeys
colnames(rangesDF) <- c("count", "range")
head(rangesDF)
tail(rangesDF)
plotText <- paste(as.character((countByRangesKeys)), ": ", as.character(rangesDF$count), sep="")

labelX <- "Aggregierte Wortanzahl in Beschreibungstexten"
plot2 <- qplot(data=rangesDF, x=rangesDF["range"], y=rangesDF["count"])
#plot2 <- plot2 + geom_smooth(span=.6, method="loess", color="#666666")
plot2 <- plot2 + geom_point()
plot2 <- plot2  + geom_line(color="#999999")
plot2 <- plot2 + labs(x = labelX, y = labelY)
plot2 <- plot2 + geom_text_repel(aes(label = plotText), size=3, box.padding = 0.4)
plot2



#### Examine data structures ####

# https://www.statmethods.net/input/contents.html
#examine <- countByRanges
#str(examine)
#names(examine)
#levels(examine)
#class(examine)
#dim(examine)
#head(examine)
#tail(examine)
#examine
#typeof(examine)