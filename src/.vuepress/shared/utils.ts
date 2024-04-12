/**
 * 返回标准化的链接
 *
 * e.g.
 * 前: https://aa//bb/cc///dd/
 * 后：https://aa/bb/cc/dd/
 *
 * @param url 非标准化链接
 */
export function normalizeUrl(url: String): String {
  url = url.replace(/([^:]\/)\/+/g, "$1");
  return url;
}
