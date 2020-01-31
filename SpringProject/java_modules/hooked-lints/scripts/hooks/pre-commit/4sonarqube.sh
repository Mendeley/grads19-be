#!/usr/bin/env bash

. $(cd "$(dirname "$0")" && pwd -P)/../hook-tools.sh
. $(cd "$(dirname "$0")" && pwd -P)/../../../../../sonar-config.sh

url="https://sq.prod.tio.elsevier.systems"

sonarConfigCheck() {
  if [ "$projectKey" = "" ] || [ "$projectName" = "" ] || [ "$token" = "" ] || [ "$binaries" = "" ] || { [ "$branchName" = "" ] && [ "$branchTarget" != "" ]; }; then
    log ERROR "There is an issue regarding your sonar-config.sh file."
    log ERROR "Please check the file exists and the information is correct."
    log ERROR "Skipping hook..."
    exit 0
  fi
}

sonarQubeStatusCheck() {
  log INFO "Checking SonarQube server online..."
  if [ "$(curl --silent "$url")" != "" ] ; then
    log INFO "SonarQube server is online."
    log INFO "Locating project: ${projectName}"
    searchResponse=$(curl -u "$token": --silent \""$url"/api/components/search?qualifiers=TRK&q="$projectKey"\")
    if [ "$searchResponse" != "" ] && [ "$searchResponse" != "{\"errors\":[{\"msg\":\"Insufficient privileges\"}]}" ]; then
      x=${searchResponse/*total\":}
      total=${x%%\}*}
      if [ "$total" = "1" ]; then
        log INFO "Project: ${projectName} located."
      else
        log ERROR "Project: ${projectName} cannot be located."
        log ERROR "Skipping hook..."
        exit 0
      fi
    else
      log ERROR "You have insufficient privileges to perform this action."
      log ERROR "Please check your SonarQube token is correct."
      log ERROR "Commit failed."
      exit 1
    fi
  else
    log ERROR "SonarQube server is offline."
    log ERROR "Skipping hook..."
    exit 0
  fi
}

sonarScanner() {
  saveIFS=$IFS
  IFS=","
  args="${*:1}"
  IFS=$saveIFS

  log INFO "Sending files to be checked by SonarQube..."
  if ./sonar-scanner-4.2.0.1873/bin/sonar-scanner -Dsonar.projectKey="$projectKey"  -Dsonar.branch.name="$branchName" -Dsonar.branch.target="$branchTarget" -Dsonar.sources="$args" -Dsonar.java.binaries="$binaries" -Dsonar.login="$token" > /dev/null ; then
    log INFO "Files successfully sent."
  else
    log ERROR "Error occured while sending files."
    log ERROR "Commit failed."
    exit 1
  fi
}

analysisProgressCheck() {
  checking=true
  checkNumber=0

  while [ $checking = true ]
  do
    log INFO "Checking if analysis is complete..."
    queueCheck=$(curl -u "$token": --silent \""$url"/api/ce/component?component="$projectKey"\")
    checkNumber=$((checkNumber + 1))
    y=${queueCheck/*queue\":\[}
    queue=${y%%\],\"current\":*}
    if [ "$queue" = "" ]; then
      log INFO "Analysis is complete."
      checking=false
    else
      if [ $checkNumber -gt 2 ]; then
        log ERROR "Skipping hook as analysis wait time too long."
        log ERROR "Go to $url/dashboard?id=$projectKey to see analysis results when completed."
        exit 0
      else
        log INFO "Analysis incomplete, waiting 5 seconds..."
        sleep 5
      fi
    fi
  done

  y2=${y/*status\":\"}
  jobStatus=${y2%%\",*}
  if [ "$jobStatus" = "SUCCESS" ]; then
    log INFO "Analyis was successful."
  else
    y3=${y/*errorMessage\":\"}
    errorMessage=${y3%%\",*}
    log ERROR "Analyis failed."
    log ERROR "Error message reads: \"$errorMessage\""
    log ERROR "Commit failed."
    exit 1
  fi
}

qualityGateCheck() {
  qualityGateStatusFull=$(curl -u "$token": --silent \""$url"/api/qualitygates/project_status?projectKey="$projectKey"&branchKey="$branchName"\")
  z=${qualityGateStatusFull/*:\{\"status\":\"}
  qualityGateStatus=${z%%\",*}
  if [ "$qualityGateStatus" = "OK" ]; then
    log INFO "Code passed SonarQube checks."
    log INFO "See full report at $url/dashboard?id=$projectKey"
    exit 0
  else
    if [ "$qualityGateStatus" = "ERROR" ]; then
      log ERROR "Code failed SonarQube check."
      log ERROR "See full report at $url/dashboard?id=$projectKey"
      log ERROR "Commit failed."
      exit 1
    else
      if [ "$qualityGateStatus" = "WARN" ]; then
        log WARN "Code produced warning during SonarQube check."
        log WARN "See full report at $url/dashboard?id=$projectKey"
        log ERROR "Commit failed."
        exit 1
      else
        log ERROR "No quality gate associated with this project."
        log ERROR "Please see full report at $url/dashboard?id=$projectKey to add one, and try again."
        log ERROR "Commit failed."
        exit 1
      fi
    fi
  fi
}

sonarConfigCheck
sonarQubeStatusCheck
runscriptfiles "\.*\$" sonarScanner
analysisProgressCheck
qualityGateCheck
