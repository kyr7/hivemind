#!/bin/bash
#https://app.swaggerhub.com/apis/kyr7/hivemind/0.0.1#/model/modelApplyInput
curl -d '{"instances" : [[-1.0, -2.0, 3.0, 3.0, -5.0, 6.0, 6.0, 8.0, 1.0, 2.0, 3.0, 4.0, 5.0, -1.0, 7.0, 8.0, 1.0, 2.0, -3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 1.0, 2.0, 5.0, 4.0, 5.0, 6.0, 7.0, 1.0]] }' \
-X POST http://localhost:8080/apply/1d722019-c892-44bc-844b-eb5708d55987