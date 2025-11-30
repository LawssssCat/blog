#!/bin/bash

set -e

VM_PROVIDE="$1"
VM_NAME="$2"
VM_IPADDR="$3"

SCRIPT_PATH="$(cd $(dirname $0); pwd)"
VGHOME_PATH="/vagrant"
VGUSER_HOME="/home/vagrant"

#####################################
echo "--- [print system] ---"
uname -a
arch
cat /etc/os-release
whoami


#####################################
echo "todo OS镜像源"
