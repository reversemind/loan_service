#!/bin/bash

curl -X POST -H "Content-Type: application/json; charset=utf8"  -d @apply.loan.json http://localhost:28282/api/v1.0/loan