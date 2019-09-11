$LoadtestStartTime = $(get-date)

[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12


$groupDataFiles = Get-ChildItem -Path .\groupdata\*.json
foreach ($file in $groupDataFiles) {
	$rawGroupData = Get-Content $file -Raw
	$groupData = [System.Text.Encoding]::UTF8.GetBytes($rawGroupData)
	
	Write-Output $file.Name | Tee-Object -Append log.txt
	
	$StartTime = $(get-date)
	Invoke-WebRequest -UseBasicParsing https://nginx-broker.skolelogin.127.0.0.1.nip.io/auth/realms/broker/aktoer-service/gruppe -ContentType "application/json" -Method POST -Body $groupData
	$elapsedTime = $(get-date) - $StartTime
	$totalTime = "{0:HH:mm:ss.fff}" -f ([datetime]$elapsedTime.Ticks)
	Write-Output "aktoer-service/gruppe: " $totalTime | Tee-Object -Append log.txt

	$StartTime = $(get-date)
	Invoke-WebRequest -UseBasicParsing https://nginx-unilogin.skolelogin.127.0.0.1.nip.io/auth/realms/idp/aktoerrestservice/gruppe -ContentType "application/json" -Method POST -Body $groupData
	$elapsedTime = $(get-date) - $StartTime
	$totalTime = "{0:HH:mm:ss.fff}" -f ([datetime]$elapsedTime.Ticks)
	Write-Output "aktoerrestservice/gruppe: " $totalTime | Tee-Object -Append log.txt
	
	Write-Output "" | Tee-Object -Append log.txt
}
	
$personDataFiles = Get-ChildItem -Path .\persondata\*.json
foreach ($file in $personDataFiles) {
	$rawPersonData = Get-Content $file -Raw
	$personData = [System.Text.Encoding]::UTF8.GetBytes($rawPersonData)
	
	Write-Output $file.Name | Tee-Object -Append log.txt
	
	$StartTime = $(get-date)
	Invoke-WebRequest -UseBasicParsing https://nginx-broker.skolelogin.127.0.0.1.nip.io/auth/realms/broker/aktoer-service/aktoer -ContentType "application/json" -Method POST -Body $personData
	$elapsedTime = $(get-date) - $StartTime
	$totalTime = "{0:HH:mm:ss.fff}" -f ([datetime]$elapsedTime.Ticks)
	Write-Output "aktoer-service/aktoer: " $totalTime | Tee-Object -Append log.txt

	$StartTime = $(get-date)
	Invoke-WebRequest -UseBasicParsing https://nginx-unilogin.skolelogin.127.0.0.1.nip.io/auth/realms/idp/aktoerrestservice/aktoer -ContentType "application/json" -Method POST -Body $personData
	$elapsedTime = $(get-date) - $StartTime
	$totalTime = "{0:HH:mm:ss.fff}" -f ([datetime]$elapsedTime.Ticks)
	Write-Output "aktoerrestservice/aktoer: " $totalTime | Tee-Object -Append log.txt
	
	Write-Output "" | Tee-Object -Append log.txt
}

$LoadtestElapsedTime = $(get-date) - $LoadtestStartTime
$LoadtestTotalTime = "{0:HH:mm:ss.fff}" -f ([datetime]$LoadtestElapsedTime.Ticks)
Write-Output "Total loadtime: " $LoadtestTotalTime | Tee-Object -Append log.txt