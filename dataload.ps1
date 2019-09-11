$rawGroupData = Get-Content -Path .\groupdata.json -Raw
$groupData = [System.Text.Encoding]::UTF8.GetBytes($rawGroupData)

$rawPersonData = Get-Content -Path .\persondata.json -Raw
$personData = [System.Text.Encoding]::UTF8.GetBytes($rawPersonData)

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12

$StartTime = $(get-date)
Invoke-WebRequest -UseBasicParsing https://nginx-broker.skolelogin.127.0.0.1.nip.io/auth/realms/broker/aktoer-service/gruppe -ContentType "application/json" -Method POST -Body $groupData
$elapsedTime = $(get-date) - $StartTime
$totalTime = "{0:HH:mm:ss}" -f ([datetime]$elapsedTime.Ticks)
write-host "aktoer-service/gruppe:" $totalTime
Write-Output "aktoer-service/gruppe:" $totalTime | Tee-Object -Append log.txt

$StartTime = $(get-date)
Invoke-WebRequest -UseBasicParsing https://nginx-unilogin.skolelogin.127.0.0.1.nip.io/auth/realms/idp/aktoerrestservice/gruppe -ContentType "application/json" -Method POST -Body $groupData
$elapsedTime = $(get-date) - $StartTime
$totalTime = "{0:HH:mm:ss}" -f ([datetime]$elapsedTime.Ticks)
write-host "aktoerrestservice/gruppe:" $totalTime
Write-Output "ktoerrestservice/gruppe:" $totalTime | Tee-Object -Append log.txt

$StartTime = $(get-date)
Invoke-WebRequest -UseBasicParsing https://nginx-broker.skolelogin.127.0.0.1.nip.io/auth/realms/broker/aktoer-service/aktoer -ContentType "application/json" -Method POST -Body $personData
$elapsedTime = $(get-date) - $StartTime
$totalTime = "{0:HH:mm:ss}" -f ([datetime]$elapsedTime.Ticks)
write-host "aktoer-service/aktoer:" $totalTime
Write-Output "aktoer-service/aktoer:" $totalTime | Tee-Object -Append log.txt

$StartTime = $(get-date)
Invoke-WebRequest -UseBasicParsing https://nginx-unilogin.skolelogin.127.0.0.1.nip.io/auth/realms/idp/aktoerrestservice/aktoer -ContentType "application/json" -Method POST -Body $personData
$elapsedTime = $(get-date) - $StartTime
$totalTime = "{0:HH:mm:ss}" -f ([datetime]$elapsedTime.Ticks)
write-host "aktoerrestservice/aktoer:" $totalTime
Write-Output "aktoerrestservice/aktoer:" $totalTime | Tee-Object -Append log.txt
