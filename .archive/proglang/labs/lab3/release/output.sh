#!/bin/bash

FILES=programs/*
for file in $FILES
do
  NAME=`echo $file | cut -d'.' -f1`
  EXTENSION=".output"
  FILENAME=$NAME$EXTENSION
  if [ -f "$file" ]
  then
    echo "Created $FILENAME"
    java Lexer $file > $FILENAME
  fi
done
