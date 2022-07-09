<template>
  <div class="main">
    <el-menu
      default-active="/post"
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      :router="true"
      background-color="#272f4b"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <div class="el-menu-item" @click="ifCollapse">
        <i class="el-icon-menu"></i>
        <span slot="title">{{ isCollapseSpan }}</span>
      </div>
      <el-menu-item index="/panel" disabled>
        <i class="el-icon-s-data"></i>
        <span slot="title">数据</span>
      </el-menu-item>
      <el-menu-item index="/post">
        <i class="el-icon-document"></i>
        <span slot="title">文章</span>
      </el-menu-item>
      <el-menu-item index="/category">
        <i class="el-icon-copy-document"></i>
        <span slot="title">分类</span>
      </el-menu-item>
      <el-menu-item index="/message">
        <i class="el-icon-chat-line-square"></i>
        <span slot="title">留言</span>
      </el-menu-item>
      <el-menu-item index="/user">
        <i class="el-icon-user"></i>
        <span slot="title">用户</span>
      </el-menu-item>
      <el-menu-item index="/log">
        <i class="el-icon-s-order"></i>
        <span slot="title">日志</span>
      </el-menu-item>
      <el-menu-item index="/account">
        <i class="el-icon-s-custom"></i>
        <span slot="title">我的</span>
      </el-menu-item>
      <div class="el-menu-item" @click="exit">
        <i class="el-icon-switch-button"></i>
        <span slot="title">退出</span>
      </div>
    </el-menu>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      isCollapseSpan: "展开",
      isCollapse: true,
    };
  },
  methods: {
    // 左侧菜单展开
    ifCollapse() {
      this.isCollapse = !this.isCollapse;
      if (this.isCollapseSpan === "展开") {
        this.isCollapseSpan = "折叠";
      } else {
        this.isCollapseSpan = "展开";
      }
    },
    // 退出登录
    exit() {
      axios.get("api/logout").then(() => {
        // sessionStorage.removeItem("user");
        this.$store.commit("setUser", "{}")
        this.$router.push({
          path: "/login",
        });
      });
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-menu-item {
  color: #fff;
}
.el-menu-item:hover,
.el-menu-item:focus {
  background-color: #00000039;
}
.el-menu {
  height: 100vh;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
}
</style>
