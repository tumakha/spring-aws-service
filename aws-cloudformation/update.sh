#!/bin/bash
aws cloudformation update-stack --stack-name spring-aws --template-body file://spring-aws-stack.yml --capabilities CAPABILITY_NAMED_IAM
