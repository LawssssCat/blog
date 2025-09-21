#!/bin/bash

set -ex

mkdir ~/.bashrc.d
cat > ~/.bashrc.d <<EOF
umask 007
EOF
