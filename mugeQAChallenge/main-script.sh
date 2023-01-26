#!/bin/sh

function runAppiumTest() {
    mvn clean test -Dtest=blupayTests.appiumTests.appiumTests
}

echo "Run Appium Test"
runAppiumTest
