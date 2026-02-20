---
title: RPM 笔记
order: 10
---

<https://linuxconfig.org/how-to-create-an-rpm-package>

```bash
sudo dnf install -y rpmdevtools rpmlint
rpmdev-setuptree
rpmlint ~/rpmbuild/SPECS/mypackage.spec
rpmbuild -bb ~/rpmbuild/SPECS/mypackage.spec
```

```bash
BUILD
RPMS
SOURCES
SPECS
SRPMS
```
