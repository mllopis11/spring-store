#!/bin/bash

function die { echo "[ERROR] $*"; exits 1; }

case $1 in
product) ;;
*) die "unkown project: $1"
esac

_service="${1}-service"

cd ${_service} || die "service folder not found: ${_service}"

_app=$(find ./target/${_service}-*.jar ! -name "*sources*" | head -1)
[ -z ${_app} ] && die "application binary not found in ./target"

echo "service: ${_service} (app: ${_app})"

java -jar ${_app}