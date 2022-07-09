<template>
  <div>
    <el-empty description="请求错误！"></el-empty>
    <el-button type="primary" icon="el-icon-thumb" @click="back">点我返回</el-button>
    <el-button type="primary" icon="el-icon-user" @click="reLogin">重新登录</el-button>
  </div>
</template>

<script>
export default {
  methods:{
    back(){
      this.$router.push({
        path: "/",
      });
    },
    reLogin(){
      this.$router.push({
        path: "/login",
      });
    }
  }
};
</script>

