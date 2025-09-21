---
title: GPG使用
---

## GPG with Github

参考：

+ https://zhuanlan.zhihu.com/p/76861431
+ https://docs.github.com/en/authentication/managing-commit-signature-verification

::: tabs

@tab 生成钥匙

```bash
$ gpg --version
gpg (GnuPG) 2.2.16-unknown
libgcrypt 1.8.4
Copyright (C) 2019 Free Software Foundation, Inc.
License GPLv3+: GNU GPL version 3 or later <https://gnu.org/licenses/gpl.html>
This is free software: you are free to change and redistribute it.
There is NO WARRANTY, to the extent permitted by law.

Home: /c/Users/---/.gnupg
Supported algorithms:
Pubkey: RSA, ELG, DSA, ECDH, ECDSA, EDDSA
Cipher: IDEA, 3DES, CAST5, BLOWFISH, AES, AES192, AES256, TWOFISH,
        CAMELLIA128, CAMELLIA192, CAMELLIA256
Hash: SHA1, RIPEMD160, SHA256, SHA384, SHA512, SHA224
Compression: Uncompressed, ZIP, ZLIB, BZIP2


# 生成签名
# 注意：
# 1. 打开Git Bash
# 1. GPG密钥中个人信息的邮箱部分，必须使用在Github中验证过的邮箱，否则添加GPG key会提示未经验证。（注意：使用github提供的邮箱，以避免个人信息泄露！）
$ gpg --full-generate-key
gpg: key DC3DB5873563E6B2 marked as ultimately trusted
gpg: revocation certificate stored as '/c/Users/---/.gnupg/openpgp-revocs.d/1BA074F113915706D141348CDC3DB5873563E6B2.rev'
public and secret key created and signed.

pub   rsa2048 2019-08-04 [SC] [expires: 2021-08-03]
      1BA074F113915706D141348CDC3DB5873563E6B2
uid                      fortest <test@test.com>
sub   rsa2048 2019-08-04 [E] [expires: 2021-08-03]


# 查看已生成钥匙
$ gpg --list-keys
# some output is omitted here
pub   rsa2048 2019-08-04 [SC] [expires: 2021-08-03]
      1BA074F113915706D141348CDC3DB5873563E6B2
uid           [ultimate] fortest <test@test.com>
sub   rsa2048 2019-08-04 [E] [expires: 2021-08-03]


# 查看公钥
$ gpg --armor --export 1BA074F113915706D141348CDC3DB5873563E6B2
-----BEGIN PGP PUBLIC KEY BLOCK-----
mQENBF1GT3wBCADC9Hb3HtDc69XzYlohVKvdL1KnK0FslJISRuF6S0sdoOiWo2wJ
OiYVplWguTSkrMytjnMsoysZVolkYluY1wk67NT8YuYfnu6LSuF/doihrRldnKmz
9NZWw+15MLnENKsWCtwNwcCGDeZNJACyyUMYk7nJeIiM72k3/rnsyEpHqB25W/Zf
1VBkwf/ShePZ2W+rUktJ8j1TZuxe2bQpJdHQ9EKWG50D8O3xk+N+xEg4pcXLMfwT
vnVpf2wINGLA6+3ypVMDipC0fgAnINBrrjiKsq2Sskv0O73D3sZlkOi0jgAhx+21
5dI2xHbcs3DrcZbWAF1xEA8wGsoyYQWoSCBrABEBAAG0F2ZvcnRlc3QgPHRlc3RA
dGVzdC5jb20+iQFUBBMBCAA+FiEEG6B08RORVwbRQTSM3D21hzVj5rIFAl1GT3wC
GwMFCQPCZwAFCwkIBwIGFQoJCAsCBBYCAwECHgECF4AACgkQ3D21hzVj5rJ3BQgA
nUusNKaf8SIWq1w4ZR6CKhZP+kz+5kOEBs3+qIXJV++9nbjs4jnqOnXJUUdpLS9E
HGYnd6XSeyqWmBAuFCcmld4VGIajYxgDbF11/ql5Gnbu26/jV7hnrBBK6Xn/6oV9
bBmLoT9xget5xFC6g2VE0EvneRqacUgMBCkvrMzcVnHmpkSOpjfXRAItnyK/bhia
8k/+5URO8v7Ao2+QO0zk8XzgGc5B8H/yItzDiKe7gpzdUyCviG8m/tkDUURzloY4
09wCmQWWzerbBHJT4RdpPqdTEtC6f4jTuT32zp5NtLpJ740WmSJly/8nAJ/0x3Vf
pVkzhsg9gVHe/JSFa6/hXbkBDQRdRk98AQgAyjXZ98VOgftRThuGuYxKhqahonLf
Ihu+NuNMFG6sGGzkm2T+1i4uKyM8T/kGdcTzTXE/SMHmrCMz94FNcQ77/OFLz5HY
8hjaz5Sun7iNmz5HGct8OrsP6gQeJ5ucqm3vDZmnwU/+J+wcTosv5mgWoBVob7jb
PBnoNVBQSVhD2ek4CDljn3PdReqYfe+ee8yn+6K1t9c1HHHMco3WpdgofUABd+7l
Q1LF8IpBRDvWgdMciAPaSthIqFT6R6xLQhXV8SUm0mr2/GXbYqIptjvy1JmUwNk3
jE3LOLYulZChRdvVg3Y+xgkVlMYLy3SBQ1EaTnUUsGYbhGQnOwDwVAlxgwARAQAB
iQE8BBgBCAAmFiEEG6B08RORVwbRQTSM3D21hzVj5rIFAl1GT3wCGwwFCQPCZwAA
CgkQ3D21hzVj5rLzvAf/QzfDOrhRz9AVLiAqus3Z/WfZY81sUiewNM+YdV9aODht
q4VE92SYHeR/b72+Fl62SRbDqxw7qG5FJGByuqo6nJjHEpnFzqB/pepTVDzlwvdn
JO46tmepFAChPBpeTTjTs2CF/BG0As0KxXQCpdFw4m8UdkZ7Olt1/LKnXrFmr1BA
jp2MvmAo38j2RyPTyXKWmJW+vC8DwmOGMoHCL6fM0TeaWey3rNxST7bbxPdRVc4Z
/26k450FEW5D+VInb9NuFYSoE2UXs6DgI1OWuuGvWePrtXHeQvuNbGdEdUwU14mf
msQ78G2MjX4AAYR5iNnQ/IWDBKbOWt3ajIoJuebArw==
=oHpZ
-----END PGP PUBLIC KEY BLOCK-----
```

@tab 提交&签名

```bash
# 提交 & 签名
$ git commit -S -m "..."

# 全局配置
$ git config --global commit.gpgsign true

# 查看
$ git log --show-signature

# 导入Github签名
$ curl https://github.com/web-flow.gpg | gpg --import
# 信任签名（用自己的密钥为其签名验证，需要输入密码）
$ gpg --sign-key 4AEE18F83AFDEB23
```

:::
