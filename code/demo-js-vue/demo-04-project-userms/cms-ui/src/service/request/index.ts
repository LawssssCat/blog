import axios from "axios";
import type { AxiosInstance } from "axios";
import type { XRequestConfig } from "./type.ts";

/**
 * 两个难点：
 * 1. 拦截器进行精细控制
 *   1. 全局拦截器
 *   2. 实例拦截器
 *   3. 单次请求拦截器
 * 2. 响应结果的类型处理（泛型）
 */

class XRequest {
  instance: AxiosInstance;

  // request 实例 => axios 实例
  constructor(config: XRequestConfig) {
    this.instance = axios.create(config);

    // 1. 全局拦截器：每个 axios 实例都添加拦截器
    this.instance.interceptors.request.use(
      (conf) => {
        // Lodding/token
        console.log("全局请求成功拦截");
        return conf;
      },
      (err) => {
        console.log("全局请求失败拦截");
        Promise.reject(err);
      },
    );
    this.instance.interceptors.response.use(
      (res) => {
        console.log("全局响应成功拦截");
        return res.data;
      },
      (err) => {
        console.log("全局响应失败拦截");
        return Promise.reject(err);
      },
    );

    // 2. 实例拦截器：针对指定的 XRequest 实例添加拦截器
    this.instance.interceptors.request.use(
      config.interceptors?.requestSuccessFn,
      config.interceptors?.requestFailureFn,
    );
    this.instance.interceptors.response.use(
      config.interceptors?.responseSuccessFn,
      config.interceptors?.responseFailureFn,
    );
  }

  // T = Data
  request<T = unknown>(config: XRequestConfig<T>) {
    // 3. 单次请求拦截器：单次请求的成功拦截处理
    if (config.interceptors?.requestSuccessFn) {
      // @ts-expect-error: ts(2345) 前置回调，不希望额外处理 config 值，需回调方法内自行判断 headers 是否符合预期
      config = config.interceptors.requestSuccessFn(config);
    }

    return new Promise<T>((resolve, reject) => {
      this.instance
        .request<unknown, T>(config)
        .then((res) => {
          // 单次响应的成功拦截处理
          if (config.interceptors?.responseSuccessFn) {
            res = config.interceptors.responseSuccessFn(res);
          }
          resolve(res);
        })
        .catch((err) => {
          if (config.interceptors?.responseFailureFn) {
            err = config.interceptors.responseFailureFn(err);
          }
          reject(err);
        });
    });
  }

  get<T = unknown>(config: XRequestConfig<T>) {
    return this.request({
      headers: {},
      ...config,
      method: "GET",
    });
  }
  post<T = unknown>(config: XRequestConfig<T>) {
    return this.request({ ...config, method: "POST" });
  }
  delete<T = unknown>(config: XRequestConfig<T>) {
    return this.request({ ...config, method: "DELETE" });
  }
  patch<T = unknown>(config: XRequestConfig<T>) {
    return this.request({ ...config, method: "PATCH" });
  }
}

export default XRequest;
