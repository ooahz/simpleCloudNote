<template>
  <el-main>
    <el-table
      :data="
        postData.filter(
          (data) =>
            !keyword || data.name.toLowerCase().includes(keyword.toLowerCase())
        )
      "
      style="width: 100%"
    >
      <el-table-column
        prop="title"
        column-key="title"
        :resizable="true"
        :show-overflow-tooltip="true"
        fixed
        min-width="150"
        ><template slot="header">
          <el-input
            v-model="keyword"
            size="small"
            placeholder="输入关键字搜索"
          />
        </template>
      </el-table-column>
      <el-table-column
        prop="category"
        label="分类"
        :resizable="true"
        :show-overflow-tooltip="true"
      >
        <template slot-scope="scope">
          <el-tag disable-transitions>{{ scope.row.category.label }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createDate"
        label="日期"
        sortable
        width="100"
        column-key="createDate"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="updateDate"
        label="更新"
        sortable
        width="100"
        column-key="updateDate"
        align="center"
      >
      </el-table-column>
      <el-table-column
        prop="mark"
        label="权限"
        sortable
        width="90"
        column-key="mark"
        align="center"
      >
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-main>
</template>

<script>
import axios from "axios";
export default {
  // 接收父控件数据和方法
  props: ["postData", "keyword", "goEdit", "getPost"],
  methods: {
    // 编辑文章
    handleEdit(row) {
      this.goEdit(row.id);
    },
    // 删除文章
    handleDelete(row) {
      row.state = "0";
      axios.post("a/post/update", row).then((res) => {
        if (res.data.result === "true") {
          this.$message.success(res.data.msg);
          this.getPost("1", 1);
        } else {
          this.$message.error(res.data.msg);
        }
      });
    },
  },
};
</script>

