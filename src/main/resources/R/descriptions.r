workingDirectory <- "/home/adrian/eclipse-workspace/marleen-mcloud-json/src/main/resources/json/out"
descriptionCsv <- "mCloud-statistics-description-length-2018-03-28.txt"

labelX <- "Anzahl Wörter in Beschreibungen"
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
descrLength <- read.csv(descriptionCsv, header = F, sep = ",")
##numberOfWords <- descrLength[1]
##count <- descrLength[2]
colnames(descrLength) <- c("numberOfWords", "count")
head(descrLength)
tail(descrLength)



#### Simple plot ####

plot <- qplot(data=descrLength, x=numberOfWords, y=count)
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
ranges <- floor(descrLength$numberOfWords / rangeStep) * rangeStep
head(ranges, n=rangeStep+1)
tail(ranges)

# Aggregate
# ?by data, INDICES, FUN
countByRanges <- by(descrLength$count, ranges, sum)
countByRangesKeys <- as.numeric(names(countByRanges))
rangesDF <- as.data.frame(as.numeric(countByRanges))
rangesDF$numberOfWords <- countByRangesKeys
colnames(rangesDF) <- c("count", "range")
head(rangesDF)
tail(rangesDF)
plotText <- paste( as.character((countByRangesKeys)), ": ", as.character(rangesDF$count), sep="")

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