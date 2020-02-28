#!bin/bash

mvn -f login/pom.xml clean package

mvn -f encuesta/pom.xml clean package