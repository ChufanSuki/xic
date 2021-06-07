#!/bin/bash
mvn package
cat stub.sh target/xic-compiler-1.0-SNAPSHOT.jar > xic && chmod +x xic
