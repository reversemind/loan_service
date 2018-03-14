# Loan Service

### Run application with test Profile

from root directory of project
```bash
./gradlew clean build bootRun
```

### Test requests
GET http://localhost:28282/api/v1.0/loan/status/approved
GET http://localhost:28282/api/v1.0/loan/status/approved/person/1
curl -X POST -H "Content-Type: application/json; charset=utf8"  -d @apply.loan.json http://localhost:28282/api/v1.0/loan



### View test data in DB
Use in test Profile http://localhost:28282/console - to navigate in H2 DB 
connection details in application.yml


###  External web services for country <-> ip lookup

- http://freegeoip.net
usage: freegeoip.net/{format}/{IP_or_hostname}

- http://ip-api.com/json/87.250.250.242?lang=en

