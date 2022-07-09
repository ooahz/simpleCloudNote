<template>
  <div class="edit-main" v-loading="loading">
    <el-page-header @back="goBack" :content="title"> </el-page-header>
    <el-container>
      <el-divider class="margin0"></el-divider><br />
      <el-header>
        <el-date-picker
          v-model="postData.createDate"
          type="date"
          placeholder="发布日期"
          size="small"
        >
        </el-date-picker>
        <el-button-group>
          <el-button
            type="primary"
            icon="el-icon-caret-left"
            title="版本回退"
          ></el-button>
          <el-button
            type="primary"
            icon="el-icon-s-promotion"
            title="发布"
            @click="dialogFormVisible = true"
          ></el-button>
        </el-button-group>
        <el-dialog :visible.sync="dialogFormVisible">
          <el-form :model="postData">
            <el-form-item label="标签" :label-width="formLabelWidth">
              <el-select
                disabled
                v-model="postData.sTags"
                multiple
                filterable
                allow-create
                default-first-option
                size="medium"
                placeholder="请选择文章标签"
              >
                <el-option
                  v-for="item in postData.tags"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="分类" :label-width="formLabelWidth">
              <div class="select">
                <el-cascader
                  :options="categoryList"
                  :props="{ checkStrictly: true }"
                  clearable
                  :placeholder="placeholder"
                  size="small"
                  @change="selectCategory"
                ></el-cascader>
              </div>
            </el-form-item>
            <el-form-item label="权重" :label-width="formLabelWidth">
              <el-input-number
                size="small"
                v-model="postData.mark"
                :max="99"
                :min="-1"
              ></el-input-number>
            </el-form-item>
            <el-form-item
              label="标题"
              size="medium"
              :label-width="formLabelWidth"
              maxlength="30"
            >
              <el-input v-model="postData.title"></el-input>
            </el-form-item>
            <el-form-item
              label="路径"
              size="medium"
              :label-width="formLabelWidth"
            >
              <el-input
                v-model="postData.path"
                maxlength="30"
                placeholder="不填则默认生成"
              ></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="savePost">保 存</el-button>
            <el-button type="primary" @click="publishPost">发 布</el-button>
          </div>
        </el-dialog>

        <!-- <el-button-group>
  <el-button type="primary" icon="el-icon-caret-left">版本回退</el-button>
  <el-button type="primary">发布<i class="el-icon-s-promotion"></i></el-button>
</el-button-group> -->
      </el-header>
      <el-main>
        <mavon-editor v-model="postData.postContent.content" @save="save" />
      </el-main>
    </el-container>
  </div>
</template>

<script>
import axios from "axios";
export default {
  props: ["id"],
  data() {
    return {
      // 顶部标题
      title: "新建文章",
      loading: false,
      // 分类框默认值
      placeholder: "选择分类",
      // 分类列表
      categoryList: [],
      // 文章信息
      postData: {
        id: "",
        title: "",
        digest: "",
        categoryId: "",
        path: "",
        mark: "1",
        // sTags:[],
        // tags: [{ value: "HTML" }, { value: "CSS" }],
        createDate: "",
        postContent: {
          content: "",
        },
        state: "1",
      },
      // 编辑框宽度
      formLabelWidth: "50px",
      dialogFormVisible: false,
    };
  },
  methods: {
    // 返回
    goBack() {
      this.$router.back(-1);
    },
    // 选择分类
    selectCategory(value) {
      this.postData.categoryId = value[value.length - 1];
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
    // 编辑框保存
    save() {
      console.log(this.$router);
    },
    // 发布文章
    publishPost() {
      this.saveRequest("2", this.id);
    },
    // 保存文章
    savePost() {
      this.saveRequest("1", this.id);
    },

    saveRequest(state, id) {
      this.loading = false;
      if (this.postData.title === "") {
        this.$message.error("标题不能为空");
      } else if (this.postData.postContent.content === "") {
        this.$message.error("内容不能为空");
      } else if (this.postData.categoryId === "") {
        this.$message.error("请选择分类");
      } else {
        var url = "a/post/save";
        if (id != 0 && id != undefined) {
          this.postData.id = id;
          url = "a/post/update";
        }
        this.postData.state = state;
        axios
          .post(url, this.postData)
          .then((res) => {
            if (res.data.result === "true") {
              this.$message.success(res.data.msg);
              this.$router.replace("Post");
            } else {
              this.$message.error(res.data.msg);
            }
            this.loading = false;
          })
          .catch(() => {
            this.loading = false;
          });
      }
    },
  },
  created() {
    this.getCategory();
    if (this.id != undefined) {
      axios.get("a/post/get/" + this.id).then((res) => {
        this.postData = res.data;
        this.placeholder = res.data.category.label;
        this.title = "编辑文章";
      });
    }
  },
};
</script>

<style scoped>
.v-note-wrapper {
  margin: 0 3%;
  height: 100%;
  box-shadow: rgb(0 0 0 / 10%) 0px 2px 3px 0px !important;
}
.el-page-header {
  padding: 11px;
  margin: 3px;
}
.el-button-group {
  margin-right: calc(3% - 11px);
}
.edit-main {
  height: 100%;
}
.el-input {
  width: calc(30px + 50%);
}
.el-input-number {
  max-width: 100%;
}
</style>