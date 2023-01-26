#!/bin/sh

function runAppiumTest() {
    mvn clean test -Dtest=blupayTests.apiTests.apiTests
}

echo "Run Appium Test"
runAppiumTest


