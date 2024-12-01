import { defineStore } from 'pinia';
import { accountLoginRequest } from '@/service/api/';
import { localCache } from '@/utils/cache';
import router from '@/router';
import { LOGIN_TOKEN } from '@/global/constants';

const useLoginStore = defineStore('login', {
  state: () => ({
    token: localCache.getCache(LOGIN_TOKEN) ?? '',
  }),
  actions: {
    async loginAccountAction(username: string, password: string) {
      // 1. 账号登录，获取token等信息
      const loginResult = await accountLoginRequest(username, password);
      this.token = loginResult.token;

      // 2. 进行本地缓存
      localCache.setCache(LOGIN_TOKEN, this.token);

      // 3. 首页
      router.push('/main');
    },
  },
});

export default useLoginStore;
