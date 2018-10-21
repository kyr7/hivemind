#!/bin/bash
#https://app.swaggerhub.com/apis/kyr7/hivemind/0.0.1#/model/modelPost
#clean up servables
docker ps -a | awk '{ print $1,$2 }' | grep tensorflow/serving | awk '{print $1 }' | xargs -I {} docker rm -f {}
curl -X POST "http://localhost:8080/model/1d722019-c892-44bc-844b-eb5708d55987?gpu=true" \
 -H "accept: application/json" -H "Content-Type: application/json" \
 -d "{ \"scriptId\": \"7de76908-d4d9-4ce9-98de-118a4fb3b8f8\", \"dataId\": \"5d335160-bd2a-45e4-9199-8105a38941ad\" }}"