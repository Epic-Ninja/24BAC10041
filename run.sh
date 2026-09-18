#!/bin/bash
set -e
echo "Java Library Management System"
mvn clean package
mvn exec:java
