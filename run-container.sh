#!/bin/sh

docker create --name chuck -p 8080:8080 -m 2g  chuck:latest
docker start chuck
docker logs -f chuck
