<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100vh;
}
.main {
  height: 100%;
}
body {
  margin: 0;
}
.el-container{
  height:100%
}
.el-button {
  padding: 8px 11px !important;
}
.margin0 {
  margin: 0 !important;
  z-index: 1;
}
.el-pagination{
  margin: 7px
}
</style>
