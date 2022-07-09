<template>
  <div class="main">
    <el-container>
      <el-aside><Menu></Menu></el-aside>
      <el-container>
        <el-header class="top"><Header></Header></el-header>

        <el-divider class="margin0"></el-divider>
        <el-main>
          <Card></Card>
          <keep-alive><router-view></router-view></keep-alive>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import Header from "../components/header";
import Menu from "../components/menu";
import Card from "../components/card";

export default {
  components: {
    Header,
    Menu,
    Card,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
.el-header,
.el-footer {
  background-color: #ffffff;
  color: #333;
  text-align: center;
  line-height: 50px;
  height: 50px !important;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-end;
  padding: 5px 11px !important;
}

.el-aside {
  color: #333;
  text-align: center;
  width: auto !important;
}

.el-main {
  color: #333;
  text-align: center;
  padding: 0 9px!important;
}
</style>