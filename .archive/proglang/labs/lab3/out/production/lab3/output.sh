#!/bin/bash
FILES=/Users/chris/Code/cnc/proglang/labs/lab3/programs/*
for file in $FILES
do
  NAME=`echo $file | cut -d'.' -f1`
  EXTENSION=".output"
  FILENAME=$NAME$EXTENSION
  echo $FILENAME
  java Lexer $file > $FILENAME
  #echo $FILENAME
done
