import { BASE_URL, TIME_OUT } from "./config";
import XRequest from "./request";

const xRequest = new XRequest({
  baseURL: "http://example.org/aribnb/api",
  timeout: 8000,
});

export const xRequest2 = new XRequest({
  baseURL: BASE_URL,
  timeout: TIME_OUT,

  // 配置实例拦截
  interceptors: {
    requestSuccessFn: (config) => {
      console.log("example 请求成功拦截");
      return config;
    },
    requestFailureFn: (err) => {
      console.error("example 请求失败拦截");
      Promise.reject(err);
    },
    responseSuccessFn: (resp) => {
      console.log("example 响应成功拦截");
      return resp;
    },
    responseFailureFn: (err) => {
      console.error("example 响应失败拦截");
      return Promise.reject(err);
    },
  },
});

export { xRequest };
