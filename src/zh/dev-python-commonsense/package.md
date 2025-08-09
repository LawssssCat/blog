---
title: Python打包
---

随着 AI 浪潮的到来，使用 Python 作为主要开发语言的项目激增。
但同时此类 Python 项目经常需要与 cuda/mlk/ROCm 等非 python 项目打交道，如果没有一套优秀的代码管理实践方案，会使代码工程管理变得复杂。
因此，了解 Python 现代的工程结构、打包发布方式尤为重要。

<!-- more -->

## 工程结构

Python 工程结构分两种 conda方案、python官方方案：

+ conda方案 —— 商用的跨平台工程组织方案（有自己的配置文件、软件仓库、Python语言解析器Anaconda。解决了多语言支持、依赖管理、虚拟环境等问题），主要支持 python，免费版有 miniconda、Pixi。
+ python官方方案 —— 官方主要提供标准和官方实践，具体管理工具非常多。
  如打包工具有 setuptools/hatchling/...；
  如依赖安装工具有 pip/uv/poetry/...；

### 工程结构演进记录

下面记录多种常见的工程结构。
（不推荐的管理结构会进行标识）

#### 全局依赖【不推荐】

```bash
# 安装依赖
pip3 install flask
pip3 show flask # 可查看存放位置 location
```

问题：

1. 版本冲突 —— 如果全局中有两个项目依赖不同版本 flask 会导致依赖冲突导致的运行问题

#### venv + requirements.txt 模式【不推荐】

```bash
# 启动虚拟环境
python3 -m venv .venv # 创建名称为 .venv 的虚拟环境
source .venv/bin/activate # 激活环境
# 安装依赖
pip3 install flask
pip3 show flask # 可查看存放位置 location
# ... 安装各种依赖
# 导出依赖列表
pip freeze > requirements.txt
pip install -r requirements.txt # 别人可通过该命令一键安装依赖
```

这种模式原理是修改 python 中的 `sys.path` 变量，使项目根目录有最高的模块导入优先级。

问题：

+ 依赖关系不清楚 —— 通过 `requirements.txt` 管理的依赖关系无法区分直接依赖、间接依赖，当项目体量增大、依赖关系变多后，想区分无用的依赖、对项目体积进行删减将变得困难。
+ 只卸载直接依赖 —— 通过 `pip uninstall flask` 命令卸载 flask 时， pip 只卸载 flask 本身，不卸载 flask 引入的依赖。

#### venv + pyproject.toml 模式【不推荐】

```bash
# 启动虚拟环境
python3 -m venv .venv # 创建名称为 .venv 的虚拟环境
source .venv/bin/activate # 激活环境
# 安装 pyproject.toml 里声明的依赖
# -e 解决开发环境中两份项目源码问题
pip install -e .
```

```toml title="pyproject.toml"
[project]
name = "proj"
version = "0.1.0"
dependencies = [
  "Flask==3.1.1"
]

[tool.mypy] # 被整合的其他工具配置
exclude = ["build/"]

[tool.pytest.ini_options]
testpaths = ["tests"]
```

> 当看见 `mypy.ini`（类型检器mypy配置） `pytest.ini`（测试框架pytest配置） 时，可以考虑是否可去除，因为这些工具已兼容 `pyproject.toml` 配置。
> 如今当前大多数 python 工具都支持 `pyproject.toml` 配置，如 uv/poetry/ruff/pyright/black/tox/pylint/pytest/hatch/isort/flit/deputy/mypy/pdm/...

问题：

1. 维护 `pyptoject.toml` 麻烦 —— 添加新依赖需要到官网查询版本信息

#### uv 模式

目前 python 项目类似 uv 的工具很多，以目前最流行的 uv 为例。

假设目录：

::: tabs

@tab 目录

```bash
main.py
pyproject.toml
```

@tab pyproject.toml

```toml title="pyproject.toml"
[project]
name = "proj"
version = "0.1.0"
```

:::

常用命令：

```bash
# 开发时，初始化/添加新依赖
# 1. 启动虚拟环境
# 2. 维护 pyproject.toml 文件
# 3. 安装 pyproject.toml 里声明的依赖
uv add flask

# 接手代码时，初始化项目
# 1. 启动虚拟环境
# 2. 安装 pyproject.toml 里声明的依赖
uv sync

uv run main.py
```

## 打包发布

Python 打包方式由 “Python Packaging Authority” 组织维护（像 `pip`/`virtualenv`/`twine` 工具都由该组织提供）。

> `pip` —— whl下载工具
> `twine` —— whl生成/发布工具
> `virtualenv` —— 项目环境管理工具

其组织提供 whl 格式包作为 python 源代码的打包制品。
关于 whl 有如下定义：

+ whl格式定义 —— PEP427 - The Wheel Binary Package Format 1.0
+ whl生成定义 —— PEP517 - A build-system independent format for source trees

具体为：

+ 依赖位置： `xxx-version.dist-info/METADATA` （一般根据源代码中 `src/pyproject.toml` 文件生成）
+ 交互关系： 用户 -> frontend -> backend -> `.py`/`.whl`
  其中前端（frontend）、后端（backend）有不同实现。官方的实现分别为 build（frontend）、setuptools（backend）。
  除此之外，还有三方的前后端实现，如 `FLIT`/`hatchling`/`uv`/`poetry`/`PDM`。
  目前（2025），一般的组合为：
  + uv（frontend）、Poetry（backend）
  + uv（frontend）、hatchling（backend）

### build + hatchling 模式打包

::: tabs

@tab 目录结构

```bash
.venv/
dist/
docs/
scripts/
src/build_test/__init__.py
src/build_test/foo.py
tests/
pyproject.toml
```

@tab 配置文件

```toml title="pyproject.toml"
[project]
name = "build_test"
version = "0.1"

# 依赖
dependencies = [
  "flask>=3.1.1"
]

[build-system]
requires = ["hatchling"]
build-backend = "hatchling.build"

# 可有可无，hatchling 以 src layout 为默认项目结构
# [tool.hatch.build.targets.wheel]
# packages = ["src/build_test"]
```

:::

```bash
# 安装打包工具
# 也可以使用 uv/poetry/... 工具
pip install build
# 打包
# uv: uv build
python -m build

# 运行/测试
pip install -e . # 将项目本身链接到虚拟环境，解决文件依赖项目本身的问题
python src/build_test/foo.py
# uv run src/build_test/foo.py # uv 封装了上述过程
```

## 最佳实践

### UV all in one

UV 集成了 python安装、虚拟环境、依赖管理、工具安装、打包发布 的python开发全流程需求。

```bash
# 安装python
uv python list # 查看可用python
uv python install cpython-3.12 # 下载python
uv run -p 3.12 foo.py # 用 3.12 版本python运行代码
uv run -p 3.12 python # 用 3.12 版本python进入交互式界面

# 项目初始化/项目结构生成
uv init -p 3.13 # 指定用 3.13 版本python运行项目文件
#### 生成文件 start ####
.python-version # 指定的python运行环境版本
hello.py # demo文件，可删除
pyproject.toml # 项目配置，重要
README.md
#### 生成文件 end   ####

# 添加依赖
uv add pydantic_ai
# 列出依赖树
uv tree

# 安装开发工具
uv add pytest -dev
uv add mock -dev
uv add ruff -dev
# 删除（当前环境）依赖
uv remove ruff
# 安装全局工具
uv tool install ruff
ruff check
# 查看安装的全局工具
uv tool list

# 打包
# 1. 在pyproject.toml中添加项目需要暴露的命令 （可选）
[project.scripts]
ai = "ai:main" # 命令 = 文件:方法
# 2. 打包
uv build
# 3. 安装
uv tool install dist/test-0.1.0-py3-none-any.whl
# 4. 调用脚本
ai 打印helloworld文字
```

## 参考

+ 让uv管理Python的一切 —— <https://www.bilibili.com/video/BV1Stwfe1E7s/>
+ 从pip到uv：一口气梳理现代Python项目管理全流程！ —— <https://www.bilibili.com/video/BV13WGHz8EEz>
+ build + hatchling 15分钟搞懂Python项目结构和打包 —— <https://www.bilibili.com/video/BV12NgLzhEKx>
