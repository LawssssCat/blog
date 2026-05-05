---
title: JavaScript
---

## this

todo call/apply

## 节流&防抖

+ 防抖（debounce） —— 频繁事件，等最后一个事件再做，如：input输入、窗口滚动、窗口resize
+ 节流（throttle） —— 频繁事件，等固定时间后再做，如：页面自动刷新

## 文件

+ 文件上传
+ 文件下载
+ 分片传输

```javascript title="通过URL下载文件并重命名"
// TODO https://juejin.cn/post/7057771259832041486

// a 标签简单设置 href 方式
function downloadFile() {
  const link = document.createElement('a');
  link.style.display = 'none';
  // 设置下载地址
  link.setAttribute('href', file.sourceUrl);
  // 设置文件名
  link.setAttribute('download', file.fileName);
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
}

// a 标签使用 blob 数据类型方式
async function download(downloadUrl: string, downloadFileName: string ) {
  const blob = await getBlob(downloadUrl);
  saveAs(blob, downloadFileName);
}
function getBlob(url: string) {
  return new Promise<Blob>((resolve, reject) => {
    const xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.responseType = 'blob';
    xhr.onload = () => {
      if (xhr.status === 200) {
        resolve(xhr.response);
      } else {
        reject(new Error(`Request failed with status ${xhr.status}`));
      }
    };
    xhr.onerror = () => {
      reject(new Error('Request failed'));
    };
    xhr.send();
  });
}
function saveAs(blob: Blob, filename: string) {
  const link = document.createElement('a');
  const body = document.body;
  link.href = window.URL.createObjectURL(blob);
  link.download = filename;
  // hide the link
  link.style.display = 'none';
  body.appendChild(link);
  link.click();
  body.removeChild(link);
  window.URL.revokeObjectURL(link.href);
}
```

## 网络

+ JWT
+ HTTP2
+ WebSocket/SSE
+ 缓存协议
