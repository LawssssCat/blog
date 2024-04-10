export function normalizeUrl(url: String): String {
  url = url.replace(/([^:]\/)\/+/g, "$1");
  return url;
}
