$LoadtestStartTime = $(get-date)

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$personData = '[{
  "aktoerUuid" : "f6f6959d-68d8-461d-bee5-e2564d0e846f",
  "cpr" : "7404990001",
  "efternavn" : "ÅBertelsen",
  "fornavn" : "ØEiron",
  "localPersonId" : "xx9",
  "uniId" : "xx9",
  "institutionId" : "101022",
  "initialPassword" : "1234",
  "rolle" : null,
  "grupper" : [ "76100946-495b-4362-b2e0-75c357679e06", "bc921092-c193-4d55-a0a6-d4f90ef71f12", "006b07d6-90f0-4447-95f7-4cc420b9b845", "69b07ee1-5957-4559-baa2-84f5c9268ed7" ],
  "@type" : "Medarbejder"
}]'
		
$personData = [System.Text.Encoding]::UTF8.GetBytes($personData)
	
Invoke-RestMethod -Uri https://lt-broker.unilogin.dk/auth/realms/broker/aktoer-service/aktoer -ContentType "application/json; charset=utf-8" -Method POST -Body $personData
Invoke-RestMethod -Uri https://lt-idp.unilogin.dk/auth/realms/idp/aktoerrestservice/aktoer -ContentType "application/json; charset=utf-8" -Method POST -Body $personData
