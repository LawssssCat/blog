<script setup>
import SellerCard from '@/components/cards/SellerCard.vue'
</script>

<template>
  <div class="screen-container">
    <!-- 头部 -->
    <header class="screen-header">
      <ul class="title-left">
        <li>
          <img src="@/assets/logo.svg" alt="" class="logo" />
        </li>
      </ul>
      <span class="title">Xxxxxxxxxxxxxxxxxxxxxxxxxxx大屏</span>
      <ul class="title-right">
        <li>
          <!-- <img src="" alt="" class="qiehuan" /> -->
          <span class="datetime"> 2049-01-01 00:00:00 </span>
        </li>
        <li>租户</li>
        <li>应用</li>
        <li>环境</li>
        <li>搜索</li>
      </ul>
    </header>
    <!-- 工具箱 -->
    <div class="screen-second-header" v-if="false">
      <div class="screen-group">
        <div>屏幕1</div>
        <div>屏幕2</div>
        <div>屏幕3</div>
        <div>屏幕4</div>
      </div>
      <div class="screen-toolkit">
        <div class="screen-action">
          <img src="@/assets/logo.svg" alt="" srcset="" />
        </div>
      </div>
    </div>
    <!-- 主体 -->
    <div class="screen-body">
      <section class="screen-card"><SellerCard></SellerCard></section>
      <section class="screen-card"><SellerCard></SellerCard></section>
      <section class="screen-card"><SellerCard></SellerCard></section>
      <section class="screen-card"><SellerCard></SellerCard></section>
      <section class="screen-card"><SellerCard></SellerCard></section>
      <section class="screen-card"><SellerCard></SellerCard></section>
      <section class="screen-card"><SellerCard></SellerCard></section>
    </div>
  </div>
</template>

<style lang="scss" scoped>
ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}
.screen-container {
  width: 100%;
  height: 100%;
  padding: 0 20px;
  box-sizing: border-box;

  display: flex;
  flex-direction: column;

  :nth-child(1n) {
    flex-shrink: 0;
  }
}

.screen-header {
  height: 64px;
  font-size: 20px;
  background: url(@/assets/images/header_border_dark.png) no-repeat;
  background-position: center;

  display: flex;
  align-items: center;
  justify-content: space-between;

  :nth-child(1n) {
    flex: 1;
  }
  .title-left {
    display: flex;
    align-items: center;
    img {
      width: 50px;
      margin: auto 0;
    }
  }
  .title {
    text-align: center;
    font-size: 30px;
    font-weight: 600;
    /* 宽度 */
    max-width: 14em;
    text-wrap: nowrap;
    overflow: hidden; //超出的隐藏
    text-overflow: ellipsis; //显示省略符号来代表被修剪的文本。
  }
  .title-right {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    flex-basis: 3em;
    li {
      // flex: 1;

      display: flex;
      justify-content: center;
      // flex-shrink: 0;
      font-size: 13px;
      margin: 0 auto;
      text-wrap: nowrap;
    }
  }
}

.screen-body {
  flex-grow: 1;
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  .screen-card {
    border: 2px solid #fafafa;
    border-radius: 10px;
    margin: 1px;
  }
}
</style>
