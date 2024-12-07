import { defineStore } from 'pinia';
import { accountLoginRequest, helloRequest } from '@/service/api/';
import { localCache } from '@/utils/cache';
import router from '@/router';
import { LOGIN_TOKEN } from '@/global/constants';

interface ILoginState {
  token: string;
  userInfo: {
    id?;
    role?;
  } & any;
  userMenus: any;
}

const useLoginStore = defineStore('login', {
  state: (): ILoginState => ({
    token: localCache.getCache(LOGIN_TOKEN) ?? '',
    userInfo: {},
    userMenus: {},
  }),
  actions: {
    async loginAccountAction(username: string, password: string) {
      // 1. 账号登录，获取token等信息
      const loginResult = await accountLoginRequest(username, password);
      this.token = loginResult.token;

      // 2. 进行本地缓存
      localCache.setCache(LOGIN_TOKEN, this.token);

      // 瞎搞
      const hello = await helloRequest();
      console.log(hello);

      // 3. 获取登录用户的详细信息（role信息）（{{baseURL}}/users/1）
      // 4. 根据角色请求用户的权限（menus菜单）（{{baseURL}}/role/4/menu）

      // 3. 首页
      router.push('/main');
    },
  },
});

export default useLoginStore;
