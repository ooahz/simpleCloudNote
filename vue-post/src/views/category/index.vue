<template>
  <el-container v-loading="loading">
    <el-header class="post-menu">
      <el-button type="primary" icon="el-icon-edit" @click="() => append(0)"
        >新增</el-button
      >
    </el-header>
    <el-divider class="margin0"><i class="el-icon-document"></i></el-divider>
    <el-main>
      <div class="block">
        <el-tree :data="categoryList" node-key="id" accordion>
          <span class="custom-tree-node" slot-scope="{ node, data }">
            <span>{{ node.label }}</span>
            <span>
              <el-button type="text" size="mini" @click="() => append(data)">
                增加
              </el-button>
              <el-button type="text" size="mini" @click="() => remove(data)">
                删除
              </el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </el-main>
  </el-container>
</template>

<script>
import axios from "axios";
import qs from "qs";

export default {
  data() {
    return {
      loading: false,
      // 添加的分类
      category: {
        id: 0,
        value: "",
      },
      // 分类列表
      categoryList: [],
    };
  },
  methods: {
    // 获取分类列表
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
    // 添加分类
    append(data) {
      var title;
      if (data === 0) {
        title = "增加新的分类";
        this.category.id = 0;
      } else {
        title = "为 " + data.label + " 添加子分类";
        this.category.id = data.id;
      }
      this.$prompt(title, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(({ value }) => {
          axios
            .post(
              "a/category/save",
              qs.stringify({
                id: this.category.id,
                value: value,
              })
            )
            .then((res) => {
              if (res.data.result === "true") {
                this.$message.success("添加成功！");
                this.getCategory();
              } else {
                this.$message.error("添加失败！");
              }
            });
        })
        .catch(() => {
          // 取消
        });
    },
    // 删除分类
    remove(data) {
      axios.get("a/category/delete/" + data.id).then((res) => {
        if (res.data.result === "true") {
          this.$message.success(res.data.msg);
          this.getCategory();
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },
  },
  created() {
    this.getCategory();
  },
};
</script>

<style>
.block {
  margin: 20px;
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>