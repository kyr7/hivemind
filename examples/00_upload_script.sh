#!/bin/bash
#https://app.swaggerhub.com/apis/kyr7/hivemind/0.0.1#/script/scriptPost
rm -f master.zip
wget -O emg-nn.zip https://github.com/cyber-punk-me/emg-nn/archive/master.zip
curl -X POST "http://localhost:8080/script/7de76908-d4d9-4ce9-98de-118a4fb3b8f8" \
 -H "accept: application/json" -H "Content-Type: multipart/form-data" -F "upfile=@emg-nn.zip;type=application/zip"
echo
