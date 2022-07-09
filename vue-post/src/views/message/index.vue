<template>
  <el-container v-loading="loading">
    <el-aside width="200px">
      <el-form ref="sendMsg" :model="sendMsg">
        <el-form-item>
          <el-input
            class="msgInput"
            type="textarea"
            maxlength="300"
            v-model="sendMsg.msg"
            placeholder="说点什么吧"
          ></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="button" type="primary" @click="onSend"
            >确定</el-button
          >
        </el-form-item>
      </el-form>
    </el-aside>
    <el-container>
      <el-main>
        <div class="message" v-for="(item, index) in info" :key="index">
          <el-avatar :src="item.user.avatar"></el-avatar>
          <div class="info">
            <div id="topInfo">
              <h4>{{ item.user.username }}</h4>
              <h6>{{ item.createDate }}</h6>
            </div>
            <p>{{ item.message }}</p>
          </div>
        </div>
      </el-main>
      <el-footer>
        <el-pagination
          @current-change="handleCurrentChange"
          background
          layout="prev, pager, next"
          :total="total"
        >
        </el-pagination>
      </el-footer>
    </el-container>
  </el-container>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      loading: false,
      // 发送的消息
      sendMsg: {
        msg: "",
      },
      // 消息总数
      total: 1,
      // 消息列表
      info: [],
    };
  },
  methods: {
    // 获取消息列表
    getMsg(page) {
      this.loading = true;
      axios
        .get("/a/message/messages/" + page)
        .then((res) => {
          this.info = res.data.messages;
          this.total = res.data.total;
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    // 发送消息
    onSend() {
      this.loading = true;
      axios
        .post("/a/message/send", this.sendMsg.msg, {
          headers: { "Content-Type": "application/json" },
        })
        .then((res) => {
          if (res.data.result === "true") {
            this.sendMsg.msg = "";
            this.$message.success("发送成功！");
            this.getMsg(1);
          } else {
            this.$message.error("发送失败！");
          }
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },
    // 标签页改变
    handleCurrentChange(val) {
      this.getMsg(val);
    },
  },
  created() {
    this.getMsg(1);
  },
};
</script>

<style scoped>
.message {
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  padding: 12px;
  display: flex;
  margin: 22px;
}
.info {
  margin-left: 9px;
  width: 100%;
}
h4 {
  text-align: left;
  margin: 7px 20px 3px 0;
}
h6 {
  text-align: right;
  margin: 7px 20px 3px 0;
}
p {
  text-align: left;
}
.button {
  float: right;
}
form.el-form {
  float: left;
  margin: 20px;
}
</style>