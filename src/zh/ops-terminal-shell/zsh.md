---
title: ZSH 介绍
order: 10
---

参考： <https://www.haoyep.com/posts/zsh-config-oh-my-zsh/>

## zsh

```bash
sudo apt install zsh
# 将 zsh 设置为默认 shell
chsh -s /bin/zsh
echo $SHELL
```

## oh-my-zsh

官网： <http://ohmyz.sh/>

主题

```bash
powerlevel10k —— 这个主题对字体有要求，需要看 Readme.md

vim ~/.zshrc
ZSH_THEME="powerlevel10k"
```

插件

```bash
# 插件：提示 —— zsh-autosuggestions
git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions

# 插件：高亮 —— zsh-syntax-highlighting
git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting

# 插件：记录转跳 —— z （内置）

# 插件：解压 —— extract （内置）

# 插件：搜索 —— web-search （内置）
```

插件使用 `~/.zshrc`

```bash
plugins=(git zsh-autosuggestions zsh-syntax-highlighting z extract web-search)
```

## neovim

todo nvim 终端 ide
