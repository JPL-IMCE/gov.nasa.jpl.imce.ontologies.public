#!/bin/bash

set -ev

[ ! ${TRAVIS_SECURE_ENV_VARS} ] && exit -1;

openssl aes-256-cbc -pass pass:$ENCRYPTION_PASSWORD -in secring.gpg.enc -out local.secring.gpg -d
openssl aes-256-cbc -pass pass:$ENCRYPTION_PASSWORD -in pubring.gpg.enc -out local.pubring.gpg -d
