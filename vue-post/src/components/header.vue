<template>
  <div class="profile">
    <router-link class="a-item" active-class="active" to="/account">
      <el-avatar shape="circle" :size="36" :src="$store.state.user.avatar"></el-avatar>
    </router-link>
  </div>
  
</template>

<script>
export default {
  data() {
    return {
      url: "",
    };
  },
};
</script>

<style scoped>
.profile {
  height: 100%;
}
</style>