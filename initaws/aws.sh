#!/bin/bash
set -x
awslocal sqs create-queue --queue-name test-order-queue
awslocal s3 mb s3://test-order-bucket
