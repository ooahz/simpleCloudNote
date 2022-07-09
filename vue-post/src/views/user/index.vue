<template>
  <el-table :data="userData" style="width: 100%">
    <el-table-column prop="userCode" label="账户" align="center">
    </el-table-column>
    <el-table-column prop="username" label="昵称" align="center">
    </el-table-column>
    <el-table-column
      prop="userStatus"
      label="状态"
      :formatter="formatterStatus"
      width="90"
      align="center"
    >
    </el-table-column>
    <el-table-column fixed="right" label="操作" width="120" align="center">
      <template slot-scope="scope">
        <el-button
          @click.native.prevent="deleteRow(scope.$index, userData)"
          type="text"
          size="small"
        >
          停用/启用
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import axios from "axios";
import qs from "qs";
export default {
  data() {
    return {
      userData: [],
    };
  },
  methods: {
    // 改变用户状态
    deleteRow(index, rows) {
      if (rows[index].userStatus === "0") {
        rows[index].userStatus = "1";
      } else {
        rows[index].userStatus = "0";
      }
      axios.post(
        "a/user/update",
        qs.stringify({
          user: JSON.stringify(rows[index]),
          operation: "stateChange"
        })
      )
    },
    // 格式化用户状态
    formatterStatus(row) {
      return row.userStatus == "1" ? "正常" : "已停用";
    },
  },
  created() {
    axios.get("/a/user/users").then((res) => {
      this.userData = res.data;
    });
  },
};
</script>