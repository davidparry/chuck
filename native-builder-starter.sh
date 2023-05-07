#!/bin/sh

PROJ_PATH=`pwd`
echo "project path: $PROJ_PATH"
docker run --name graalvm-build \
-p 8000:8080 \
-p 3000:3000 \
-it --rm -v $PROJ_PATH:/project marksailes/al2-graalvm:17-22.3.0
## need to point out  https://github.com/marksailes/al2-graalvm