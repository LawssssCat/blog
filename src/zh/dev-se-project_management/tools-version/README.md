---
title: 版本控制系统（VCS）
date: 2025-02-03
order: 1
---

版本控制系统（Version Control System，VCS）：

+ ~~Torvalds~~ —— 被 git 淘汰
+ SVN（Subversion） —— 收费
+ [git](./vcs-git.md) —— YYDS

代码仓：

- Github —— 用户最多，私人仓收费
- Bitbucket —— 免费，不限制私人仓数量
- GitLab —— 开源
- Gogs
- Gitee，码云 —— 适合中国宝宝

## 提交规范

> 参考：
> + <https://www.cnblogs.com/anly95/p/13163384.html>
> + 《Contributing to Angular》 <https://github.com/angular/angular/blob/main/CONTRIBUTING.md#-commit-message-format>

git message:

```bash
[Pull Request/Issue No.][type] title...  

details...
```


用于说明 commit 的类别，只允许使用下面7个标识。

+ `feat`：新功能（feature）
+ `fix`：修补bug
+ `docs`：文档（documentation）
+ `style`： 格式（不影响代码运行的变动）
+ `refactor`：重构（即不是新增功能，也不是修改bug的代码变动）
+ `test`：增加测试
+ `chore`：构建过程或辅助工具的变动

如果type为`feat`和`fix`，则该 commit 将肯定出现在 Change log 之中。
其他情况（`docs`、`chore`、`style`、`refactor`、`test`）由你决定，要不要放入 Change log，建议是不要。
