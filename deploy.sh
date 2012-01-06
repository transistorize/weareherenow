#!/bin/sh

# This is a temporary deployment script until ANT can be set up

if [ -f "deploy" ]; then
  rm -rf deploy;
fi

mkdir deploy
cp -f resource/logging.properties deploy/
cp -f resource/weareherenow_prod.properties deploy/
cp -f resource/Grid_Centroids_500_random.csv deploy/
cp -f resource/client_ids.csv deploy/
cp -f resource/run.sh deploy/

cp lib/*.jar deploy

cd out/class/

if [ -f "herenow.jar" ]; then
  rm -rf herenow.jar;
fi

jar -cf herenow.jar org/*
cd ../../
cp out/class/herenow.jar deploy/


# tar and zip for transfer
cd deploy
tar -c -f weareherenow.tar *
gzip weareherenow.tar
cd ..