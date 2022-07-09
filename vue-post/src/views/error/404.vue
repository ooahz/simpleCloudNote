<template>
  <div>
    <el-empty description="404 NOT FOUND !"></el-empty>
    <el-button type="primary" icon="el-icon-thumb" @click="back">点我返回</el-button>
  </div>
</template>

<script>
export default {
  methods:{
    back(){
      this.$router.push({
        path: "/",
      });
    }
  }
};
</script>

