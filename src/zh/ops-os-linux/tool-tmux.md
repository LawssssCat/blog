---
title: tmux笔记
order: 199
---

Tmux Cheat Sheet & Quick Reference
<https://tmuxcheatsheet.com/>

参考脚本
<https://github.com/theniceboy/.config/tree/master/tmux>

```bash
$ tmux # 创建新会话（Session）
ctrl + b # 进入功能模式（Prefix）

# session
d # 切换到后台，通过 tmux ls 查看后台中的会话，通过 tmux a 或者 tmux attach -t 0 重新进入会话

# window
c # 创建窗口
& # 关闭窗口
n/p/0~9 # 切换上一个/下一个/编号0~9的窗口

# panel
% # 创建小窗口（Panel） - 左右
" # 创建小窗口（Panel） - 上下
x # 关闭小窗口
↑/↓/←/→ # 切换小窗口
q # 切换小窗口
z # 最大化/还原小窗口
```
