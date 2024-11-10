import type {
  InternalAxiosRequestConfig,
  AxiosRequestConfig,
  AxiosResponse,
} from "axios";

// 针对 AxiosRequestConfig 配置进行扩展
export interface XInterceptors<T = AxiosResponse> {
  // 请求拦截
  requestSuccessFn?: (
    config: InternalAxiosRequestConfig,
  ) => InternalAxiosRequestConfig;
  requestFailureFn?: (err: unknown) => unknown;
  // 响应拦截
  responseSuccessFn?: (res: T) => T;
  responseFailureFn?: (err: unknown) => unknown;
}

export interface XRequestConfig<T = AxiosResponse> extends AxiosRequestConfig {
  interceptors?: XInterceptors<T>;
}
