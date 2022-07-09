<template>
  <div class="main" v-loading="loading">
    <el-container>
      <el-header class="post-menu">
        <div class="select">
          <el-cascader
            :options="categoryList"
            :props="{ checkStrictly: true }"
            clearable
            placeholder="选择分类"
            size="small"
            @change="categoriesChange"
          ></el-cascader>
        </div>
        <el-button type="primary" icon="el-icon-edit" @click="goEdit()"
          >新建</el-button
        >
      </el-header>
      <el-divider class="margin0"><i class="el-icon-document"></i></el-divider>
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="已发布" name="first">
          <List :postData="postData" :keyword="keyword" :goEdit="goEdit" :getPost="getPost"></List>
        </el-tab-pane>
        <el-tab-pane label="未发布" name="second">
          <List :postData="postData" :keyword="keyword" :goEdit="goEdit" :getPost="getPost"></List>
        </el-tab-pane>
        <el-tab-pane label="草稿箱" name="third">
          <el-empty description="描述文字"></el-empty>
        </el-tab-pane>
      </el-tabs>
      <el-pagination
        @current-change="handleCurrentChange"
        background
        layout="prev, pager, next"
        :total="total"
      >
      </el-pagination>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import List from "../../components/postList";
export default {
  components: {
    List,
  },
  data() {
    return {
      loading: false,
      // 列表搜索框默认值
      keyword: "",
      // 导航栏默认值
      activeName: "first",
      // 文章列表
      postData: [],
      // 分类列表
      categoryList: [],
      // 文章总数
      total: 1,
    };
  },
  methods: {
    // 选择分页
    handleCurrentChange(val) {
      this.getMsg(val);
    },
    // 选择分类
    categoriesChange(value) {
      console.log(value);
      this.$message.error("todo...");
    },
    // 点击导航
    handleClick() {
      if (this.activeName === "first") {
        this.getPost("2", 1);
      } else if (this.activeName === "second") {
        this.getPost("1", 1);
      }
    },
    // 获取文章
    getPost(state, page) {
      this.loading = true;
      axios
        .get("a/post/posts/" + state + "/" + page)
        .then((res) => {
          this.postData = res.data.post;
          this.total = res.data.total;
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    // 获取分类
    getCategory() {
      this.loading = true;
      axios
        .get("a/category/categories")
        .then((res) => {
          this.categoryList = res.data;
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },

    // 编辑/新建文章
    goEdit(id) {
      this.$router.push({
        name: "Edit",
        params: {
          id: id,
        },
      });
    },
  },
  created() {
    this.getCategory();
    this.getPost("2", 1);
  },
};
</script>

<style scoped>
.post-menu {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>