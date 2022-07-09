<template>
  <div v-loading="loading">
    <el-table :data="tableData" style="width: 100%">
      <el-table-column
        fixed
        prop="date"
        label="时间"
        width="150"
        align="center"
      >
      </el-table-column>
      <el-table-column prop="userCode" label="账户" align="center">
      </el-table-column>
      <el-table-column prop="info" label="事件" align="center">
      </el-table-column>
      <el-table-column prop="url" label="地址" align="center">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      loading: false,
      tableData: [],
    };
  },
  created() {
    this.loading = true;
    axios
      .get("/a/log/logs")
      .then((res) => {
        this.tableData = res.data;
        this.loading = false;
      })
      .catch(() => {
        this.loading = false;
      });
  },
};
</script>
