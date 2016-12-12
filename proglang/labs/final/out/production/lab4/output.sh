#!/bin/bash

FILES=programs/*
for file in $FILES
do
  NAME=`echo $file | cut -d'.' -f1`
  EXTENSION=".output"
  FILENAME=$NAME$EXTENSION
  if [ -f "$file" ]
  then
    echo "Parsed $FILENAME"
    java Parser $file > $FILENAME
  fi
done
