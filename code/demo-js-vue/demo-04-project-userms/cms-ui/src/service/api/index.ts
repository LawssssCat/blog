import { xRequest } from '..';

interface V1Response {
  code: number;
  msg: string;
}

export function accountLoginRequest(username: string, password: string) {
  return xRequest.post<V1Response & { token: string }>({
    url: '/login',
    data: {
      username: username,
      password: password,
    },
  });
}
