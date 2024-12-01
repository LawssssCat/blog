import { defineStore, storeToRefs } from "pinia";

export const testStore = defineStore('test', {
  // state 相当于 data
  state: () => {
    return {
      count: 0,
      message: "Hello world"
    }
  },
  // actions 相当于 methods
  actions: {
    increment() {
      this.count ++;
    }
  },
  // getters 相当于 computed
  getters: {
    testCount(state) {
      return state.count * 2;
    }
  }
})