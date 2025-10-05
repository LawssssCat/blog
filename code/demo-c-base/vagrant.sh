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
echo "--- [write script] ---"
su - vagrant
cat > $VGUSER_HOME/bootstrap-buildchain.sh <<EOF
#!/bin/bash
set -ex
echo ==================== common bashrc ====================
VGUSER_BASHRC_HOME="$VGUSER_HOME/.bashrc.d"
mkdir -pv "\$VGUSER_BASHRC_HOME" && echo umask 007 > \$VGUSER_BASHRC_HOME/common.sh
echo ==================== config sshd ====================
# sudo sed -i 's/^[# ]*PasswordAuthentication .*$/PasswordAuthentication yes/' /etc/ssh/sshd_config
# sudo sed -i 's/^[# ]*AllowAgentForwarding .*$/AllowAgentForwarding yes/' /etc/ssh/sshd_config
# sudo sed -i 's/^[# ]*AllowTcpForwarding .*$/AllowTcpForwarding yes/' /etc/ssh/sshd_config
sudo sed -i 's/^[# ]*X11Forwarding .*$/X11Forwarding yes/' /etc/ssh/sshd_config
sudo systemctl restart sshd.service
echo ==================== config ssh ====================
SSH_PUB="$(cat $VGHOME_PATH/.ssh/id_ed25519.pub)"
sed "/\$SSH_PUB/d" $VGUSER_HOME/.ssh/authorized_keys
echo \$SSH_PUB >> $VGUSER_HOME/.ssh/authorized_keys
cat $VGUSER_HOME/.ssh/authorized_keys
echo ================== 镜像源 ==================
sudo sed -e 's|^metalink=|#metalink=|g' \
  -e 's|^#baseurl=http://download.example/pub/fedora/linux|baseurl=https://mirrors.jlu.edu.cn/fedora|g' \
  -i.bak \
  /etc/yum.repos.d/fedora.repo \
  /etc/yum.repos.d/fedora-updates.repo
# sudo yum clean dbcache
sudo yum makecache
echo ==================
echo 基础开发调试环境环境
echo ==================
sudo yum -y install gcc gcc-g++ make cmake gdb
gcc --version   # 编译工具
g++ --version   # 编译工具
make --version  # 项目组织工具
cmake --version # 项目组织工具
gdb --version   # 调试工具
echo ##########################
echo # 项目集成开发环境
echo ##########################
sudo yum -y install clang clangd lldb ninja
clang --version  # 编译工具
ninja --version  # 项目组织工具
clangd --version # LSP
lldb --version   # 调试工具
sudo yum -y install git zip unzip
git --version
zip --version
function install_vcpkg {
  local install_home="\$1"
  if [ ! -f "\$install_home/vcpkg" ]; then
    rm -rf "\$install_home"
    mkdir -pv "\$install_home"
    cd "\$install_home"
    git clone -v --progress --depth 1 --branch 2025.09.17 https://github.com/microsoft/vcpkg.git .
    ./bootstrap-vcpkg.sh
  fi
  cat > \$VGUSER_BASHRC_HOME/vcpkg.sh <<EOF2
export PATH=\$install_home:\$PATH
export VCPKG_ROOT=\$install_home
EOF2
  source ~/.bashrc
  vcpkg --version
}
install_vcpkg "$VGUSER_HOME/.local/vcpkg"
EOF


#####################################
echo "--- [introduce] ---"
echo
echo run:
echo
echo vagrant ssh -c \"bash $VGUSER_HOME/bootstrap-buildchain.sh\"
echo
echo to install build chain.
echo
