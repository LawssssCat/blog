declare module "*.vue" { // 解决：找不到模块“../views/HomeView.vue”或其相应的类型声明。ts(2307)
  import Vue from "@/vue";
  export default Vue;
}