#!/bin/bash

javac -cp "lib/mysql-connector-j-9.7.0.jar:src" src/HospitalManagement/*.java

java -cp "lib/mysql-connector-j-9.7.0.jar:src" HospitalManagement.HospitalManagementSystem