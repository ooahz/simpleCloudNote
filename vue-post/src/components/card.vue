<template>
  <div>
    <el-card class="box-card">
      <div v-for="o in items" :key="o" class="text item">
        {{ o }}
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: {
        name: "",
        exit: "退出",
      },
    };
  },
};
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 200px;
  z-index: 999;
  display: none;
}
</style>