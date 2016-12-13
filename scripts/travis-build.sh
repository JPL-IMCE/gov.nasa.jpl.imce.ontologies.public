#!/bin/bash

set -ev

# Build if TRAVIS_TAG is unset or empty.

[ -n "${TRAVIS_TAG}" ] && exit 0;

sbt -jvm-opts travis/jvmopts.compile packageBin

