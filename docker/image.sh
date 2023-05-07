#!/bin/sh

cp ~/code/github/chuck/build/native/nativeCompile/chuck .
docker build -t chuck .
rm chuck
