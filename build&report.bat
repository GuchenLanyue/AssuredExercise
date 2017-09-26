@echo off
call mvn compile
call mvn clean test -e
call allure serve target/allure-results