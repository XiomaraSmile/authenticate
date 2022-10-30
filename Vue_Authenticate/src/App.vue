<template>
  <AxiosRequestTest1 msg = "九 公共方法网络请求测试"/>
</template>

<script >
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://vuejs.org/api/sfc-script-setup.html#script-setup
import AxiosRequestTest from "./components/AxiosRequestTest.vue";

export default {
  name: 'App',
  components: {
    AxiosRequestTest1:AxiosRequestTest

  }
}
</script>


