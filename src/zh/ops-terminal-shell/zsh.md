---
title: ZSH 介绍
date: 2024-06-09
tag:
  - linux
order: 1
---

参考： <https://www.haoyep.com/posts/zsh-config-oh-my-zsh/>

## zsh

todo 安装

## oh-my-zsh

todo 安装 http://ohmyz.sh/

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
