workingDirectory <- "/home/adrian/eclipse-workspace/marleen-mcloud-json/src/main/resources/json/out"
csvFile <- "mCloud-statistics-licenses-2018-03-28.txt"

library(ggplot2)
library(dplyr)

# Get license records
setwd(workingDirectory)
licenses <- read.csv(csvFile, header = F, sep = ",")
names(licenses) <- c("name","location")

# Get unique license recordes and count occurences
uni <- unique(licenses)
count <- plyr::count(licenses, "name")
unique <- merge(uni, count)

# Manual creation of abbreviations
#write.csv(unique$name, "license-names.txt", row.names = F)
abbreviations <- read.csv("licenses-abbreviations.txt", header = T, sep = ",")
unique <- merge(unique, abbreviations)

# Adding percent
unique$rel <- round(100*unique$freq / length(licenses$name),2)

# Ugly plot
#barplot(unique$freq,
#        names=unique$abbreviation,
#        xlab = "Lizenz",
#        ylab = "Häufigkeit")

# Plot Absolute values
#g <- ggplot(unique, aes(abbreviation,freq))
#g <- g+ xlab("Lizenz")
#g <- g+ ylab("Häufigkeit")
#g <- g + geom_bar(stat="identity")
#g <- g + geom_text(size=3, aes(label=paste(rel, "%", sep = "")), vjust=-.2)
#g + theme(axis.text.x = element_text(angle = 270, hjust = 0))

# Save
write.csv(unique, "licenses.csv", row.names = T)

# Plot
g <- ggplot(unique, aes(abbreviation,rel))
g <- g+ xlab("Lizenz")
g <- g+ ylab("Nutzung in Prozent")
g <- g + geom_bar(stat="identity")
#g <- g + geom_text(size=3, aes(label=paste(rel, "%", sep = "")), vjust=1.5, color='white')
g <- g + geom_text(size=3, aes(label=paste(rel, "%", sep = "")), vjust=-.2)
g <- g + theme(axis.text.x = element_text(angle = -45, hjust = 0))
g <- g + scale_y_continuous(label=function(x){return(paste(x, "%", sep = ""))}, expand = c(.07,0))
g 

