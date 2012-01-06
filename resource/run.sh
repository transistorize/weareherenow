#!/bin/sh

# Run this after all the values have been set in weareherenow_X.properties

if [ ! -f "logs" ]; then
  mkdir logs;
fi

java -server -cp json.jar:mysql.jar:herenow.jar \
     -XX:-PrintClassHistogram -XX:-HeapDumpOnOutOfMemoryError -verbose:gc  \
     -Djava.util.logging.config.file="logging.properties" \
     org.sidl.herenow.HereNow \
     weareherenow_prod.properties Grid_Centroids_500_random.csv client_ids.csv